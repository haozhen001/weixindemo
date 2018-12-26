package com.haozhen.service.distribute.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.websocket.Session;

import com.haozhen.service.distribute.model.UserSequency;

public class DistributionUtil {

	public static Set<UserSequency> cleintSessionSequency = new ConcurrentSkipListSet<UserSequency>();
	
	public static Set<UserSequency> serverSessionSequency = new ConcurrentSkipListSet<UserSequency>();
	
    public static Map<String, List<Session>> clientSession = new ConcurrentHashMap<String, List<Session>>();

    public static Map<String, List<Session>> serverSession = new ConcurrentHashMap<String, List<Session>>();
    
    public static Map<String, String> clientToServer = new ConcurrentHashMap<String, String>();
}
