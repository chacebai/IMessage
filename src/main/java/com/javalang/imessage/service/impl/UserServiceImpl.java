package com.javalang.imessage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalang.imessage.dto.ResponseResult;
import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;
import com.javalang.imessage.model.UserApply;
import com.javalang.imessage.service.UserService;
import com.javalang.imessage.utils.KeyPrefixAdder;
import com.javalang.imessage.utils.RedisUtils;
import com.javalang.imessage.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    public static final Integer PARAM_INVALID = -1001;
    private final ObjectMapper mapper;
    private final RedisUtils redisUtils;

    public UserServiceImpl(ObjectMapper mapper, RedisUtils redisUtils) {
        this.mapper = mapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public ResultT<User> applyFriend(String friendId) {
        if (friendId == null || UserThreadLocal.get() == null || UserThreadLocal.get().getId() == null) {
            return ResponseResult.fail(PARAM_INVALID, "参数错误");
        }
        String myId = KeyPrefixAdder.addUserPrefix(UserThreadLocal.get().getId());
        UserApply userApply = new UserApply();
        userApply.setUid(myId);
        try {
            redisUtils.set(KeyPrefixAdder.addUserApplyPrefix(friendId), mapper.writeValueAsString(userApply));
            return ResponseResult.success(UserThreadLocal.get());
        } catch (Exception e) {
            log.error("{error:}|" + e.toString());
            return ResponseResult.exception("system exception");
        }
    }

    @Override
    public ResultT<User> deleteFriend() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> agreeFriend() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> getApplyList() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> getFriendList() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> isFriend() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> unreadApplyNum() {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }

    @Override
    public ResultT<User> getUserInfoByName(String name) {
        if (UserThreadLocal.get() == null) {
            return ResponseResult.fail(-1, "未授权访问");
        }
        return ResponseResult.success(UserThreadLocal.get());
    }
}
