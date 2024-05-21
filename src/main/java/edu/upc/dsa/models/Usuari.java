package edu.upc.dsa.models;

public class Usuari {
    String id;
    String nom;
    String cognom;
    String nomusuari;
    String password;
    String password2;
    double coins; //moneda de canvi
    boolean clau;
    String skin;

    public Usuari(){}
    public Usuari(String nom, String cognom, String nomusuari, String password, String password2) {
        this.nom = nom;
        this.cognom = cognom;
        this.nomusuari = nomusuari;
        this.password = password;
        this.password2 = password2;
        this.coins = 0;
        this.clau = false;
        this.skin = "Verd";
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id=id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getNomusuari(){
        return nomusuari;
    }
    public void setNomusuari(String nomusuari){
        this.nomusuari = nomusuari;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }
    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public double getCoins(){
        return coins;
    }
    public void setCoins(double coins){
        this.coins = coins;
    }

    public Boolean getClau() {
        return clau;
    }
    public void setClau(Boolean clau) {
        this.clau = false;
    }

    public String getSkin() {
        return skin;
    }
    public void setSkin(String skin) {
        this.skin = skin;
    }
}