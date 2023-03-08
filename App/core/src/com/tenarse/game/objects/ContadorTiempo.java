package com.tenarse.game.objects;

import java.util.Timer;
import java.util.TimerTask;

public class ContadorTiempo {
    private Timer timer;
    private int segundos;
    private int minutos;

    public ContadorTiempo() {
        segundos = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundos++;
            }
        }, 0, 1000); // se actualiza cada 1000 milisegundos, es decir, cada segundo
    }

    public String getTiempo() {
        String tiempoFinal = "";
        this.minutos = this.segundos / 60;
        this.segundos = this.segundos % 60;
        if (this.segundos < 10){
            tiempoFinal = this.minutos + ":0" + this.segundos;
        } else {
            tiempoFinal = this.minutos + ":" + this.segundos;
        }

        return tiempoFinal;
    }

    public void detener() {
        timer.cancel();
    }
}
