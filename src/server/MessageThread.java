package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageThread extends Thread {
	private Socket s;
	private String message;
	private PrintWriter writer;
	private BufferedReader reader;
	
	public MessageThread(Socket s){
		this.s = s;
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			boolean done = false;
			
			
			while (!done){
				setMessage(reader.readLine());	
				
				if(getMessage() == null) {
					done = true;
					Server.users.remove(this.s);
					
					System.out.println("1 usuário ficou offline!");
				} else {
					System.out.println("Recebendo : " + getMessage());
					System.out.println("Online: " + Server.users.size());
					for (Socket client : Server.users){
						writer = new PrintWriter(client.getOutputStream());
						writer.println(getMessage());
						writer.flush();
						
						System.out.println("Escreveu e enviou");
					}
				}
			}
			
			
			
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
