//package com.flag.flag_back.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.servlet.DispatcherType;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity  //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션, 내부적으로 SpringSecurityFilterChain이 동작하여 URL 필터가 적용된다.
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors().disable()
//                .authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()  //여기에 비밀번호 찾기나, 이메일 찾기, 자동 로그인 시 허용할 url- 조건으로 써주기
//                        .anyRequest().authenticated()
//                )
//                .formLogin(login -> login
//                        //.loginPage("/view/login") ->여기 로그인 페이지 넣기.
//                        .loginProcessingUrl("/login-process")
//                        .usernameParameter("email")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/view", true)
//                        .permitAll()
//                )
//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers(new AntPathRequestMatcher("/postman/**")))
//                .logout(withDefaults());
//
//        return http.build();
//    }
//}