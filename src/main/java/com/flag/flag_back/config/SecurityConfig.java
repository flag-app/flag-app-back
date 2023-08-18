//package com.flag.flag_back.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> {
//                    authorize.antMatchers("/web-login").permitAll() // 웹 로그인 페이지 허용
//                            .antMatchers("/ios-login").permitAll()  // iOS 로그인 페이지 허용
//                            .antMatchers("/common-login").permitAll() // 공통 로그인 페이지 허용
//                            .anyRequest().authenticated();
//                })
//                .formLogin(login -> {
//                    login.loginPage("/common-login") // 공통 로그인 페이지
//                            .loginProcessingUrl("/login-process")
//                            .usernameParameter("email")
//                            .passwordParameter("password")
//                            .defaultSuccessUrl("/view", true)
//                            .permitAll();
//                })
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // 로그아웃 URL
//                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL
//                        .invalidateHttpSession(true) // 세션 무효화
//                        .deleteCookies("JSESSIONID") // 쿠키 삭제
//                        .permitAll()
//                );
//
//        return http.build();
////        http.addFilterAfter(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
////        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//}
//
