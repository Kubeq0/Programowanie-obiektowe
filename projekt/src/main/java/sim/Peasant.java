package sim;

import java.util.Random;

public class Peasant extends Creature{
    protected boolean infected;

    public Peasant(int x, int y) {
        super(x,y);
        this.type=2;
        this.damage=0;
        this.speed=1;
        this.stamina=2;
        this.range=0;
        this.name="\u001B[32mP\u001B[0m";
        infected=false;
        Counter.typeCounter[type+1]++;
        Counter.maxCounter[type+1]++;
    }
    @Override
    public void setInfected() {
        infected=true;
    }
    @Override
    public void performAction(Map map) {
        Random rand=new Random();
        if(rand.nextInt(1001)<1){
            int x= position.getX();
            int y= position.getY();
            map.world.get(x).get(y).add(new Guardian(x, y));
            Counter.trained++;
            super.kill(map);
        }
    }
    @Override
    public void kill(Map map){
        int x= position.getX();
        int y= position.getY();
        if(infected){
            map.world.get(x).get(y).add(new Vampire(x, y));
        }else{
            map.world.get(x).get(y).add(new Body(x, y));
        }
        Counter.vilKillCount++;
        super.kill(map);
    }
}
