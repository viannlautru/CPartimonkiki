package com.example.myapplication.modele;

import android.util.Log;

import com.example.myapplication.outils.AccesHTTP;
import com.example.myapplication.outils.AsyncResponse;

import org.json.JSONArray;

public class AccesDistant implements AsyncResponse {
    private static final String SERVEURADDR = "https://vianneylaut.com/photopromo/serveuphoto.php";
    public AccesDistant(){
        super();
    }

    @Override
    public void processFinish(String output) {
        Log.d("serveur","***********"+output);
        String[] message = output.split(("%"));

        if(message.length>1){
            if(message[0].equals("enreg")){
                Log.d("enreg","***********"+output);
            }else{
                if(message[0].equals("tous")){
                    Log.d("tous","***********"+output);
                }else {
                    if(message[0].equals("Erreur !")){
                        Log.d("Errur","***********"+output);
                    }
                }
            }
        }

    }
    public void envoi(String operation, JSONArray lesDonnesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        accesDonnees.delegate = this;
        accesDonnees.addParam("operation",operation);
        accesDonnees.addParam("lesdonnees", lesDonnesJSON.toString());
        accesDonnees.execute();
    }
}
