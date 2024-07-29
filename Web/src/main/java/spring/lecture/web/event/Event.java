package spring.lecture.web.event;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.member.Member;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "event_shard_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> shardMembers = new HashSet<>();
}
