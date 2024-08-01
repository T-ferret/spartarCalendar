package spring.lecture.web.subgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.lecture.web.event.EventService;
import spring.lecture.web.member.Member;
import spring.lecture.web.member.MemberService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubgroupService {
    private final SubgroupRepository subgroupRepository;
    private final EventService eventService;
    private final MemberService memberService;

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

    @Transactional
    public void signup(int subgroupId) {
        Subgroup subgroup = findById(subgroupId);
        Member currentMember = memberService.getCurrentMember();

//        중복 멤버 추가 방지
        if (!subgroup.getMembers().contains(currentMember)) {
            subgroup.getMembers().add(currentMember);
            subgroupRepository.save(subgroup);
        }
    }

//    모든 소모임 리스트 return
    public List<Subgroup> findAll() {
        return subgroupRepository.findAll();
    }

//    특정 유저가 소속된 소모임 리스트 return
    public List<Subgroup> findByMemberId() {
        Integer memberId = memberService.getCurrentMember().getId();
        return subgroupRepository.findByMemberId(memberId);
    }

//    특정 그룹에 소속된 소모임 리스트 return
    public List<Subgroup> findByGroupId(Integer groupId) {
        return subgroupRepository.findByGroupId(groupId);
    }
}
