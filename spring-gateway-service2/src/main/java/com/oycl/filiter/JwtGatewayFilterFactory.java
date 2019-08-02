package com.oycl.filiter;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


/**
 *  JwtGatewayFilterFactory
 *  可以直接使用
 * @author kasaya
 */
@Component
public class JwtGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtGatewayFilterFactory.Config> {


    public JwtGatewayFilterFactory(){
        super(Config.class);
        logger.info("Loaded GatewayFilterFactory [Jwt]");
    }

    private static final String TOKEN_SCHEME = "Bearer ";

    String secret = "BfVvTqm7eQDWcQASd0O0LAsdKOKFuzHnocmoyE9xcTwhkJ4OMxifGdmMS84ocdaK";
    private static final Logger logger = LoggerFactory.getLogger(JwtGatewayFilterFactory.class);

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {

        // 本地的密码解码
        byte[] encodedKey = new byte[0];
        try {
            encodedKey = Base64.getEncoder().encode(secret.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        return key;
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null) {
                logger.warn("not find AUTHORIZATION");
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            String token = authHeader.replace(TOKEN_SCHEME, "").trim();

            try {
                /*Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
                TokenInfo tokenInfo = new TokenInfo(claims);
                logger.info("token:{}  ", tokenInfo);*/

                logger.warn("调用正常");

                ServerHttpResponse response = exchange.getResponse();
                DataBufferFactory dataBufferFactory = response.bufferFactory();

                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // probably should reuse buffers
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                //释放掉内存
                                DataBufferUtils.release(dataBuffer);
                                String s = new String(content, Charset.forName("UTF-8"));
                                s += "UNAUTHORIZED";
                                //TODO，s就是response的值，想修改、查看就随意而为了
                                byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
                                return dataBufferFactory.wrap(uppedContent);
                            }));
                        }
                        // if body is not a flux. never got there.
                        return super.writeWith(body);
                    }
                };


                //TODO: 验证token 有效性

                /*Flux<DataBuffer> message = Flux.just(dataBufferFactory.wrap((new String("{'a':'b'}").getBytes())));
                response.writeWith(message);
                response.getHeaders().add("Context-type","application/json;charset=utf-8");
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();*/




//            ServerHttpResponse response = exchange.getResponse();
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();

            }catch (ExpiredJwtException e) {
                // token 超时
                logger.warn("jwt token is expired");
            } catch (MalformedJwtException e) {
                // token Malformed
                logger.warn("jwt token is malformed222");
            }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
