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
     * ��������
     */
    public static int onlineNumber = 0;

    /**
     * ���еĶ���
     */
    public static List<Session> clientSession = new CopyOnWriteArrayList<Session>();

    public static List<Session> serverSession = new CopyOnWriteArrayList<Session>();
    /**
     * ��������
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
        System.out.println("�������Ӽ��룡 ��ǰ��������" + onlineNumber);
    }

    /**
     * ���ӹر�
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
        System.out.println("�����ӹرգ� ��ǰ��������" + onlineNumber);
    }

    /**
     * �յ��ͻ��˵���Ϣ
     *
     * @param message ��Ϣ
     * @param session �Ự
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        System.out.println("���Կͻ�����Ϣ��" + message);

        sendMessage("��ӭ����");
    }

    /**
     * ������Ϣ
     *
     * @param message ��Ϣ
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
