package com.example.myapplication.modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.controleur.Controle;
import com.example.myapplication.outils.MySQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccesLocal {

    //propriétés
    private String nomBase = "bdphoto.sqlite";
    private String nomBase2 = "bdphotopromo.sqlite";
    private Integer versioBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;
    private Controle controle;
    /**
     * Constructeur
     * @param contexte
     */
    public AccesLocal(Context contexte){
        accesBD = new MySQLiteOpenHelper(contexte, nomBase, null, versioBase);
        accesBD = new MySQLiteOpenHelper(contexte, nomBase2, null, versioBase);
    }
    public AccesLocal(){
        controle = Controle.getInstance(null);
    }
    /**
     * ajout d'une photo dans la BD
     * @param photo
     */
    public void ajout(Photo photo){
        bd = accesBD.getWritableDatabase();
        String req = "insert into photo(liennomphoto,Adresse,codepostal) values ";
        req += "(\""+photo.getLiennomphoto()+"\",\""+photo.getAdresse()+"\",\""+photo.getCodepostal()+"\")";
        bd.execSQL(req);
    }
    public void supprimerttous(){
        bd = accesBD.getWritableDatabase();
        bd.delete("photopromo", "1", null);
    }

    /**
     * Recupération des 2 premiers chiffres du code postal pour lui attribuées son département
     * @param num
     * @return
     */
    public ArrayList<Photo> affichage(int num){
        bd = accesBD.getWritableDatabase();
        ArrayList<Photo> photosselect = new ArrayList<>();
        String selectionner = "select * from photo where codepostal LIKE '"+num+"___'";
        Cursor curseur = bd.rawQuery(selectionner, null);
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            String liennomphoto=curseur.getString(1);
            String Adresse = curseur.getString(2);
            String codepostal = curseur.getString(3);
            Photo photo = new Photo(liennomphoto, Adresse, codepostal);
            photosselect.add(photo);
            curseur.moveToNext();
        }
        curseur.close();
        return photosselect;
    }

    /**
     * Vérifie si le nombre de département corespond un numPhotopromo dans la table promo
     * @param num
     * @return
     */
    public int recuppromo(int num){
        bd = accesBD.getWritableDatabase();
        String selectionner = "select count(*) from photopromo where numPhotopromo ="+num+"";
        Cursor curseur = bd.rawQuery(selectionner, null);
        curseur.moveToFirst();
        int count= curseur.getInt(0);
        curseur.close();
        return count;
    }

    /**
     * Affiche tout les codes promos
     * @return
     */
    public ArrayList<PointPromo> affichagepromos(){
        bd = accesBD.getWritableDatabase();
        ArrayList<PointPromo> promos = new ArrayList<>();
        String select = "select * from photopromo ";
        Cursor curseur = bd.rawQuery(select, null);
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            Integer numphoto=curseur.getInt(1);
            String codepromo = curseur.getString(2);
            PointPromo promo = new PointPromo(numphoto, codepromo);
            promos.add(promo);
            curseur.moveToNext();
        }
        curseur.close();
        return promos;
    }

    /**
     * Récupère la dernière promo
     * @return
     */
    public PointPromo recupDernier(){
        bd = accesBD.getReadableDatabase();
        PointPromo promo = null;
        String req = "select * from photopromo";
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToLast();
        if(!curseur.isAfterLast()){
            Integer numphoto=curseur.getInt(1);
            String codepromo = curseur.getString(2);
            promo = new PointPromo(numphoto, codepromo);
        }
        curseur.close();
        return promo;
    }

    /**
     * Créer un nouveau code promo pour le mettre dans SQLite
     * @param pointPromo
     */
    public void creecodepromo(PointPromo pointPromo){
        bd = accesBD.getWritableDatabase();
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String req = "insert into photopromo(numPhotopromo,codoPhotopromo,datePhotopromo) values ";
        req += "("+pointPromo.getNombre()+",\""+pointPromo.getCodepromo()+"\",\""+time+"\")";
        bd.execSQL(req);
    }
    /**
     * Récupération de toute les photos
     * @return
     */

    public ArrayList<Photo> recuptous(){
        bd = accesBD.getReadableDatabase();
        ArrayList<Photo> photos = new ArrayList<>();
        String tous = "select * from photo";
        Cursor curseur = bd.rawQuery(tous, null);
        curseur.moveToFirst();
        while(!curseur.isAfterLast()){
            String liennomphoto=curseur.getString(1);
            String Adresse = curseur.getString(2);
            String codepostal = curseur.getString(3);
            Photo photo = new Photo(liennomphoto, Adresse, codepostal);
            photos.add(photo);
            curseur.moveToNext();
        }
        curseur.close();
        return photos;
    }
}
