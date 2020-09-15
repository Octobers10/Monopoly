package Monopoly.Game;

import java.util.Random;
public class Card {
    /*  Card class allows the player to make a draw; the result can be the following:
        move forward by [0, moveBound],
        move backword by [0, moveBound],
        receive money by [0, moneyBound],
        pay tax by [0, moneyBound],
    */

    private static final int moneyBound = 500;
    private static final int moveBound=6;
    private static int boardSize=0;

    public static void changeBoardSize(int size){ boardSize=size;}


    public static boolean draw(Player player, String [] result){
        /**
         * the player gets to draw a card randomly
         * @player  the player that makes the draw
         * @result  additional message
         * 
         * @return  false if the player is bankrupted, true otherwise 
         */
        Random rand = new Random();
        int randomInt = rand.nextInt(4)+1;
        int steps = rand.nextInt(moveBound);
        int money = rand.nextInt(moneyBound);
        switch(randomInt){
            case(1):
                result[0]=player.getName()+" moves forward by "+steps+"!";
                player.move(steps, boardSize);
                break;
            case(2):
                result[0]=player.getName()+" moves backward by "+steps+"!";
                player.move(-steps, boardSize);
                break;
            case(3):
                result[0]=player.getName()+" gets "+money+"!";
                player.pay(money);
                break;
            case(4):
                if (! player.pay(money)) {
                    result[0]=player.getName()+" is bankrupted!";
                    return false;
                }
                result[0]=player.getName()+" pays "+money+"!";
                break;
        }
        return true;
    }
}
