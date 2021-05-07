package com.example.myapplication.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.controleur.Controle;
import com.example.myapplication.modele.AccesLocal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //constante
    private static final int RETOUR_PRENDRE_PHOTO = 1;
    //propriétés
    private Button btnPrendrePhoto;
    private ImageView imgffichephoto;
    private String photoPath = null;
    private String time;

    private Button btnEnreg;
    private Bitmap image;
    private ExifInterface exif;
    private Controle controle;
    private String localistion;
    private String codepostale;
    private File photofile;
    private Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();

    }

    private void initActivity(){
        btnPrendrePhoto = (Button)findViewById(R.id.btnPrendrePhoto);
        imgffichephoto = (ImageView)findViewById(R.id.imgffichephoto);
        retour=(Button)findViewById((R.id.retour)) ;
        btnEnreg = (Button) findViewById(R.id.btnEnreg);
        this.controle = Controle.getInstance(this);
        //méthode pour gérer les évènement
        createOnclicBtnPrendrePhoto();
        createOnclicBtnEnreg();
        afficher();
        retour();
    }
    public void retour() {
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();

            }
        });
    }
    private void afficher(){
        localistion = (String) getIntent().getSerializableExtra("localistion");
        String locality = (String) getIntent().getSerializableExtra("locality");
        codepostale = (String) getIntent().getSerializableExtra("codepostale");
    }

    private void createOnclicBtnEnreg() {
        btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaStore.Images.Media.insertImage(getContentResolver(), image, "photo"+time, "description");
                enregitrephoto(photoPath,localistion,codepostale);
            }
        });
    }
    /**
     * Récupération de tous les info de la photo
     * @param nomimage
     * @param adress
     * @param Codepostale
     */
    private void enregitrephoto(String nomimage, String adress, String Codepostale){
        this.controle.creephoto(nomimage, adress, Codepostale, this);

    }

    private void createOnclicBtnPrendrePhoto(){
        btnPrendrePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prendreUnePhoto();
            }
        });
    }



    private void prendreUnePhoto(){
        //cree un intent pour ouvrir une fenêtre pour prendre la photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            //cree un nom de fichier unique
            time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                photofile = File.createTempFile("photo"+time,".jpg",photoDir);
                //enregistrer le chemin complet
                photoPath = photofile.getAbsolutePath();
                //créer l'URI
                Uri photoUri = FileProvider.getUriForFile(MainActivity.this,
                        MainActivity.this.getApplicationContext().getPackageName()+".provider",photofile);
                //transfert uri vers l'instent pour enregistrement  photo dans fichier temporaire
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //ouvrir l'activity par rapport à l'intent
                startActivityForResult(intent,RETOUR_PRENDRE_PHOTO);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * retour de l'appel de l'appareil photo
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //vérifie le bon code de retour et l'état du retour ok
        if(requestCode==RETOUR_PRENDRE_PHOTO && resultCode==RESULT_OK){
            //récupérer l'image
            image = BitmapFactory.decodeFile(photoPath);
            imgffichephoto.setScaleType(ImageView.ScaleType.FIT_XY);
            imgffichephoto.setMaxWidth(1000);
            imgffichephoto.setMaxHeight(1000);
            imgffichephoto.drawableHotspotChanged(0,0);
            imgffichephoto.setAdjustViewBounds(true);
            imgffichephoto.setHorizontalFadingEdgeEnabled(true);
            imgffichephoto.setImageBitmap(image);
            imgffichephoto.setRotation(90);
        }
    }
}