package com.chat.client.ui;

public interface ChatInterface {
	public void displayMsg(String msg);
	public void displayHighlight(String msg);
	
	public void send(String msg);
	
	public void disconnect();
	public void setTitle(String title);
}
