package com.chat.model;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chat.model.net.Client;

public class Server extends Thread {
	public static final int MAX_CLIENTS = 10;
	
	private ServerSocket server;
	private boolean online;
	
	private ExecutorService executor;
	private ArrayList<Client> clients;
	
	private ServerWindow window;
	
	private int id_counter = 0;
	
	public Server() {	
		executor = Executors.newFixedThreadPool(Server.MAX_CLIENTS);
		clients = new ArrayList<>();
		window = new ServerWindow(this);
	}
	
	@Override
	public void run() {
		while(online) {
			try {
				Client client = new Client(this, server.accept(),id_counter++);
				clients.add(client);
				executor.submit(client);
				
				window.log("'" + client + "' connected.", Color.BLUE);
			} catch (IOException e) {
				if(e instanceof SocketException) 
					System.out.println("Server stopped accepting Clients!");
				else 
					e.printStackTrace();
			}
		}
	}
	
	public void broadcastMsg(String user,int id, String msg) {
		for(Client client : clients) {
			if(!client.isClosed() && client.id() != id) {
				client.sendMessage(user, msg);
			}
		}
	}
	
	public void broadcast(String msg) {
		for(Client client : clients) {
			if(!client.isClosed())
				client.broadcast(msg);
		}
	}

	
	public boolean startServer() {

		try {
			server = new ServerSocket(3006);
			server.setSoTimeout(0);
			
			window.log("Server started...");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.start();
		return online = true;
	}
	
	public boolean stopServer() {
		if(!online)
			return online;
		
		for(Client client : clients)
			client.close();
		
		try {
			server.close();
			window.log("Server stopped...", Color.RED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return online = false;
	}
}
