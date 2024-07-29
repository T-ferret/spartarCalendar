package spring.lecture.web.member;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.event.Event;
import spring.lecture.web.group.Group;
import spring.lecture.web.subgroup.Subgroup;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    // spring boot는 username을 로그인/회원가입의 id로 받음
    private String username;
    private String password;
    private String role;
    private String schoolName;
    private String major;
    private int studentNumber;

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Subgroup> subgroups = new HashSet<>();

    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();
}
