package Monopoly.Property;

import Monopoly.Game.Player;

public class Land {
    private final String name;
    private final int originalPrice;
    private Player owner;
    private int numBuildings;
    private final int landIndex;
    
    
    public Land(String name, int originalPrice, int index){
        this.name=name;
        this.originalPrice=originalPrice;
        this.landIndex=index;
    }

    public int getLandIndex(){ return landIndex;}

    public String getName(){ return name;}

    public int getNumBuildings(){ return numBuildings; }

    public int getOriginalPrice(){ return originalPrice; }

    public void setOwner(Player player){ owner=player; }

    public Player getOwner(){ return owner; }

    public void build(int numProperties){ this.numBuildings += numProperties; }

    public int sell(int numProperties, boolean sellLand){
        /*
        Permanent sale of 75% of the net value;
        @numBuildings: how many properties on this land are sold
        @sellLand: is this land also sold; this can be true only when all properties on this land are sold
        */

        assert(numProperties <= this.numBuildings);
        if (sellLand) {
            assert(numProperties == this.numBuildings);
            this.owner=null;
        }
        
        int landPrice = (sellLand)? originalPrice:0;
        return (int) ((int) (numProperties * originalPrice * 0.2 + landPrice) * 0.75);

    }

    public long getToll(){
        return (long) ((long) (owner==null?0:numBuildings * originalPrice * 0.2 + originalPrice) * 0.1);
    }

    public long getPrice(){
        return (long) (numBuildings * originalPrice * 0.2 + originalPrice);
    }
}
