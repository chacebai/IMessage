package com.javalang.imessage.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @RequestMapping("/create")
    public String createGroup() {
        // 调用 CreateGroupController 逻辑
        return "Group created";
    }

    @RequestMapping("/{id}")
    public String deleteGroup(@PathVariable Long id) {
        // 调用 DeleteGroupService 逻辑
        return "Group deleted: " + id;
    }

    @RequestMapping("/join")
    public String joinGroup() {
        // 调用 JoinGroupService 逻辑
        return "Joined group";
    }

    @RequestMapping("/quit")
    public String quitGroup() {
        // 调用 QuitGroupService 逻辑
        return "Quit group";
    }

    @RequestMapping("/getGroupMemberList")
    public String getGroupMemberList() {
        // 调用 GetGroupMemberListService 逻辑
        return "Group member list";
    }

    @RequestMapping("/grantAdministrator")
    public String grantAdministrator() {
        // 调用 GrantAdministratorService 逻辑
        return "Granted administrator";
    }

    @RequestMapping("/removeAdministrator")
    public String removeAdministrator() {
        // 调用 RemoveAdministratorService 逻辑
        return "Removed administrator";
    }
}
