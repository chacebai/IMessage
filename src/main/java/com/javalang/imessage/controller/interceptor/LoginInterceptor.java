package com.javalang.imessage.controller.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalang.imessage.dto.ResponseResult;
import com.javalang.imessage.dto.ResultT;
import com.javalang.imessage.model.User;
import com.javalang.imessage.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    public static final Integer PRE_ERROR = -1001;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //if (!(handler instanceof HandlerMethod)) {
        //    sendErrorResponse(response, "未授权访问");
        //    return true;
        //}
        // 获取Session
        HttpSession session = request.getSession();
        // 获取Session用户
        Object user = session.getAttribute("user");
        log.info("===============request start===============");
        log.info("request uri:{}", request.getRequestURI());
        log.info("request method:{}", request.getMethod());
        log.info("user:{}", user);
        log.info("===============request end===============");
        // 判断是否存在
        if (user == null) {
            //sendErrorResponse(response, "User Invalid");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        // 存在保存到ThreadLocal
        UserThreadLocal.put((User) user);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //response.setContentType("text/html;charset=utf-8");
        //response.sendRedirect("nologin.html");
        //response.setContentType("application/json;charset=UTF-8");
        //ResultT<Void> result = ResponseResult.fail(PRE_ERROR, "PRE_ERROR:" + message);
        //String jsonResponse = objectMapper.writeValueAsString(result);
        //response.getWriter().write(jsonResponse);
    }
}
