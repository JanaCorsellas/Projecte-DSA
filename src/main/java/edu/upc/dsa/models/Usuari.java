package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Usuari {
    String nom;
    String cognom;
    String nomusuari;
    String contrasenya;

    public Usuari(){}
    public Usuari(String nom, String cognom, String nomusuari, String contrasenya) {
        this.nom = nom;
        this.cognom = cognom;
        this.nomusuari = nomusuari;
        this.contrasenya = contrasenya;
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

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
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