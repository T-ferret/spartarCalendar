package spring.lecture.web.group;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.member.Member;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    //    그룹 생성시각 및 업데이트 시각 필드
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

//    그룹에 소속된 유저 리스트 필드
    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();

//    그룹 태그 리스트 필드
    @ManyToMany
    @JoinTable(
            name = "group_tags",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
