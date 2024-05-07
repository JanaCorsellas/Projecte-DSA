package edu.upc.dsa.models;

public class Item { //de moment els ítems seran skins de diferents colors
    String id;
    String color;
    int preu;
    String descripcio;
    String imatge;

    public Item() {}
    public Item(String color, int preu, String descripcio, String imatge){
        this.color = color;
        this.preu = preu;
        this.descripcio = descripcio;
        this.imatge = imatge;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id=id;
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

    public String getDescripcio(){
        return descripcio;
    }
    public void setDescripcio(String descripcio){
        this.descripcio = descripcio;
    }

    public String getImatge() {
        return imatge;
    }
    public void setImatge(String imatge) {
        this.imatge = imatge;
    }
}
