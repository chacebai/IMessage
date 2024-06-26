package com.javalang.imessage.service;

import com.javalang.imessage.dto.ResultT;

import java.util.Map;

public interface AuthService {
    ResultT<Map<String, Object>> register(String id);
    ResultT<Map<String, Object>> login(String id);
}
