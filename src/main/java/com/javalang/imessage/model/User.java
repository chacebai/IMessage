package com.javalang.imessage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String password;
    private String name;
    private String avatar;
    private Integer sex;
    private String openId;
    private Integer activeStatus;
    private LocalDateTime lastOptTime;
    private String ipInfo;
    private Long itemId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
