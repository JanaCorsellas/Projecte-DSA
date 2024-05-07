package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Usuari {
    String nom;
    String cognom;
    String nomusuari;
    String password;
    String password2;
    boolean clau;

    public Usuari(){}
    public Usuari(String nom, String cognom, String nomusuari, String password, String password2) {
        this.nom = nom;
        this.cognom = cognom;
        this.nomusuari = nomusuari;
        this.password = password;
        this.password2 = password2;
        this.clau = false;
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

    public Boolean getClau() {
        return clau;
    }

    public void setClau(Boolean clau) {
        this.clau = false;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", nomusuari=" + nomusuari +
                '}';
    }
}