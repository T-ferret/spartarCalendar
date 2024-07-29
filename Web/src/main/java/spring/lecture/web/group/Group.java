package spring.lecture.web.group;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.member.Member;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();
}
