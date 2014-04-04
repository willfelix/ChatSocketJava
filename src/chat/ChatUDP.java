package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import server.ServerUDP;

public class ChatUDP extends Thread implements Chat {
	private String message;
	private DatagramSocket client;
	private boolean flag = false;
	private int ack, seqNumber;
	
	public void run(){
		
		try {
			client = new DatagramSocket(ServerUDP.portClient);
			
			while(true){
				byte[] buffer = new byte[1024];
				DatagramPacket p = new DatagramPacket(buffer, buffer.length);
				client.receive(p);
				
				setAck(p.getLength());
				setFlag(true);
				
				setMessage(new String(buffer, 0, p.getLength()));
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
			seqNumber = message.length() + 1;
			ack = ack + 1;
			
			message += ".seqNumber: " + seqNumber + ".ack: " + ack + ".flag: " + flag;
			
			InetAddress end = InetAddress.getByName("localhost");
			byte[] msg = new byte[message.length()];
			msg = message.getBytes();
			
			DatagramPacket packet = new DatagramPacket(msg, msg.length, end, ServerUDP.portServer);
			DatagramPacket packet2 = new DatagramPacket(msg, msg.length, end, ServerUDP.portClient);
			DatagramSocket socket = new DatagramSocket();
			socket.send(packet);
			socket.send(packet2);
			socket.close();
			
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

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getAck() {
		return ack;
	}

	public void setAck(int ack) {
		this.ack = ack;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}
}
