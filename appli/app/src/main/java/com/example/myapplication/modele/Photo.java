package com.example.myapplication.modele;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {
    //propriétés
    private Integer id;
    private String liennomphoto;
    private String Adresse;
    private String codepostal;

    public Photo(String liennomphoto, String adresse, String codepostal) {
        this.liennomphoto = liennomphoto;
        this.Adresse = adresse;
        this.codepostal = codepostal;
    }

    public Integer getId() {
        return id;
    }
    public String getLiennomphoto() {
        return liennomphoto;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLiennomphoto(String liennomphoto) {
        this.liennomphoto = liennomphoto;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String Departement(){
        String code = codepostal.substring(0,2);
        return code;
    }
    @Override
    public String toString(){
        return liennomphoto;
    }
}
