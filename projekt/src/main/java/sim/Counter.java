package sim;

import java.util.Arrays;

public class Counter {
    public static int[] typeCounter=new int[4];
    public static int[] maxCounter=new int[4];
    public static int vampCounter=0;
    public static int guardCounter=0;
    public static int vilKillCount=0;
    public static int heroKillCount=0;
    public static int healCounter=0;
    public static int maxRes=0;
    public static int trained=0;
    public static int k=0;
    public static int iteration=0;
    public static void clearCounters(){
        Arrays.fill(typeCounter,0);
        Arrays.fill(maxCounter,0);
    }
    public static void clearKllCounters(){
        vilKillCount=0;
        heroKillCount=0;
    }
    public static void clearAll(){
        clearCounters();
        clearKllCounters();
        vampCounter=0;
        guardCounter=0;
        healCounter=0;
        maxRes=0;
        trained=0;
        k=0;
        iteration=0;
    }
}
