package edu.upc.dsa.models;

import java.awt.image.BufferedImage;

public class Botiga { //de moment els ítems seran skins de diferents colors
    String color;
    int preu;
    String descripcio;
    BufferedImage imatge;


    public Botiga() {}
    public Botiga(String color, int preu, String descripcio, BufferedImage imatge){
        this.color = color;
        this.preu = preu;
        this.descripcio = descripcio;
        this.imatge = imatge;
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

    public BufferedImage getImatge() {
        return imatge;
    }
    public void setImatge(BufferedImage imatge) {
        this.imatge = imatge;
    }
}
