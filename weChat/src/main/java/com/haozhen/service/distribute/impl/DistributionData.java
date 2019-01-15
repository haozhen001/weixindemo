package com.haozhen.service.distribute.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.Session;

import org.springframework.stereotype.Component;

import com.haozhen.service.distribute.model.UserSequency;

/**
  * 连接数据中心
 * @author haozhen
 *
 */
@Component
public class DistributionData {

	protected Map<String, UserSequency> serverSessionSequencyMap = new ConcurrentHashMap<String, UserSequency>();
	/**
	 * 客服连接数集合
	 */
	protected  Set<UserSequency> serverSessionSequency = new ConcurrentSkipListSet<UserSequency>();
	/**
	 * 客户连接map
	 */
	protected  Map<String, List<Session>> clientSession = new ConcurrentHashMap<String, List<Session>>();
    /**
        * 客服连接map
     */
	protected  Map<String, List<Session>> serverSession = new ConcurrentHashMap<String, List<Session>>();
    /**
        * 客户与客服连接对应关系
     */
	protected  Map<String, String> clientToServer = new ConcurrentHashMap<String, String>();
	
	protected  List<String> noConnectClient = new CopyOnWriteArrayList<String>();
}
