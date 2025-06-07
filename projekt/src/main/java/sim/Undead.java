package sim;

import java.util.Random;

public class Undead extends Creature{
    public Undead(int x, int y){
        super(x,y);
        this.type=1;
        this.damage=1;
        this.speed=1;
        this.stamina=1;
        this.range=0;
        this.name="\u001B[33mU\u001B[0m";
        Counter.typeCounter[type+1]++;
        Counter.maxCounter[type+1]++;
    }

    @Override
    public void kill(Map map){
        Counter.heroKillCount++;
        super.kill(map);
    }
    @Override
    //atakuje przeciwnikow
    public void performAction(Map map) {
        int x= position.getX();
        int y= position.getY();
        for(int k=0;k<map.world.get(x).get(y).size()-1;k++) {
            if(map.world.get(x).get(y).get(k).getType()==2 && map.world.get(x).get(y).get(k).isAlive()) {
                map.world.get(x).get(y).get(k).takeDamage(damage);
                break;
            }
        }
    }
}
