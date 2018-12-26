package com.haozhen.service.distribute;

import java.util.List;

import javax.websocket.Session;

/**
 * ���ڷַ���Ϣ�ķ���
 * @author haozhen
 *
 */
public interface InformationDistributionService {
	
	
	public void reConnectNewServer(String oldServeruserid);
	
	public void setConnectWithClient(String clientuserid);

	public String selectOneServer();
	

	public void clientSendMessage(String userid,String message);
	
	/**
	 * ��ȡ�ͷ��Ի��Ŀͻ�session ����֧��һ�Զ�ķ�����Ϣ
	 * @param server
	 * @param clientId
	 * @return
	 */
	public void getClientSession(String serveruserid,String clientuserid);
	
	
	/**
	 * �ͷ�������Ϣ
	 * @param server
	 * @param message
	 */
	public void serverSendMessage(String serveruserid,String message);
	
}
