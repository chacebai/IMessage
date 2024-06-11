package com.javalang.imessage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @RequestMapping("/getContactList")
    public String getContactList() {
        // 调用 GetContactListController 逻辑
        return "Contact list";
    }

    @RequestMapping("/getNewContactList")
    public String getNewContactList() {
        // 调用 GetNewContactListController 逻辑
        return "New contact list";
    }

    @RequestMapping("/getMessageList")
    public String getMessageList() {
        // 调用 GetContactDetailService 逻辑
        return "Message list";
    }

    @RequestMapping("/getNewMsgList")
    public String getNewMsgList() {
        // 调用 GetNewMsgListController 逻辑
        return "New message list";
    }

    @RequestMapping("/userInfo/batch")
    public String getUserInfoBatch() {
        // 调用 GetUserInfoBatchController 逻辑
        return "User info batch";
    }
}
