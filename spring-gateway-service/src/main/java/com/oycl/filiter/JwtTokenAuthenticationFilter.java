package com.oycl.filiter;


import com.oycl.config.JwtAuthenticationToken;
import com.oycl.config.SecurityConfig;
import com.oycl.config.TokenInfo;
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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 *  JWT认证
 * @author kasaya
 */
public class JwtTokenAuthenticationFilter implements WebFilter {

    private static final String TOKEN_SCHEME = "TOKEN=";

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = "miyao";

        // 本地的密码解码
        byte[] encodedKey = Base64.getEncoder().encode(stringKey.getBytes());

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        return key;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            logger.warn("not find AUTHORIZATION");
            return Mono.empty();
        }
        String token = authHeader.replace(TOKEN_SCHEME, "").trim();

        try {
            Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
            TokenInfo tokenInfo = new TokenInfo(claims);
            logger.info("token:{}  ", tokenInfo);


            return chain.filter(exchange);
        }catch (ExpiredJwtException e) {
            // token 超时
            logger.warn("jwt token is expired");
        } catch (MalformedJwtException e) {
            // token Malformed
            logger.warn("jwt token is malformed222");
        }
        return Mono.empty();
    }




}
