package com.joandma.protgt.Activities;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.joandma.protgt.R;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

public class HomeActivity extends AppCompatActivity
        implements  SwipeRefreshLayout.OnRefreshListener, OnLocationUpdatedListener {

    private static final int PETICION_PERMISO_LOCALIZACION = 1001;
    private LocationGooglePlayServicesProvider provider;
    TextView localizacion;

    //TODO Falta actualizar la imagen cuando pinches en ella y darle accion
    //TODO Falta darle una accion al boton del telefono para el intent inplicito de la llamada
    SwipeRefreshLayout swipeContainer;
    ImageView imagenEmergencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        localizacion = findViewById(R.id.textViewLocalizacion);

        imagenEmergencia = findViewById(R.id.imageViewEmergencia);

        startLocation();

        imagenEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean enviado = true;

                if (enviado == true) {
                    imagenEmergencia.setImageResource(R.drawable.ic_checked);
                    Toast.makeText(HomeActivity.this, "Se han enviado los datos de emergencia", Toast.LENGTH_SHORT).show();
                    enviado = false;
                }

            }
        });


        //RefreshLayout para actualizar deslizando la pantalla hacia abajo
        swipeContainer = findViewById(R.id.contenedor);
        swipeContainer.setOnRefreshListener(this);
        //Esto sirve para cambiarle los colores al circulo que va dando vueltas
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocation();
        }
    }

    //Muestra la ultima localización, esto hay que setearlo en el textView del XML
    private void showLast() {
        Location lastLocation = SmartLocation.with(this).location().getLastLocation();
        if (lastLocation != null) {
            localizacion.setText(
                    String.format("[Caché] Latitude %.6f, Longitude %.6f",
                            lastLocation.getLatitude(),
                            lastLocation.getLongitude())
            );
        }
    }

    //Empieza a buscar la localización
    private void startLocation() {

        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);
        localizacion.setText("Active la ubicación o deslize para actualizar su posición");

        SmartLocation smartLocation = new SmartLocation.Builder(this).logging(true).build();


        // NAVIGATION cambia cada 0 metros
        smartLocation.location().config(LocationParams.NAVIGATION).start(this);

    }

    //Muestra la localizacion
    private void showLocation(Location location) {
        if (location != null) {
            final String text = String.format("",
                    location.getLatitude(),
                    location.getLongitude());

            final String text2 = text;

            if (text != text2){
                localizacion.setText(text);
            }



            // We are going to get the address for the current position
            SmartLocation.with(this).geocoding().reverse(location, new OnReverseGeocodingListener() {
                @Override
                public void onAddressResolved(Location original, List<Address> results) {
                    if (results.size() > 0) {
                        Address result = results.get(0);
                        StringBuilder builder = new StringBuilder(text);
                        //builder.append("\n[Reverse Geocoding] ");
                        List<String> addressElements = new ArrayList<>();
                        for (int i = 0; i <= result.getMaxAddressLineIndex(); i++) {
                            addressElements.add(result.getAddressLine(i));
                        }
                        builder.append(TextUtils.join(", ", addressElements));
                        localizacion.setText(builder.toString());
                    }
                }
            });
        } else {
            localizacion.setText("No hay localización");
        }
    }

    //Para la localización
    private void stopLocation() {
        SmartLocation.with(this).location().stop();
    }


    @Override
    public void onLocationUpdated(Location location) {
        showLocation(location);
    }


    private void toggleLocationUpdates(boolean enable) {
        if (enable) {
            startLocation();
        } else {
            stopLocation();
        }
    }


    //Esto es del refreshLayout para actualizar solo con bajar el activity
    //TODO Esto tenemos que probarlo con el movil de jorge caminando por la calle para comprobar si está cogiendo de verdad la localización o no
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean actualizar = true;

                if (actualizar == true) {
                    toggleLocationUpdates(actualizar);
                    actualizar = false;
                } else {
                    toggleLocationUpdates(actualizar);
                    actualizar = true;
                }

                Toast.makeText(HomeActivity.this, "Tu ubicación se ha actualizado", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        }, 2000);
    }



}
