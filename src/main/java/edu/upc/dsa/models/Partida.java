package edu.upc.dsa.models;
import edu.upc.dsa.util.RandomUtils;


import java.util.Timer;
import java.util.Date;


public class Partida {
    String id;
    String idJugador;
    Timer timer;
    int segonsRestants;
    Date data;
    int vidas;

    public Partida(String id, String idJugador, int segonsRestants, int vidas) {
        this.timer = new Timer();
        this.segonsRestants = 15 * 60; // Convertir minutos a segundos
        this.data = new Date();
        this.id = id;
        this.idJugador = idJugador;
        this.vidas = vidas;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getSegonsRestants() {
        return segonsRestants;
    }

    public void setSegonsRestants(int segonsRestants) {
        this.segonsRestants = segonsRestants;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
}
