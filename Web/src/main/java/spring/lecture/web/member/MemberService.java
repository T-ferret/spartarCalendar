package spring.lecture.web.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public void create(Member member) {
        member.setRole("ROLE_USER");
        memberRepository.save(member);
    }

    @Override
    //유저가 로그인하면 스프링 시큐리티는 UserDetailService를 호출하고 loadUserByUsername을 호출하여 로그인 정보와 DB의 정보 비교
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        찾고자 하는 User 데이터 부재 시, 예외 발생
        Optional<Member> tempMembers = memberRepository.findByUsername(username);
        if(tempMembers.isEmpty()){
            throw new UsernameNotFoundException("You need to signup first");
        }
        
//        예외 없을 시, 데이터를 가져옴.
        Member member = tempMembers.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        
//        해당 User의 Role이 ROLE_USER일 경우, 해당 권한 부여
        if("ROLE_USER".equals(member.getRole())){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
