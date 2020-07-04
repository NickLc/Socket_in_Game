//Programa Servidor
import java.net.*;
import java.io.*;
public class Servidor{
	public static void main(String[] args) throws Exception{

		Socket servicioCliente = null;
		ServerSocket servidor = new ServerSocket(80);
		int lim_num_players = 2;
		int num_player = 0;
		ThreadPlayer[] player = new ThreadPlayer[lim_num_players];
		Map map = new Map(20, 50);

		System.out.print("Server Battle City\n Watting player...");

		while( true ){
			try{
				if(num_player < lim_num_players){
					// ServerSocket me da el Socket
					servicioCliente = servidor.accept();
					// Instancio un Thread
					player[num_player] = new ThreadPlayer(servicioCliente);
					player[num_player].SetConection(num_player);
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
			player[i].setMap(map);
			player[i].start();
		}
	
	}

	static class ThreadPlayer extends Thread{
		Socket servicioCliente = null;
		ObjectInputStream entradaServidor = null;;
		ObjectOutputStream salidaServidor = null;
		Map map;
		
		public ThreadPlayer(Socket socket){
			this.servicioCliente = socket;
		}

		public void setMap(Map map){
			this.map = map;
		}

		public Map getMap(){
			return this.map;
		}

		public void SetConection(int num_player){
			try {
				
				System.out.print("\nPlayer "+num_player+" was connected from the IP "+servicioCliente.getInetAddress());
				entradaServidor = new ObjectInputStream(servicioCliente.getInputStream());
				salidaServidor = new ObjectOutputStream(servicioCliente.getOutputStream());

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}finally{
				try{
					/* if( salidaServidor !=null ) salidaServidor.close();
					if( entradaServidor !=null ) entradaServidor.close();
					if( servicioCliente != null ) servicioCliente.close();
					System.out.println("Conexion cerrada!\n"); */
				}
				catch(Exception e){
					System.err.println("Error: " + e.getMessage());
				}
			} 	
		}

		public void run(){
			try{
				
				while(true){
					// Envio el intervalo al cliente
					salidaServidor.writeObject(this.map.figure);
					// Leo el resultado que envia el cliente
					this.map.figure = (String[][]) entradaServidor.readObject();
					
				}
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}finally{
				try{
					/* if( salidaServidor !=null ) salidaServidor.close();
					if( entradaServidor !=null ) entradaServidor.close();
					if( servicioCliente != null ) servicioCliente.close();
					System.out.println("Conexion cerrada!\n"); */
				}
				catch(Exception e){
					System.err.println("Error: " + e.getMessage());
				}
			} 	
		} 
	} 

}