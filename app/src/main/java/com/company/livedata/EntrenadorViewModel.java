package com.company.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EntrenadorViewModel extends AndroidViewModel {
    Pokemon pokemon;

    LiveData<Integer> imagenLiveData;

    public EntrenadorViewModel(@NonNull Application application) {
        super(application);

        pokemon = new Pokemon();

        imagenLiveData = Transformations.switchMap(pokemon.evolucionLiveData, new Function<String, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(String evolucion) {
                    int imagen;
                    switch (evolucion) {
                        case "EV1":
                        default:
                            imagen = R.drawable.squirtle;
                            break;
                        case "EV2":
                            imagen = R.drawable.wartortle;
                            break;
                        case "EV3":
                            imagen = R.drawable.blastoise;
                            break;
                        case "EV4":
                            imagen = R.drawable.mega;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
            }
        });
    }

    public LiveData<Integer> obtenerImagen(){
        return imagenLiveData;
    }
}