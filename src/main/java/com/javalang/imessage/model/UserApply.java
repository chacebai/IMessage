package com.javalang.imessage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApply {

    private Long id;                // id
    private String uid;               // 申请人uid
    private Integer type;           // 申请类型 1加好友
    private Long targetId;          // 接收人uid
    private String msg;             // 申请信息
    private Integer status;         // 申请状态 1待审批 2同意
    private Integer readStatus;     // 阅读状态 1未读 2已读
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
}
