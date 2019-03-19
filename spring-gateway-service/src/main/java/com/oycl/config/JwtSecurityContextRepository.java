package com.oycl.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.SignatureException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
//        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null) {
//            logger.warn("not find AUTHORIZATION");
//            return Mono.empty();
//        }
//        String token = authHeader.replace(TOKEN_SCHEME, "").trim();
//
//        try {
//            Claims claims = Jwts.parser().setSigningKey("iotSignKey".getBytes()).parseClaimsJws(token).getBody();
//            TokenInfo tokenInfo = new TokenInfo(claims);
//            logger.info("token:{}  ", tokenInfo);
//
//            // 获取授权信息
//            List<GrantedAuthority> authorities = tokenInfo.getAuthorities()
//                    .stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(toList());
//
//            Authentication authentication = new JwtAuthenticationToken(authorities, tokenInfo.getUserName());
//            authentication.setAuthenticated(true);
//
//            return Mono.justOrEmpty(new SecurityContextImpl(authentication));
//        } catch (SignatureException e) {
//            // 验证错误
//            logger.warn("jwt token parse error: {}", e.getCause());
//        } catch (ExpiredJwtException e) {
//            // token 超时
//            logger.warn("jwt token is expired");
//        } catch (MalformedJwtException e) {
//            // token Malformed
//            logger.warn("jwt token is malformed");
//        }
        return Mono.empty();
    }
}
