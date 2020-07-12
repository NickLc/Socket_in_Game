

public class Tank {
    int position_x=0;
    int position_y=0;
    int size = 5;
    String direction="none";
    String[][] figure;
    Bullet bullet;

    public Tank(){
        this.figure = getFigure();
        this.bullet = new Bullet(position_x, position_y);
    }

    public String[][] getFigure(){

        String[][] fig = {{" ", " ", " ", " ", " "},
                          {" ", "#","-","#", " "},
                          {" ", "|"," ","|", " "},
                          {" ", "#","-","#", " "},
                          {" ", " ", " ", " ", " "}};

        return fig;
    }

    public Map move(String direction, Map map){

        if(direction != this.direction){
            changeDirection(direction);
            map = setInMap(map);
        }
        else{
            switch(direction){
                case "up": this.position_y -= 1; break;
                case "down": this.position_y += 1; break;
                case "left": this.position_x -= 1; break;
                case "right": this.position_x += 1; break;
                
            }
            map = setInMap(map);
        }

        return map;

    }

    public void changeDirection(String direction){
        this.figure = getFigure();
        this.direction = direction;
        switch(direction){
            case "up": figure[1][2] = "|";  this.figure[0][2] = "|";  break;
            case "down": this.figure[3][2] = "|";  this.figure[4][2] = "|"; break;
            case "left": this.figure[2][1] = "-";  this.figure[2][0] = "-"; break;
            case "right": this.figure[2][3] = "-";  this.figure[2][4] = "-"; break;
        }
    }

    public void show(){
        for(int h = 0; h<size; h++){
            for(int w=0; w<size; w++)
                System.out.print(this.figure[h][w]);
            System.out.println();    
        }
    }

    
    public Map setInMap(Map map){
        try {
            for(int y=0; y<this.size; y++){
                for(int x=0; x<this.size; x++){
                    map.figure[y+this.position_y][x+this.position_x] = this.figure[y][x]; 
                }
            }
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    public void shoot(){
        if(this.bullet.ready){
            
            this.bullet.direction = this.direction;
            int aux_x = this.position_x;
            int aux_y = this.position_y;
            switch(this.direction){
                case "up": aux_x += 2; break;
                case "down": aux_x += 2; aux_y += 4 ; break;
                case "left": aux_y += 2; break;
                case "right": aux_y += 2; aux_x += 4; break;
            }
            this.bullet.pos_x = aux_x;
            this.bullet.pos_y = aux_y;
            this.bullet.ready = false;
        }
    }


}
