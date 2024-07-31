package spring.lecture.web.subgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.lecture.web.member.Member;

import java.util.List;

public interface SubgroupRepository extends JpaRepository<Subgroup, Integer> {
    List<Subgroup> findByGroupId(Integer groupId);
    List<Subgroup> findByMemberId(Integer memberId);
}
