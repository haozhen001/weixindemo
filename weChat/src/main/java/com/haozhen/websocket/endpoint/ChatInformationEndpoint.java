package com.haozhen.websocket.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/weChat/{role}")
public class ChatInformationEndpoint {

	/**
     * 在线人数
     */
    public static int onlineNumber = 0;

    /**
     * 所有的对象
     */
    public static List<Session> clientSession = new CopyOnWriteArrayList<Session>();

    public static List<Session> serverSession = new CopyOnWriteArrayList<Session>();
    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("role")String role)
    {
        onlineNumber++;
        if(Objects.equals(role, "client")) {
        	clientSession.add(session);
        }else if(Objects.equals(role, "server")){
        	serverSession.add(session);
        }
        System.out.println("有新连接加入！ 当前在线人数" + onlineNumber);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session,@PathParam("role")String role)
    {
        onlineNumber--;
        if(Objects.equals(role, "client")) {
        	clientSession.remove(session);
        }else if(Objects.equals(role, "server")){
        	serverSession.remove(session);
        }
        System.out.println("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        System.out.println("来自客户端消息：" + message);

        sendMessage("欢迎连接");
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message)
    {
        try
        {
            session.getBasicRemote().sendText(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
