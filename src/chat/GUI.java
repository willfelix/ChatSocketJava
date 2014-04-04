package chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {
	private static final long serialVersionUID = 2463885357441348191L;
	private ChatUDP chat;
	public static TextArea messages;
	
	public GUI(){
		this(false);
	}
	
	public GUI(boolean isUDP) {
		super("Msn do Will");
		chat = new ChatUDP();
		chat.start();
		
		addChatBox();
		getContentPane();
	}
	
	
	public static void main(String[] args) {
		GUI app = new GUI();
		
		app.setVisible(true);
		app.setSize(500, 220);
		app.setResizable(false);
		app.setLocationRelativeTo(null);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	public void addChatBox(){
		messages = new TextArea();
		messages.setEditable(false);
		messages.setFocusable(false);
		messages.setCursor(Cursor.getDefaultCursor());
		messages.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		final TextField write = new TextField();
		final Button button = new Button("Enviar Mensagem");

		write.setFocusable(true);
		write.setSize(490, 30);
		button.setSize(10, 30);
		panel.add(write);
		panel.add(button);
		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String message = write.getText();
				write.setText("");
				write.setFocusable(true);
				
				chat.sendMessage(message);
			}
		});
		
		
		write.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {}
			
			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER){
					String message = write.getText();
					write.setText("");
					write.setFocusable(true);
					System.out.println(message);
					
					chat.sendMessage(message);
				}
			}
		});
		
		
		add(messages, BorderLayout.NORTH);
		add(panel, BorderLayout.SOUTH);
	}
}
