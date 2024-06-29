package com.abcd.chatapp.network;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import com.abcd.chatapp.utils.ConfigReader;

public class Server {		
		ServerSocket serverSocket;
		ArrayList<ServerWorker> workers=new ArrayList<>();
		
		public Server() throws IOException{
			int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
			serverSocket=new ServerSocket(PORT);
			System.out.println("Server start and waiting for the clients to join..");
			handleClientRequest(); 
		}
		 
		public void handleClientRequest() throws IOException {
			while(true) {
				Socket clientSocket= serverSocket.accept(); // HandShaking
				// per client per thread
				ServerWorker serverWorker=new ServerWorker(clientSocket,this); //creating a new worker/thread
				workers.add(serverWorker);
				serverWorker.start();
			}
		}
		
		/* Single Client */ 
		/* 
		public Server() throws IOException {
			int PORT=Integer.parseInt(ConfigReader.getValue("PORTNO"));
			serverSocket=new ServerSocket(PORT);
			System.out.println("Server started and waiting for the client connection...");
			Socket socket= serverSocket.accept(); // HandShaking
			System.out.println("Client joins the Server");
			InputStream in=socket.getInputStream();
			byte arr[]=in.readAllBytes();
			String str=new String(arr);
			System.out.println("Message Rec From the client "+str);
			in.close();
			socket.close();
		}*/ 
		
	public static void main(String[] args) throws IOException {
		Server server=new Server();
		

	}

}
