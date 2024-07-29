package spring.lecture.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.lecture.web.member.Member;
import spring.lecture.web.member.MemberService;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final MemberService memberService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute Member member) {
        memberService.create(member);
        return "redirect:/login";
    }
}
