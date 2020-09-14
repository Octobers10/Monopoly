package Monopoly;

import java.util.Scanner;

import Monopoly.Game.*;
import Monopoly.Property.*;
import Monopoly.GUI.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static Game game = new Game();
    public static void main (String[] args) throws FileNotFoundException {
        // read a file to construct the game
        File file = new File("setting.txt");
        Scanner reader = new Scanner(file);

        while(reader.hasNext()) {
            String name = reader.nextLine();
            int originalPrice = Integer.valueOf(reader.nextLine());
            Land land = new Land(name, originalPrice, game.getNumLand());
            game.addLand(land);
        }

        reader.close();

        SetPlayerWindow window1 = new SetPlayerWindow();
        window1.run();
    }

    public static void notify(int numPlayer){
        for (int i = 0; i < numPlayer; ++i) {
            game.addPlayer(new Player("Player " + (i + 1), i));
        }

        GameDisplay gameDisplay = new GameDisplay(game);
        gameDisplay.run();
        game.init(gameDisplay);
    }
}