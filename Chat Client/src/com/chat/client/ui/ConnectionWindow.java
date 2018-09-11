package com.chat.client.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chat.client.App;

public class ConnectionWindow extends JFrame implements ActionListener, ItemListener{
	
	private App app;
	private JPanel top, center, bottom;
	
	private JButton button;
	private JTextField host, username;
	private JCheckBox localCheckbox;
	
	
	public ConnectionWindow(App app) {
		this.app = app;
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setSize(250, 220);
		
		
		JLabel info = new JLabel("Enter Login Details:");
		info.setAlignmentX(CENTER_ALIGNMENT);
		add(info);
		
		top = new JPanel(new FlowLayout()); add(top);
		center = new JPanel(new FlowLayout()); add(center);
		bottom = new JPanel(new FlowLayout()); add(bottom);
		
		host = new JTextField(16);
		username = new JTextField(16);
		
		localCheckbox = new JCheckBox("local server");
		localCheckbox.addItemListener(this);
		
		top.add(new JLabel("Host:"));
		top.add(host);
		
		center.add(new JLabel("User:"));
		center.add(username);
		
		bottom.add(new JLabel("Options:"));
		bottom.add(localCheckbox);
		
		button = new JButton("Connect");
		button.addActionListener(this);
		add(button);
		
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.getText().isEmpty())
			JOptionPane.showMessageDialog(this, "Host field cannot be empty!");
		else if(username.getText().isEmpty())
			 JOptionPane.showMessageDialog(this, "Username field cannot be empty!");
		else {
			if(!app.startChat(host.getText(), 3006, username.getText())) {
				JOptionPane.showMessageDialog(this, "Could not connect try again...");
			} else {
				setVisible(false);
				dispose();
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(source == localCheckbox) {
				host.setText("localhost");
				host.setEnabled(false);
			}
		} else {
			if(source == localCheckbox) {
				host.setText("");
				host.setEnabled(true);
			}
		}
	}
}
