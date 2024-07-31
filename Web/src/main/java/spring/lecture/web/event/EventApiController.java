package spring.lecture.web.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/events")
public class EventApiController {
    private final EventService eventService;

    @GetMapping("/user/{memberId}/all-timeslots")
    public ResponseEntity<List<LocalDateTime[]>> getAllUserTimeslots(
            @PathVariable Integer memberId,
            @RequestParam(defaultValue = "10") int intervalMinutes) {
        List<LocalDateTime[]> timeSlots = eventService.getAllUserScheduleTimeSlots(memberId, intervalMinutes);
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/subgroup/{subgroupId}/date/{date}")
    public ResponseEntity<DaySchedule> getFreeTimesForSubgroupOnDate(
            @PathVariable int subgroupId,
            @RequestParam(defaultValue = "10") int intervalMinutes,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        eventService를 통해 해당 날짜의 소모임 내 유저 별 일정 및 빈 시간대를 UserSchedule 객체로 return
        DaySchedule daySchedule = eventService.getSubgroupScheduleForDay(subgroupId, date, intervalMinutes);
        return ResponseEntity.ok(daySchedule);
    }
}
