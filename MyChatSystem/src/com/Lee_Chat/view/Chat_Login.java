package com.Lee_Chat.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.Lee_Chat.comon.Message;
import com.Lee_Chat.comon.MessageType;
import com.Lee_Chat.tools.ClientThreadManager;

public class Chat_Login {

	private JFrame frame;
	private JTextField textField;
//	private String hostip = "127.0.0.1";
	private String hostip = null;
	private JTextField Prot_textField;
	private JLabel errorLabel;
	private int Portnum;

	// 得到显示器屏幕的宽高
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	public int windowsWedth = 447;
	public int windowsHeight = 220;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Chat_Login window = new Chat_Login();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	@SuppressWarnings("static-access")
	public Chat_Login() {
		initialize();
		frame.setVisible(true);
		// 当关闭子窗口时，父窗口不必一起关闭
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		// 禁止窗体拉伸
		frame.setResizable(false);
		// 窗体居中显示
		frame.setBounds((width - windowsWedth) / 2,
				(height - windowsHeight) / 2, windowsWedth, windowsHeight);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, windowsWedth, windowsHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel UserLabel = new JLabel("用户ID：");
		UserLabel.setBounds(49, 24, 72, 18);
		frame.getContentPane().add(UserLabel);

		textField = new JTextField(12);
		textField.setBounds(122, 21, 234, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel ProtLabel = new JLabel("端 口：");
		ProtLabel.setBounds(49, 75, 72, 18);
		frame.getContentPane().add(ProtLabel);

		Prot_textField = new JTextField("0", 12);
		Prot_textField.setBounds(122, 72, 104, 24);
		frame.getContentPane().add(Prot_textField);
		Prot_textField.setColumns(10);

		errorLabel = new JLabel("");
		errorLabel.setBounds(240, 75, 175, 18);
		frame.getContentPane().add(errorLabel);

		JButton LoginButton = new JButton("登　录");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socket socket;
				Portnum = Integer.parseInt(Prot_textField.getText());
				try {
					if (Portnum == 0) {
						errorLabel.setText("请输入服务器正确的端口号！");
					} else {
						InetAddress hostaddress = InetAddress.getLocalHost();
						hostip=hostaddress.getHostAddress();
						socket = new Socket(hostip, Portnum);
						ObjectOutputStream write = new ObjectOutputStream(
								socket.getOutputStream());
						Message msg = new Message();
						msg.setMessageType(MessageType.checklogin);
						msg.setSender(textField.getText());
						msg.setContent("kkkkkk");
						write.writeObject(msg);

						ObjectInputStream read = new ObjectInputStream(socket
								.getInputStream());
						msg = (Message) read.readObject();
						if (msg.getMessageType() == MessageType.checklogin) {
							if (msg.getContent().equals("ok")) {
								msg.setMessageType(MessageType.getfriend);
								msg.setSender(msg.getGetter());
								write.writeObject(msg);
								Chat_Client user = new Chat_Client(textField
										.getText(), socket, write);
								ClientThreadManager.addClient(
										textField.getText(), user);
								frame.setVisible(false);
							} else {
								JOptionPane.showMessageDialog(frame,
										"您登录失败,可能是密码错误!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame,
							"登录错误,服务器异常,请联系管理员开启服务!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		LoginButton.setBounds(75, 118, 113, 27);
		// 按enter键时即可登录
		frame.getRootPane().setDefaultButton(LoginButton);
		frame.getContentPane().add(LoginButton);

		JButton ResetButton = new JButton("重 置");
		ResetButton.setBounds(243, 118, 113, 27);
		frame.getContentPane().add(ResetButton);

	}
}
