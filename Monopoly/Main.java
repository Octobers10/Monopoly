package Monopoly;

import java.util.Scanner;

import Monopoly.Game.*;
import Monopoly.Property.*;
import Monopoly.GUI.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main (String[] args) throws FileNotFoundException {
        // read a file to construct the game
        File file = new File("setting.txt");
        Scanner reader = new Scanner(file);
        Game game = new Game();

        while(reader.hasNext()) {
            String name = reader.nextLine();
            int originalPrice = Integer.valueOf(reader.nextLine());
            Land land = new Land(name, originalPrice, game.getNumLand());
            game.addLand(land);
        }

        reader.close();

        SetPlayerWindow window1 = new SetPlayerWindow();
        window1.run();
        while (window1.getNum() <= 0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int numPlayer = window1.getNum();
        window1.dispose();

        for (int i = 0; i < numPlayer; ++i) {
            game.addPlayer(new Player("Player " + (i + 1), i));
        }

        GameDisplay gameDisplay = new GameDisplay(game);
        gameDisplay.run();
        game.start(gameDisplay);
    }
}