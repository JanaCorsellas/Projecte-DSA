package edu.upc.dsa.models;

public class Sala {
    String id;
    int numeroSala;

    public Sala (String id, int numeroSala){
        this.id = id;
        this.numeroSala = numeroSala;
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

    @Override
    public String toString() {
        return "Sala{" +
                ", id='" + id + '\'' +
                ", numeroSala=" + numeroSala +
                '}';
    }
}
