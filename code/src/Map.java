import java.util.Random;

public class Map {

    public char[][] figure;
    public int height;
    public int width;
    public String figStr;

    public Map(){
        width = 60;
        height = 20;
        figure = new char[height][width];
        this.figure = createMap(height, width);
        figStr = "#########";
    }
    public Map(int height, int width){
        this.width = width;
        this.height = height;
        this.figure = createMap(height, width);
    }

    public char[][] createMap(int height, int width){
        char[][] new_map  = new char[height][width];
        Random rd = new Random();
        int val_rd = 0;
        char fig=' ';
        for(int h = 0; h<height; h++){
            for(int w=0; w<width; w++){
                if(w==0 || w==width-1){
                    fig = '|';
                }
                else if(h==0 ||h==height-1){
                    fig = '-';
                }
                else{
                    val_rd = rd.nextInt(10);
                    if(val_rd == 0)
                        fig = '#'; //'#'
                    else 
                        fig = ' ';
                }
                new_map[h][w] = fig;
            }
        }
        return new_map;
    } 

    public void show(){
        for(int h = 0; h<this.figure.length; h++){
            for(int w=0; w<this.figure[h].length; w++)
                System.out.print(this.figure[h][w]);
            System.out.println();    
        }
    }

    public void convertMaptoString(){
        String mapStr = "";
        for(int h = 0; h<figure.length; h++){
            StringBuilder sb = new StringBuilder();
            for (char ch: figure[h]) {
                sb.append(ch);
            }
            String string = sb.toString();
            mapStr += string;
        }
        figStr = mapStr;
    }    
    public void convertStringtoMap(){
        int i = 0;
        for(int h = 0; h<figure.length; h++){
            for(int w=0; w<figure[h].length; w++){
                figure[h][w] = figStr.substring(i, i+1).toCharArray()[0];  
                i++;
            } 
        }
    }
}
