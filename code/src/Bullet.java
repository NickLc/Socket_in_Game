

public class Bullet {
    public int pos_x;
    public int pos_y;
    public boolean ready;
    public String figure;
    public String direction;

    public Bullet(int pos_x,int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ready = true;
        this.figure = "*";
    }

    public Map move(Map map){
        if(this.ready == false){
            int aux_pos_x = this.pos_x;
            int aux_pos_y = this.pos_y;
            
            switch(direction){
                case "up": this.pos_y -= 1; break;
                case "down": this.pos_y += 1; break;
                case "left": this.pos_x -= 1; break;
                case "right": this.pos_x += 1; break;
            }
            try {
                map.figure[aux_pos_y][aux_pos_x] = " ";
                // Si no se tiene un obstaculo
                if(map.figure[pos_y][pos_x] == " "){
                    map.figure[pos_y][pos_x] = this.figure;
                }

                else{
                    map.figure[pos_y][pos_x] = " ";
                    this.ready = true;
                }
                
            } catch (Exception e) {
                this.ready = true;
            }

            
        }
        
        return map;
        
    }
}
