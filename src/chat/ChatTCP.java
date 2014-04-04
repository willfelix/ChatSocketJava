package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatTCP extends Thread implements Chat {
	private Socket client;
	private PrintWriter writer;
	private BufferedReader reader;
	
	private String message;
	
	
	public void run(){
		try {
			client = new Socket("localhost", 8592);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			while(true){
				setMessage(reader.readLine());
				
				if (getMessage() != null && !getMessage().isEmpty()){
					GUI.messages.append(getMessage() + "\n");
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendMessage(String message){
		try {
			writer = new PrintWriter(client.getOutputStream());
			writer.println(message);
			writer.flush();
			
			System.out.println("Mensagem Enviada com sucesso!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

}
