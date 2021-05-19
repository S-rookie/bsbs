package com.whpu.house.pojo;

import com.whpu.house.common.Groups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class Reserve {
    @NotNull(groups = {Groups.Update.class})
    private Integer id;
    private Integer user_id;
	private Integer house_id;
    private Date time;
    private Integer status;
    private Date create_time;
    private Integer close;
}
