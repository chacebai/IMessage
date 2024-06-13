package com.javalang.imessage.controller;

import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.service.AuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseBody
    @RequestMapping("/register")
    public ResultT<Map<String, Object>> register(@RequestParam(required = false) String id) {
        // 调用你的 RegisterController 逻辑
        return authService.register(id);
    }

    @RequestMapping("/login")
    public ResultT<Map<String, Object>> login(@RequestParam(required = false) String id, HttpSession session) {
        // 调用你的 LoginController 逻辑
        return authService.login(id);
    }
}
