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
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@Controller
public class BasicController {

    // http://127.0.0.1:8080/html
    @GetMapping("/html")
    public String html() {
        return "index.html";
    }

    // http://127.0.0.1:8080/login
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    // http://127.0.0.1:8080/doLogin
    @PostMapping("/doLogin")
    public String doLogin(String username, HttpSession session) {
        User user1 = new User();
        user1.setId(username);
        session.setAttribute("user", user1);
        User user = (User) session.getAttribute("user");
        System.out.println("login user:" + user);
        return "redirect:/api/chat/websocket";
    }

    @ResponseBody
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 退出登录就是将用户信息删除
        session.removeAttribute("user");
        return "退出成功";
    }

    // http://127.0.0.1:8080/websocket用户权限
    @RequestMapping(value = "/websocket")
    public String websocket(HttpSession session) {
        User user1 = new User();
        user1.setId("111");
        session.setAttribute("user", user1);
        // 模拟各种api，访问之前都要检查有没有登录，没有登录就提示用户登录
        User user = (User) session.getAttribute("user");
        System.out.println("websocket user:" + user);
        if (user == null) {
            return "redirect:/login";
        }
        // 如果有登录就调用业务层执行业务逻辑，然后返回数据
        return "websocket.html";
    }

}
