package spring.lecture.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    localhost:8080에 해당하는 부분
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public String index() {
        return "index";
    }

//    localhost:8080/login에 접속 시 login.html 파일로 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    localhost:8080/signup에 접속 시 signup.html 파일로 이동
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

//    localhost:8080/signup에서 Member class에 해당되는 데이터를 받았을 시 해당 유저 데이터를 db에 저장
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute Member member) {
//        바로 이 부분이 db에 Member class로 유저 데이터를 저장하는 부분.
        memberService.create(member);
        return "redirect:/login";
    }
}
