package edu.upc.dsa.models;
import edu.upc.dsa.util.RandomUtils;

import java.util.Timer;

public class Partida {
    String id;
    String idJugador;
    Timer timer;
    int segonsRestants;

    public Partida() {
        this.timer = new Timer();
        this.segonsRestants = 15 * 60; // Convertir minutos a segundos
    }

}
