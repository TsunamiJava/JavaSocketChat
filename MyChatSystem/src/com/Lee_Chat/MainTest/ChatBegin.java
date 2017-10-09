package com.Lee_Chat.MainTest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.Lee_Chat.view.Chat_Login;
import com.Lee_Chat.view.Server_JFrame;

public class ChatBegin {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatBegin window = new ChatBegin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatBegin() {
		initialize();
		frame.setResizable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 448, 149);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 432, 50);
		frame.getContentPane().add(panel);

		JButton Server_Button = new JButton("创建服务器");
		Server_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Server_JFrame();
			}
		});
		panel.add(Server_Button);

		JButton UserButton = new JButton("用户创建");
		UserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Chat_Login();
			}
		});
		panel.add(UserButton);

		JButton FinishButton = new JButton("退  出");
		FinishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		FinishButton.setBounds(160, 63, 113, 27);
		frame.getContentPane().add(FinishButton);
	}
}
