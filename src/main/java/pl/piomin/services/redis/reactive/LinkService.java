package pl.piomin.services.redis.reactive;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LinkService {

    private final String baseUrl="/home";
    private final RedisLinkRepository  linkRepository;
//@Value("${app.baseUrl}") String baseUrl,
    public LinkService( RedisLinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Mono<String> shortenLink(String link) {
        String randomKey = RandomStringUtils.randomAlphabetic(6);
        return linkRepository.save(new Link(link, randomKey))
                             .map(result -> baseUrl + result.getKey());
    }

    public Mono<Link> getOriginalLink(String key) {
        return linkRepository.findByKey(key);
    }
}
