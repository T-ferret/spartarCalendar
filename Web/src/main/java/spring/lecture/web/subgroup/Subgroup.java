package spring.lecture.web.subgroup;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.event.Event;
import spring.lecture.web.group.Group;
import spring.lecture.web.member.Member;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Subgroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    
//    소모임 생성시각 및 업데이트 시각 필드
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creater_id")
    private Member creater;

    @ManyToMany
    @JoinTable(
            name = "subgroup_members",
            joinColumns = @JoinColumn(name = "subgroup_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();

//    @OneToMany(mappedBy = "subgroup", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Event> events = new HashSet<>();
}
