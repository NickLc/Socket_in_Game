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
    Map map;
    Tank player;
    Boolean stateCliente;

	//sockets
	ObjectOutputStream salidaCliente = null;
	ObjectInputStream entradaCliente = null;
	Socket cliente = null;

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
        this.map = new Map();
		this.player = new Tank();

        this.conectClient();
    }

	public void conectClient(){

		try {
			//conectarse al juego
			cliente = new Socket("localhost",80);
			salidaCliente = new ObjectOutputStream(cliente.getOutputStream());
			entradaCliente = new ObjectInputStream(cliente.getInputStream());
			System.out.println("Client conected, waitting other players");
			startCliente();
			
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

    public void startCliente() {

		try {
			while(true){
	
				// Recibo un intervalow
				this.map.figure = (String[][]) entradaCliente.readObject();
							
				// Realizo el calculo -  realizon un movimiento de juego 
				this.map.show();
				this.map = this.player.bullet.move(map);
				TimeUnit.MILLISECONDS.sleep(10);
				clearConsole();

				// Envio mi respuesta al servidor
				salidaCliente.writeObject(this.map.figure);

			} 	
		}catch (Exception e) {
			//TODO: handle exception
		}
		finally{
			/* if( entradaCliente != null ) entradaCliente.close();
			if( salidaCliente != null ) salidaCliente.close();
			if( cliente != null ) cliente.close(); */
		}
    }

    public void clearConsole() {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (Exception e) {

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
                this.map = this.player.move("up", this.map);
                break;

            case KeyEvent.VK_DOWN:
                this.map = this.player.move("down", this.map);
                break;

            case KeyEvent.VK_LEFT:
                this.map = this.player.move("left", this.map);
                break;

            case KeyEvent.VK_RIGHT:
                this.map = this.player.move("right", this.map);
                break;

            case KeyEvent.VK_A:
                this.player.shoot();
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
