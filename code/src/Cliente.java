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

public class Cliente {

    public static String action="0";
    
    public static void main(String args[]) throws IOException{

        Socket cliente =  new Socket("localhost",80);
        Boolean stateCliente = true;
        
        new ThreadOutputPlayer(cliente).start();
        new ThreadInputPlayer(cliente).start();
    }

    static class ThreadOutputPlayer extends Thread implements KeyListener{
        JFrame frame;
        JTextField tf;
        JLabel lbl;
        Socket cliente = null;

		ObjectOutputStream salidaCliente = null;
        String action = "0";
		
		public ThreadOutputPlayer(Socket socket){
			cliente = socket;
            startGraphics(); 
        }

        public void startGraphics(){
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
        }

		public void run(){
			try{
                salidaCliente = new ObjectOutputStream(cliente.getOutputStream());
                System.out.println("Client conected, waitting other players");
				while(true){
                    TimeUnit.MILLISECONDS.sleep(300);   // Not delete
                    if (action.equals("0") == false){
                        salidaCliente.writeObject(action);
                        action ="0";
                    }
                } 
			}	
			catch(Exception e){
				System.err.println("Error: " + e.getMessage());
			}
        } 

        @Override
        public void keyTyped(KeyEvent ke) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP: action = "u"; break;
                case KeyEvent.VK_DOWN: action = "d"; break;
                case KeyEvent.VK_LEFT: action = "l"; break;
                case KeyEvent.VK_RIGHT: action = "r"; break;
                case KeyEvent.VK_A: action = "a"; break;
            }
            
        }
    
        @Override
        public void keyReleased(KeyEvent ke) {
            lbl.setText("BATTLE CITY "+ke.getKeyChar());
            tf.setText("");
        }
    }
    
    static class ThreadInputPlayer extends Thread{

        Socket cliente = null;
		ObjectInputStream entradaCliente = null;;
		Map map = new Map();

		public ThreadInputPlayer(Socket socket){
			cliente = socket;
		}

		public void run(){
			try{
                entradaCliente = new ObjectInputStream(cliente.getInputStream());
                System.out.println("Client conected, waitting other players");

				while(true){	
                    map.figStr = (String) entradaCliente.readObject();    
                    map.convertStringtoMap();
                    map.show();
                    //TimeUnit.MILLISECONDS.sleep(100);
                    clearConsole();
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
