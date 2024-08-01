package spring.lecture.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.lecture.web.event.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Controller
public class MainController {
    private final EventService eventService;

//    localhost:8080에 해당하는 부분
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/landing")
    public String landing(Model model, @RequestParam(value = "intervalMinutes", defaultValue = "10") Integer intervalMinutes) {
        List<LocalDateTime[]> eventSlots = eventService.getAllUserScheduleTimeSlots(intervalMinutes);
        model.addAttribute("eventSlots", eventSlots);
        return "landing";
    }
}
