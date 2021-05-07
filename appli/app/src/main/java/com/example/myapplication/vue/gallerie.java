package com.example.myapplication.vue;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.controleur.Controle;
import com.example.myapplication.modele.AccesLocal;
import com.example.myapplication.modele.Deparment;
import com.example.myapplication.modele.Photo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class gallerie extends AppCompatActivity {

    private Controle controle;
    private TextView depart;
    private Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerie);
        init();

    }
    public void init(){
        retour=(Button)findViewById((R.id.retour)) ;
        depart=(TextView)findViewById(R.id.departement);
        this.controle = Controle.getInstance(this);
        recupphoto();
        nombredepart();
        retour();
    }

    /**
     * Bouton de retour
     */
    public void retour() {
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallerie.this.finish();

            }
        });
    }

    /**
     * récupère le nombre de départment enregistrer
     */
    public void nombredepart(){
        int dep = controle.nombredepartement();
        depart.setText( dep+" départements ont été photographiés" );
    }

    /**
     * récupère les photos du dossier avec les noms dans la base SQLite
     * Puis les affcihe dans des layouts
     */
    private void recupphoto(){
        Bitmap images ;

        ArrayList<Deparment> deparments = Deparment.getdepartment();

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.principale);

        for( int k = 0; k < deparments.size(); k++ )
        {
            ScrollView main = new ScrollView( this );
            linearLayout2 .addView( main , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
            LinearLayout linearLayout = new LinearLayout(this);

            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout TitreContent = new LinearLayout( this );

            TitreContent.setOrientation(LinearLayout.HORIZONTAL);

            TextView textView = new TextView(this);
            textView.setText(deparments.get(k).getdepartement());

            main.addView(linearLayout , new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT ) );
            linearLayout .addView( TitreContent , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
            TitreContent.addView(textView);
            HorizontalScrollView scvMain = new HorizontalScrollView( this );
            LinearLayout lilContent = new LinearLayout( this );
            LinearLayout listContent = new LinearLayout( this );

            lilContent.setOrientation(LinearLayout.VERTICAL);
            listContent.setOrientation(LinearLayout.HORIZONTAL);

            linearLayout .addView( scvMain , new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
            scvMain .addView( lilContent, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
            lilContent .addView( listContent,  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
            ArrayList<Photo> lesPhotos= controle.affichage(deparments.get(k).getNumero());
            for( int i = 0; i < lesPhotos.size(); i++ )
            {
                ImageView bitmap= new ImageView((this));
                images = BitmapFactory.decodeFile(lesPhotos.get(i).getLiennomphoto());
                bitmap.setImageBitmap(images);
                bitmap.setScaleType(ImageView.ScaleType.FIT_XY);
                bitmap.setMaxWidth(300);
                bitmap.setMaxHeight(300);
                bitmap.setRotation(90);
                bitmap.drawableHotspotChanged(0,0);
                bitmap.setAdjustViewBounds(true);
                bitmap.setHorizontalFadingEdgeEnabled(true);


                listContent.addView(bitmap, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT ) );
             
            }
        }


    }

}