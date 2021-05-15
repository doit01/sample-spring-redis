package pl.piomin.services.redis.reactive;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LinkController {

   // @Autowired
 //   private ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;
    @Autowired
    private RedisTemplate<Object, PermissionVo> reactiveRedisTemplate;
    private final LinkService linkService;
    //    private void savePermissionToRedis(AdminDetails adminDetails,String tokenId){
//        List<PermissionDomain> permissionDomainList = adminDetails.getRoleList().stream()
//                .flatMap(role -> role.getPermList().stream()).collect(Collectors.toList());
//        String redisKey = "perm_"+adminDetails.getUsername()+"_"+tokenId;
//        permissionDomainRedisTemplate.opsForList().leftPushAll(redisKey,permissionDomainList);
//        permissionDomainRedisTemplate.expire(redisKey,expirationRefreshTime, TimeUnit.SECONDS);
//    }

    @PostMapping("/savePermissionToRedis")
    List savePermissionToRedis() {
        RoleVo r1=new  RoleVo(1,"admin");RoleVo r2=new  RoleVo(2,"user");
        RoleVo r3=new  RoleVo(3,"SUPERadmin");
        List <RoleVo> roles1= List.of(r1,r2);
        List <RoleVo> roles2= List.of(r1,r3);
        PermissionVo p1=new PermissionVo( "p1",roles1);
        PermissionVo p2=new PermissionVo( "p2",roles2);
        List<PermissionVo> permissionVos =List.of(p1,p2);
        String redisKey = "key_permission_with_roles";
//

        reactiveRedisTemplate.opsForList().leftPushAll(redisKey,permissionVos);
//        reactiveRedisTemplate.opsForList().leftPushAll(redisKey,List.of("1","2"));
        reactiveRedisTemplate.expire(redisKey, Duration.ofHours(2));
        List<PermissionVo>  returnfromredis= reactiveRedisTemplate.opsForList().range(redisKey,0,-1);
         return returnfromredis;
    }

    @PostMapping("/link")
    Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequest request) {
        return linkService.shortenLink(request.getLink())
                          .map(CreateLinkResponse::new);
    }

    @GetMapping("/{key}")
    Mono<ResponseEntity<Object>> getLink(@PathVariable String key) {
        return linkService.getOriginalLink(key)
                          .map(link -> ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                                                     .header("Location", link.getOriginalLink())
                                                     .build())
                          .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Value
    public static class CreateLinkRequest {

        private String link;
    }

    @Value
    public static class CreateLinkResponse {

        private String shortenedLink;
    }
}
