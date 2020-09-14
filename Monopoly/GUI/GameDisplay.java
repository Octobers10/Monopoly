package Monopoly.GUI;

import Monopoly.Game.Game;
import Monopoly.Game.Player;
import Monopoly.Property.Land;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameDisplay extends javax.swing.JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private JPanel upperPanel, lowerPanel;
    private JLabel currentPlayer;
    private DiceIcon diceIcon;
    private ArrayList<PlayerIcon> players = new ArrayList<>();
    private ArrayList<LandIcon> lands = new ArrayList<>();

    public GameDisplay(Game game) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Monopoly");
        init(game);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        upperPanel.setVisible(true);
        lowerPanel.setVisible(true);
        this.getContentPane().add(upperPanel);
        this.getContentPane().add(lowerPanel);
        pack();
    }

    private class PlayerIcon extends JPanel {
        private static final long serialVersionUID = 1L;
        JLabel playerName, playerPosition, playerAccount, playerProperties;

        public PlayerIcon(Player p) {
            playerName = new JLabel(p.getName());
            playerAccount = new JLabel("$" + p.getBankAccount());
            playerPosition = new JLabel("Position: "+p.getCurrentPosition());
            String allPropertiesName = "";
            ArrayList<Land> allProperties = p.getProperties();
            for (Land l : allProperties) {
                allPropertiesName += l.getName() + " ";
            }
            playerProperties = new JLabel(allPropertiesName);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(playerName);
            this.add(playerPosition);
            this.add(playerAccount);
            this.add(playerProperties);
            this.setBorder(BorderFactory.createLineBorder(Color.black,2));
            this.setBackground(Color.white);
        }
    }

    private class LandIcon extends JPanel {
        private static final long serialVersionUID = 1L;
        JLabel landName, landOwner, landNumBuildings, landToll;

        public LandIcon(Land l) {
            landName = new JLabel(l.getName());
            landOwner = new JLabel("Owner: " + "none");
            landNumBuildings = new JLabel("Buildings: " + l.getNumBuildings());
            landToll = new JLabel("Toll: $0");
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(landName);
            this.add(landOwner);
            this.add(landNumBuildings);
            this.add(landToll);
            this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            this.setBackground(Color.white);
        }

    }

    private class DiceIcon extends JPanel {

        private static final long serialVersionUID = 1L;
        private JButton start = new JButton("Roll");
        private JLabel dice = new JLabel("Dice: "+0);
        private int num = 0;
        private Game game;

        public DiceIcon(Game game){
            this.game = game;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(dice);
            this.add(start);
            start.addActionListener(l->{
                Random rand = new Random();
                num = rand.nextInt(5)+1;
                dice.setText("Dice: "+num);
                game.notify(num);
            });
        }
    }

    private void init(Game game) {
        upperPanel = new JPanel();
        int numPlayer = game.getNumPlayer();
        upperPanel.setLayout(new GridLayout(1, numPlayer + 2));

        ArrayList<Player> allPlayers = game.getPlayers();
        for (Player p : allPlayers) {
            PlayerIcon playerIcon = new PlayerIcon(p);
            players.add(playerIcon);
            upperPanel.add(playerIcon);
            playerIcon.setVisible(true);
        }
        currentPlayer = new JLabel("Current: Player 1");
        currentPlayer.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));

        diceIcon = new DiceIcon(game);

        upperPanel.add(diceIcon);
        upperPanel.add(currentPlayer);
        upperPanel.add(diceIcon);
        diceIcon.setVisible(true);
        currentPlayer.setVisible(true);
        upperPanel.setVisible(true);

        lowerPanel = new JPanel();
        int numLand = game.getNumLand();
        GridLayout lowerPanelLayout = new GridLayout(2, (int) Math.ceil(numLand/2.0));
        lowerPanel.setLayout(lowerPanelLayout);
        ArrayList<Land> allLands = game.getLands();
        for (Land l: allLands){
            LandIcon landIcon = new LandIcon(l);
            lands.add(landIcon);
            lowerPanel.add(landIcon);
            landIcon.setVisible(true);
        }
    
    }

    public static String toMultiline(String orig){
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public void updateCurrentPlayer(Player player){
        boolean lost = player.isBankrupt();
        int playerIndex = player.getPlayerIndex();
        PlayerIcon currentPlayerIcon = players.get(playerIndex);
        if (lost) currentPlayerIcon.setBackground(Color.GRAY);
        currentPlayerIcon.playerAccount.setText("$"+player.getBankAccount());
        currentPlayerIcon.playerPosition.setText("Position: "+player.getCurrentPosition());
        String allPropertiesName="";
        ArrayList<Land> allProperties = player.getProperties();
        for (Land l: allProperties){
            allPropertiesName+=l.getName()+";\n";
        }
        allPropertiesName=toMultiline(allPropertiesName);
        currentPlayerIcon.playerProperties.setText(allPropertiesName);
    }

    public void updateLand(Land land){
        int landIndex = land.getLandIndex();
        LandIcon currentLandIcon = lands.get(landIndex);
        currentLandIcon.landOwner.setText("Owner: "+land.getOwner()==null?"None":land.getOwner().getName());
        currentLandIcon.landNumBuildings.setText("Buildings: "+land.getNumBuildings());
        currentLandIcon.landToll.setText("Toll: "+land.getOwner()==null?"None":""+land.getToll());
    }

    public void updateOtherPlayer(Player player) {
        int playerIndex = player.getPlayerIndex();
        PlayerIcon currentPlayerIcon = players.get(playerIndex);
        currentPlayerIcon.playerAccount.setText("$"+player.getBankAccount());

        String allPropertiesName="";
        ArrayList<Land> allProperties = player.getProperties();
        for (Land l: allProperties){
            allPropertiesName+=l.getName()+";\n";
        }
        allPropertiesName=toMultiline(allPropertiesName);
        currentPlayerIcon.playerProperties.setText(allPropertiesName);
    }
    
    public void updateCurrentIcon(int nextPlayer){
        currentPlayer.setText("Current: Player " + (nextPlayer+1));
    }


    @Override
    public void run() {
        setVisible(true);
    }
}
