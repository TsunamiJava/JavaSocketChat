package com.Lee_Chat.tools;

import java.util.HashMap;

import com.Lee_Chat.view.Chat_Client;

public class ClientThreadManager {
	private static HashMap<String, Chat_Client> ht = new HashMap<String, Chat_Client>();

	public static void addClient(String key, Chat_Client value) {
		ht.put(key, value);
	}

	public static Chat_Client getClient(String key) {
		return ht.get(key);
	}
}
