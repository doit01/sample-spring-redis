package pl.piomin.services.redis.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RedisLinkRepository   {

    private  ReactiveRedisOperations<String, String> operations;


    public RedisLinkRepository(ReactiveRedisOperations<String, String> operations) {
        this.operations = operations;
    }

    public Mono<Link> save(Link link) {
        return operations.opsForValue()
                         .set(link.getKey(), link.getOriginalLink())
                         .map(__ -> link);
    }

    public Mono<Link> findByKey(String key) {
        return operations.opsForValue()
                         .get(key)
                         .map(result -> new Link(result, key));
    }
}
