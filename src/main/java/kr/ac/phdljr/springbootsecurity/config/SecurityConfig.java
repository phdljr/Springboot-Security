package kr.ac.phdljr.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig {

    // 비밀번호 암호화 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Cross Site Request Forgery 사이트간 위조 요청
                .csrf().disable()

                .authorizeRequests()
                // 로그인 한 사람만 들어올 수 있음(인증)
                .antMatchers("/user/**").authenticated()
                // 권한이 있는 사람만 접근 가능(권한)
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                // 나머지 주소는 모든 사용자가 접근 가능
                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage("/loginForm")
                // /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                // 이게 존재하기 때문에 컨트롤러에 /login을 등록해주지 않아도 됨
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/"); // 로그인 완료 시, 이동할 url
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/resources/**");
    }
}
