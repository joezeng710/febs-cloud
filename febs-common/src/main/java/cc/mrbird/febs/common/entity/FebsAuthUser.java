package cc.mrbird.febs.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FebsAuthUser implements Serializable {

    private static final long serialVersionUID = -3283211698653815560L;

    private String username;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;
}
