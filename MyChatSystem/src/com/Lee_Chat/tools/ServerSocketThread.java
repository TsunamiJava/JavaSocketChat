package com.Lee_Chat.tools;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.Lee_Chat.comon.Message;
import com.Lee_Chat.comon.MessageType;
import com.Lee_Chat.view.Server_JFrame;

public class ServerSocketThread extends Thread {
	private Socket socket;
	private ObjectOutputStream write;
	private ObjectInputStream read;

	public ServerSocketThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServerSocketThread(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run() {
		try {
			int count = 0;
			while (true) {
				if (count++ == 0) {
					read = new ObjectInputStream(socket.getInputStream());
				}
				Message msg = (Message) read.readObject();
				Socket getter = ServerSocketManager.getServerSocket(msg
						.getGetter());
				if (msg.getMessageType() == MessageType.chat_msg) {
					System.out.println(msg.getSender() + " 对 "
							+ msg.getGetter() + "于" + msg.getDate() + "时说: "
							+ msg.getContent());
					Server_JFrame.loggerStr += msg.getSender() + " 对 "
							+ msg.getGetter() + "于" + msg.getDate() + "时说: "
							+ msg.getContent();
					Server_JFrame.putLoggerInfo();
					write = new ObjectOutputStream(getter.getOutputStream());
					write.writeObject(msg);
				} else if (msg.getMessageType() == MessageType.checklogin) {
					write = new ObjectOutputStream(socket.getOutputStream());
					if (msg.getContent().equals("kkkkkk")) {
						msg.setContent("ok");
						msg.setGetter(msg.getSender());
						write.writeObject(msg);
						ServerSocketManager.addServerSocket(msg.getSender(),
								socket);
					} else {
						msg.setContent("fiald");
						write.writeObject(msg);
					}
				} else if (msg.getMessageType() == MessageType.getfriend) {
					msg.setMessageType(MessageType.returnfriend);
					System.out.println(msg.getSender() + "想要获取在线好友!");
					Server_JFrame.loggerStr += msg.getSender() + "想要获取在线好友";
					Server_JFrame.putLoggerInfo();
					String[] keys = ServerSocketManager.getServerScoketList();
					StringBuffer sbf = new StringBuffer();
					for (int i = 0; i < keys.length; i++) {
						System.out.println("获取的好友:" + keys[i]);
						Server_JFrame.loggerStr += "获取的好友:" + keys[i];
						Server_JFrame.putLoggerInfo();
						sbf.append(keys[i] + "_");
						if (keys[i].equals(msg.getSender()))
							continue;
						Socket s = ServerSocketManager.getServerSocket(keys[i]);
						write = new ObjectOutputStream(s.getOutputStream());
						Message m = new Message();
						m.setMessageType(MessageType.returnfriend);
						m.setSender(msg.getSender());
						m.setContent(msg.getSender());
						m.setGetter(keys[i]);
						System.out.println(msg.getSender() + "想告诉  " + keys[i]
								+ ",他上线了!");
						Server_JFrame.loggerStr += msg.getSender() + "想告诉  "
								+ keys[i] + ",他上线了!";
						Server_JFrame.putLoggerInfo();
						write.writeObject(m);
					}
					write = new ObjectOutputStream(socket.getOutputStream());
					msg.setContent(sbf.toString());
					msg.setGetter(msg.getSender());
					write.writeObject(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
