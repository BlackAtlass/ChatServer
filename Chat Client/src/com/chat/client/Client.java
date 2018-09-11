package com.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JOptionPane;

import com.chat.client.ui.ChatInterface;
import com.chat.client.ui.ChatWindow;
import com.chat.model.net.Flag;

public class Client extends Thread{
	
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	
	private String host,user;
	private int port;
	private ChatInterface ui;
	
	public Client(String host, int port, String user, ChatInterface ui) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.ui = ui;
	}
	
	@Override
	public void run() {
		try {
			ui.setTitle("You are currently connected to '" + socket.getInetAddress().toString() + "'");
			while(!socket.isClosed()) {
				switch (Flag.getValue(in.readInt())) {
				case MSG:
					ui.displayMsg(in.readUTF());
					break;
					
				case BROADCAST:
					ui.displayHighlight(in.readUTF());
					break;
					
				case EXIT:
					System.out.println("Server is shutting down...");
					close();
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			ui.disconnect();
		}
	}
	
	public void close() {
		try {
			if (!socket.isClosed()) {
				out.writeInt(Flag.EXIT.id);
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean connect() {
		try {
			socket = new Socket(host, port);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(user);
			start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void sendMessage(String msg) {
		try {
			out.writeInt(Flag.MSG.id);
			out.writeUTF(msg);
			ui.displayMsg(String.format("You: %s", msg));
		} catch (IOException e) {
			e.printStackTrace();
			ui.disconnect();
		}
		
	}
	
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	public Socket getConnection() {
		return socket;
	}
	
}
