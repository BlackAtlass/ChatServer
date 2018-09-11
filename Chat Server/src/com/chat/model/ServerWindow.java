package com.chat.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ServerWindow extends JFrame {
	private static final long serialVersionUID = 4510394819218152375L;
	
	private Server server;
	private JTextPane log;
	private SimpleAttributeSet style;
	
	private JButton stop;
	
	public ServerWindow(Server s) {
		super("Server");
		
		server = s;
		style = new SimpleAttributeSet();
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(300, 350);
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( final WindowEvent e ) {
            	server.stopServer();
            }
        } );
		
		log = new JTextPane();
		log.setEditable(false);
		
		stop = new JButton("Stop Server");
		stop.setAlignmentX(CENTER_ALIGNMENT);
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				server.stopServer();
			}
			
		});
		
		add(stop);
		add(log);
		
		setVisible(true);
	}
	
	
	public void log(String str, Color color) {
		StyleConstants.setForeground(style, color);
		StyleConstants.setFontSize(style, 14);
		
		try {
			log.getDocument().insertString(log.getDocument().getLength(), str + '\n', style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String str) {
		log(str, Color.BLACK);
	}
}
