package com.javalang.imessage.service;

import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;

public interface UserService {
    ResultT<User> applyFriend(String friendId);
    ResultT<User> deleteFriend();
    ResultT<User> agreeFriend();
    ResultT<User> getApplyList();
    ResultT<User> getFriendList();
    ResultT<User> isFriend();
    ResultT<User> unreadApplyNum();
    ResultT<User> getUserInfoByName(String name);
}

