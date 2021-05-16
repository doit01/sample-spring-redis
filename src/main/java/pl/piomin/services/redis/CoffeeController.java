//package pl.piomin.services.redis;
//
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//
//import javax.annotation.PostConstruct;
//import java.util.UUID;
//
//@Component
// class CoffeeLoader {
//	private final ReactiveRedisConnectionFactory factory;
//	private final ReactiveRedisOperations<String, Coffee> coffeeOps;
//
//	public CoffeeLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Coffee> coffeeOps) {
//		this.factory = factory;
//		this.coffeeOps = coffeeOps;
//	}
//
//	@PostConstruct
//	public void loadData() {
//		factory.getReactiveConnection().serverCommands().flushAll().thenMany(
//				Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
//						.map(name -> new Coffee(UUID.randomUUID().toString(), name))
//						.flatMap(coffee -> coffeeOps.opsForValue().set(coffee.getId(), coffee)))
//				.thenMany(coffeeOps.keys("*")
//						.flatMap(coffeeOps.opsForValue()::get))
//				.subscribe(System.out::println);
//	}
//}
//
//
//
//@Configuration
// class CoffeeConfiguration {
//	@Bean
//	ReactiveRedisOperations<String, Coffee> redisOperations(ReactiveRedisConnectionFactory factory) {
//		Jackson2JsonRedisSerializer<Coffee> serializer = new Jackson2JsonRedisSerializer<>(Coffee.class);
//
//		RedisSerializationContext.RedisSerializationContextBuilder<String, Coffee> builder =
//				RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//		RedisSerializationContext<String, Coffee> context = builder.value(serializer).build();
//
//		return new ReactiveRedisTemplate<>(factory, context);
//	}
//
//}
//
//
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
// class Coffee {
//	private String id;
//	private String name;
//}
//
//
//
//@RestController
//public class CoffeeController {
//	private final ReactiveRedisOperations<String, Coffee> coffeeOps;
//
//	CoffeeController(ReactiveRedisOperations<String, Coffee> coffeeOps) {
//		this.coffeeOps = coffeeOps;
//	}
//
//	@GetMapping("/coffees")
//	public Flux<Coffee> all() {
//		return coffeeOps.keys("*")
//				.flatMap(coffeeOps.opsForValue()::get);
//	}
//}
//
