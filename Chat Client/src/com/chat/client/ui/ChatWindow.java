package com.chat.client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.chat.client.Client;

public class ChatWindow extends JFrame implements ChatInterface{
	private static final long    serialVersionUID = 1337L;
	private JTextPane text;
	private Client client;
	private SimpleAttributeSet attrib;
	private JTextField edit;
	private JButton send;
	private JLabel title;

    public ChatWindow() {
        super( "Chat" );
        
        this.setSize( 600, 400 );
        this.setLocationRelativeTo( null );
        this.setResizable( false );
        this.setLayout( new BorderLayout() );
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( final WindowEvent e ) {
            	client.close();
            }
        } );

        text = new JTextPane();
        text.setEditable(false);
        JScrollPane pane = new JScrollPane(text);
      
        attrib = new SimpleAttributeSet();
        
        StyleConstants.setForeground(attrib, Color.ORANGE);
        StyleConstants.setBold(attrib, true);
        StyleConstants.setItalic(attrib, true);
        
        edit = new JTextField(45);
        edit.setEditable(true);
        JPanel editbox = new JPanel();
        send = new JButton("Send"); 
        
        editbox.add(edit);
        editbox.add(send);
        
        send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(edit.getText().trim().isEmpty())
					return;
				send(edit.getText());
				edit.setText("");
			}
		});
        
        edit.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(edit.getText().trim().isEmpty())
						return;
					send(edit.getText());
					edit.setText("");
				}
			}
		});
        
        title = new JLabel("Chat Client");
        title.setHorizontalAlignment(SwingConstants.CENTER);
       
        
        this.add(title, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);
        this.add(editbox, BorderLayout.SOUTH);
       
        this.revalidate();
        this.setVisible(false);
    }
    
    public void setClient(Client client) {
    	this.client = client;
    }

	@Override
	public void displayMsg(String msg) {
		try {
			text.getDocument().insertString(text.getDocument().getLength(), msg + '\n', null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void displayHighlight(String msg) {
		try {
			text.getDocument().insertString(text.getDocument().getLength(), msg + '\n', attrib);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String msg) {
		client.sendMessage(msg);
	}

	@Override
	public void disconnect() {
		JOptionPane.showMessageDialog(null, "Lost connection to server!");
		this.setVisible(false);
		this.dispose();
		client.close();
	}
	
	@Override
	public void setTitle(String title) {
		this.title.setText(title);
	}
}
