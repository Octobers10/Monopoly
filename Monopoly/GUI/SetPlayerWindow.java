package Monopoly.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SetPlayerWindow extends javax.swing.JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTextField numPlayer = new JTextField("How many players are in the game:");
    private int num=0;
    
    public SetPlayerWindow(){
        numPlayer.addActionListener(new AbstractAction(){
			private static final long serialVersionUID = 1L;
			@Override
            public void actionPerformed(ActionEvent err1) {
                try{
                    num = Integer.parseInt(numPlayer.getText());
                    if (num<=0){
                        JOptionPane.showMessageDialog(null, "The number of players should be bigger than 0!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException err2){
                    JOptionPane.showMessageDialog(null, "The number of players should be an integer!", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
                    
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Monopoly");
    }

    public int getNum(){
        return num;
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