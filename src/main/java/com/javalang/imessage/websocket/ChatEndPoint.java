package com.javalang.imessage.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
@Component
public class ChatEndPoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static Map<String, ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();

    //和某个客户端连接对象，需要通过他来给客户端发送数据
    private Session session;
    // 用户名
    private String username;

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("接受的长度：" + message.length());
        System.out.println("最大的长度：" + Integer.MAX_VALUE);
        System.out.println("接收的消息是：" + message);
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message) {
        try {
            //遍历 onlineUsers 集合
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                //获取该用户对应的ChatEndpoint对象
                ChatEndPoint chatEndpoint = onlineUsers.get(name);
                //发送消息
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("连接打开了。。。");
        this.session = session;
        // 为了简单起见，我们使用会话ID作为用户名
        this.username = session.getId();
        onlineUsers.put(username, this);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("连接关闭了。。。");
        onlineUsers.remove(this.username);
    }

    @OnError
    public void onError(Session session,Throwable error) {
        System.out.println("出错了。。。。" + error.getMessage());
    }
}
