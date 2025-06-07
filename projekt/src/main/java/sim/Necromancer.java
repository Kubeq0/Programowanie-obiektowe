package sim;

import java.util.List;
import java.util.Random;

public class Necromancer extends Creature{
    private int resurrected=0;
    public Necromancer(int x, int y){
        super(x,y);
        this.type=0;
        this.damage=0;
        this.speed=1;
        this.stamina=1;
        this.range=10;
        this.name="\u001B[36mN\u001B[0m";
        Counter.typeCounter[type+1]++;
        Counter.maxCounter[type+1]++;
    }
    @Override
    public void new_iteration(){
        super.new_iteration();
        resurrected=0;
    }
    public void performAction(Map map){
        for(int i=-range;i<=range;i++){
            for(int j=-range;j<=range;j++){         // Sprawdzamy otoczenie

                int checkX=getX()+i;
                int checkY=getY()+j;

                if(checkX>=0 && checkX<Map.getSize() && checkY>=0 && checkY<Map.getSize() && (i!=0 || j!=0)) {
                    if (map.world.get(checkX).get(checkY).isEmpty()) {
                        //do nothing
                    } else {
                        for (int k = 0; k <= map.world.get(checkX).get(checkY).size()-1; k++) {
                            if(map.world.get(checkX).get(checkY).get(k).getType()==-1){
                                Random rand=new Random();
                                map.world.get(checkX).get(checkY).get(k).kill(map);

                                //szansa na wskrzeszenie wampira
                                if(rand.nextInt(101)<30){
                                    map.world.get(checkX).get(checkY).add(new Vampire(checkX,checkY));
                                    resurrected++;
                                }else {
                                    map.world.get(checkX).get(checkY).add(new Undead(checkX, checkY));
                                    resurrected++;
                                }
                                k--;

                                if(resurrected>Counter.maxRes){
                                    Counter.maxRes=resurrected;
                                }
                            }
                        }
                    }
                }else{
                    //do nothing
                }
            }
        }
    }

}
