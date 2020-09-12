package Monopoly.Game;

import java.util.Random;
public class Card {
    /*
        moveForward,
        moveBackword,
        giveMoney,
        takeMoney,
        oneMoreDraw
    */

    private static final int moneyBound = 500;
    private static final int moveBound=6;
    private static int boardSize=0;

    public static void changeBoardSize(int size){
        boardSize=size;
    }


    public static boolean draw(Player player, String [] result){
        Random rand = new Random();
        int randomInt = rand.nextInt(4)+1;
        int steps = rand.nextInt(moveBound);
        int money = rand.nextInt(moneyBound);
        switch(randomInt){
            case(1):
                result[0]="Player "+player.getName()+" moves forward by "+steps+"!";
                player.move(steps, boardSize);
                break;
            case(2):
                result[0]="Player "+player.getName()+" moves backward by "+steps+"!";
                player.move(-steps, boardSize);
                break;
            case(3):
                result[0]="Player "+player.getName()+" gets "+money+"!";
                player.pay(money);
                break;
            case(4):
                if (! player.pay(money)) {
                    result[0]="Player "+player.getName()+" is bankrupted!";
                    return false;
                }
                result[0]="Player "+player.getName()+" pays "+money+"!";
                break;
        }
        return true;

    }
}
