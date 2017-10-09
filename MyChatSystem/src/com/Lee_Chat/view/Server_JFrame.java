package com.Lee_Chat.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.Lee_Chat.tools.ServerSocketThread;

import javax.swing.JTextField;

public class Server_JFrame implements Runnable {

	private JFrame frame;
	private static JTextArea textArea;
	private JButton ServerStart, ServerShutdown;
	private JLabel errorLabel;
	int Portnum;

	public static String loggerStr = "";
	private JTextField textField;
	
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Server_JFrame window = new Server_JFrame();
//					window.frame.setVisible(true);
//					window.frame.setTitle("聊天服务器");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	@SuppressWarnings("static-access")
	public Server_JFrame() {
		initialize();
		frame.setVisible(true);
		frame.setTitle("聊天服务器");
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 503, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textArea = new JTextArea();
		textArea.setBounds(14, 8, 330, 383);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);

		JLabel Host_Label = new JLabel("主机名称：");
		Host_Label.setBounds(351, 24, 84, 18);
		frame.getContentPane().add(Host_Label);

		JLabel IP_Label = new JLabel("主机IP地址：");
		IP_Label.setToolTipText("");
		IP_Label.setBounds(351, 73, 101, 18);
		frame.getContentPane().add(IP_Label);

		JLabel ShowHostLabel = new JLabel(" ");
		ShowHostLabel.setBounds(361, 45, 72, 18);
		// 获取电脑主机用户名
		ShowHostLabel.setText(System.getProperty("user.name"));
		frame.getContentPane().add(ShowHostLabel);

		JLabel ShowIPLabel = new JLabel(" ");
		ShowIPLabel.setBounds(359, 94, 104, 18);
		// 获取主机IP地址
		InetAddress hostaddress;
		try {
			hostaddress = InetAddress.getLocalHost();
			ShowIPLabel.setText(hostaddress.getHostAddress());
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		frame.getContentPane().add(ShowIPLabel);

		ServerStart = new JButton("开启服务器");
		ServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openServer();
			}
		});
		ServerStart.setBounds(358, 261, 113, 27);
		frame.getContentPane().add(ServerStart);

		ServerShutdown = new JButton("关闭服务器");
		ServerShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeServer();
			}
		});
		ServerShutdown.setBounds(358, 310, 113, 27);
		frame.getContentPane().add(ServerShutdown);

		JLabel PortLabel = new JLabel("设置端口：");
		PortLabel.setBounds(352, 124, 84, 18);
		frame.getContentPane().add(PortLabel);

		textField = new JTextField("0", 12);
		textField.setBounds(358, 155, 105, 24);
		frame.getContentPane().add(textField);
		Portnum = Integer.parseInt(textField.getText());
		textField.setColumns(10);

		errorLabel = new JLabel("");
		errorLabel.setBounds(358, 192, 113, 18);
		frame.getContentPane().add(errorLabel);
	}

	public void openServer() {
		Thread t = new Thread(this);
		t.start();
	}

	public static void putLoggerInfo() {
		loggerStr += "\r\n";
		textArea.setText(loggerStr);
	}

	ServerSocket serversocket;

	public void closeServer() {
		System.out.println("服务器已关闭端口的监听.........");
		loggerStr += "服务器已关闭端口的监听.........";
		putLoggerInfo();
		int option = JOptionPane.showConfirmDialog(null, "是否关闭服务器？", "关闭服务器",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		switch (option) {
		case JOptionPane.YES_NO_OPTION: {
			System.exit(0);
		}
		case JOptionPane.NO_OPTION:
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Portnum = Integer.parseInt(textField.getText());
			if (Portnum == 0) {
				errorLabel.setText("端口异常");
			} else {
				serversocket = new ServerSocket(Portnum);
				System.out.println("服务器正在端口" + serversocket.getLocalPort()
						+ "监听.........");
				loggerStr += "服务器正在端口" + serversocket.getLocalPort()
						+ "监听.........";
				putLoggerInfo();
				JOptionPane.showMessageDialog(frame, "服务已经打开,点击确定继续!", "成功",
						JOptionPane.INFORMATION_MESSAGE);
				ServerStart.setEnabled(false);
				while (true) {
					Socket s = serversocket.accept();
					ServerSocketThread sst = new ServerSocketThread(s);
					sst.start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
