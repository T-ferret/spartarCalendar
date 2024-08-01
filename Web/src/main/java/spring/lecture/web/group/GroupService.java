package spring.lecture.web.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.lecture.web.member.MemberService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final TagRepository tagRepository;
    private final MemberService memberService;

    //    그룹 생성
    public void save(Group group) {
        group.setCreateAt(LocalDateTime.now());
        groupRepository.save(group);
    }

//    그룹 업데이트
    public void update(Group group) {
        group.setUpdateAt(LocalDateTime.now());
        groupRepository.save(group);
    }

//    그룹 삭제
    public void delete(Group group) {
        groupRepository.delete(group);
    }

//    특정 그룹 return
    public Group findById(Integer id) {
        return groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
    }

//    모든 그룹 리스트 return
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

//    특정 유저가 소속된 그룹 리스트 return
    public List<Group> getGroupsByMemberId() {
        Integer memberId = memberService.getCurrentMember().getId();
        return groupRepository.findByMemberId(memberId);
    }

//    이름으로 검색 후 그룹 리스트 return
    public List<Group> searchGroupsByName(String name) {
        return groupRepository.findByGroupName(name);
    }

//    태그로 검색 후 그룹 리스트 return
    public List<Group> searchGroupsByTags(Set<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(tagRepository::findByName)
                .toList();
        return groupRepository.findByTags(tags.stream().map(Tag::getName).toList());
    }
}
