package pl.piomin.services.redis.reactivelink;

import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import pl.piomin.services.redis.reactive.Link;
import pl.piomin.services.redis.reactive.LinkService;
import pl.piomin.services.redis.reactive.RedisLinkRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LinkServiceTest {

    private RedisLinkRepository linkRepository = mock(RedisLinkRepository.class);
    private LinkService linkService = new LinkService( linkRepository);

    @Before
    public void setup() {
        when(linkRepository.save(any()))
                .thenAnswer((Answer<Mono<Link>>) invocationOnMock -> Mono.just((Link) invocationOnMock.getArguments()[0]));
    }

    @Test
    public void shortensLink() {
        StepVerifier.create(linkService.shortenLink("http://spring.io"))
                    .expectNextMatches(result -> result != null && result.length() > 0
                                                 && result.startsWith("http://some-domain.com/"))
                    .expectComplete()
                    .verify();
    }

}
