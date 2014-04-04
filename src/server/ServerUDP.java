package server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerUDP extends Thread {
	public static final int portServer = 1343;
	public static final int portClient = 1344;

	public static void main(String[] args) {
		System.out.println("Servidor Iniciado!");
		new ServerUDP().start();
	}
	
	public void run(){
		try {
			List<String> lines = new ArrayList<String>();
			boolean renovarBuffer = false;
			int amountLines = 0;
			String pack, msg, ln;
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			DatagramSocket client = new DatagramSocket();
			DatagramSocket server = new DatagramSocket(ServerUDP.portClient);

			server.setSoTimeout(10000);
			while(true){
				try {
					
					server.receive(packet);
					pack = new String(buffer, 0, packet.getLength());
					
					if (amountLines == 0){
						amountLines = Integer.parseInt(pack); 
						renovarBuffer = true;
					}else{
						ln = pack.substring(0, pack.indexOf("."));
						msg = pack.substring(pack.indexOf(".") + 1);
						
						System.out.println("Linha: " + ln);
						System.out.println("Mensagem: " + msg);
						lines.add(pack);
					}
					System.out.println("true");
					
					client.send(p);
					
					
				} catch (InterruptedIOException e) {
					System.out.println("TimeOut: " + e);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (amountLines > 0 && renovarBuffer){
					System.out.println("aqui");
					buffer = new byte[1024 * amountLines];
					packet = new DatagramPacket(buffer, buffer.length);
					renovarBuffer = false;
				}
				
			}
			
		} catch (SocketException e) {
			System.out.println("Erro de Socket: " + e);
		}
	}
}
