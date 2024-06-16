package com.javalang.imessage.websocket;

import com.javalang.imessage.dto.ResponseResult;
import com.javalang.imessage.model.User;
import com.javalang.imessage.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
public class ChatEndPoint {
    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static final Map<String, ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();
    //和某个客户端连接对象，需要通过他来给客户端发送数据
    private Session session;
    //httpSession中存储着当前登录的用户名
    private HttpSession httpSession;

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("当前的用户：" + session.getId());
        log.info("接收的长度：" + message.length());
        log.info("在线用户数:" + onlineUsers.size());
        // 遍历Map并输出键值对
        for (Map.Entry<String, ChatEndPoint> entry : onlineUsers.entrySet()) {
            String username = entry.getKey();
            ChatEndPoint chatEndPoint = entry.getValue();
            log.info("用户名: {}, ChatEndPoint: {}",  username, chatEndPoint);
        }
        try {
            //获取客户端发送来的数据  {"toName":"张三","message":"你好"}
            //ObjectMapper mapper = new ObjectMapper();
            //Message mess = mapper.readValue(message, Message.class);
            //获取当前登录的用户名
            //String username = (String) httpSession.getAttribute("user");
            //拼接推送的消息
            //String data = ResponseResult.wsMessage(false, username, mess.getMessage());
            //将数据推送给指定的客户端
            String[] requestMess = message.split(" ");
            if (requestMess.length > 1) {
                log.info("requestMess[0]: {}, requestMess[1]: {}", requestMess[0], requestMess[1]);
                ChatEndPoint chatEndpoint = onlineUsers.get(requestMess[0]);
                if (chatEndpoint != null && chatEndpoint.session != null && chatEndpoint.session.getBasicRemote() != null) {
                    chatEndpoint.session.getBasicRemote().sendText(requestMess[1]);
                } else {
                    log.warn("chatEndpoint:" + chatEndpoint);
                    this.session.getBasicRemote().sendText("ChatEndPoint NullPointer");
                }
            } else {
                log.info("requestMess[1] is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        log.info("连接打开了。。。");
        // 需要通知其他的客户端，将所有的用户的用户名发送给客户端
        this.session = session;
        // 获取HttpSession对象
        log.info("onOpen UserThreadLocal:" + UserThreadLocal.get());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            // 将该httpSession赋值给成员httpSession
            this.httpSession = httpSession;
            // 为了简单起见，我们使用会话ID作为用户名
            User user = (User) httpSession.getAttribute("user");
            if (user != null) {
                // 存储该链接对象
                onlineUsers.put(user.getId(), this);
                // 获取需要推送的消息
                String message = ResponseResult.wsMessage(true, null, onlineUsers.keySet());
                // 广播给所有的用户
                broadcastAllUsers(message);
            } else {
                try {
                    if (session != null)
                        session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized"));
                    else
                        log.warn("session is null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                if (session != null)
                    session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Unauthorized2"));
                else
                    log.warn("session is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("连接关闭了。。。");
        //获取用户名
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            onlineUsers.remove(user.getId());
        }
    }

    @OnError
    public void onError(Session session,Throwable error) {
        System.out.println("出错了。。。。" + error.getMessage());
    }
}
