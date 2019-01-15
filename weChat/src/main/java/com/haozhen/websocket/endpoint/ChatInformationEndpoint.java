package com.haozhen.websocket.endpoint;

import java.util.Objects;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haozhen.service.distribute.InformationDistributionService;
import com.haozhen.service.distribute.model.ServerMessage;
import com.haozhen.service.distribute.util.GsonUtil;

@Component
@ServerEndpoint("/weChat/{role}/{userid}")
public class ChatInformationEndpoint {

    public static int onlineNumber = 0;
    
    public static InformationDistributionService distributionService;
    
    
    @Autowired
	public void setDistributionService(InformationDistributionService distributionService) {
		ChatInformationEndpoint.distributionService = distributionService;
	}

    /**
     	* 打开连接
     * @param session
     * @param role
     * @param userid
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("role")String role,@PathParam("userid")String userid)
    {
        onlineNumber++;
        String roleName = null;
        if(Objects.equals("server", role)) {
        	roleName = "客服人员";
        	distributionService.serverConnect(userid, session);
        }else if(Objects.equals("client", role)) {
        	roleName = "客户人员";
        	distributionService.clientConnect(userid, session);
        }
        System.out.println("建立一条新的连接： 当前连接身份是"+roleName+ "  当前总的连接数" + onlineNumber);
    }
    
    @OnClose
    public void onClose(Session session,
    		@PathParam("role")String role,@PathParam("userid")String userid){
        onlineNumber--;
        String roleName = null;
        if(Objects.equals(role, "client")) {
        	roleName = "客户人员";
        	distributionService.clientClose(userid);
        }else if(Objects.equals(role, "server")) {
        	roleName = "客服人员";
        	distributionService.serverClose(userid);
        }
        System.out.println("关闭一条连接  当前连接身份是"+roleName+ " 当前连接数" + onlineNumber);
    }

    @OnMessage
    public void onMessage(String message, Session session,
    		@PathParam("role")String role,@PathParam("userid")String userid){
    	if(Objects.equals(role, "client")) {
    		distributionService.toClientSendMessage(userid, message);
        }else if(Objects.equals(role, "server")) {
        	ServerMessage serverMessage = GsonUtil.getGson().fromJson(message, ServerMessage.class);
        	distributionService.toServerSendMessage(serverMessage.getUserid(), serverMessage.getMessage());
        }
    }

}
