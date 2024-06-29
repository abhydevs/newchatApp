package com.abcd.chatapp.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.abcd.chatapp.dao.UserDAO;
import com.abcd.chatapp.dto.UserDTO;
import com.abcd.chatapp.utils.UserInfo;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class UserScreen extends JFrame{
	private static final int INFORMATION_MESSAGE = 0;
	private JTextField useridtxt;
	private JPasswordField passwordField;
	
	UserDAO userDAO=new UserDAO();
	
	private void doLogin() {
		String userid=useridtxt.getText();
		char password[]=passwordField.getPassword();
		UserDTO userDTO=new UserDTO(userid, password); 
		try {
			String message="";
			if(userDAO.isLogin(userDTO)) {
				message="Welcome "+userid;
				UserInfo.USER_NAME=userid;
//				JOptionPane.showMessageDialog(this, message);
				Icon icon = new ImageIcon(UserScreen.class.getResource("/images/icons8-ok-100.png"));
				JOptionPane.showMessageDialog(this, message, "Login Successful", INFORMATION_MESSAGE, icon);
				setVisible(false);
				dispose();
				DashBoard dashBoard=new DashBoard(message);
				dashBoard.setVisible(true);
			}
			else {
				message="Invalid Userid or Password";
				JOptionPane.showMessageDialog(this, message);
			}
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void register() {
		String userid=useridtxt.getText();
		char password[]=passwordField.getPassword();
		UserDTO userDTO=new UserDTO(userid, password);
		try {
			int result=userDAO.add(userDTO);
			if(result>0) {
//				JOptionPane.showMessageDialog(this, "Registered Successfully");
				Icon icon = new ImageIcon(UserScreen.class.getResource("/images/icons8-ok-100.png"));
				JOptionPane.showMessageDialog(this, "Registered Successfully", "Done", INFORMATION_MESSAGE, icon);
			}
			else {
				JOptionPane.showMessageDialog(this, "Registered Fail");
			}
		}
		catch (ClassNotFoundException |SQLException ex) {
			System.out.println("DB Issue...");
			ex.printStackTrace();
		}
		catch (Exception ex) {
			System.out.println("Some Generic Exception Raised...");
			ex.printStackTrace();
		}
		System.out.println("userid "+userid+" password "+password);
	}
	
	public UserScreen() {
		setResizable(false);
		setTitle("Login");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(298, 46, 148, 83);
		getContentPane().add(lblNewLabel);
		
		useridtxt = new JTextField();
		useridtxt.setBounds(390, 171, 263, 38);
		getContentPane().add(useridtxt);
		useridtxt.setColumns(10);
		
		JLabel useridlbl = new JLabel("Userid");
		useridlbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		useridlbl.setBounds(128, 171, 155, 38);
		getContentPane().add(useridlbl);
		
		JLabel pwdlbl = new JLabel("Password");
		pwdlbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		pwdlbl.setBounds(128, 267, 155, 38);
		getContentPane().add(pwdlbl);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(390, 268, 263, 37);
		getContentPane().add(passwordField);
		
		JButton loginbt = new JButton("Login");
		loginbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		loginbt.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginbt.setBounds(184, 374, 131, 38);
		getContentPane().add(loginbt);
		
		JButton registerbt = new JButton("Register");
		registerbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		registerbt.setFont(new Font("Tahoma", Font.BOLD, 13));
		registerbt.setBounds(379, 374, 131, 38);
		getContentPane().add(registerbt);
		setBackground(Color.WHITE);
		setSize(726, 498);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		UserScreen window= new UserScreen();
	}	
}
