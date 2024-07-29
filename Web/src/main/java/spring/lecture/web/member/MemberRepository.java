package spring.lecture.web.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Optional<Member> findByUsername(String username);
}
