package spring.lecture.web.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);
    List<Member> findByGroupId(int groupId);
    List<Member> findBySubgroupId(int subgroupId);
}
