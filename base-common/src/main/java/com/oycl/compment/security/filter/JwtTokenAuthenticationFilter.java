package com.oycl.compment.security.filter;


import com.google.gson.Gson;
import com.oycl.compment.security.entity.UserInfoDetail;
import com.oycl.util.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *  JWT认证
 * @author kasaya
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");

        UsernamePasswordAuthenticationToken authentication = null;

        //验证token是否过期 如果过期
        if(!JwtTokenUtil.INSTENS.isTokenExpired(authorization)){
            //将token转换成认证信息
            Claims claims = JwtTokenUtil.INSTENS.getClaimsFromToken(authorization);

            CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
            if (claims != null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private class CustomUserDetailsService implements UserDetailsService {
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
            System.out.println("当前的用户是："+ s);

            Gson gson = new Gson();
            // 还原用户信息
            UserInfoDetail userInfo = gson.fromJson(s, UserInfoDetail.class);

            return userInfo;
        }
    }


}
