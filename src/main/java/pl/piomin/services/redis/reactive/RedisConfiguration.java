package pl.piomin.services.redis.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.UnknownHostException;

@Configuration
public class RedisConfiguration {
    @Primary
    @Bean //<String, String>
    ReactiveRedisOperations reactiveRedisOperations(
            ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }
//    ReactiveListOperations ReactiveListOperations(){
//        ReactiveRedisConnectionFactory factory) {
//            return new DefaultReactiveListOperations();
//
//    }

    @Bean
//    @ConditionalOnMissingBean(  name = {"redisTemplate"}
//    )
    public RedisTemplate<Object, PermissionVo> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, PermissionVo> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
