package sim;

import java.util.Random;

public class Vampire extends Undead {
    private boolean atacked=false;
    public Vampire(int x, int y){
        super(x,y);
        this.speed=2;
        this.range=2;
        this.name="\u001B[31mV\u001B[0m";
        Counter.vampCounter++;
    }
    @Override
    public void kill(Map map){
        super.kill(map);
        Counter.vampCounter--;
    }
    @Override
    public void performAction(Map map) {                        //Wampir atakuje zaczynając z losowego miejsca
                                                                //pozwala to zniwelować tendencję do atakowania górnego lewego rogu
                                                                // nieaktualne w celu balansu, ale aktualne do guardiana
        Random rand=new Random();
        int start_x;
        int start_y;
        int my_x= position.getX();
        int my_y= position.getY();


        //losowanie od którego miejsca zaczynamy sprawdzanie czy można zaatakować
        do{
            start_x=rand.nextInt(range+range+1)-range;
        }while(!(my_x+start_x>=0 && my_y+start_x<Map.getSize()));
        start_x=my_x+start_x;

        do{
            start_y=rand.nextInt(range+range+1)-range;
        }while(!(my_y+start_y>=0 && my_y+start_y<Map.getSize()));
        start_y=my_y+start_y;
        int i=start_x;
        int j=start_y;


        for(;i<= my_x+range && i< Map.getSize();){
            for(;j<= my_y+range && j<Map.getSize();){
                if(atacked){
                    break;
                }
                if(map.world.get(i).get(j).isEmpty()){
                    //do nothing
                }else {
                    for (int k = 0; k <= map.world.get(i).get(j).size() - 1; k++) {
                        if (map.world.get(i).get(j).get(k).getType() == 2 && map.world.get(i).get(j).get(k).isAlive()) {
                            map.world.get(i).get(j).get(k).takeDamage(damage);
                            //atacked=true;
                            if (rand.nextInt(101) < 10) {
                                map.world.get(i).get(j).get(k).setInfected();
                            }
                            break;
                        }
                    }
                }
                j++;
                if(j==start_y && i==start_x){
                    break;
                }
            }
            if(atacked){
                break;
            }
            if(j==start_y && i==start_x){
                break;
            }
            j= my_y-range;
            while(j<0){
                j++;
            }
            i++;
            if(i== my_x+range+1 || i==Map.getSize()){
                i= my_x-range;
                while (i<0){
                    i++;
                }
            }
            if(j==start_y && i==start_x){
                break;
            }
        }
    }
    @Override
    public void new_iteration(){
        super.new_iteration();
        atacked=false;
    }
}
