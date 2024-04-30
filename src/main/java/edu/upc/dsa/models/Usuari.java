package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Usuari {
    String id;
    String nom;
    String cognom;
    String nomusuari;
    String password;

    public Usuari() {
    }

    public Usuari(String id, String nom, String cognom, String nomusuari, String password) {
        this.id = id;
        this.nom = nom;
        this.cognom = cognom;
        this.nomusuari = nomusuari;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNomusuari() {
        return nomusuari;
    }

    public void setNomusuari(String nomusuari) {
        this.nomusuari = nomusuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.nomusuari = pwd;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                ", id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", nomusuari=" + nomusuari + '\'' +
                ", password=" + password +
                '}';
    }
}