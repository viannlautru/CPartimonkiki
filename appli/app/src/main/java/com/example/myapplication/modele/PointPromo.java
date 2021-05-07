package com.example.myapplication.modele;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointPromo implements Serializable {
    private Integer ids;
    private Integer nombre;
    private String Codepromo;

    public PointPromo(Integer nombre, String codepromo) {
        this.nombre = nombre;
        Codepromo = codepromo;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public String getCodepromo() {
        return Codepromo;
    }

    public void setCodepromo(String codepromo) {
        Codepromo = codepromo;
    }
    public static ArrayList<PointPromo> getAListOfPersonne() {
        ArrayList<PointPromo> listPers = new ArrayList<PointPromo>();

        listPers.add(new PointPromo( 3,"AbdaDA1da51d" ));
        listPers.add(new PointPromo(6, "FKfkzof4z21f2"));


        return listPers;
    }

    /**
     * convertion du code en JSONArray
     * @return
     */
    public JSONArray convertToJSONARRAY(){
        List lalist = new ArrayList();
        lalist.add(nombre);
        lalist.add(Codepromo);
        return new JSONArray(lalist);
    }
}
