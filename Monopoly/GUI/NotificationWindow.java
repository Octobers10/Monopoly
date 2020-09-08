package Monopoly.GUI;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Monopoly.Game.Player;
import Monopoly.Property.Land;



public class NotificationWindow extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;

    private JLabel info;
    private boolean updated=false;
    private boolean actionPerformed=false;
    //private JButton confirm = new JButton("Confirm"); //ArrayList<JLabel> actions = new ArrayList<>();
    

    public NotificationWindow(Player player, Land land, String optionalMessage){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        String playerName = player.getName();
        String landName = land.getName();
        Player owner = land.getOwner();
        //System.out.println(playerName+" arrives at "+landName);
        this.info = new JLabel(playerName+" arrives at "+landName);
        this.add(info);
        if ("Start".equals(landName)){
            JLabel action1 = new JLabel("You get a chance to make a draw.");
            JLabel action2 = new JLabel(optionalMessage);
            JButton confirm = new JButton("continue");
            confirm.addActionListener(e-> updated=true);
            this.add(action1);
            this.add(action2);
            this.add(confirm);
        } else if (owner == null){
            JLabel action1 = new JLabel("Land "+land.getName()+" is available, do you want to purchase it for $"+land.getPrice()+"?");
            JButton purchase = new JButton("Yes");
            JButton cancel = new JButton("No");
            cancel.addActionListener(e->updated=true);
            purchase.addActionListener(e->{
                updated=true;
                actionPerformed=true;
            });
            this.add(action1);
            this.add(purchase);
            this.add(cancel);
        } else if (owner.equals(player)) {
            JLabel action1 = new JLabel("Land "+land.getName()+" is available for constructing one building, do you want to construct it for $"+land.getOriginalPrice()*0.2+"?");
            JButton purchase = new JButton("Yes");
            JButton cancel = new JButton("No");
            cancel.addActionListener(e->updated=true);
            purchase.addActionListener(e->{
                updated=true;
                actionPerformed=true;
            });
            this.add(action1);
            this.add(purchase);
            this.add(cancel);
        } else {
            JLabel action1 = new JLabel("Land "+land.getName()+" is owned by "+owner.getName()+". Please make a payment of $"+land.getToll());
            JLabel action2 = new JLabel(optionalMessage);
            JButton confirm = new JButton("continue");
            confirm.addActionListener(e-> updated=true);
            this.add(action1);
            this.add(action2);
            this.add(confirm);
        }
    }

    public Boolean isUpdated(){ return updated; }

    public Boolean isActionPerformed(){ return actionPerformed; }

    public void run(){ 
        pack();
        this.setVisible(true);}
}
