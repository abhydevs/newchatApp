package com.abcd.chatapp.network;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
 
public class ServerWorker extends Thread {
	
	private Socket clientSocket;
	private InputStream in;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private OutputStream out;
	private Server server;
	int flag=0;
	public ServerWorker(Socket clientSocket, Server server) throws IOException
	{
		this.server=server;
		this.clientSocket=clientSocket;
		in=clientSocket.getInputStream();
		out=clientSocket.getOutputStream();
		dataInputStream = new DataInputStream(clientSocket.getInputStream());
		dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		System.out.println("New Client Comes...");     
	}
	
//	public void sendFile(byte[] fileNameBytes, byte[] fileContentBytes) throws IOException
//	{
//	   dataOutputStream.writeInt(fileNameBytes.length);
//	   dataOutputStream.write(fileNameBytes);
//	   dataOutputStream.writeInt(fileContentBytes.length);
//	   dataOutputStream.write(fileContentBytes);
//	   
//	}
	public void run() {
		// Read data from the client and broadcast the data to all
		
		BufferedReader br= new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while(true) 
			{
				
					flag = dataInputStream.readInt();
					System.out.println("value of flag at sever is"+flag);
					
					for(ServerWorker serverWorker : server.workers)
			    	{	    
			     		
					    serverWorker.dataOutputStream.writeInt(flag);
					    System.out.println("value of flag broadcasted is "+flag);
					    dataOutputStream.flush();
				    }
				if(flag ==1)
				{
			     	line = br.readLine();// \n
				    System.out.println("Line read..."+line);
				    if(line.equalsIgnoreCase("quit")) 
				     {
					   break; //client chat ends
				      }
				    line=line+"\n";
			     	for(ServerWorker serverWorker : server.workers)
			    	{	    
			     		
					    serverWorker.out.write(line.getBytes());
					    out.flush();
				    }
			     	System.out.println("msg send from server");
				}
				if (flag==2) 
				{
					
					int fileNameLength = dataInputStream.readInt();
					
					if(fileNameLength>0)
					{
						byte[] fileNameBytes = new byte[fileNameLength];
						dataInputStream.readFully(fileNameBytes,0,fileNameBytes.length);
//						String fileName = new String(fileNameBytes);
						
						int fileContentLength = dataInputStream.readInt();
//						
						if(fileContentLength>0)
						{
							byte[] fileContentBytes = new byte[fileContentLength];
							dataInputStream.readFully(fileContentBytes,0, fileContentLength);	
							
							for(ServerWorker serverWorker : server.workers)
		       	    	    {
								  serverWorker.dataOutputStream.writeInt(fileNameBytes.length);
								  dataOutputStream.flush();
								  serverWorker.dataOutputStream.write(fileNameBytes);
								  dataOutputStream.flush();
								  serverWorker.dataOutputStream.writeInt(fileContentBytes.length);
								  dataOutputStream.flush();
								   serverWorker.dataOutputStream.write(fileContentBytes);
								   dataOutputStream.flush();
			    	
						    }
 						}
						System.out.println("file send from server");
					}
				}	
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		finally {
			try {
			if(br!=null) {
				br.close();
			}
			if(in!=null) {
				in.close();
			}
			if(out!=null) {
				out.close();
			}
			if(clientSocket!=null) {
				clientSocket.close();
			}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
}
