import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sim.*;

public class Tests {
    @Test
    public void testKill(){
        Map swiat= new Map(3);
        swiat.world_initialization();
        Counter.clearAll();
        swiat.world.get(0).get(0).add(new Undead(0,0));
        swiat.world.get(0).get(1).add(new Undead(0,1));
        swiat.world.get(0).get(2).add(new Undead(0,2));
        swiat.world.get(1).get(0).add(new Undead(1,0));
        swiat.world.get(1).get(2).add(new Undead(1,2));
        swiat.world.get(2).get(0).add(new Undead(2,0));
        swiat.world.get(2).get(1).add(new Undead(2,1));
        swiat.world.get(2).get(2).add(new Undead(2,2));
        swiat.world.get(1).get(1).add(new Guardian(1,1));
        swiat.world.get(1).get(1).get(0).performAction(swiat);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!swiat.world.get(i).get(j).get(0).isAlive()) {
                    swiat.world.get(i).get(j).get(0).kill(swiat);
                }
            }
        }
        Assertions.assertEquals(7,Counter.typeCounter[2]);
    }
    @Test
    public void testMove(){
        Map swiat= new Map(3);
        swiat.world_initialization();
        Counter.clearAll();
        swiat.world.get(1).get(1).add(new Guardian(1,1));
        swiat.world.get(1).get(1).add(new Necromancer(1,1));
        swiat.world.get(1).get(1).add(new Vampire(1,1));
        swiat.world.get(1).get(1).add(new Undead(1,1));
        swiat.world.get(1).get(1).add(new Peasant(1,1));
        for(int i=0;i<swiat.world.get(1).get(1).size();i++){
            if(swiat.world.get(1).get(1).get(i).isMoved()) {
                swiat.world.get(1).get(1).get(0).move(swiat);
                i--;
            }
        }

        Assertions.assertTrue(swiat.world.get(1).get(1).isEmpty());
    }
    @Test
    public void testHeal(){
        Map swiat= new Map(3);
        swiat.world_initialization();
        Counter.clearAll();
        swiat.world.get(0).get(0).add(new Peasant(0,0));
        swiat.world.get(0).get(0).get(0).takeDamage(2);
        System.out.println(swiat.world.get(0).get(0).get(0).isAlive());
        swiat.world.get(0).get(1).add(new Peasant(0,1));
        swiat.world.get(0).get(1).get(0).takeDamage(2);
        System.out.println(swiat.world.get(0).get(1).get(0).isAlive());
        swiat.world.get(0).get(2).add(new Peasant(0,2));
        swiat.world.get(1).get(0).add(new Peasant(1,0));
        swiat.world.get(1).get(2).add(new Peasant(1,2));
        swiat.world.get(2).get(0).add(new Peasant(2,0));
        swiat.world.get(2).get(1).add(new Peasant(2,1));
        swiat.world.get(2).get(2).add(new Peasant(2,2));
        swiat.world.get(1).get(1).add(new Guardian(1,1));
        swiat.world.get(1).get(1).get(0).performAction(swiat);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!swiat.world.get(i).get(j).get(0).isAlive()) {
                    swiat.world.get(i).get(j).get(0).kill(swiat);
                }
            }
        }
        Assertions.assertEquals(8,Counter.typeCounter[3]);
    }
}
