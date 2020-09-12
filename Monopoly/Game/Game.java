package Monopoly.Game;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Monopoly.GUI.GameDisplay;
import Monopoly.GUI.NotificationWindow;
import Monopoly.GUI.SellWindow;
import Monopoly.Property.Land;


public class Game {
    private ArrayList<Land> cells=new ArrayList<>();
    private ArrayList<Player> alivePlayers=new ArrayList<>();

    private int currentPlayerIndex=-1;
    private int dice=0;

    public Game(){
        Land start = new Land("Start", 0, 0);
        cells.add(start);
    }
    
    public void addLand(Land land){ cells.add(land);}

    public void addPlayer(Player player){ alivePlayers.add(player); }

    public int getNumLand(){ return cells.size(); }

    public int getNumPlayer(){ return alivePlayers.size(); }

    public int getDice(){ return dice;}

    public int getCurrentPlayerIndex(){ return currentPlayerIndex; }

    public void incCurrentPlayer(){ currentPlayerIndex = (currentPlayerIndex+1)%alivePlayers.size(); }

    public ArrayList<Player> getPlayers() { return alivePlayers; }

    public ArrayList<Land> getLands() { return cells; }

    public void start(GameDisplay gameDisplay){
        Card.changeBoardSize(cells.size());
        Random rand = new Random();
        while (alivePlayers.size()>1) {
            waitResponse(gameDisplay);
            incCurrentPlayer();
            gameDisplay.updateCurrentIcon(currentPlayerIndex);
            dice = rand.nextInt(5)+1;
            gameDisplay.updateDice(dice, currentPlayerIndex+1);
            Player currentPlayer = alivePlayers.get(currentPlayerIndex);
            if (currentPlayer.isBankrupt()) continue;

            currentPlayer.move(dice, cells.size());
            Land currentLand = cells.get(currentPlayer.getCurrentPosition());
            arrive(currentPlayer, currentLand, gameDisplay);
            
        }
    }

    public void waitResponse(NotificationWindow notification){
        while (!notification.isUpdated()){
            try{Thread.sleep(2000);} 
            catch (Exception e){System.out.println(e.getMessage());}
        }
    }

    public void waitResponse(GameDisplay game){
        while (!game.isUpdated()){
            try{
                Thread.sleep(2000);
            } catch (Exception e){System.out.println(e.getMessage());}
        }
    }

    public void arrive(Player player, Land land, GameDisplay gameDisplay){
        Player owner = land.getOwner();
        NotificationWindow notification;
        if ("Start".equals(land.getName())){
            String[] result = {""};
            boolean lost = ! Card.draw(player, result);
            notification = new NotificationWindow(player, land, result[0]);
            notification.run();
            waitResponse(notification);
            if (lost) {
                player.bankrupt();
                ArrayList<Land> allLands = player.getProperties();
                for (Land l: allLands){ l.setOwner(null); }
            }
        } else if (owner == null){
            notification = new NotificationWindow(player, land, null);
            notification.run();
            waitResponse(notification);
            if (notification.isActionPerformed()){
                if (player.pay(land.getPrice())){
                    player.addLand(land);
                    land.setOwner(player);
                    gameDisplay.updateLand(land);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"Sorry, you don't have enough money");
                }
            }
            
        } else if (owner.equals(player)) {
            notification = new NotificationWindow(player, land, null);
            notification.run();
            waitResponse(notification);
            if (notification.isActionPerformed()){
                if (player.pay(land.getOriginalPrice()/5)){
                    land.build(1);
                    gameDisplay.updateLand(land);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(),"Sorry, you don't have enough money");
                }
            }
        } else {
            notification = new NotificationWindow(player, land, null);
            notification.run();
            waitResponse(notification);
            long toll = land.getToll();
            boolean paySucceed = player.pay(toll);
            if (! paySucceed){
                SellWindow sw = new SellWindow(alivePlayers.get(currentPlayerIndex));
                sw.run();
                while (! sw.isUpdated()){
                    try{Thread.sleep(1000);}
                    catch(Exception e){}
                }
                player.pay(-sw.getValue());
                sw.dispose();
                if (alivePlayers.get(currentPlayerIndex).pay(toll)){
                    JOptionPane.showMessageDialog(new JFrame(),"You are bankrupte! Try harder next time. ");
                    owner.addProperties(player.getProperties());
                    owner.pay(-player.getBankAccount());
                    player.bankrupt();
                }
            } else {
                owner.pay(-toll);
            }
            gameDisplay.updateOtherPlayer(owner);
        }
        notification.dispose();
        gameDisplay.updateCurrentPlayer(player);


    }


}