package com.apple.shop.Config;

import com.apple.shop.Member.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


        //CSRF 추가
//    @Bean
//    public CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("ㅌㅌ");
        http.csrf((csrf) -> csrf.disable());
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository()) //CSRF 추가
//                .ignoringRequestMatchers("/login")
//        );
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/join","new","/login/**", "/css/**", "/js/**", "/images/**","favicon.ico").permitAll() // 정적 리소스와 메인 페이지 접근 허용
                .requestMatchers("/list").authenticated()
                .anyRequest().authenticated()
        );
        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/list")
                .failureUrl("/login"));
        http.logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

}
