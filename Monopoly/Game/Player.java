package Monopoly.Game;

import java.util.ArrayList;

import Monopoly.Property.Land;

public class Player {
    private final String name;
    private final int playerIndex;
    private long bankAccount=50000;
    private int currentPosition=0;
    private ArrayList<Land> properties=new ArrayList<>();
    private boolean lost=false;
    

    public Player(String name, int index){
        this.name=name;
        this.playerIndex=index;
    }

    public String getName(){ return name; }

    public int getPlayerIndex(){ return playerIndex; }

    public ArrayList<Land> getProperties(){ return properties; }

    //bank account
    public long getBankAccount(){ return bankAccount; }

    public boolean pay(long amount){
        if (amount > bankAccount){
            return false;
        } 
        bankAccount -= amount;
        return true;
    }


    //position
    public int getCurrentPosition(){ return currentPosition;}

    public void move(int steps, int boardSize){ currentPosition = (currentPosition+steps+boardSize)%boardSize; }


    //bankrupcy
    public boolean isBankrupt(){ return lost; }

    public void bankrupt(){ 
        lost=true; 
        System.out.println("Sorry, you are bankrupted.");
    }
    


    public void addProperties(ArrayList<Land> lands){
        properties.addAll(lands);
    }

    public void addLand(Land land){ properties.add(land);}




}
