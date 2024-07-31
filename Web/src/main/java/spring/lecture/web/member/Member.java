package spring.lecture.web.member;

import jakarta.persistence.*;
import lombok.Data;
import spring.lecture.web.event.Event;
import spring.lecture.web.group.Group;
import spring.lecture.web.subgroup.Subgroup;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
//     spring boot는 username을 로그인/회원가입의 id로 받음
    private String username;
    private String password;
    private String role;
    private String schoolName;
    private String major;
    private int studentNumber;
//     위 내용 중 role을 제외한 변수가 회원가입 폼에서 받을 데이터
//     html 파일에서 <input type="해당 변수타입" name="해당 변수이름" 그 외 다른 태그> 방식으로 선언할 것.
//    <from> 태그 내부에 반드시 csrf 토큰 선언할 것. 토큰 없으면 에러 발생. 토큰은 아래와 같은 방식으로 선언.
//    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

    //    유저 생성시각 및 업데이트 시각 필드
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Subgroup> subgroups = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events = new HashSet<>();
}
