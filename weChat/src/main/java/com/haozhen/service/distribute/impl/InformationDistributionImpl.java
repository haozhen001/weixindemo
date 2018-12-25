package com.haozhen.service.distribute.impl;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.haozhen.service.distribute.InformationDistributionService;

@Service
public class InformationDistributionImpl implements InformationDistributionService {

	public Session selectOneServerSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clientSendMessage(Session client, String message) {
		// TODO Auto-generated method stub

	}

	public Session getClientSession(Session server, String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session getClientSession(Session server) {
		// TODO Auto-generated method stub
		return null;
	}

	public void serverSendMessage(Session server, String message) {
		// TODO Auto-generated method stub

	}

}
