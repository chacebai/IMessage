package com.javalang.imessage.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // config.getUserProperties().put(HttpSession.class.getName(), httpSession);
        if (httpSession != null) {
            // 记录错误或采取其他措施
            System.out.println("HTTP session is object during WebSocket handshake");
            config.getUserProperties().put(HttpSession.class.getName(), httpSession);
        } else {
            // 记录错误或采取其他措施
            System.err.println("HTTP session is null during WebSocket handshake");
        }
    }
}
