package Monopoly;

import java.util.Scanner;

import Monopoly.Game.*;
import Monopoly.Property.*;
import Monopoly.GUI.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
* This is a simplified version of the board game Monopoly
* with GUI implemented by Java Swing
*
* @author  Sylvia Liu
* @version 1.0
* @since   2020-08
*/

public class Main {
    /** 
     * The Main class is responsible for initializing the game, such as the map and the players
    */

    public static Game game = new Game();
    public static void main (String[] args) throws FileNotFoundException {
        
        // 1. read "setting.txt" to construct the game map
        File file = new File("setting.txt");
        Scanner reader = new Scanner(file);

        while(reader.hasNext()) {
            String name = reader.nextLine();
            int originalPrice = Integer.valueOf(reader.nextLine());
            Land land = new Land(name, originalPrice, game.getNumLand());
            game.addLand(land);
        }

        reader.close();

        //2. display window prompt for total number of players
        SetPlayerWindow window1 = new SetPlayerWindow();
        window1.run();
    }

    
    public static void notify(int numPlayer){
        /*
            notify(int numPlayer) is called by the SetPlayerWindow when the user enters a valid number (triggered by ActionEvent)
            @numPlayer: an integer representing the number of players in this game
            @return: None
        */

        //future functionality: customize player name
        for (int i = 0; i < numPlayer; ++i) {
            game.addPlayer(new Player("Player " + (i + 1), i));
        }

        GameDisplay gameDisplay = new GameDisplay(game);
        gameDisplay.run();
        game.init(gameDisplay);
    }
}