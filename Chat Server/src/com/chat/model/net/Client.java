package com.chat.model.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.chat.model.Server;

public class Client implements Runnable{
	private Socket client;
	private Server server;
	private DataInputStream in;
	private DataOutputStream out;
	private String user;
	private int id;
	
	public Client(Server server, Socket socket,int id) {
		this.server = server;
		this.client = socket;
		this.id = id;
		try {
			this.in = new DataInputStream(client.getInputStream());
			this.out = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		greet();
		
		while(!client.isClosed()) {
			try {
				switch(Flag.getValue(in.readInt())) {
				case ERROR:
					break;
				case EXIT:
					close();
					break;
				case MSG:
					readMessage();
					break;
				default:
					break;
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void greet() {
		try {
			user = in.readUTF();
			server.broadcast(String.format("%s joined the chat!", user));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String user, String msg) {
		try {
			out.writeInt(Flag.MSG.id);
			out.writeUTF(String.format("%s: %s", user, msg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void broadcast(String msg) {
		try {
			out.writeInt(Flag.BROADCAST.id);
			out.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readMessage() throws IOException {
			String msg = in.readUTF();
			server.broadcastMsg(user, id, msg);
	}
	
	
	public void close() {
		try {
			server.broadcast(String.format("%s has left the chat room.", user));
			if (!client.isClosed()) {
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isClosed() {
		return client.isClosed();
	}

	public String getUser() {
		return user;
	}
	
	public int id() {
		return id;
	}
	
	public String toString() {
		return client.getInetAddress().getHostAddress();
	}
}
