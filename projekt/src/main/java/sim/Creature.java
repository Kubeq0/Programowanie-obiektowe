package sim;

import java.util.List;
import java.util.Random;

public abstract class Creature {
    protected int type;
    protected int stamina;
    protected int speed;
    protected Position position;
    protected int damage;
    protected int range;
    protected String name;

    public String toString(){
        return name;
    }
    public Creature(int x, int y){
        position= new Position(x,y);
    }
    protected boolean moved=true;
    public int getX(){
        return position.getX();
    }
    public int getY(){
        return position.getY();
    }
    public int getType(){
        return type;
    }
    public void move(Map mapa){
        Random rand=new Random();
        int negative;
        int new_x;
        int new_y;
        int actual_x= position.getX();
        int actual_y= position.getY();
            do {
                new_x = rand.nextInt(speed+1);                          //losujemy w ktora strone bedzie sie poruszac jednostka
                negative = rand.nextInt(2);
                if (negative == 1) {
                    new_x = -new_x;
                }
            } while (!(actual_x + new_x >= 0 && actual_x + new_x < Map.getSize())); //sprawdzamy czy nie wychodzi poza granice mapy
            if (new_x != 0) {                                             //tutaj tak bo nie chce zeby jednostki pozostawaly w miejscu
                do {
                    new_y = rand.nextInt(speed+1);
                    negative = rand.nextInt(2);
                    if (negative == 1) {
                        new_y = -new_y;
                    }
                } while (!(actual_y + new_y >= 0 && actual_y + new_y < Map.getSize()));
            } else {
                do {
                    new_y = rand.nextInt(speed)+1;
                    negative = rand.nextInt(2);
                    if (negative == 1) {
                        new_y = -new_y;
                    }
                } while (!(actual_y + new_y >= 0 && actual_y + new_y < Map.getSize()));
            }
            mapa.world.get(actual_x+ new_x).get(actual_y+new_y).add(this);
            mapa.world.get(actual_x).get(actual_y).remove(this);
            position.changeX(actual_x + new_x);
            position.changeY(actual_y + new_y);
            moved=false;
    }
    public boolean isMoved(){
        return moved;
    }
    public void new_iteration(){
        moved=true;
    }
    public void takeDamage(int damage){
        stamina=stamina-damage;
    }
    public boolean isAlive(){
        if(stamina>0) {
            return true;
        }else{
            return false;
        }
    }
    public void setInfected() {

    }
    public void kill(Map map){                           //jednostka jest usuwana z mapy
        Counter.typeCounter[type+1]--;
        map.world.get(position.getX()).get(position.getY()).remove(this);// musze zapamietac zeby dodac zmniejszanie licznika
    }
    public abstract void performAction(Map map);         //wykonanie specjalnych akcji
                                                                              //w tym chyba bedzie tez atak
}
