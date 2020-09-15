package Monopoly.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;

import java.awt.GridLayout;

import Monopoly.Game.Player;
import Monopoly.Property.Land;

class Pair<T1, T2> { 
    public T1 first; 
    public T2 second; 
    public Pair(T1 t1, T2 t2) { 
        this.first = t1; 
        this.second = t2; 
    } 
} 

class LinkedCheckBox extends JCheckBox {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JComboBox<Integer> linkedComBox;

    public LinkedCheckBox(String text, JComboBox<Integer> linkedComBox){
        super(text);
        this.linkedComBox=linkedComBox;
    }

    public JComboBox<Integer> getlinkedComboBox(){
        return linkedComBox;
    }
}

public class SellWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private long value=0;
    JLabel money = new JLabel("Total gain: $0");
    private boolean updated=false;
    HashMap<Land, Pair<JComboBox<Integer>, LinkedCheckBox>> soldProperties = new HashMap<>();  

    public SellWindow(Player player) {
        super();
        setLocationRelativeTo(null);
        setTitle(player.getName()+"'s properties:");
        ArrayList<Land> allProperties = player.getProperties();
        setLayout(new GridLayout(allProperties.size()+2, 3));
        this.getContentPane().add(new JLabel("Property Name:     "));
        this.getContentPane().add(new JLabel("# of Buildings:     "));
        this.getContentPane().add(new JLabel("Sell Land?     "));
        for (Land land: allProperties){
            this.add(new JLabel(land.getName()));
            Integer bound = land.getNumBuildings()+1;
            Integer[] range = new Integer[bound];
            for (int i=0; i<= land.getNumBuildings(); ++i){ range[i]=i; }
            JComboBox<Integer> coBox = new JComboBox<>(range);
            //coBox.setSelectedIndex(0);
            coBox.addActionListener(l->calculateNet(soldProperties));
            LinkedCheckBox chBox = new LinkedCheckBox("Yes", coBox);
            chBox.addActionListener(l->{
                coBox.setSelectedIndex(coBox.getItemCount()-1);
                calculateNet(soldProperties);
            });
            Pair<JComboBox<Integer>,LinkedCheckBox> pair = new Pair<JComboBox<Integer>,LinkedCheckBox>(coBox, chBox);
            soldProperties.put(land, pair);
            this.add(coBox);
            this.add(chBox);
        }
        this.add(money);
        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(l->updated=true);
        this.getContentPane().add(confirm);
        pack();
        this.setVisible(true);
    }

    private void calculateNet(HashMap<Land, Pair<JComboBox<Integer>, LinkedCheckBox>> soldProperties){
        long val=0;
        for (Map.Entry<Land, Pair<JComboBox<Integer>, LinkedCheckBox>> iter: soldProperties.entrySet()){
            double multiplier=0;
            multiplier += ((Integer)iter.getValue().first.getSelectedItem())*0.1;
            multiplier += iter.getValue().second.isSelected()?1:0;
            val += multiplier*iter.getKey().getOriginalPrice();
        }
        money.setText("Total gain: $"+val*0.75);
        value = val;
    }


    public boolean isUpdated(){return updated;}

    public long getValue(){ return value;}

}
