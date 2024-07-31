package spring.lecture.web.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByMemberId(Integer memberId);
    List<Event> findBySubgroupsId(Integer subgroupId);
    @Query("select e from Event e join e.subgroups s where s.id = :subgroupId and e.startTime >= :start and e.endTime <= :end")
    List<Event> findBySubgroupIdAndDateTimeRange(@Param("subgroupId") Integer subgroupId,
                                                 @Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);
    @Query("select e from Event e join e.member s where s.id = :memberId and e.startTime >= :start and e.endTime <= :end")
    List<Event> findByMemberIdAndDateTimeRange(@Param("memberId") Integer memberId,
                                                 @Param("start") LocalDateTime start,
                                                 @Param("end") LocalDateTime end);
}
