package com.javalang.imessage.websocket;

import com.javalang.imessage.model.User;
import com.javalang.imessage.utils.UserThreadLocal;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        if (httpSession != null) {
            //System.out.println("HTTP session is object during WebSocket handshake");
            Object user = httpSession.getAttribute("user");
            if (user != null) {
                // 设置到ThreadLocal
                UserThreadLocal.put((User) user);
            }
            // 设置到WebSocket的EndpointConfig
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
        } else {
            // 记录错误或采取其他措施
            //System.err.println("HTTP session is null during WebSocket handshake");
        }
    }
}
