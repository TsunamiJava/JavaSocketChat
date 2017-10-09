package com.Lee_Chat.tools;

import java.net.Socket;
import java.util.HashMap;

public class ServerSocketManager {
	// 创建一个hashMap
	private static HashMap<String, Socket> ht = new HashMap<String, Socket>();

	public static void addServerSocket(String key, Socket value) {
		ht.put(key, value);
	}

	public static Socket getServerSocket(String key) {
		return ht.get(key);
	}

	public static String[] getServerScoketList() {
		String[] list = new String[ht.size()];
		int i = 0;
		for (String s : ht.keySet()) {
			list[i++] = s;
		}
		return list;
	}
}
