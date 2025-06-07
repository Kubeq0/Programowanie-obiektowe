//Jakub Warczyk
//273014
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BurningForestSimulation {
    public static final String ANSI_RESET = "\u001B[0m";    //resteuje kolor tekstu
    public static final String ANSI_RED = "\u001B[31m";     //zmienia kolor tekstu na czerwony
    public static final String ANSI_GREEN = "\u001B[32m";   //zmienia kolor tekstu na zielony
    private String[][] map;
    private int size;                    //wielkość mapy
    private double forestation;          //wskaźnik zalesienia - procentowy udział drzew w całej mapie
    public int trees=0;                  //ilość wszystkich drzew na mapie
    public int burned=0;                 //ilość wszystkich spalonyc drzew na mapie

    public BurningForestSimulation(int size, double forestation){  //ustalenie parametrów symulacji
        this.size=size;
        this.forestation=forestation;
    }
    public void map_initialization(){    //tworzenie i losowe wypełnianie mapy drzewami
        Random random= new Random();
        map= new String[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(random.nextDouble()<forestation) {
                    map[i][j] = "T";
                    trees++;
                }else{
                    map[i][j] = "X";
                }
            }
        }
        print_map();
        System.out.println(" ");
    }
    public void fire_initialization(){   //"podpalenie" rzędu "0" symulacji
        for(int i=0;i<size;i++){
            if(map[0][i].equals("T")){
                map[0][i]="B";
                burned++;
            }
        }
    }

    public void tile_check(){     //sprawdza czy sąsiadujące elementy są drzewem, jeżeli tak to je podpala
        for(int x=0;x<size;x++){
            int count=0;
            for(int y=0;y<size;y++){
                int i;
                int limit_x;      //ustalenie limitów tak żeby nie wyjść poza obszar tablicy (znacznie krótsze rozwiązanie niż poprzednie które zrobiłem)
                if(x!=0 && x!=size-1){
                    i=x-1;
                    limit_x=x+1;
                }else if(x==0) {
                    i=x;
                    limit_x=x+1;
                }else{
                    i=x-1;
                    limit_x=x;
                }
                for(;i<=limit_x;i++){
                    int j;
                    int limit_y;
                    if(y!=0 && y!=size-1){
                        j=y-1;
                        limit_y=y+1;
                    }else if(y==0) {
                        j =y;
                        limit_y =y+1;
                    }else {
                        j = y - 1;
                        limit_y = y;
                    }
                    for(;j<=limit_y;j++){
                        if(map[x][y].equals("B") && map[i][j].equals("T") && (i!=x || j!=y)){
                            map[i][j]="B";
                            count++;
                        }
                    }
                }
            }
            burned=burned+count;
            this.count2=count2+count;
        }
    }
    private void sim(PrintWriter writer){  //wywołuje wszystkie funkcje symulacji
        map_initialization();
        fire_initialization();
        make_simulation();
        writer.print(" " + burned + "; " + trees + "; ");
    }

    private int count2=0; // zlicza podpalenia w danej epoce
    public void make_simulation() {       //wykonuje podpalenia dopóki dana epoka jest "pusta"
        boolean epoka = true;
        while(epoka) {
            count2 = 0;
            tile_check();
            print_map();
            //String pom = scanner.nextLine();
            System.out.println("B:: " + burned + " T_In: " + trees + " T_Out: " + (trees-burned) + " ");
            System.out.println();
            if(count2==0){
                epoka=false;
            }
        }
    }
    public void print_map(){     //drukuje mapę z drzewami
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(map[i][j].equals("B")) {
                    System.out.print(ANSI_RED + map[i][j] + " " + ANSI_RESET);
                }else if(map[i][j].equals("T")){
                    System.out.print(ANSI_GREEN + map[i][j] + " " + ANSI_RESET);
                }else{
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println(" ");
        }
    }
    public static void main(String[] args) throws IOException {
        File file = new File("srednie.txt");
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        for(int i=0;i<=100;i=i+5){       //wywołuje symulację dla wartości zalesienia od 0-1 co 0,05
            double k=i;                  //musiałem to zrobić w ten sposób ponieważ w dodawaniu 0,05 do double pojawiały się błędy
            k=k/100;
            writer.print(k + ";");
            for(int j=0;j<10;j++){       //testuje symulację 10 razy dla każdej wartości zalesienia
                BurningForestSimulation test = new BurningForestSimulation(100, k);
                test.sim(writer);
            }
            writer.print("\n");
        }
        writer.close();
    }
}