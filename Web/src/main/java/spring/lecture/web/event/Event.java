package spring.lecture.web.event;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.member.Member;
import spring.lecture.web.subgroup.Subgroup;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    유저가 이벤트 생성 시 입력하는 필드
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String color;
    private Integer intervalMinutes;

//    이벤트 생성시각 및 업데이트 시각 필드
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

//    반복 규칙을 위한 필드 추가
    private String recurrenceRule;

//    이벤트를 생성한 유저 정보 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

//    이벤트가 소속된 소모임 정보 필드
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "event_subgroups",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "subgroup_id")
    )
    private Set<Subgroup> subgroups = new HashSet<>();
}
