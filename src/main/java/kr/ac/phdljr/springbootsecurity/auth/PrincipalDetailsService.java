package kr.ac.phdljr.springbootsecurity.auth;

import kr.ac.phdljr.springbootsecurity.model.User;
import kr.ac.phdljr.springbootsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login")
 * /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 빈을 찾아
 * loadUserByUsername 함수가 실행
 *
 * 만약, form에서 username이 아니라 username2라고 파라미터 명을 바꿔서 쓰면
 * 파라미터 바인딩이 제대로 이뤄지지 않음. 이건 따로 설정해줘야 가능함
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 그러면 이 메소드의 리턴은 어디로 되는 것이냐?
    // Authentication 내부에 리턴된다.
    // => 이를 시큐리티 세션에 들어가게 된다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new PrincipalDetails(user);
        }
        return null;
    }
}
