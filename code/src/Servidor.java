//Programa Servidor
import java.net.*;
import java.io.*;


public class Servidor{

	public static Map MAP = new Map(20, 50);
	
	public static void main(String[] args) throws Exception{
		Map MAP = new Map(20, 50);
		Socket servicioCliente = null;
		ServerSocket servidor = new ServerSocket(80);
		int lim_num_players = 1;
		int num_player = 0;

		ThreadPlayerListener[] playerListen = new ThreadPlayerListener[lim_num_players];
		ThreadPlayerSender[] playerSend = new ThreadPlayerSender[lim_num_players];
		
		

		System.out.print("Server Battle City\n Watting player...");

		while( true ){
			try{
				if(num_player < lim_num_players){
					// ServerSocket me da el Socket
					servicioCliente = servidor.accept();
					// Instancio un ThreadListend
					playerListen[num_player] = new ThreadPlayerListener(servicioCliente);
					playerListen[num_player].SetConection(num_player);

					// Instancio un ThreadSend
					playerSend[num_player] = new ThreadPlayerSender(servicioCliente);
					playerSend[num_player].SetConection(num_player);
					
					num_player++;
					System.out.print("\nWaiting for "+(lim_num_players-num_player)+" more players...");
				}

				if(num_player == lim_num_players){
					System.out.print("\nStart game...");
					break;
				}
			}
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}	
		}
		
		for(int i=0; i<lim_num_players; i++){
			System.out.print("\nStart player "+i);
			playerSend[i].start();
			playerListen[i].start();
		}

	}
	

	static class ThreadPlayerListener  extends Thread{
		Socket servicioCliente = null;
		ObjectInputStream entradaServidor = null;;

		public ThreadPlayerListener (Socket socket){
			this.servicioCliente = socket;
		}

		public void SetConection(int num_player){
			try {
				
				System.out.print("\nPlayer "+num_player+" was connected from the IP "+servicioCliente.getInetAddress());
				entradaServidor = new ObjectInputStream(servicioCliente.getInputStream());

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}	
		}
		public void run(){
			try{		
				int i = 0;
				while(i<5){
					// Leo el resultado que envia el cliente
					String accion = (String) entradaServidor.readObject();	
					System.err.println(accion);
					i++;
				}
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}
			 	
		} 
	} 

	static class ThreadPlayerSender extends Thread{
		Socket servicioCliente = null;
		ObjectOutputStream salidaServidor = null;

		public ThreadPlayerSender(Socket socket){
			this.servicioCliente = socket;
		}

		public void SetConection(int num_player){
			try {
				
				System.out.print("\nPlayer "+num_player+" was connected from the IP "+servicioCliente.getInetAddress());
				salidaServidor = new ObjectOutputStream(servicioCliente.getOutputStream());

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}	
		}

		public void run(){
			try{
				
				int i = 0;
				while(i<5){
					// Envio el intervalo al cliente
/* 					String map = MAP.toString(); 
					System.out.print(map);
 */					salidaServidor.writeObject("mapa del servidor");
					salidaServidor.reset();
					i++;
				}
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}	
		} 
	} 
	
}