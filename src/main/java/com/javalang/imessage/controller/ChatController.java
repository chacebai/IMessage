package com.javalang.imessage.controller;

import com.javalang.imessage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

    @RequestMapping(value = "/websocket", method = RequestMethod.GET)
    public String websocket(HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("websocket user:" + user);
        return "websocket.html";
    }

    @RequestMapping(value = "/msg", method = RequestMethod.GET)
    public String sendMessage() {
        // 调用 SendMessageController 逻辑
        return "websocket.html";
    }
}
