package com.haozhen.service.distribute;

import javax.websocket.Session;

/**
 * 用于分发消息的服务
 * @author haozhen
 *
 */
public interface InformationDistributionService {

	/**
	 * 选择一个客服回话
	 * @return
	 */
	public Session selectOneServerSession();
	
	/**
	 * 客户发送消息
	 * @param client
	 * @param message
	 */
	public void clientSendMessage(Session client,String message);
	
	/**
	 * 获取客服对话的客户session 用于支持一对多的发送消息
	 * @param server
	 * @param clientId
	 * @return
	 */
	public Session getClientSession(Session server,String clientId);
	
	/**
	 * 客服获取对话的客服session
	 * @param server
	 * @return
	 */
	public Session getClientSession(Session server);
	
	/**
	 * 客服发送消息
	 * @param server
	 * @param message
	 */
	public void serverSendMessage(Session server,String message);
	
}
