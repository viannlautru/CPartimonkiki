package com.example.myapplication.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.controleur.Controle;
import com.example.myapplication.modele.PointPromo;

import java.util.ArrayList;

public class Promo extends AppCompatActivity {
    private Controle controle;
    private Button retour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        init();
    }

    public void init(){
        retour=(Button)findViewById((R.id.retour)) ;
        this.controle = Controle.getInstance(this);
        recuppromo();
        retour();
    }

    /**
     * Bouton retour
     */
    public void retour() {
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Promo.this.finish();

            }
        });
    }

    /**
     * compte et affiche le nombre de code promo gagné
     */
    public void recuppromo(){
        ArrayList<PointPromo> promo = controle.recuppromo();
        String text = "";
        ((TextView) findViewById(R.id.textView2)).setText("Tu poséde "+promo.size()+" codes promos ");
        TextView txt = (TextView)findViewById(R.id.textView4);
        for(PointPromo promos : promo ){
            text = text+""+promos.getCodepromo()+"\n\n";
            txt.setText(text);
        }

    }
}