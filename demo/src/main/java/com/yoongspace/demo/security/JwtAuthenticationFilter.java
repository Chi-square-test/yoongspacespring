package com.yoongspace.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  TokenProvider tokenProvider;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            String studentid="";
            if (token!=null&&!token.equalsIgnoreCase("null")){
                if(token!=null&& tokenProvider.isTokenExpired(token)){
                    studentid=tokenProvider.validateAndGetUserId(token);
                }
                log.info("인증된 사용자 : "+studentid);
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        studentid,
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        }catch (Exception ex){
            logger.error("보안 인증 실패",ex);
        }

        filterChain.doFilter(request,response);

    }

    private String parseBearerToken (HttpServletRequest request){
        String brarerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(brarerToken)&&brarerToken.startsWith("Bearer ")){
            return  brarerToken.substring(7);
        }
        return null;
    }
}
