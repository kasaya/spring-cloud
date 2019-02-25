package com.oycl.util.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @Primary
    public CacheManager cacheManager(RedisTemplate redisTemplate){

        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                .build();
        return cacheManager;
    }

    @Bean
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        //序列化规则
        /**
         * StringRedisSerializer：Key或者value为字符串的场景，
         * 根据指定的charset对数据的字节序列编码成string，
         * 是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。
         * 是最轻量级和高效的策略。
         */
        template.setKeySerializer(new StringRedisSerializer());
        /**
         * JacksonJsonRedisSerializer：jackson-json工具提供了javabean与json之间的转换能力，
         * 可以将pojo实例序列化成json格式存储在redis中，也可以将json格式的数据转换成pojo实例。
         * 因为jackson工具在序列化和反序列化时，需要明确指定Class类型，因此此策略封装起来稍微复杂。
         * 【需要jackson-mapper-asl工具支持】
         */
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }


}
