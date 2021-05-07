package com.example.myapplication.modele;

import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Deparment {
    private Integer id;
    private String departement;
    private Integer numero;

    public Deparment(String departement, Integer numero) {
        this.departement = departement;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getdepartement() {
        return departement;
    }

    public void setdepartement(String liennomphoto) {
        this.departement = liennomphoto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public static ArrayList<Deparment> getdepartment() {
        ArrayList<Deparment> Deparment = new ArrayList<Deparment>();
        Deparment.add(new Deparment("Aveyron", 12));
        Deparment.add(new Deparment("Aude", 11));
        Deparment.add(new Deparment("Sarthe", 72));
        Deparment.add(new Deparment("Maine-et-Loire", 49));
        Deparment.add(new Deparment("Val-de-Marne", 94));
        Deparment.add(new Deparment("Pyrénées-Orientales", 66));
        Deparment.add(new Deparment("Rhône", 69));
        Deparment.add(new Deparment("Tarn-et-Garonne", 82));
        Deparment.add(new Deparment("Vienne", 86));
        Deparment.add(new Deparment("Haute-Vienne", 87));
        Deparment.add(new Deparment("Territoire-de-Belfort", 90));
        Deparment.add(new Deparment("Pyrénées-Atlantiques", 64));
        Deparment.add(new Deparment("Eure", 27));
        Deparment.add(new Deparment("Cher", 18));
        Deparment.add(new Deparment("Aube", 10));
        Deparment.add(new Deparment("Ardèche", 7));
        Deparment.add(new Deparment("Ariège", 9));
        Deparment.add(new Deparment("Saône-et-Loire", 71));
        Deparment.add(new Deparment("Savoie", 73));
        Deparment.add(new Deparment("Haute-Savoie", 74));
        Deparment.add(new Deparment("Paris", 75));
        Deparment.add(new Deparment("Seine-Maritime", 76));
        Deparment.add(new Deparment("Seine-et-Marne", 77));
        Deparment.add(new Deparment("Tarn", 81));
        Deparment.add(new Deparment("Tarn-et-Garonne", 82));
        Deparment.add(new Deparment("Var", 83));


        return Deparment;
    }

}
