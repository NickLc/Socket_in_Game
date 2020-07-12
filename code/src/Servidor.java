//Programa Servidor
import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

public class Servidor{

	public static int lim_num_players = 2;
	public static Map map = new Map();
	public static Tank[] players = new Tank[lim_num_players]; 

	public static void main(String[] args) throws Exception{
		Socket servicio = null;
		ServerSocket servidor = new ServerSocket(80);
		List<Integer> player_lst = new ArrayList<Integer>();

		int num_player = 0;
		ThreadInputPlayer[] ti_player = new ThreadInputPlayer[lim_num_players];
		ThreadOutputPlayer[] to_player = new ThreadOutputPlayer[lim_num_players];

		System.out.print("Server Battle City\n Watting player...");

		while( true ){
			try{
				if(num_player < lim_num_players){
					servicio = servidor.accept();
					ti_player[num_player] = new ThreadInputPlayer(servicio, num_player);
					to_player[num_player] = new ThreadOutputPlayer(servicio, num_player);					
					players[num_player] = new Tank(map.width, map.height);
					player_lst.add(num_player);
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
			ti_player[i].start();
			to_player[i].start();
		}
		
		while(true){
			for(int i=0; i<player_lst.size(); i++){
				if(map.figure[players[i].pos_y][players[i].pos_x] == '$'){
					ti_player[i].stop();
					to_player[i].stop();
					System.out.println("Jugador "+i+ " eliminado");
					player_lst.remove(i);
				}	
			}
			if(player_lst.size() == 1){
				System.out.println("Ganador: "+player_lst.get(0));
				System.out.println("Juego finalizado");
				break;
			}
		}
	}

	static class ThreadInputPlayer extends Thread{
		Socket servicio = null;
		ObjectInputStream entradaServidor = null;;
		String action = "0";
		int id;
		
		public ThreadInputPlayer(Socket socket, int id_player){
			servicio = socket;
			id = id_player;
		}

		public void run(){
			try{

				System.out.print("\nPlayer "+id+" was connected from the IP "+servicio.getInetAddress());
				entradaServidor = new ObjectInputStream(servicio.getInputStream());
				while(true){	
					action = (String) entradaServidor.readObject();
					map = players[id].bullet.move(map);
					if(action.equals("0") == false){
						startAction(action, id);
					}
				}
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}
		} 

		public static void startAction(String action, int id){
			if (action != ""){
				switch (action) {
					case "u": map = players[id].move("up", map); break;
					case "d": map = players[id].move("down", map); break;
					case "l": map = players[id].move("left", map); break;
					case "r": map = players[id].move("right", map); break;
					case "a": players[id].shoot(); break;
				}
			
			}
		} 
	}	

	static class ThreadOutputPlayer extends Thread{
		Socket servicio = null;
		ObjectOutputStream salidaServidor = null;
		int id;
		public ThreadOutputPlayer(Socket socket, int id_player){
			servicio = socket;
			id = id_player;
		}

		public void run(){
			try{
				salidaServidor = new ObjectOutputStream(servicio.getOutputStream());
				while(true){
					map.convertMaptoString();
					salidaServidor.writeObject(map.figStr);
					TimeUnit.MILLISECONDS.sleep(300);	// not delete				
				}
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}
		}

		public void clearConsole() {
            try {
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } catch (Exception e) {

            }
        }  
	
	}
}