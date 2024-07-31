package spring.lecture.web.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.lecture.web.member.Member;
import spring.lecture.web.subgroup.Subgroup;
import spring.lecture.web.subgroup.SubgroupService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final SubgroupService subgroupService;

//    일정 생성
    public void save(Event event) {
        event.setCreateAt(LocalDateTime.now());
//        db에 저장
        eventRepository.save(event);
    }

//    일정 업데이트
    public void update(Event event) {
        event.setUpdateAt(LocalDateTime.now());
//        db에 저장
        eventRepository.save(event);
    }

//    일정 삭제
    public void delete(Integer id) {
        eventRepository.deleteById(id);
    }

    public Set<LocalDateTime> getEventSlots(int memberId) {
        // 유저의 모든 일정 가져오기
        List<Event> personalEvents = eventRepository.findByMemberId(memberId);

        // 각 이벤트의 시작 시간과 종료 시간을 기반으로 슬롯 생성
        Set<LocalDateTime> allSlots = new HashSet<>();
        for (Event event : personalEvents) {
            Set<LocalDateTime> eventSlots = generateTimeSlots(event.getStartTime(), event.getEndTime(), event.getIntervalMinutes());
            allSlots.addAll(eventSlots);
        }

        return allSlots;
    }

//    특정 일정 찾기
    public Event findById(Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
    }

//    특정 유저의 일정 리스트 return
    public List<Event> findByMemberId(Integer memberId) {
        return eventRepository.findByMemberId(memberId);
    }

//    특정 소모임 id를 기준으로 event 검색해 return
    public List<Event> findBySubgroupId(Integer subgroupId) {
        return eventRepository.findBySubgroupsId(subgroupId);
    }

//    이벤트에 소모임 추가(유저가 소모임에 일정 공유 시 함수 실행)
    @Transactional
    public void addSubgroupsToEvent(Integer eventId, Integer subgroupId) {
        Event event = findById(eventId);
        Subgroup subgroup = subgroupService.findById(subgroupId);

        Set<Subgroup> subgroups = event.getSubgroups();
        if (!subgroups.contains(subgroup)) {
            subgroups.add(subgroup);
            eventRepository.save(event);
        }
    }

////    일정 시작 시간과 종료 시간을 찾아 return
//    public List<LocalDateTime[]> findFreeTiemsForSubgroup(Integer subgroupId) {
////        소모임에 속한 이벤트를 가져옴
//        List<Event> events = findBySubgroupId(subgroupId);
//
////        이벤트 리스트에서 시작시각과 종료시각이 저장된 LocalDateTime 배열 생성
//        List<LocalDateTime[]> eventTimes = events.stream()
//                .map(event -> new LocalDateTime[]{event.getStartTime(), event.getEndTime()})
//                .toList();
//
////        비어있는 시간대 찾기
//        return findFreeTimes(eventTimes);
//    }

    //    타임 슬롯 생성 함수
    private Set<LocalDateTime> generateTimeSlots(LocalDateTime start, LocalDateTime end, int intervalMinutes) {
        Set<LocalDateTime> slots = new LinkedHashSet<>();
        while (start.isBefore(end)) {
            slots.add(start);
            start = start.plusMinutes(intervalMinutes);
        }
        return slots;
    }

//    특정 유저의 모든 일정을 슬롯으로 return
    public List<LocalDateTime[]> getAllUserScheduleTimeSlots(int userId, int intervalMinutes) {
        List<Event> events = eventRepository.findByMemberId(userId);
        List<LocalDateTime[]> allTimeSlots = new ArrayList<>();
        for (Event event : events) {
            Set<LocalDateTime> slots = generateTimeSlots(event.getStartTime(), event.getEndTime(), intervalMinutes);
            for (LocalDateTime slot : slots) {
                allTimeSlots.add(new LocalDateTime[]{slot, slot.plusMinutes(intervalMinutes)});
            }
        }
        return allTimeSlots;
    }

//    특정 유저의 해당 날짜 일정을 슬롯으로 return
    public List<LocalDateTime[]> getUserScheduleTimeSlots(int userId, LocalDate date, int intervalMinutes) {
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

//    해당 유저의 모든 이벤트를 해당 날짜에 대해 조회
    List<Event> events = eventRepository.findByMemberIdAndDateTimeRange(userId, startOfDay, endOfDay);

    List<LocalDateTime[]> eventTimeSlots = new ArrayList<>();
    for (Event event : events) {
        LocalDateTime current = event.getStartTime();
        while (!current.isAfter(event.getEndTime()) && !current.isAfter(endOfDay)) {
            eventTimeSlots.add(new LocalDateTime[]{current, current.plusMinutes(intervalMinutes)});
            current = current.plusMinutes(intervalMinutes);
        }
    }

    return eventTimeSlots;
}

//    특정 날짜에 해당하는 소모임 내 일정 조회
    public DaySchedule getSubgroupScheduleForDay(int subgroupId, LocalDate date, int intervalMinutes) {
//        특정 날짜의 시작 및 종료 시각 return
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

//        특정 소모임 내 특정 날짜에 존재하는 일정 리스트 return
        List<Event> events = eventRepository.findBySubgroupIdAndDateTimeRange(subgroupId, startOfDay, endOfDay);
        List<LocalDateTime[]> eventTimes = events.stream()
                .map(e -> new LocalDateTime[]{e.getStartTime(), e.getEndTime()})
                .toList();

//        위에서 찾은 일정 리스트를 토대로, 특정 날짜의 비어 있는 시간대 return
        List<LocalDateTime[]> freeTimes = findFreeTimes(eventTimes);

//        타임 슬롯 생성
        Set<LocalDateTime> allSlots = generateTimeSlots(startOfDay, endOfDay, intervalMinutes);

//        사용중인 시간대 제거해 실제 사용 가능한 타임 슬롯 계산
        freeTimes.forEach(range -> {
            LocalDateTime start = range[0];
            while (start.isBefore(range[1])) {
                if (allSlots.contains(start)) {
                    allSlots.remove(start);
                }
                start = start.plusMinutes(intervalMinutes);
            }
        });
//        events.forEach(event -> {
//            LocalDateTime start = event.getStartTime();
//            LocalDateTime end = event.getEndTime();
//            while (start.isBefore(end) && allSlots.contains(start)) {
//                allSlots.remove(start);
//                start = start.plusMinutes(10);
//            }
//        });

//        소모임 내 유저 리스트 return
        Map<Integer, List<LocalDateTime[]>> userSchedules = new HashMap<>();
        Set<Member> members = subgroupService.findById(subgroupId).getMembers();

//        유저 리스트를 토대로, 특정 날짜의 각각 유저별 일정을 슬롯으로 return
        for (Member member : members) {
            List<Event> userEvents = eventRepository.findByMemberIdAndDateTimeRange(member.getId(), startOfDay, endOfDay);
            List<LocalDateTime[]> timeslots = new ArrayList<>();
            for (Event event : userEvents) {
                LocalDateTime currentTime = event.getStartTime();
                while (currentTime.isBefore(event.getEndTime()) && !currentTime.isAfter(endOfDay)) {
                    timeslots.add(new LocalDateTime[]{currentTime, currentTime.plusMinutes(intervalMinutes)});
                    currentTime = currentTime.plusMinutes(intervalMinutes);
                }
            }
//            List<LocalDateTime[]> userEventTimes = userEvents.stream()
//                    .map(e -> new LocalDateTime[]{e.getStartTime(), e.getEndTime()})
//                    .toList();
//            userSchedules.put(member.getId(), userEventTimes);
            userSchedules.put(member.getId(), timeslots);
        }

//        DaySchedule에 비어 있는 시간대와 각 유저 별 일정 저장 및 return
        DaySchedule daySchedule = new DaySchedule();
        daySchedule.setFreeTimes(freeTimes.stream()
                .map(ft -> new LocalDateTime[]{ft[0], ft[0].plusMinutes(intervalMinutes)})
                .toList());
        daySchedule.setUserSchedules(userSchedules);

        return daySchedule;
    }

//    비어 있는 시간대 찾는 알고리즘
    private List<LocalDateTime[]> findFreeTimes(List<LocalDateTime[]> events) {
        if (events.isEmpty()) {
            return Collections.emptyList();
        }

//        이벤트를 시작 시간에 따라 정렬
        events.sort((a, b) -> a[0].compareTo(b[0]));

        List<LocalDateTime[]> mergedTimes = new ArrayList<>();
        LocalDateTime currentStart = events.get(0)[0];
        LocalDateTime currentEnd = events.get(0)[1];

//        겹치거나 시작시간과 종료시간이 같은 이벤트를 병합
        for (LocalDateTime[] time : events) {
            if (time[0].isBefore(currentEnd) || time[0].equals(currentEnd)) {
                if (time[1].isBefore(currentEnd)) {
                    currentEnd = time[1];
                }
            } else {
                mergedTimes.add(new LocalDateTime[]{currentStart, currentEnd});
                currentStart = time[0];
                currentEnd = time[1];
            }
        }
        mergedTimes.add(new LocalDateTime[]{currentStart, currentEnd});

//        병합된 시간대에 따라, 병합된 시간대 사이 빈 시간대를 return
        List<LocalDateTime[]> freeTimes = new ArrayList<>();
        for (int i = 0; i < mergedTimes.size() - 1; i++) {
            LocalDateTime endCurrent = mergedTimes.get(i)[1];
            LocalDateTime startNext = mergedTimes.get(i + 1)[0];
            if (endCurrent.isBefore(startNext)) {
                freeTimes.add(new LocalDateTime[]{endCurrent, startNext});
            }
        }
        return freeTimes;
    }
}
