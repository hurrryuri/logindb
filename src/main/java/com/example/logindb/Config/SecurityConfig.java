package com.example.logindb.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//사용자 보안설정
@Configuration
@EnableWebSecurity //웹보안 활성화(2.7버전에서 extends와 동일)
@RequiredArgsConstructor //클래스 자동생성(외부 클래스 추가)
public class SecurityConfig {
    //1. 비밀번호 암호화 설정
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //2. 필터링(try~catch 대신 throws 선언)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //2-1. 맴핑권한(permitAll(), hasRole(), hasAnyRole())
        http.authorizeHttpRequests((auth) -> {
            //Controller의 모든 매핑에 대한 권한을 부여 (**-모든매핑 대응)
            auth.requestMatchers("/").permitAll(); //시작페이지는 모든 사용자 접근 가능
            auth.requestMatchers("/result").permitAll(); //result매핑은 모든 사용자 접근 가능
            auth.requestMatchers("/user").hasRole("USER"); // USER와 사용자만 /USER매핑으로 접근가능
            //auth.requestMatchers("/user").hasAnyRole("USER", "ADMIN"); // USER와 ADMIN사용자를 같이 접근가능하게 하려면 hasAnyRole
            auth.requestMatchers("/admin").hasRole("ADMIN"); //ADMIN사용자만 /admin매핑으로 접근가능
        });
        //2-2. 로그인 (username, password)
        http.formLogin(login->login
                .defaultSuccessUrl("/result", true) //로그인 성공시 /result로
                .permitAll() //모든 사용자 접근 가능
        );

        //2-3. csrf보안 (일단 비활성화, http의 변조방지
        http.csrf(AbstractHttpConfigurer::disable);
        //2-4. 로그아웃

        return http.build();
    }
}
