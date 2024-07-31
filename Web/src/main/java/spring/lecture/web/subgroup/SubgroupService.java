package spring.lecture.web.subgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.lecture.web.event.EventService;
import spring.lecture.web.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubgroupService {
    private final SubgroupRepository subgroupRepository;
    private final EventService eventService;

//    소모임 생성
    public void save(Subgroup subgroup) {
        subgroup.setCreateAt(LocalDateTime.now());
        subgroupRepository.save(subgroup);
    }

//    소모임 업데이트
    public void update(Subgroup subgroup) {
        subgroup.setUpdateAt(LocalDateTime.now());
        subgroupRepository.save(subgroup);
    }

//    소모임 삭제
    public void delete(Integer subgroupId) {
        subgroupRepository.deleteById(subgroupId);
    }

//    특정 소모임을 찾아 return
    public Subgroup findById(int id) {
        return subgroupRepository.findById(id).orElseThrow(() -> new RuntimeException("Subgroup not found with id: " + id));
    }

//    모든 소모임 리스트 return
    public List<Subgroup> findAll() {
        return subgroupRepository.findAll();
    }

//    특정 유저가 소속된 소모임 리스트 return
    public List<Subgroup> findByMember(Integer memberId) {
        return subgroupRepository.findByMemberId(memberId);
    }

//    특정 그룹에 소속된 소모임 리스트 return
    public List<Subgroup> findByGroup(Integer groupId) {
        return subgroupRepository.findByGroupId(groupId);
    }
}
