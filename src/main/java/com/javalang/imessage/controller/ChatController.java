package com.javalang.imessage.controller;

import com.javalang.imessage.model.User;
import com.javalang.imessage.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/api/chat")
public class ChatController {

    @RequestMapping(value = "/websocket")
    public String websocket(Model model) {
        log.info("websocket UserThreadLocal:" + UserThreadLocal.get());
        model.addAttribute("user", UserThreadLocal.get());
        return "websocket.html";
    }

    @RequestMapping(value = "/msg")
    public String sendMessage() {
        // 调用 SendMessageController 逻辑
        return "websocket.html";
    }
}
