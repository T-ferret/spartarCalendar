package spring.lecture.web.group;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.member.Member;

@Data
@Entity
public class GroupApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    예시: "신청", "승인", "거절"
    private String status;
}
