package Monopoly.GUI;

import javax.swing.*;

import Monopoly.Main;


public class SetPlayerWindow extends JFrame {
    /** 
     * SetPlayerWindow will prompt the user to set the number of players in this game
    */

    private static final long serialVersionUID = 1L;
    private JTextField numPlayer = new JTextField("How many players are in the game:");
    private int num=0;
    
    public SetPlayerWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Monopoly");
        numPlayer.addActionListener(l->{
            //the entered number should be an integer greater than 1
            try{
                num = Integer.parseInt(numPlayer.getText());
                if (num<=1){
                    JOptionPane.showMessageDialog(null, "The number of players should be bigger than 1!", "Error Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    Main.notify(num);
                    dispose();
                }
            } catch (NumberFormatException err2){
                JOptionPane.showMessageDialog(null, "The number of players should be an integer!", "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(numPlayer);
        pack();
    }
    

}