package com.Lee_Chat.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.Lee_Chat.comon.Message;
import com.Lee_Chat.comon.MessageType;

public class Chat_Client implements Runnable{

	private JFrame frame;
	private Socket socket;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private JTextArea Output_textArea, Input_textArea;
	private int count = 0;
	ObjectOutputStream write;
	ObjectInputStream read;
	String ownerId;
	String friendId;

	/**
	 * Launch the application.
	 */

	@SuppressWarnings("unchecked")
	public void updateFriend(String[] friends) {
		if (count != 0) {
			JOptionPane.showMessageDialog(frame, "您的好友" + friends[0] + "上线了!",
					"上线提示ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
		for (String friend : friends) {
			comboBox.addItem(friend);
		}
		count++;
	}

	public void sendMsgToYou(Message msg) {
		Output_textArea.append(msg.getSender() + "    " + msg.getDate()
				+ "\r\n");
		Output_textArea.append("   " + msg.getContent() + "\r\n");
	}

	SimpleDateFormat formater = new SimpleDateFormat("YYYY/MM/dd/ hh:mm:ss");
	
	/**
	 * Create the application.
	 */
	@SuppressWarnings("static-access")
	public Chat_Client(String ownerId, Socket s, ObjectOutputStream write) {
		this.ownerId = ownerId;
		this.socket = s;
		this.write = write;
		initialize();
		frame.setTitle("用户: " + ownerId);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("rawtypes")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 563, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Output_textArea = new JTextArea();
		Output_textArea.setBounds(14, 39, 517, 225);
		Output_textArea.setEditable(false);
		frame.getContentPane().add(Output_textArea);

		comboBox = new JComboBox();
		comboBox.setBounds(14, 2, 65, 24);
		frame.getContentPane().add(comboBox);

		Input_textArea = new JTextArea();
		Input_textArea.setBounds(14, 277, 517, 63);
		frame.getContentPane().add(Input_textArea);

		JButton SendButton = new JButton("发 送");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message();
				friendId = comboBox.getSelectedItem().toString();
				msg.setMessageType(MessageType.chat_msg);
				msg.setGetter(friendId);
				msg.setSender(ownerId);
				if (friendId.equals(ownerId)) {
					JOptionPane.showMessageDialog(frame, "您正在和自己发送消息!", "提示ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				}
				msg.setContent(Input_textArea.getText());
				msg.setDate(formater.format(new Date()));
				if (e.getSource() == SendButton) {
					String str = msg.getSender() + " 给 " + msg.getGetter()
							+ "  发送消息:" + msg.getContent();
					System.out.println(str);
					Output_textArea.append(ownerId + "    " + msg.getDate()
							+ "\r\n");
					Output_textArea.append("   " + msg.getContent() + "\r\n");
					Input_textArea.setText("");
					try {
						write.writeObject(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		SendButton.setBounds(418, 353, 113, 27);
		// 按enter键时即可发送
		frame.getRootPane().setDefaultButton(SendButton);
		frame.getContentPane().add(SendButton);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				read = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) read.readObject();
				if (msg.getMessageType() == MessageType.chat_msg) {
					sendMsgToYou(msg);
				} else if (msg.getMessageType() == MessageType.returnfriend) {
					String[] friends = msg.getContent().split("_");
					updateFriend(friends);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
