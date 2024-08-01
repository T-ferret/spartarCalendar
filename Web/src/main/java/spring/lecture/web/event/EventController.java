package spring.lecture.web.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import spring.lecture.web.member.MemberService;

@RequiredArgsConstructor
@Controller
public class EventController {
    private final EventService eventService;
    private final MemberService memberService;

    @GetMapping("/create")
    public String createEvent() {
        return "EventCreate";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute Event event) {
        event.setMember(memberService.getCurrentMember());
        eventService.save(event);
        return "redirect:/landing";
    }

    @GetMapping("/update")
    public String updateEvent() {
        return "EventUpdate";
    }

    @PostMapping("/update")
    public String updateEvent(@ModelAttribute Event event) {
        eventService.update(event);
        return "redirect:/events";
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Integer eventId) {
        eventService.delete(eventId);
        return "redirect:/events";
    }
}
