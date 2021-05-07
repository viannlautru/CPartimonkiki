package com.example.myapplication.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.controleur.Controle;
import com.example.myapplication.modele.Deparment;
import com.example.myapplication.modele.Photo;
import com.example.myapplication.modele.PointPromo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu extends AppCompatActivity implements View.OnClickListener, LocationListener {

    public static Address adresse;
    private Button radarButton, positionCarte;
    private LocationListener ll;
    private LocationManager lManager;
    private Location location;
    private Button btn;
    private Button btnEnreg;
    private Controle controle;
    private Button promo;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lManager = (LocationManager) getSystemService(Menu.this.LOCATION_SERVICE);
        this.controle = Controle.getInstance(this);
        promo = (Button) findViewById(R.id.CodePromo);
        positionCarte = (Button) findViewById(R.id.positionCarte);
        positionCarte.setOnClickListener(this);
        btnEnreg = (Button) findViewById(R.id.photo);
        btn = (Button) findViewById(R.id.gallerie);
        createOnclicBtnEnreg();
        afficherAdresse();
        affichedepart();
        lespromo();
        newactiviti();
    }
    private void createOnclicBtnEnreg() {
        btnEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appel = new Intent(Menu.this, MainActivity.class);
                String adresseAddressLine = adresse.getAddressLine(0);
                String postalCode = adresse.getPostalCode();
                String locality = adresse.getLocality();
                String strs = "Pas mis votre localisation";
                if (adresseAddressLine != null ){
                    appel.putExtra("localistion", adresseAddressLine);
                    appel.putExtra("codepostale", postalCode);
                    appel.putExtra("locality", locality);
                    startActivity(appel);
                }else{
                    appel.putExtra("localistion", strs);
                    startActivity(appel);
                }
            }
        });
    }
    public void newactiviti() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appel = new Intent(Menu.this, gallerie.class);
                startActivity(appel);

            }
        });
    }
    private void lespromo() {
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appel = new Intent(Menu.this,Promo.class);
                startActivity(appel);
            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.positionCarte:
                position(v);
                break;
            default:
                break;
        }
    }
    private void affichedepart() {
        int dep = controle.nombredepartement();
        ArrayList<PointPromo> promo = controle.recuppromo();

            ((TextView) findViewById(R.id.dep)).setText("nombre de département " + dep );
            int p = dep%3;
        if (p == 0) {
            int m =controle.nombrepromo(dep);
            //Vérifie si le nombre de département ne corespond pas un numPhotopromo dans la table promo
            if( m== 0){
                String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
                String pass = "";
                for (int x = 0; x < 6; x++) {
                    int i = (int) Math.floor(Math.random() * chars.length() - 1); // Si tu supprimes des lettres tu diminues ce nb
                    pass += chars.charAt(i);

                }
                creepromo(dep,pass);
            }

            }

        ((TextView) findViewById(R.id.point)).setText("nombre de Promo " + promo.size());
    }
    private void creepromo(Integer num, String code){
        this.controle.creecodepromo(num, code, this);

    }
    private void afficherAdresse() { // Affiche l'adresse postale dans un TextView
        Geocoder geo = new Geocoder(Menu.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = this.lManager.getLastKnownLocation(
                LocationManager.PASSIVE_PROVIDER);
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // Et bien vérifier ces permissions (il faut mettre ces permissions aussi dans ton Manifest.xml)
                location = this.lManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                lManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 60000, 0, this);
            }


        try {
            //Ici on récupère la premiere adresse trouvée gràce à la position que l'on a récupérée
            List<Address> adresses = geo.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            if (adresses != null && adresses.size() == 1) {
                adresse = adresses.get(0);
                // Si le geocoder a trouvé une adresse, alors on l'affiche
                ((TextView) findViewById(R.id.adresse)).setText(String.format("%s",
                        adresse.getAddressLine(0)));
            } else {
                // Sinon on affiche un message d'erreur
                ((TextView) findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
            }
        } catch (IOException e) {
            e.printStackTrace();
            ((TextView) findViewById(R.id.adresse)).setText("L'adresse n'a pu être déterminée");
        }
    }

    public void position(final View v) { // Cette méthode ouvre Google Map et affiche la position géographique de l'utilisateur sur la carte, un toast te préviens si ça a marché ou pas
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = this.lManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                location = this.lManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        }

        if(null == location || (System.currentTimeMillis()-location.getTime() > 3600e3)) {
            Toast.makeText(this, "Position inconnue" + location, Toast.LENGTH_LONG).show();
            v.setEnabled(false);
            this.lManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, ll, null);
        } else {
            Toast.makeText(this, "Dernière position connue " + (((TextView)this.findViewById(R.id.adresse))).getText(), Toast.LENGTH_LONG).show();
            this.analyseLocation(location);
        }
    }

    private void analyseLocation(final Location l) { // Cette méthode est utilisée dans la méthode précédente, c'est pour pouvoir ouvrir l'application Google Map (donc il y a un Intent)
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + l.getLatitude() + "," + l.getLongitude()));
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        if(activities.size() > 0) {
            this.startActivity(i);
        }
    }

    public void onLocationChanged(Location location) { // Les 4 fonctions qui suivent sont obligatoires dans la documentation Android, même si certaines n'ont rien dedans il faut les mettre
        analyseLocation(location);
        //... on sauvegarde la position
        this.location = location;
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        lManager.removeUpdates(this);
    }

    public void onProviderDisabled(String provider) {
        //... et on spécifie au service que l'on ne souhaite plus avoir de mise à jour
        lManager.removeUpdates(this);
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
