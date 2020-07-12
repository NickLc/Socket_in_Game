

import java.util.Random;

public class Tank {
    int pos_x=0;
    int pos_y=0;
    String direction="none";
    char figure;
    Bullet bullet;
    boolean live;

    public Tank(int w, int h){
        Random rd = new Random();
        this.pos_x = 1 + rd.nextInt(w-2);
        this.pos_y = 1 + rd.nextInt(h-2);
        this.figure = getFigure();
        this.bullet = new Bullet(pos_x, pos_y);
        this.live = true;
    }
    
    public boolean checkIsLive(Map map){
        if(map.figure[pos_y][pos_x] == '$'){
            this.live = false;
            return false;
        }
        else{
            return true;
        }
    }

    public char getFigure(){
        char fig = 'x';    
        return fig;
    }

    public Map move(String direction, Map map){
        
        if(direction != this.direction){
            changeDirection(direction);
            map = setInMap(map,figure);
        }
        else{
            map = setInMap(map, ' ');
            int aux_x= pos_x, aux_y=pos_y;
            switch(direction){
                case "up": aux_y -= 1; break;
                case "down": aux_y += 1; break;
                case "left": aux_x -= 1; break;
                case "right": aux_x += 1; break;
            }
            if(map.figure[aux_y][aux_x] == ' '){
                pos_x = aux_x;
                pos_y = aux_y;
            }
            map = setInMap(map,figure);
        }
        return map;

    }

    public void changeDirection(String direction){
        figure = getFigure();
        this.direction = direction;
        switch(direction){
            case "up": figure = '^';   break;
            case "down": figure = 'v'; break;
            case "left": figure = '<';  break;
            case "right": figure = '>';  break;
        }
    }

    public void show(){
        System.out.print(this.figure);
        System.out.println();    
    }

    
    public Map setInMap(Map map, char fig){
        try {
            map.figure[this.pos_y][this.pos_x] = fig; 
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    public void shoot(){
        if(this.bullet.ready){
            this.bullet.direction = direction;
            this.bullet.pos_x = this.pos_x;
            this.bullet.pos_y = this.pos_y;
            this.bullet.ready = false;
            this.bullet.start = true;
        }
    }
}

