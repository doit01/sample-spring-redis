package pl.piomin.services.redis.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RoleVo implements Serializable {
    int id;
    String name;
}
