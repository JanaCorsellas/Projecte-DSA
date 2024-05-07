package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Motxilla {
    String id;
    String idUsuari;
    String idSkin;
    static int lastId;

    public Motxilla(){
    }

    public Motxilla(String idUsuari, String idSkin){
        this.idUsuari=idUsuari;
        this.idSkin=idSkin;
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

    public String getSkin() {
        return this.idSkin;
    }
    public void setIdSkin(String idSkin) {
        this.idSkin=idSkin;
    }
}
