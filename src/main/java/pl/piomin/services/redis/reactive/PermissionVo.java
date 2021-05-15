package pl.piomin.services.redis.reactive;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PermissionVo implements Serializable {



    String name;
    List<RoleVo> roles;

}
