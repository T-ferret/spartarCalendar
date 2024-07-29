package spring.lecture.web.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByMemberId(Integer memberId);
    List<Event> findBySharedMembersId(Integer sharedMembersId);
}
