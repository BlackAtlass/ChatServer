package com.chat.client;
import com.chat.client.ui.ChatWindow;
import com.chat.client.ui.ConnectionWindow;

public class App {
	
	private ChatWindow chatWindow;
	private ConnectionWindow connectionWindow;
	private Client client;
	
	public static void main(String[] args) {
		App app = new App();
	}
	
	public App() {
		connectionWindow = new ConnectionWindow(this);
	}
	
	public boolean startChat(String host, int port, String username) {
		chatWindow = new ChatWindow();
		client = new Client(host, port, username, chatWindow); //3006
		
		chatWindow.setClient(client);
		if(!client.connect()) {
			chatWindow.setVisible(false);
			chatWindow.dispose();
			
			return false;
		}
		
		chatWindow.setVisible(true);
		return true;
	}

}
