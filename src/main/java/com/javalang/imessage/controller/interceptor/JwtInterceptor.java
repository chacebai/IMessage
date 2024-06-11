package com.javalang.imessage.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalang.imessage.dto.ResponseResult;
import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;
import com.javalang.imessage.service.UserService;
import com.javalang.imessage.utils.JwtUtil;
import com.javalang.imessage.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    public static final Integer PRE_ERROR = -1001;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            sendErrorResponse(response, "未授权访问");
            return true;
        }
        // 获取请求头中的 token
        String token = request.getHeader("Authorization");
        log.info("===============request start===============");
        log.info("request uri:{}", request.getRequestURI());
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("===============request end===============");

        // 检查 token 是否存在并且有效
        if (token == null || JwtUtil.getUsernameFromToken(token) == null) {
            sendErrorResponse(response, "Token Invalid");
            return false;
        }
        User user = userService.checkToken(token);
        if (user == null) {
            sendErrorResponse(response, "User Invalid");
            return false;
        }
        // 设置用户信息到请求属性中
        UserThreadLocal.put(user);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // System.out.println("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // System.out.println("afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        ResultT<Void> result = ResponseResult.fail(PRE_ERROR, "PRE_ERROR:" + message);
        String jsonResponse = objectMapper.writeValueAsString(result);
        response.getWriter().write(jsonResponse);
    }
}
