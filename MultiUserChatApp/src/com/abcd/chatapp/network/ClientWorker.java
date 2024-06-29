package com.abcd.chatapp.network;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.PseudoColumnUsage;

import javax.management.loading.PrivateClassLoader;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.*;
import javax.swing.Icon;

// Client Data read
public class ClientWorker extends Thread
{
	private InputStream in;
//	private JTextArea textArea;
	private JTextPane textPane;
	private DataInputStream dataInputStream;
	private FileOutputStream fileOutputStream;
	private Socket socket;
	int flag =0;
	StyledDocument doc;
    private static final int INFORMATION_MESSAGE = 0;
	
	public ClientWorker(InputStream in, JTextPane textPane) 
	{
		this.in=in;
//		this.textArea=textArea;
		this.textPane=textPane;
		this.doc = textPane.getStyledDocument();
		
	}
	
	public void setSocket(Socket socket)
	{
        this.socket = socket;
        
    }
	
	private void setTextInJTextPane(String text) throws BadLocationException 
	{
//         doc = textPane.getStyledDocument();
        SimpleAttributeSet standard = new SimpleAttributeSet();
        StyleConstants.setFontFamily(standard, "SansSerif");
        StyleConstants.setFontSize(standard, 12);

       // Clear existing text
            doc.insertString(doc.getLength(), text + "\n", standard); // Insert new text
    }

	private void appendLabelToPane(JLabel label)
	{
		javax.swing.text.Style style = doc.addStyle("LabelStyle", null);
		StyleConstants.setComponent(style, label);
		
		try {
			doc.insertString(doc.getLength(),"ignored text", style);
			setTextInJTextPane(" ");
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void downloadFile(String fileName, byte[]fileContentBytes) throws IOException
	{
		int option = JOptionPane.showConfirmDialog(null, "Do you want to Download file: "+ fileName, "Confirmation", JOptionPane.YES_NO_OPTION);
		  if (option == JOptionPane.YES_OPTION)
		  {
			  String folderPath = "C:\\Users\\abhim\\OneDrive\\Desktop\\chatappfiles";
			  String filePath = folderPath + File.separator + fileName;

			  try {
					fileOutputStream = new FileOutputStream(filePath);
					 fileOutputStream.write(fileContentBytes); // Write the byte array to the file
			            System.out.println("File has been created successfully at: " + filePath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  ImageIcon icon2 = new ImageIcon(ClientWorker.class.getResource("/images/icons8-ok-100.png"));
   			  JOptionPane.showMessageDialog(textPane,"File Successfully Downloaded", "Done", INFORMATION_MESSAGE, icon2);

	       } 
		
	}
	

	public void run() 
	{
		
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String line;
		
		try 
		{
			dataInputStream = new DataInputStream(socket.getInputStream());
			
			while(true) 
			{
				
				 flag = dataInputStream.readInt();
				System.out.println("value of flag at client side is"+ flag);
				if(flag==1)
				{
				   line=br.readLine();// \n
				   System.out.println("Line read..."+line);
//				   textArea.setText(textArea.getText()+line+"\n");
				   setTextInJTextPane(line);
				} 
				if(flag==2)
				{
					
                    int fileNameLength = dataInputStream.readInt();
//					
//					if(fileNameLength>0)
//					{
						byte[] fileNameBytes = new byte[fileNameLength];
						dataInputStream.readFully(fileNameBytes,0,fileNameBytes.length);
						String fileName = new String(fileNameBytes);
//					textArea.setText(textArea.getText()+"file recieved:"+fileName+ "\n");
						System.out.println(fileName);
//						
						int fileContentLength = dataInputStream.readInt();
//						
//						if(fileContentLength>0)
//						{
							byte[] fileContentBytes = new byte[fileContentLength];
							dataInputStream.readFully(fileContentBytes,0, fileContentLength);	
//						}
//					}
							 ImageIcon icon = new ImageIcon("C:\\Users\\abhim\\OneDrive\\Desktop\\MultiUserChatApp\\src\\images\\downloading.png");
							 Image resizedImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					            ImageIcon resizedIcon = new ImageIcon(resizedImage);
					            JLabel label = new JLabel(fileName,resizedIcon,JLabel.RIGHT);
							label.setOpaque(true);
				            label.setBackground(Color.LIGHT_GRAY);
							appendLabelToPane(label);
							Border border = BorderFactory.createLineBorder(Color.blue,0);
				            label.setBorder(border);
				            
				            label.addMouseListener(new MouseAdapter() {
				            	 public void mouseClicked(MouseEvent e) 
				            	 {
				                     try {
										downloadFile(fileName,fileContentBytes);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
				                    
				                 }
				             
							});
							
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
			if(br!=null) {
				br.close();
			}}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
		}
}
