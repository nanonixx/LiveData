package com.company.livedata;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Pokemon {

    interface EntrenadorListener {
        void cuandoEvolucione(String orden);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> entrenando;

    void iniciarEvolucion(final EntrenadorListener entrenadorListener) {
        if (entrenando == null || entrenando.isCancelled()) {
            entrenando = scheduler.scheduleAtFixedRate(new Runnable() {
                int evo;

                @Override
                public void run() {

                        evo++;
                        if (evo==5) evo=1;

                    entrenadorListener.cuandoEvolucione("EV" + evo);
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararEntrenamiento() {
        if (entrenando != null) {
            entrenando.cancel(true);
        }


    }
    LiveData<String> evolucionLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEvolucion(new EntrenadorListener() {
                @Override
                public void cuandoEvolucione(String evolucion) {
                    postValue(evolucion);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEntrenamiento();
        }
    };
}


/*
E1:3
E1:2
E1:1


EV1
EV2
EV3


 */