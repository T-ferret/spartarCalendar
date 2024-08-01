package spring.lecture.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.lecture.web.event.EventService;
import spring.lecture.web.group.GroupService;
import spring.lecture.web.subgroup.SubgroupService;

import java.time.LocalDate;
import java.time.YearMonth;

@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final GroupService groupService;
    private final SubgroupService subgroupService;
    private final MemberService memberService;
    private final EventService eventService;

    @GetMapping("/list")
    public String groupAndSubgroupList(Model model) {
        model.addAttribute("groups", groupService.getGroupsByMemberId());
        model.addAttribute("subgroups", subgroupService.findByMemberId());
        return "groupAndSubgroupList";
    }

    @GetMapping("/subgroup/{subgroupId}")
    public String subgroup(Model model, @PathVariable("subgroupId") int subgroupId,
                           @RequestParam("currentMonth")YearMonth currentMonth,
                           @RequestParam(value = "intervalMonth", defaultValue = "1") int intervalMonth) {
        model.addAttribute("subgroup", subgroupService.findById(subgroupId));
        model.addAttribute("members", memberService.findBySubgroupId(subgroupId));
        model.addAttribute("slots", eventService.getSubgroupScheduleForMonth(subgroupId, currentMonth, intervalMonth));
        return "subgroup";
    }

    @GetMapping("/subgroup/{subgroupId}/event")
    public String subgroupDetail(Model model, @PathVariable("subgroupId") int subgroupId,
                                 @RequestParam("day") LocalDate day,
                                 @RequestParam(value = "intervalMinutes", defaultValue = "10") int intervalMinutes) {
        model.addAttribute("slotsForDay", eventService.getSubgroupScheduleForDay(subgroupId, day, intervalMinutes));
        return "subgroupEvent";
    }
}
