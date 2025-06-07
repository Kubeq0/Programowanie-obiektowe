package sim;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /*Tu beda parametry   */
        int x = 20;                                           //wielkosc mapy
        int starting_x = 3;                                   //pozycja startowa nekromanty
        int starting_y = 3;
        int population = 50;                                  //procentowe wypelnienie wioski
        int training = 10;
        /*####################*/

        int delay = 760;

        PrintWriter printer = new PrintWriter("dane.txt");

        Random rand = new Random();
        Map mapa = new Map(x);
        mapa.world_initialization();
        Counter.clearCounters();
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < Map.getSize(); i++) {                                                 //wypelnienie wiesniakami
            for (int j = 0; j < Map.getSize(); j++) {
                if (rand.nextInt(101) <= population) {
                    if (rand.nextInt(101) < training) {
                        mapa.world.get(i).get(j).add(new Guardian(i, j));
                    } else {
                        mapa.world.get(i).get(j).add(new Peasant(i, j));
                    }
                }
            }
        }

        if (!(mapa.world.get(starting_x).get(starting_y).isEmpty())) {                    //wyczyszczenie wiesniakow z pozycji nekromanty i wstawienie tego drugiego
            for (int i = 0; i < mapa.world.get(starting_x).get(starting_y).size(); i++) {
                mapa.world.get(starting_x).get(starting_y).get(0).kill(mapa);
                i--;
            }
            mapa.world.get(starting_x).get(starting_y).add(new Necromancer(starting_x, starting_y));
        } else {
            mapa.world.get(starting_x).get(starting_y).add(new Necromancer(starting_x, starting_y));
        }

        for (int i = -1; i < 2; i++) {                     //wyczyszcznie pozycji obok nekromanty z wiesniakow i wstawienie tam nieumarlych
            for (int j = -1; j < 2; j++) {
                int checkX = starting_x + i;
                int checkY = starting_y + j;
                if (checkX >= 0 && checkX < Map.getSize() && checkY >= 0 && checkY < Map.getSize() && (i != 0 || j != 0)) {
                    if (mapa.world.get(checkX).get(checkY).isEmpty()) {
                        mapa.world.get(checkX).get(checkY).add(new Undead(checkX, checkY));
                    } else {
                        for (int k = 0; k < mapa.world.get(checkX).get(checkY).size(); k++) {
                            mapa.world.get(checkX).get(checkY).get(k).kill(mapa);
                            k--;
                        }
                        mapa.world.get(checkX).get(checkY).add(new Undead(checkX, checkY));
                    }
                } else {
                    continue;
                }
            }
        }

        Counter.clearKllCounters();

        //wyswietlanie mapy
        for (int i = 0; i < Map.getSize(); i++) {
            for (int j = 0; j < Map.getSize(); j++) {
                if (mapa.world.get(i).get(j).isEmpty()) {
                    System.out.print("  ");
                } else {
                    System.out.print(mapa.world.get(i).get(j).get(0).toString() + " ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        Thread.sleep(delay);

        //Symulacja
        for (int iteration = 0; Counter.typeCounter[2] != 0 && Counter.typeCounter[3] != 0; iteration++) {

            // Poruszanie
            for (int i = 0; i < Map.getSize(); i++) {
                for (int j = 0; j < Map.getSize(); j++) {
                    for (int k = 0; k < mapa.world.get(i).get(j).size(); k++) {
                        if (mapa.world.get(i).get(j).get(k).isMoved() && mapa.world.get(i).get(j).get(k).isAlive()) {      //if nie jest w metodzie poruszania bo oddzialowywuje mi na iteracje
                            mapa.world.get(i).get(j).get(k).move(mapa);
                            k--;     // wywolujemy ruch kazdej jednostki, jest pewne ze nie wroci na to samo miejsce wiec zmniejszamy k
                        }
                    }
                }
            }

            //wykonywanie akcji
            for (int i = 0; i < Map.getSize(); i++) {
                for (int j = 0; j < Map.getSize(); j++) {
                    for (int k = 0; k < mapa.world.get(i).get(j).size(); k++) {
                        /*kontrola*/
                        Counter.k = k;
                        mapa.world.get(i).get(j).get(k).performAction(mapa);
                    }
                }
            }

            //zabijanie
            for (int i = 0; i < Map.getSize(); i++) {
                for (int j = 0; j < Map.getSize(); j++) {
                    for (int k = 0; k < mapa.world.get(i).get(j).size(); k++) {
                        if (!mapa.world.get(i).get(j).get(k).isAlive()) {
                            mapa.world.get(i).get(j).get(k).kill(mapa);
                        }
                    }
                }
            }

            //umozliwienie ruchow
            for (int i = 0; i < Map.getSize(); i++) {
                for (int j = 0; j < Map.getSize(); j++) {
                    for (int k = 0; k < mapa.world.get(i).get(j).size(); k++) {
                        mapa.world.get(i).get(j).get(k).new_iteration();
                    }
                }
            }

            //wyswietlanie mapy
            System.out.print("\n");
            for (int i = 0; i < Map.getSize(); i++) {
                for (int j = 0; j < Map.getSize(); j++) {
                    if (mapa.world.get(i).get(j).isEmpty()) {
                        System.out.print("  ");
                    } else {
                        System.out.print(mapa.world.get(i).get(j).get(0).toString() + " ");
                    }
                }
                System.out.print("\n");
            }
            Counter.iteration = iteration;
            Thread.sleep(delay);
        }
        Counter.clearAll();
    }

}
