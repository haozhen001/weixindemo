package com.haozhen.service.distribute;

import javax.websocket.Session;

/**
 * websocket 服务分发器
 * @author haozhen
 */
public interface InformationDistributionService {
	
	/**
	 * 客服下线，对应的客户尝试重新连接其他客服
	 * @param oldServeruserid
	 */
	public void reConnectNewServer(String oldServeruserid);
	
	public void clientClose(String clientuserid);
	
	public void serverClose(String serveruserid);
	/**
	 * 连接服务
	 * @param clientuserid
	 */
	public void clientConnect(String clientuserid,Session session);

	public void serverConnect(String serveruserid,Session session); 
	/**
	 * 选择一个客户连接
	 * @return
	 */
	public String selectOneServer();
	
	/**
	 * 向客户发送信息
	 * @param userid
	 * @param message
	 */
	public void toClientSendMessage(String userid,String message);
	
	
	/**
	 * 向客服发送消息
	 * @param serveruserid
	 * @param message
	 */
	public void toServerSendMessage(String serveruserid,String message);
	
}
