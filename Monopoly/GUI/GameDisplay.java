package Monopoly.GUI;

import Monopoly.Game.Game;
import Monopoly.Game.Player;
import Monopoly.Property.Land;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameDisplay extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel upperPanel, lowerPanel;
    private DiceIcon diceIcon;
    private static JLabel currentPlayer;
    private static ArrayList<PlayerIcon> players = new ArrayList<>();
    private static ArrayList<LandIcon> lands = new ArrayList<>();

    public GameDisplay() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Monopoly");
        init();
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
            playerPosition = new JLabel("Position: " + p.getCurrentPosition());
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
            this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
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
        private JLabel dice = new JLabel("Dice: " + 0);
        private int num = 0;

        public DiceIcon() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(dice);
            this.add(start);
            start.addActionListener(l -> {
                Random rand = new Random();
                num = rand.nextInt(5) + 1;
                dice.setText("Dice: " + num);
                Game.notify(num);
            });
        }
    }

    private void init() {
        upperPanel = new JPanel();
        int numPlayer = Game.getNumPlayer();
        upperPanel.setLayout(new GridLayout(1, numPlayer + 2));

        ArrayList<Player> allPlayers = Game.getPlayers();
        for (Player p : allPlayers) {
            PlayerIcon playerIcon = new PlayerIcon(p);
            players.add(playerIcon);
            upperPanel.add(playerIcon);
            playerIcon.setVisible(true);
        }
        currentPlayer = new JLabel("Current: Player 1");
        currentPlayer.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        diceIcon = new DiceIcon();

        upperPanel.add(diceIcon);
        upperPanel.add(currentPlayer);
        upperPanel.add(diceIcon);
        diceIcon.setVisible(true);
        currentPlayer.setVisible(true);
        upperPanel.setVisible(true);

        lowerPanel = new JPanel();
        int numLand = Game.getNumLand();
        GridLayout lowerPanelLayout = new GridLayout(2, (int) Math.ceil(numLand / 2.0));
        lowerPanel.setLayout(lowerPanelLayout);
        ArrayList<Land> allLands = Game.getLands();
        for (Land l : allLands) {
            LandIcon landIcon = new LandIcon(l);
            lands.add(landIcon);
            lowerPanel.add(landIcon);
            landIcon.setVisible(true);
        }

    }

    public static String toMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public static void updateCurrentPlayer(Player player) {
        boolean lost = player.isBankrupt();
        int playerIndex = player.getPlayerIndex();
        PlayerIcon currentPlayerIcon = players.get(playerIndex);
        if (lost)
            currentPlayerIcon.setBackground(Color.GRAY);
        currentPlayerIcon.playerAccount.setText("$" + player.getBankAccount());
        currentPlayerIcon.playerPosition.setText("Position: " + player.getCurrentPosition());
        String allPropertiesName = "";
        ArrayList<Land> allProperties = player.getProperties();
        for (Land l : allProperties) { allPropertiesName += l.getName() + ";\n";}
        allPropertiesName = toMultiline(allPropertiesName);
        currentPlayerIcon.playerProperties.setText(allPropertiesName);
    }

    public static void updateLand(Land land) {
        int landIndex = land.getLandIndex();
        LandIcon currentLandIcon = lands.get(landIndex);
        currentLandIcon.landOwner.setText("Owner: " + land.getOwner() == null ? "None" : land.getOwner().getName());
        currentLandIcon.landNumBuildings.setText("Buildings: " + land.getNumBuildings());
        currentLandIcon.landToll.setText("Toll: " + land.getOwner() == null ? "None" : "" + land.getToll());
    }

    public static void updateOtherPlayer(Player player) {
        int playerIndex = player.getPlayerIndex();
        PlayerIcon currentPlayerIcon = players.get(playerIndex);
        currentPlayerIcon.playerAccount.setText("$" + player.getBankAccount());

        String allPropertiesName = "";
        ArrayList<Land> allProperties = player.getProperties();
        for (Land l : allProperties) {
            allPropertiesName += l.getName() + ";\n";
        }
        allPropertiesName = toMultiline(allPropertiesName);
        currentPlayerIcon.playerProperties.setText(allPropertiesName);
    }

    public static void updateCurrentIcon(int nextPlayer) {
        currentPlayer.setText("Current: Player " + (nextPlayer + 1));
    }

}
