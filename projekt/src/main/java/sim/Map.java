package sim;

import java.util.*;

public class Map {
    private static int size;
    public Map(int size){
        this.size=size;
    }
    public List<List<List<Creature>>> world= new ArrayList<List<List<Creature>>>();
    public void world_initialization(){
        for(int i=0;i<size;i++){
            world.add(new ArrayList<>());
            for(int j=0;j<size;j++){
                world.get(i).add(new ArrayList<>());
            }
        }
    }
    public static int getSize() {
        return size;
    }
}
