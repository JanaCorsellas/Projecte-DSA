package edu.upc.dsa.models;
import edu.upc.dsa.util.RandomUtils;

public class Productes { //de moment els ítems seran skins de diferents colors
    String id;
    String color;
    int preu;
    String descripcio;
    String imatge;
    static int lastId;

    public Productes() {}
    public Productes(String color, int preu, String descripcio, String imatge){
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
