package com.javalang.imessage.websocket;

import com.javalang.imessage.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {
    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static final Map<String, ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();
    //和某个客户端连接对象，需要通过他来给客户端发送数据
    private Session session;
    //httpSession中存储着当前登录的用户名
    private HttpSession httpSession;
    // 用户名
    private String username;

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("当前的号码：" + session.getId());
        System.out.println("接受的长度：" + message.length());
        String[] res = message.split(" ");
        broadcastAllUsers(message);
        ChatEndPoint chatEndPoint = onlineUsers.get(res[0]);
        if (chatEndPoint != null) {
            try {
                chatEndPoint.session.getBasicRemote().sendText(res[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        // 需要通知其他的客户端，将所有的用户的用户名发送给客户端
        this.session = session;
        // 获取HttpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            // 将该httpSession赋值给成员httpSession
            this.httpSession = httpSession;
            // 为了简单起见，我们使用会话ID作为用户名
            User user = (User) httpSession.getAttribute("user");
            if (user != null) {
                // 获取用户名
                this.username = user.getId();
                // 存储该链接对象
                onlineUsers.put(username, this);
                // 获取需要推送的消息
                String message = onlineUsers.keySet().toString();
                // 广播给所有的用户
                broadcastAllUsers(message);
            } else {
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized2"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("连接关闭了。。。");
        if (this.username != null) {
            onlineUsers.remove(this.username);
        }
    }

    @OnError
    public void onError(Session session,Throwable error) {
        System.out.println("出错了。。。。" + error.getMessage());
    }
}
