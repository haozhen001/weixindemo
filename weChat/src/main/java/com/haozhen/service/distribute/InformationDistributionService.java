package com.haozhen.service.distribute;



public interface InformationDistributionService {
	
	
	public void reConnectNewServer(String oldServeruserid);
	
	public void setConnectWithClient(String clientuserid);

	public String selectOneServer();
	

	public void clientSendMessage(String userid,String message);
	
	public void getClientSession(String serveruserid,String clientuserid);
	
	
	public void serverSendMessage(String serveruserid,String message);
	
}
