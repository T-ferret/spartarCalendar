package spring.lecture.web.group;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.lecture.web.member.MemberService;
import spring.lecture.web.subgroup.SubgroupService;

@PreAuthorize("hasRole('USER')")
@RequestMapping("/group")
@RequiredArgsConstructor
@Controller
public class GroupController {
    private final GroupService groupService;
    private final SubgroupService subgroupService;
    private final MemberService memberService;

    @GetMapping("/{groupId}/detail")
    public String group(@PathVariable Integer groupId, Model model) {
        model.addAttribute("group", groupService.findById(groupId));
        model.addAttribute("subgroups", subgroupService.findByGroupId(groupId));
        return "detail";
    }

    @GetMapping("/{groupId}/memberlist")
    public String memberlist(Model model, @PathVariable("groupId") Integer groupId) {
        model.addAttribute("members", memberService.findByGroupId(groupId));
        return "memberlist";
    }
}
