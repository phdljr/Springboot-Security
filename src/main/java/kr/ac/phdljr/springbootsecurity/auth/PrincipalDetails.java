package kr.ac.phdljr.springbootsecurity.auth;

import kr.ac.phdljr.springbootsecurity.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 시큐리티가 /login을 낚아채서 로그인을 진행시킴
 * 진행이 완료가 되면 시큐리티 session을 만들어 줌 (Security ContextHolder)
 * Authentication 객체만이 시큐리티 컨텍스트에 저장될 수 있음
 * Authentication 안에는 User 정보가 있어야 됨
 * User 객체의 타입은 UserDetails 타입 객체여야 함
 *
 * 시큐리티 세션 영역에 세션 정보를 저장해준다.
 * => 그 정보의 객체는 Authentication이다.
 * => Authentication 객체 안의 User 정보를 저장 할땐 UserDetails 타입이여야 한다.
 */
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 유저의 권한을 반환하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료됐는가?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는가?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 비밀번호가 1년이 지났나?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화 되어있냐?
    @Override
    public boolean isEnabled() {
        // 오랫동안 로그인을 안하면 휴면 계정으로 전환한다고 하면
        // 현재시간 - 로그인시간 => 1년 차이가 난다면 휴면 계정으로 전환시키게 한다.
        return true;
    }
}
