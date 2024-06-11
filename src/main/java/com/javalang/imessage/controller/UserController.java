package com.javalang.imessage.controller;

import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;
import com.javalang.imessage.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/add")
    public ResultT<User> applyFriend(@RequestParam(required = false) String friendId) {
        // 调用你的 ApplyFriendController 逻辑
        return userService.applyFriend(friendId);
    }

    @RequestMapping("/delete")
    public ResultT<User> deleteFriend() {
        // 调用你的 DeleteFriendController 逻辑
        return userService.deleteFriend();
    }

    @RequestMapping("/agree")
    public ResultT<User> agreeFriend() {
        // 调用你的 AgreeFriendController 逻辑
        return userService.agreeFriend();
    }

    @RequestMapping("/getApplyList")
    public ResultT<User> getApplyList() {
        // 调用你的 GetUserApplyController 逻辑
        return userService.getApplyList();
    }

    @RequestMapping("/getFriendList")
    public ResultT<User> getFriendList() {
        // 调用你的 GetFriendListController 逻辑
        return userService.getFriendList();
    }

    @RequestMapping("/isFriend/{friendUid}")
    public ResultT<User> isFriend(@PathVariable Long friendUid) {
        // 调用你的 IsFriendController 逻辑
        return userService.isFriend();
    }

    @RequestMapping("/unreadApplyNum")
    public ResultT<User> unreadApplyNum() {
        // 调用你的 UnreadApplyNumController 逻辑
        return userService.unreadApplyNum();
    }

    @RequestMapping("/getUserInfoByName")
    public ResultT<User> getUserInfoByName(@RequestParam(required = false) String name) {
        // 调用你的 GetUserInfoByNameController 逻辑
        return userService.getUserInfoByName(name);
    }

    @RequestMapping("/test")
    public String test() {
        // 调用你的 test 逻辑
        return "Test endpoint";
    }
}
