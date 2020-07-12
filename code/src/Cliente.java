//Programa Cliente
import java.io.*;
import java.net.*;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.concurrent.TimeUnit;


public class Cliente implements KeyListener {

    JFrame frame;
    JTextField tf;
    JLabel lbl;
    public static Map MAP;
	public static Tank player;
	
    Boolean stateCliente;

    Socket cliente = null;
    Socket cliente2 = null;
    ThreadPlayerSender playerSend;
    ThreadPlayerListener playerListen;
	
    public Cliente(){
        frame = new JFrame();
        lbl = new JLabel();
        tf = new JTextField(15);
        tf.addKeyListener(this);
        JPanel panel = new JPanel();
        panel.add(tf);
        frame.setLayout(new BorderLayout());
        frame.add(lbl, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setVisible(true);

        this.stateCliente = true;
        MAP = new Map();
		player = new Tank();

        this.conectClient();
    }

	public void conectClient(){

		try {
			//conectarse al juego
            cliente = new Socket("localhost",80);
            // Instancio un ThreadListend
            playerListen = new ThreadPlayerListener(cliente);
            System.out.println("ThreadPlayerListener");
            playerListen.SetConection();

            // Instancio un ThreadSend
            cliente2 = new Socket("localhost",81);
            playerSend = new ThreadPlayerSender(cliente2);
            System.out.println("ThreadPlayerSender");
            playerSend.SetConection();
			
            playerListen.start();
            playerSend.start();
			
		} catch (Exception e) {
			//TODO: handle exception
		}
    }
    
    static class ThreadPlayerSender extends Thread{
		Socket Cliente = null;
		ObjectOutputStream salidaCliente = null;

		public ThreadPlayerSender(Socket socket){
			this.Cliente = socket;
		}

		public void SetConection(){
			try {				
				salidaCliente = new ObjectOutputStream(Cliente.getOutputStream());

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		public void run(){
			try{
				int i = 0;
				while(i<5){
					// Envio el intervalo al cliente
					salidaCliente.writeObject("mapa del cliente");
                    salidaCliente.reset();
                    i++;
				}
			}	
			catch(Exception e){
				System.err.println("Error_1: " + e.getMessage());
			} 	
		} 
	} 

    static class ThreadPlayerListener  extends Thread{
        Socket Cliente = null;
		ObjectInputStream entradaCliente = null;;
		Map map = new Map();
		String mapastr = "";
		public ThreadPlayerListener (Socket socket){
			this.Cliente = socket;
		}

		public void SetConection(){
			try {
				
                entradaCliente = new ObjectInputStream(Cliente.getInputStream());
                

			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}	
		}

		public void run(){
			try{
				
				int i = 0;
				while(i<5){
					// Leo el resultado que envia el cliente
					entradaCliente = new ObjectInputStream(Cliente.getInputStream());
					mapastr = (String) entradaCliente.readObject();
					System.err.println(mapastr);
					// Realizo el calculo -  realizon un movimiento de juego 
					
					//map.show();

                    //MAP = player.bullet.move(MAP);
                    TimeUnit.MILLISECONDS.sleep(10);
                    clearConsole();
                    i++;
				}
			}	
			catch(Exception e){
				System.err.println("Error_3: " + e.getMessage());
			}	
        } 

        public void clearConsole() {
            try {
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } catch (Exception e) {
    
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                MAP = player.move("up", MAP);
                break;

            case KeyEvent.VK_DOWN:
                MAP = player.move("down", MAP);
                break;

            case KeyEvent.VK_LEFT:
                MAP = player.move("left", MAP);
                break;

            case KeyEvent.VK_RIGHT:
                MAP = player.move("right", MAP);
                break;

            case KeyEvent.VK_A:
                player.shoot();
                break;

        }
        
    }
 
    @Override
    public void keyReleased(KeyEvent ke) {
        lbl.setText("BATTLE CITY "+ke.getKeyChar());
        tf.setText("");
    }
 
    public static void main(String args[]){
		new Cliente();
    } 
}
