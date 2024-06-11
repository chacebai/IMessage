package com.javalang.imessage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @RequestMapping("/msg")
    public String sendMessage() {
        // 调用 SendMessageController 逻辑
        return "Message sent";
    }
}
