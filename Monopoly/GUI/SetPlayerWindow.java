package Monopoly.GUI;

import javax.swing.*;

import Monopoly.Main;

import java.awt.event.ActionEvent;


public class SetPlayerWindow extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTextField numPlayer = new JTextField("How many players are in the game:");
    private int num=0;
    
    public SetPlayerWindow(){
        numPlayer.addActionListener(
            l->{
                try{
                    num = Integer.parseInt(numPlayer.getText());
                    if (num<=1){
                        JOptionPane.showMessageDialog(null, "The number of players should be bigger than 1!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Main.notify(num);
                        this.dispose();
                    }
                } catch (NumberFormatException err2){
                    JOptionPane.showMessageDialog(null, "The number of players should be an integer!", "Error Message", JOptionPane.ERROR_MESSAGE);
                }

            });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Monopoly");
    }
    
    @Override
    public void run(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.add(numPlayer);
        pack();
        this.setVisible(true);
    }
}