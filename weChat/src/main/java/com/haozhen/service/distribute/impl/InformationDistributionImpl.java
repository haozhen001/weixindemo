package com.haozhen.service.distribute.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haozhen.service.distribute.InformationDistributionService;
import com.haozhen.service.distribute.model.UserSequency;

@Service
public class InformationDistributionImpl implements InformationDistributionService {
	
	@Autowired
	private DistributionData distributionData;

	@Override
	public String selectOneServer() {
		if(distributionData.serverSessionSequency.size()>0) {
			UserSequency userSequency = distributionData.serverSessionSequency.iterator().next();
			userSequency.getNum().incrementAndGet();
			return userSequency.getUserid();
		}
		return null;
	}

	@Override
	public void toClientSendMessage(String userid, String message) {
		// TODO Auto-generated method stub
		List<Session> sessions = distributionData.clientSession.get(userid);
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
	}



	@Override
	public void toServerSendMessage(String serveruserid, String message) {
		// TODO Auto-generated method stub
		List<Session> sessions = distributionData.clientSession.get(serveruserid);
		for(Session session :sessions) {
			if(session.isOpen())
				session.getAsyncRemote().sendText(message);
		}
	}

	@Override
	public void clientConnect(String clientuserid,Session session) {
		//将session 添加到 管理器中
		List<Session> sessions = null;
		if(distributionData.clientSession.containsKey(clientuserid)) {
			sessions  = distributionData.clientSession.get(clientuserid);
		}else {
			sessions = new CopyOnWriteArrayList<Session>();
		}
		sessions.add(session);
		String serveruserid = selectOneServer();
		if(Objects.equals(null, serveruserid)) {
			sendNoServerConnect(session);
			distributionData.noConnectClient.add(clientuserid);
			return;
		}
		distributionData.clientToServer.putIfAbsent(clientuserid,serveruserid);
	}
	
	public void sendNoServerConnect(Session session) {
		if(session.isOpen()) {
			try {
				session.getBasicRemote().sendText("当前服务人员休息中，系统已经通知了服务人员，请耐心等待...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void reConnectNewServer(String oldServeruserid) {
		Iterator<Entry<String, String>> it =  distributionData.clientToServer.entrySet().iterator();
    	while(it.hasNext()) {
    		Entry<String, String> entry = it.next();
    		String key = entry.getKey();
    		String value = entry.getValue();
    		if(Objects.equals(value, oldServeruserid)) {
    			String serverid = selectOneServer();
    			distributionData.clientToServer.replace(key, oldServeruserid, serverid);
    		}
    	}
		
	}
	/**
	 * 客服连接服务
	 */
	@Override
	public void serverConnect(String serveruserid, Session session) {
		List<Session> sessions = null;
		if(distributionData.serverSession.containsKey(serveruserid)) {
			sessions  = distributionData.serverSession.get(serveruserid);
		}else {
			sessions = new CopyOnWriteArrayList<Session>();
		}
		sessions.add(session);		
		if(!distributionData.serverSessionSequencyMap.containsKey(serveruserid)) {
			UserSequency sequency = new UserSequency();
			sequency.setUserid(serveruserid);
			distributionData.serverSessionSequencyMap.put(serveruserid, sequency);
			distributionData.serverSessionSequency.add(sequency);
		}
	}

	@Override
	public void clientClose(String clientuserid) {
		distributionData.clientToServer.remove(clientuserid);
		distributionData.noConnectClient.remove(clientuserid);
		distributionData.clientSession.remove(clientuserid);
	}

	@Override
	public void serverClose(String serveruserid) {
		distributionData.serverSession.remove(serveruserid);
		UserSequency usersequency = distributionData.serverSessionSequencyMap.remove(serveruserid);
		distributionData.serverSessionSequency.remove(usersequency);
		reConnectNewServer(serveruserid);
	}
}
