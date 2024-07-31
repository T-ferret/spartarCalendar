package spring.lecture.web.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByGroupName(String groupName);
    @Query("select g from Group g join g.tags t where t.name in :tags")
    List<Group> findByTags(@Param("tags") List<String> tags);
}
