package com.example.myapplication.controleur;

import android.content.Context;

import com.example.myapplication.modele.AccesDistant;
import com.example.myapplication.modele.AccesLocal;
import com.example.myapplication.modele.Photo;
import com.example.myapplication.modele.PointPromo;
import com.example.myapplication.outils.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Controle {

    private static Controle instantce = null;
    private static Photo photo;
    private static AccesLocal accesLocal;
    private static String nomFic = "savephoto";
    private static Context context;
    private ArrayList<Photo> lesPhotos = new ArrayList<Photo>();
    private static AccesDistant accesDistant;
    private PointPromo pointPromo;


    /**
     * constructeur privée
     */
    private Controle(){
        super();
    }

    /**
     * Création de l'instance
     * @return
     */
    public static final Controle getInstance(Context contexte){
        if(Controle.instantce == null){
            Controle.instantce = new Controle();
            //recupeSerialize(contexte);
            accesLocal = new AccesLocal(contexte);
            //photo = accesLocal.recupDernier();

        }
        return Controle.instantce;
    }

    /**
     * Création de la photo
     * @param liennomphoto lien photo
     * @param Adresse de la photo
     * @param codepostal de la ville
     */
    public void creephoto(String liennomphoto,String Adresse,String codepostal, Context context){
        if(context !=null){
            Controle.context = context;
        }
        photo = new Photo(liennomphoto,Adresse,codepostal);
        lesPhotos.add(photo);
        //Serializer.serialize(nomFic, photo, context);
        accesLocal.ajout(photo);
    }
    public Integer recupernumdern(){
        if (accesLocal.recupDernier() != null){
            return accesLocal.recupDernier().getNombre();
        }else {
            return 0;
        }

    }

    public int nombrepromo(int num){
        return accesLocal.recuppromo(num);
    }
    public void creecodepromo(Integer dep,String pass, Context context){
        if(context !=null){
            Controle.context = context;
        }
        pointPromo = new PointPromo(dep,pass);
        //accesDistant.envoi("enreg",pointPromo.convertToJSONARRAY());
        accesLocal.creecodepromo(pointPromo);
    }

    public ArrayList<Photo> recuptous(){
        return accesLocal.recuptous();

    }
    public ArrayList<PointPromo> recuppromo(){

        return accesLocal.affichagepromos();

    }
    public ArrayList<Photo> affichage(int num){
        return accesLocal.affichage(num);
    }

    public void supptous(){
        accesLocal.supprimerttous();
    }

    public int nombredepartement(){
        ArrayList<Photo> photos = recuptous();
        int n = 0;
        ArrayList<String>ok=new ArrayList<>();
        for( int k = 0; k < photos.size(); k++ ) {
            String dep = photos.get(k).Departement();
            ok.add(dep);
        }
        List<String> distinctElements =ok.stream()
                .distinct()
                .collect(Collectors.toList());
        return distinctElements.size();
    }
    public  ArrayList<Photo> getLesPhotos() {
        return lesPhotos;
    }

    public void setLesPhotos(ArrayList<Photo> lesPhotos) {
        this.lesPhotos = lesPhotos;
    }

    /**
     * récupération du département
     * @return code postale
     */
    public String getdepartement(){
        return photo.Departement();
    }

    /**
     * recupération de l'objet sérialisé (la photo)
     * @param context
     */
    private static void recupeSerialize(Context context){
        photo = (Photo) Serializer.deSerialize(nomFic, context);
    }

    public String getnom(){
        if(photo == null){
            return null;
        }else {
            return photo.getLiennomphoto();
        }
    }
    public String getadresse(){
        if(photo == null){
            return null;
        }else {
            return photo.getAdresse();
        }
    }
    public String getcode(){
        if(photo == null){
            return null;
        }else {
            return photo.getCodepostal();
        }
    }
    public Integer getid(){
        if(photo == null){
            return 0;
        }else {
            return photo.getId();
        }
    }
    public String getDepartement(){
        if(photo == null){
            return null;
        }else {
            return photo.Departement();
        }
    }


}
