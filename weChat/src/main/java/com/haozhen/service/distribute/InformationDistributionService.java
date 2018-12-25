package com.haozhen.service.distribute;

import javax.websocket.Session;

/**
 * ���ڷַ���Ϣ�ķ���
 * @author haozhen
 *
 */
public interface InformationDistributionService {

	/**
	 * ѡ��һ���ͷ��ػ�
	 * @return
	 */
	public Session selectOneServerSession();
	
	/**
	 * �ͻ�������Ϣ
	 * @param client
	 * @param message
	 */
	public void clientSendMessage(Session client,String message);
	
	/**
	 * ��ȡ�ͷ��Ի��Ŀͻ�session ����֧��һ�Զ�ķ�����Ϣ
	 * @param server
	 * @param clientId
	 * @return
	 */
	public Session getClientSession(Session server,String clientId);
	
	/**
	 * �ͷ���ȡ�Ի��Ŀͷ�session
	 * @param server
	 * @return
	 */
	public Session getClientSession(Session server);
	
	/**
	 * �ͷ�������Ϣ
	 * @param server
	 * @param message
	 */
	public void serverSendMessage(Session server,String message);
	
}
