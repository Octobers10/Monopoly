package Monopoly.Game;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Monopoly.GUI.GameDisplay;
import Monopoly.GUI.NotificationWindow;
import Monopoly.GUI.SellWindow;
import Monopoly.Property.Land;


public class Game {
    /**
     * Game class is the Controller in the MVC model;
     * it keeps track of all the lands and the player's data
     */
    private static ArrayList<Land> cells=new ArrayList<>();
    private static ArrayList<Player> allPlayers=new ArrayList<>();
    private static GameDisplay gameDisplay;
    private static int currentPlayerIndex=-1;
    private static int dice=0;

    public Game(){
        Land start = new Land("Start", 0, 0);
        cells.add(start);
    }
    
    public static void addLand(Land land){ cells.add(land);}

    public static void addPlayer(Player player){ allPlayers.add(player); }

    public static int getNumLand(){ return cells.size(); }

    public static int getNumPlayer(){ return allPlayers.size(); }

    public static int getDice(){ return dice;}

    public static int getCurrentPlayerIndex(){ return currentPlayerIndex; }

    public static ArrayList<Player> getPlayers() { return allPlayers; }

    public static ArrayList<Land> getLands() { return cells; }

    private static void incCurrentPlayer(){ currentPlayerIndex = (currentPlayerIndex+1)%allPlayers.size(); }

    public void init(GameDisplay gameDisplayGiven){
        gameDisplay = gameDisplayGiven;
        gameDisplay.setVisible(true);
        Card.changeBoardSize(cells.size());
    }

    public static void arrive(Player player, Land land) {
        if ("Start".equals(land.getName())) {
            String[] result = { "" };
            boolean lost = !Card.draw(player, result);
            new NotificationWindow(player, land, result[0]);
            if (lost) player.bankrupt();
        } else {
            new NotificationWindow(player, land, null);
        }
    }

    public static void notify(int stage, boolean performed) {
        Player player = allPlayers.get(currentPlayerIndex);
        Land land = cells.get(player.getCurrentPosition());
        Player owner = land.getOwner();
        switch (stage) {
            case (1):
                if (player.isBankrupt()) {
                    ArrayList<Land> allLands = player.getProperties();
                    for (Land l : allLands) {
                        l.setOwner(null);
                    }
                }
                break;
            case (2):
                if (performed) {
                    if (player.pay(land.getPrice())) {
                        player.addLand(land);
                        land.setOwner(player);
                        GameDisplay.updateLand(land);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Sorry, you don't have enough money");
                    }
                }
                break;
            case (3):
                if (performed) {
                    if (player.pay(land.getOriginalPrice() / 5)) {
                        land.build(1);
                        GameDisplay.updateLand(land);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Sorry, you don't have enough money");
                    }
                }
                break;
            case (4):
                long toll = land.getToll();
                boolean paySucceed = player.pay(toll);
                if (!paySucceed) {
                    SellWindow sw = new SellWindow(allPlayers.get(currentPlayerIndex));
                    while (!sw.isUpdated()) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                    player.pay(-sw.getValue());
                    sw.dispose();
                    if (!allPlayers.get(currentPlayerIndex).pay(toll)) {
                        JOptionPane.showMessageDialog(new JFrame(), "You are bankrupte! Try harder next time. ");
                        owner.addProperties(player.getProperties());
                        owner.pay(-player.getBankAccount());
                        player.bankrupt();
                    }
                } else {
                    owner.pay(-toll);
                }
                GameDisplay.updateOtherPlayer(owner);
                break;
        }
        GameDisplay.updateCurrentPlayer(player);
    }

    public static void notify(int dices) {
        if (allPlayers.size() > 1) {
            incCurrentPlayer();
            GameDisplay.updateCurrentIcon(currentPlayerIndex);
            Player currentPlayer = allPlayers.get(currentPlayerIndex);
            if (currentPlayer.isBankrupt())
                return;
            currentPlayer.move(dices, cells.size());
            Land currentLand = cells.get(currentPlayer.getCurrentPosition());
            arrive(currentPlayer, currentLand);
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"Player " + (currentPlayerIndex+1) + " wins!");
            System.exit(0);
        }
    }
}