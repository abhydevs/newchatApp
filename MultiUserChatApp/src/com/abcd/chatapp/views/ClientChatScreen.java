package com.abcd.chatapp.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.abcd.chatapp.network.Client;
import com.abcd.chatapp.utils.UserInfo;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.MatteBorder;

public class ClientChatScreen extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
//	private JTextArea textArea;
	private JTextPane textPane;
	private Client client;
	final File[] fileToSend = new File[1];

	public static void main(String[] args) {
					try {
						ClientChatScreen frame = new ClientChatScreen();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}
	
	private void sendIt(File[] fileToSend)
	{
//		System.out.println("send file method called");
//		String message=textField.getText();
		try 
		{
			client.sendMessage(UserInfo.USER_NAME , fileToSend);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block 
			e.printStackTrace();
//			System.out.println("send file method called");
		}
	}
	private void sendIt() 
	{
		String message=textField.getText();
		try {
			client.sendMessage(UserInfo.USER_NAME+" - "+ message);
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}

	public ClientChatScreen() throws UnknownHostException, IOException {
//		textArea= new JTextArea();
		textPane = new JTextPane();
		textPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		textPane.setBackground(new Color(255, 255, 255));
		textPane.setEditable(false);
		 textPane.setPreferredSize(new Dimension(200, 300));
//		client=new Client(textArea);
		client = new Client(textPane);
		setTitle("Chit Chat "+UserInfo.USER_NAME );
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 974, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 945, 320);
		contentPane.add(scrollPane);
		
//		
//		textArea.setFont(new Font("Courier New", Font.PLAIN, 16));
//		textArea.setBounds(21, 10, 918, 340);
		scrollPane.setViewportView(textPane);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setBounds(10, 359, 644, 52);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton sendit = new JButton("Send Message");
		sendit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					sendIt();
				}
		});
		sendit.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		sendit.setBounds(745, 344, 109, 37);
		contentPane.add(sendit);
		
		JButton btnNewButton = new JButton("Send File");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser  jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Choose a File to Send");
				
				if(jFileChooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
				{
					fileToSend[0]=jFileChooser.getSelectedFile();
			        int option = JOptionPane.showConfirmDialog(null, "Do you want to send file: "+ fileToSend[0].getName(), "Confirmation", JOptionPane.YES_NO_OPTION);
			        if (option == JOptionPane.YES_OPTION) {
			            System.out.println("User clicked Yes");
			            sendIt(fileToSend);
			        } 
			        else
			        {
			            System.out.println("User clicked No");
			        }
//					jFileName.setText("The File You  want to Send is: " + fileToSend[0].getName());
				}
			}
		});
		btnNewButton.setBounds(745, 391, 109, 37);
		contentPane.add(btnNewButton);
		setVisible(true);
	}
}
