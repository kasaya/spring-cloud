package com.oycl.common.config;

import com.oycl.common.util.RedisUtil;
import com.oycl.service.IRedisService;
import com.oycl.service.impl.RedisServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * redis 配置类
 * @author cango
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnFactory(){
        //配置单节点redis
        RedisStandaloneConfiguration standaloneConfig = new  RedisStandaloneConfiguration();
        standaloneConfig.setDatabase(redisProperties.getDatabase());
        standaloneConfig.setHostName(redisProperties.getHost());
        RedisPassword password = RedisPassword.of(redisProperties.getPassword());
        standaloneConfig.setPassword(password);
        standaloneConfig.setPort(redisProperties.getPort());
        BeanUtils.copyProperties(redisProperties, standaloneConfig);
        JedisConnectionFactory factory =  new JedisConnectionFactory(standaloneConfig);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired JedisConnectionFactory jedisConnFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory);
        return redisTemplate;
    }

    @Bean
    public IRedisService redisService(@Autowired RedisTemplate redisTemplate) {
        IRedisService redisService = new RedisServiceImpl(redisTemplate);
        RedisUtil.setRedisService(redisService);
        return redisService;
    }
}
