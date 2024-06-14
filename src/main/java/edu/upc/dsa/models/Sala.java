package edu.upc.dsa.models;

public class Sala {
    String id;
    int numeroSala;
    boolean estatPorta; //false vol dir tancada, true oberta

    public Sala (String id, int numeroSala, boolean estatPorta){
        this.id = id;
        this.numeroSala = numeroSala;
        this.estatPorta = false;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public int getNumeroSala(){
        return numeroSala;
    }
    public void setNumeroSala(int numeroSala){
        this.numeroSala = numeroSala;
    }

    public boolean isEstatPorta() {
        return estatPorta;
    }

    public void setEstatPorta(boolean estatPorta) {
        this.estatPorta = estatPorta;
    }
}
