package spring.lecture.web.subgroup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.lecture.web.member.MemberService;

@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
@RequestMapping("/subgroup")
@Controller
public class SubgroupController {
    private final SubgroupService subgroupService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String create() {
        return "subgroup/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Subgroup subgroup) {
        subgroupService.save(subgroup);
        return "redirect:/subgroup/list";
    }

    @GetMapping("/{subgroupId}/detail")
    public String detail(@PathVariable int subgroupId, Model model) {
        model.addAttribute("subgroup", subgroupService.findById(subgroupId));
        model.addAttribute("members", memberService.findBySubgroupId(subgroupId));
        return "subgroup/detail";
    }

    @GetMapping("/{subgroupId}/signup")
    public String signup(@PathVariable int subgroupId, Model model) {
        subgroupService.signup(subgroupId);
        return "redirect:/user/list";
    }
}
