package edu.upc.dsa.models;

public class Item { //de moment els ítems seran skins de diferents colors
    String color;
    int preu;

    public Item() {}
    public Item (String color, int preu){
        this.color = color;
        this.preu = preu;
    }

    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }

    public int getPreu(){
        return preu;
    }
    public void setPreu(int preu){
        this.preu = preu;
    }

    @Override
    public String toString() {
        return "Item{" +
                ", color='" + color + '\'' +
                ", preu=" + preu +
                '}';
    }
}
