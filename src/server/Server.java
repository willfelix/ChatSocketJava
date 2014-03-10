package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	private Socket client;
	private ServerSocket server;
	public static ArrayList<Socket> users;
	
	
	public static void main(String[] args) {
		System.out.println("Servidor Iniciado");
		new Server().start();		
	}
	
	
	public void run(){
		try {
			server = new ServerSocket(8592);
			users = new ArrayList<Socket>();
			
			while(true){
				client = server.accept();
				users.add(client);
				
				new MessageThread(client).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
