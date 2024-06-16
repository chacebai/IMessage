package com.javalang.imessage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalang.imessage.dto.ResponseResult;
import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;
import com.javalang.imessage.service.AuthService;
import com.javalang.imessage.utils.KeyPrefixAdder;
import com.javalang.imessage.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    public static final Integer HAS_USER = -1001;
    public static final Integer NULL_USER = -1002;

    private final ObjectMapper mapper;
    private final RedisUtils redisUtils;

    public AuthServiceImpl(ObjectMapper mapper, RedisUtils redisUtils) {
        this.mapper = mapper;
        this.redisUtils = redisUtils;
    }

    @Override
    public ResultT<Map<String, Object>> register(String id) {
        // 调用你的 RegisterController 逻辑
        String userId = KeyPrefixAdder.addUserPrefix(id);
        // 查询是否有这个userId
        User user = new User();
        user.setId(id);
        boolean existsKey = redisUtils.hasKey(userId);
        if (existsKey) {
            return ResponseResult.fail(HAS_USER, "用户已存在");
        } else {
            try {
                redisUtils.set(userId, mapper.writeValueAsString(user));
                // 包装用户数据和token
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("user", user);
                return ResponseResult.success(responseData);
            } catch (Exception e) {
                log.error("{error:}|" + e.toString());
                return ResponseResult.exception("system exception");
            }
        }
    }

    @Override
    public ResultT<Map<String, Object>> login(String id) {
        String userId = KeyPrefixAdder.addUserPrefix(id);
        // 查询是否有这个userId
        boolean existsKey = redisUtils.hasKey(userId);
        if (existsKey) {
            try {
                String userJson = (String) redisUtils.get(userId);
                User user = mapper.readValue(userJson, User.class);
                // 包装用户数据和token
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("user", user);
                return ResponseResult.success(responseData);
            } catch (Exception e) {
                log.error("{error:}|" + e.toString());
                return ResponseResult.exception("system exception");
            }
        } else {
            return ResponseResult.fail(NULL_USER, "查无此人");
        }
    }
}
