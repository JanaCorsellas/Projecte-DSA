package edu.upc.dsa.models;

public class Motxilla {
    String id;
    String idUsuari;
    String idItem;

    public Motxilla(){
    }

    public Motxilla(String id, String idUsuari, String idItem){
        this.id = id;
        this.idUsuari=idUsuari;
        this.idItem=idItem;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public String getIdUsuari() {
        return this.idUsuari;
    }
    public void setIdUsuari(String idUsuari) {
        this.idUsuari=idUsuari;
    }

    public String getIdItem() {
        return this.idItem;
    }
    public void setIdItem(String idItem) {
        this.idItem=idItem;
    }
}
