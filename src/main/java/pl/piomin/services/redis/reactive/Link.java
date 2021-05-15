package pl.piomin.services.redis.reactive;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Link {

    String originalLink;
    String key;
}
