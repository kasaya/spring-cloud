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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private static final String TOKEN_SCHEME = "Bearer ";

    String secret = "BfVvTqm7eQDWcQASd0O0LAsdKOKFuzHnocmoyE9xcTwhkJ4OMxifGdmMS84ocdaK";

    private static final Logger logger = LoggerFactory.getLogger(JwtSecurityContextRepository.class);
    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange) {
        String authHeader = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            logger.warn("not find AUTHORIZATION");
            return Mono.empty();
        }
        String token = authHeader.replace(TOKEN_SCHEME, "").trim();

        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
            TokenInfo tokenInfo = new TokenInfo(claims);
            logger.info("token:{}  ", tokenInfo);

            //TODO: 验证token信息


            //验证通过： 获取授权信息
            List<GrantedAuthority> authorities = tokenInfo.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());
            Authentication authentication = new JwtAuthenticationToken(authorities, tokenInfo.getUserName());
            authentication.setAuthenticated(true);

            return Mono.justOrEmpty(new SecurityContextImpl(authentication));
        } catch (ExpiredJwtException e) {
            // token 超时
            logger.warn("jwt token is expired");
        } catch (MalformedJwtException e) {
            // token Malformed
            logger.error("MalformedJwtException", e);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        return Mono.empty();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = "BfVvTqm7eQDWcQASd0O0LAsdKOKFuzHnocmoyE9xcTwhkJ4OMxifGdmMS84ocdaK";

        // 本地的密码解码
        byte[] encodedKey = new byte[0];
        try {
            encodedKey = Base64.getEncoder().encode(stringKey.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        return key;
    }
}
