//package com.flag.flag_back.jwt;
//
//import com.flag.flag_back.Exception.BaseException;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@AllArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilterBean {
//    private final com.flag.flag_back.jwt.JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
//        String token = resolveToken((HttpServletRequest) request);
//
//        // 토큰 유효성 검사
//        if(token != null && jwtTokenProvider.validateToken(token)) {
//
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            try {
//                jwtTokenProvider.checkUserId(authentication, (HttpServletRequest) request);
//            } catch (BaseException e) {
//                throw new RuntimeException(e);
//            }
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    // 헤더에서 토큰 추출
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}