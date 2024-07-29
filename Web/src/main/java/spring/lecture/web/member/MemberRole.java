package spring.lecture.web.member;

import lombok.Getter;

@Getter
public enum MemberRole {
    ROLE_USER("user"), ROLE_ADMIN("admin");

    private final String value;

    private MemberRole(String value) {
        this.value = value;
    }
}
