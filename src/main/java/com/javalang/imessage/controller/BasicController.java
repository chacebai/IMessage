/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.javalang.imessage.controller;

import javax.servlet.http.HttpSession;

import com.javalang.imessage.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
public class BasicController {

    // http://127.0.0.1:8080/html
    @GetMapping("/")
    public String html() {
        return "index.html";
    }

    // http://127.0.0.1:8080/login
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/loginJwt")
    public String formtest(HttpSession session) {
        return "loginJwt.html";
    }

    // http://127.0.0.1:8080/doLogin
    @PostMapping("/doLogin")
    public String doLogin(String username, HttpSession session) {
        User user1 = new User();
        user1.setId(username);
        session.setAttribute("user", user1);
        User user = (User) session.getAttribute("user");
        return "redirect:/api/chat/websocket";
    }

    // http://127.0.0.1:8080/doLoginJwt
    @ResponseBody
    @PostMapping("/doLoginJwt")
    public Map<String, Object> doLoginJwt(@RequestBody Map<String, String> loginData, HttpSession session) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Map<String, Object> response = new HashMap<>();
        User user1 = new User();
        user1.setId(username);
        user1.setPassword(password);
        session.setAttribute("user", user1);
        response.put("success", true);
        return response;
    }

    @ResponseBody
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 退出登录就是将用户信息删除
        session.removeAttribute("user");
        return "退出成功";
    }

}
