package sim;


public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void changeX(int x){
        this.x=x;
    }
    public void changeY(int y){
        this.y=y;
    }
    public boolean isEqual(Position position){
        if(x==position.getX() && y== position.getY()){
            return true;
        }else{
            return false;
        }
    }
    public double distance(Position position){
        if(position==this){
            return 0;
        }
        return Math.sqrt((this.x - position.getX())*(this.x - position.getX())+(this.y - position.getY())*(this.y - position.getY()));
    }
}
