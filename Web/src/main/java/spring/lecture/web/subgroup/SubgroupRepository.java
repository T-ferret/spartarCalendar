package spring.lecture.web.subgroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubgroupRepository extends JpaRepository<Subgroup, Integer> {
    List<Subgroup> findByGroupId(Integer groupId);
}
