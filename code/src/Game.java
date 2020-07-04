import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.concurrent.TimeUnit;

public class Game implements KeyListener {

    JFrame frame;
    JTextField tf;
    JLabel lbl;
    Map map;
    Tank player;
    Boolean stateGame;

    public Game() throws InterruptedException {
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

        this.stateGame = true;
        this.map = new Map(20, 50);
        this.player = new Tank();
        this.startGame();
    }

    public void startGame() throws InterruptedException {
        for(;;){
            this.map.show();
            this.map = this.player.bullet.move(map);
            TimeUnit.MILLISECONDS.sleep(100);
            clearConsole();
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
 
    public static void main(String args[]) throws InterruptedException {
        new Game();
    } 
}

