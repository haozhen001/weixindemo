package com.haozhen.websocket.endpoint;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.haozhen.service.distribute.InformationDistributionService;
import com.haozhen.service.distribute.model.ServerMessage;
import com.haozhen.service.distribute.model.UserSequency;
import com.haozhen.service.distribute.util.DistributionUtil;
import com.haozhen.service.distribute.util.GsonUtil;

@Component
@ServerEndpoint("/weChat/{role}/{userid}")
public class ChatInformationEndpoint {

    public static int onlineNumber = 0;
    
    public static InformationDistributionService distributionService;
    
    
    public static InformationDistributionService getDistributionService() {
		return distributionService;
	}
    @Autowired
	public static void setDistributionService(InformationDistributionService distributionService) {
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
        List<Session> sessions = getSessions(role, userid);
        //添加session
        sessions.add(session);
        if(Objects.equals("server", role)) {
        	//添加客服 承載数
        	addServerChatNum(role,userid);
        }else if(Objects.equals("client", role)) {
        	//为客户选择客服
        	distributionService.setConnectWithClient(userid);
        }
        System.out.println("建立一条新的连接： 当前连接数" + onlineNumber);
    }
    
    private void addServerChatNum(String role, String userid) {
    	if(Objects.equals("server", role)) {
    		if(!DistributionUtil.serverSessionSequency.contains(userid)) {
    			UserSequency userSequency = new UserSequency();
    			DistributionUtil.serverSessionSequency.add(userSequency);
    		}
    	}
	}
    private void revomeServerChat(String role, String userid) {
    	if(Objects.equals("client", role)) {
    		String serveruserid = DistributionUtil.clientToServer.get(userid);
    		if(DistributionUtil.serverSessionSequency.contains(serveruserid)) {
    			for(UserSequency userSequency:DistributionUtil.serverSessionSequency) {
    				if(Objects.equals(serveruserid, userSequency.getUserid()))
    					userSequency.getNum().decrementAndGet();
    			}
    		}
    	}else if(Objects.equals("server", role)) {
    		if(DistributionUtil.serverSessionSequency.contains(userid)) {
    			DistributionUtil.serverSessionSequency.remove(userid);
    		}
    	}
	}
	private List<Session> getSessions(String role,String userid){
    	List<Session> sessions = null;
        if(Objects.equals(role, "client")) {
        	if(DistributionUtil.clientSession.containsKey(userid)) {
        		sessions = new CopyOnWriteArrayList<Session>();
        		DistributionUtil.clientSession.putIfAbsent(userid, sessions);
        	}else {
        		sessions = DistributionUtil.clientSession.get(userid);
        	}
        }else if(Objects.equals(role, "server")){
        	if(DistributionUtil.serverSession.containsKey(userid)) {
        		sessions = new CopyOnWriteArrayList<Session>();
        		DistributionUtil.serverSession.putIfAbsent(userid, sessions);
        	}else {
        		sessions = DistributionUtil.serverSession.get(userid);
        	}
        }
        return sessions;
    }

    @OnClose
    public void onClose(Session session,@PathParam("role")String role,@PathParam("userid")String userid)
    {
        onlineNumber--;
        List<Session> sessions = getSessions(role, userid);
        sessions.remove(session);
        revomeServerChat(role, userid);
        if(Objects.equals(role, "client")) {
        	DistributionUtil.clientToServer.remove(userid);
        }else if(Objects.equals(role, "server")) {
        	distributionService.reConnectNewServer(userid);
        }
        System.out.println("关闭一条连接 当前连接数" + onlineNumber);
    }

    @OnMessage
    public void onMessage(String message, Session session,@PathParam("role")String role,@PathParam("userid")String userid)
    {
    	if(Objects.equals(role, "client")) {
    		distributionService.clientSendMessage(userid, message);
        }else if(Objects.equals(role, "server")) {
        	ServerMessage serverMessage = GsonUtil.getGson().fromJson(message, ServerMessage.class);
        	distributionService.serverSendMessage(serverMessage.getUserid(), serverMessage.getMessage());
        }
    }

}
