package com.whpu.house.pojo;

import com.whpu.house.common.Groups;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class HousePic {
    @NotNull(groups = Groups.Update.class)
    private int id;
    private int house_id;
    private String url;
    private int status;
}
