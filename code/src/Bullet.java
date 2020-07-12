

public class Bullet {
    public int pos_x;
    public int pos_y;
    public boolean ready;
    public char figure;
    public String direction;
    public boolean start;

    public Bullet(int pos_x,int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ready = true;
        this.figure = '*';
    }

    public Map move(Map map){
        if(this.ready == false){
            int aux_x= pos_x, aux_y=pos_y;

            switch(direction){
                case "up": aux_y -= 1; break;
                case "down": aux_y += 1; break;
                case "left": aux_x -= 1; break;
                case "right": aux_x += 1; break;
            }
            try {
                if(start){
                    start = false;
                }
                else{
                    map.figure[pos_y][pos_x] = ' ';
                }
                char new_figure = map.figure[aux_y][aux_x];
                // Si no se tiene un obstaculo
                if(new_figure== ' '){
                    map.figure[aux_y][aux_x] = figure;
                    pos_x = aux_x;
                    pos_y = aux_y;
                }
                else if(new_figure == '-' || new_figure == '|'){
                    this.ready = true;
                }
                else if(new_figure=='<'||new_figure=='>'||new_figure=='^'||new_figure=='v'){
                    map.figure[aux_y][aux_x] = '$';
                    this.ready = true;
                }
                else{
                    map.figure[aux_y][aux_x] = ' ';
                    this.ready = true;
                }
                
            } catch (Exception e) {
                this.ready = true;
            }
        }
        return map;
    }
}
