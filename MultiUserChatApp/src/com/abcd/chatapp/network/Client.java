package com.abcd.chatapp.network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.abcd.chatapp.utils.ConfigReader;

public class Client {

		Socket  socket;
		OutputStream out;
		InputStream in;
		FileInputStream fileInputStream;
		DataOutputStream dataOutputStream = null;
		
//		JTextArea textArea;
		JTextPane textPane;
		ClientWorker worker;
		public Client(JTextPane textPane) throws UnknownHostException, IOException {
			int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
			socket=new Socket(ConfigReader.getValue("SERVER_IP"),PORT);
			out=socket.getOutputStream();
			in=socket.getInputStream();
			this.textPane=textPane;
			readMessage();
//			System.out.println("Client connected...");
//			System.out.println("Enter the message send to the Server..");
//			Scanner scanner=new Scanner(3kuSystem.in);
//			String message=scanner.nextLine();
//			OutputStream out=socket.getOutputStream();
//			out.write(message.getBytes());
//			System.out.println("Message send to the Server..");
//			scanner.close();
//			out.close();
//			socket.close();
		}
		
		public void sendMessage(String message) throws IOException
		{
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.writeInt(1);
			
			message=message +"\n";
			out.write(message.getBytes());
//			out.flush();
		}
		
		public void sendMessage(String name, File[] fileToSend) throws IOException
		{
//			message=message +"\n";
//			out.write(message.getBytes());
			fileInputStream = new FileInputStream(fileToSend[0].getAbsolutePath());
			dataOutputStream =  new DataOutputStream(socket.getOutputStream());
			
			String fileName = fileToSend[0].getName();
			byte[] fileNameBytes = fileName.getBytes();
			
			byte[] fileContentBytes = new byte[(int)fileToSend[0].length()];
			fileInputStream.read(fileContentBytes);
			dataOutputStream.writeInt(2);
			dataOutputStream.flush();
			dataOutputStream.writeInt(fileNameBytes.length);
			dataOutputStream.flush();
			dataOutputStream.write(fileNameBytes);
			dataOutputStream.flush();
			
			dataOutputStream.writeInt(fileContentBytes.length);
			dataOutputStream.flush();
			dataOutputStream.write(fileContentBytes);
			dataOutputStream.flush();
			System.out.println("send message called");
		}
		public void readMessage()
		{
			ClientWorker worker = new ClientWorker(in, textPane);
			worker.setSocket(socket);
			worker.start();
		}

		
}
