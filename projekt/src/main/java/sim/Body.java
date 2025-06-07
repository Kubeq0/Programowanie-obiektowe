package sim;

import java.util.Random;

public class Body extends Creature{

    public Body(int x, int y) {
        super(x,y);
        this.type=-1;
        this.damage=0;
        this.speed=0;
        this.stamina=1;
        this.range=0;
        this.name="\u001B[37mB\u001B[0m";
        this.moved=false;
        Counter.typeCounter[type+1]++;
        Counter.maxCounter[type+1]++;
    }

    @Override
    public void move(Map map){
        moved=false;
    }

    @Override
    public void performAction(Map map) {
        Random rand=new Random();
        if(rand.nextInt(1001)<2){
            int x= position.getX();
            int y= position.getY();
            map.world.get(x).get(y).add(new Undead(x, y));
            super.kill(map);
        }
    }


}
