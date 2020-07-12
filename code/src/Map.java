
public class Map {

    public String[][] figure;
    
    public Map(){
        this.figure = null;
    }
    public Map(int height, int width){
        this.figure = createMap(height, width);
    }

    public String[][] createMap(int height, int width){
        String[][] new_map  = new String[height][width];
        for(int h = 0; h<height; h++)
            for(int w=0; w<width; w++)
                new_map[h][w] = "#";
        return new_map;
    } 

    public void show(){
        for(int h = 0; h<this.figure.length; h++){
            for(int w=0; w<this.figure[h].length; w++)
                System.out.print(this.figure[h][w]);
            System.out.println();    
        }
    }

    public String mapToString(){
        String mapStr = "";
        for(int h = 0; h<this.figure.length; h++){
            for(int w=0; w<this.figure[h].length; w++)
                mapStr += this.figure[h][w];
            mapStr += "\n";
        }
        return mapStr;
    }    
}
