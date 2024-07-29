package spring.lecture.web.member;

import lombok.Getter;

@Getter
public enum MemberRole {
//    유저 권한을 위한 role 선언 클래스.
    ROLE_USER("user"), ROLE_ADMIN("admin");

    private final String value;

    private MemberRole(String value) {
        this.value = value;
    }
}
