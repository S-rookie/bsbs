package com.whpu.house.pojo;

import com.whpu.house.common.Groups;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;


@Data
public class Role implements GrantedAuthority {

    @NotNull(groups = {Groups.Update.class})
    private Integer id;
    @NotNull(groups = {Groups.Insert.class})
    private String roleName;

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
