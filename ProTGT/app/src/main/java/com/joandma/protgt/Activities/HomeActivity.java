package com.joandma.protgt.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Fragments.DialogCancelacion;
import com.joandma.protgt.Fragments.DialogConfirmacion;
import com.joandma.protgt.Interfaces.ICancelacionDialog;
import com.joandma.protgt.Models.ContactoConfianza;
import com.joandma.protgt.Models.Direccion;
import com.joandma.protgt.Models.UserRegister;
import com.joandma.protgt.R;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationParams;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, ICancelacionDialog {

    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    TextView localizacion;
    LocationRequest locRequest;
    private GoogleApiClient apiClient;
    ImageView botonSettings;

    boolean enviado;

    SharedPreferences prefs;
    String token, nombre, address_id, contact_id;
    InterfaceRequestApi api;
    UserRegister result;
    SharedPreferences.Editor editor;

    DialogConfirmacion dialogConfirmacion;
    DialogCancelacion dialogCancelacion;
    FragmentManager fragmentManager;

    String location;


    //TODO Falta darle retrofit a la imagen para que mande los datos
    SwipeRefreshLayout swipeContainer;
    ImageView imagenEmergencia, imagenLlamada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prefs = HomeActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);


        token = prefs.getString(PreferenceKeys.USER_TOKEN, null);
        nombre = prefs.getString(PreferenceKeys.USER_NAME, null);

        address_id = prefs.getString(PreferenceKeys.ADDRESS_ID, null);

        contact_id = prefs.getString(PreferenceKeys.CONTACT_ID, null);

        // Comprueba si ya están los datos del usuario cargados en las preferencias o no
        if (nombre == null) {

            api = ServiceGenerator.createService(InterfaceRequestApi.class);

            Call<UserRegister> call = api.detailUser("Bearer " + token);

            call.enqueue(new Callback<UserRegister>() {
                @Override
                public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                    if (response.isSuccessful()) {

                        result = response.body();

                        editor = prefs.edit();

                        editor.putString(PreferenceKeys.USER_NAME, result.getNombre());
                        editor.putString(PreferenceKeys.USER_SURNAME, result.getApellidos());
                        editor.putString(PreferenceKeys.USER_EMAIL, result.getEmail());
                        editor.putString(PreferenceKeys.USER_PAIS, result.getPais());
                        editor.putString(PreferenceKeys.USER_TELEFONO, result.getTelefono());

                        editor.commit();

                    } else {
                        Toast.makeText(HomeActivity.this, "Fallo crítico recogiendo token en home", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<UserRegister> call, Throwable t) {
                    Log.e("TAG", "onFailure login: " + t.toString());
                }
            });

        }

        // Comprueba si ya están los datos de la dirección del usuario cargados en las preferencias o no
        if (address_id == null) {

            api = ServiceGenerator.createService(InterfaceRequestApi.class);

            Call<List<Direccion>> call = api.showAddressesOfUser("Bearer " +token);

            call.enqueue(new Callback<List<Direccion>>() {
                @Override
                public void onResponse(Call<List<Direccion>> call, Response<List<Direccion>> response) {

                    if (response.isSuccessful()){
                        //Recogemos la primera dirección de la lista, ya que por ahora solo guardamos una dirección
                        Direccion result = response.body().get(0);

                        Log.i("DIRECCION","address: " +result);

                        editor = prefs.edit();

                        editor.putString(PreferenceKeys.ADDRESS_ID, result.getId());
                        editor.putString(PreferenceKeys.ADDRESS_PROVINCIA, result.getProvincia());
                        editor.putString(PreferenceKeys.ADDRESS_LOCALIDAD, result.getLocalidad());
                        editor.putString(PreferenceKeys.ADDRESS_CALLE, result.getCalle());
                        editor.putInt(PreferenceKeys.ADDRESS_NUMERO, result.getNumero());

                        if (result.getPiso() != null)
                            editor.putString(PreferenceKeys.ADDRESS_PISO, result.getPiso());
                        else
                            editor.putString(PreferenceKeys.ADDRESS_PISO, null);
                        if (result.getBloque() != null)
                            editor.putString(PreferenceKeys.ADDRESS_BLOQUE, result.getBloque());
                        else
                            editor.putString(PreferenceKeys.ADDRESS_BLOQUE, null);
                        if (result.getPuerta() != null)
                            editor.putString(PreferenceKeys.ADDRESS_PUERTA, result.getPuerta());
                        else
                            editor.putString(PreferenceKeys.ADDRESS_PUERTA, null);

                        editor.commit();

                    } else {
                        Toast.makeText(HomeActivity.this, "Error crítico recogiendo dirección", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Direccion>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //Comprueba si ya están los datos del contacto de confianza del usuario
        if (contact_id == null) {
            api = ServiceGenerator.createService(InterfaceRequestApi.class);

            Call<List<ContactoConfianza>> call = api.showContactsOfUser("Bearer " +token);
            
            call.enqueue(new Callback<List<ContactoConfianza>>() {
                @Override
                public void onResponse(Call<List<ContactoConfianza>> call, Response<List<ContactoConfianza>> response) {
                    //TODO POR HACER
                }

                @Override
                public void onFailure(Call<List<ContactoConfianza>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }


        localizacion = findViewById(R.id.textViewLocalizacion);

        botonSettings = findViewById(R.id.imageButtonSettings);

        imagenEmergencia = findViewById(R.id.imageViewEmergencia);
        imagenLlamada = findViewById(R.id.imageViewLlamada);

        enviado = prefs.getBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, false);
        fragmentManager = getFragmentManager();
        dialogConfirmacion = new DialogConfirmacion();
        dialogCancelacion = new DialogCancelacion();

        if (enviado) {
            //dialogCancelacion.show(fragmentManager, "tagCancelacion");
            imagenEmergencia.setImageResource(R.drawable.ic_checked);
        } else {
            //dialogConfirmacion.show(fragmentManager, "tagConfirmacion");
            imagenEmergencia.setImageResource(R.drawable.ic_emergency);

        }

        imagenEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviado = prefs.getBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, false);

                fragmentManager = getFragmentManager();
                dialogConfirmacion = new DialogConfirmacion();
                dialogCancelacion = new DialogCancelacion();

                if (enviado == false) {
                    dialogConfirmacion.show(fragmentManager, "tagConfirmacion");
                }  else {
                    dialogCancelacion.show(fragmentManager, "tagCancelacion");
                }
            }
        });



        //////////////////////////// SETTINGS //////////////////////////////

        botonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSettings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
            }
        });

        //Esto recoge las preferencias que se están guardando cuando escribes algo en el Dialog
        /*SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

        //Muestra las preferencias que se están guardando como ejemplo
        Log.i("Pref", "Nombre: " + pref.getString("pref_nombre", ""));
        Log.i("Pref", "Apellidos: " + pref.getString("pref_apellidos", ""));
        Log.i("Pref", "Email: " + pref.getString("pref_email", ""));*/

        //////////////////////////////////////////////////////////////////////

        //Construccion cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


        imagenLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber();
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

    //Esto es para hacer la llamada de telefono
    public void dialPhoneNumber() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:653675459"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            *//*Intent intentConfiguracion = new Intent(HomeActivity.this, ConfiguracionActivity.class);
            startActivity(intentConfiguracion);*//*
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void toggleLocationUpdates(boolean enable) {
        if (enable) {
            enableLocationUpdates();
        } else {
            disableLocationUpdates();
        }
    }

    private void enableLocationUpdates(){

        locRequest = new LocationRequest();
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(HomeActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        break;
                }
            }
        });

    }

    private void disableLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient,  HomeActivity.this);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, HomeActivity.this);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.
        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc){
        if(loc != null){
            final String text = String.format("",
                    loc.getLatitude(),
                    loc.getLongitude());

            String text2 = text;

            location = loc.getLatitude() + "," +loc.getLongitude();

            editor = prefs.edit();

            editor.putString(PreferenceKeys.LOCATION_LATLNG, location);

            editor.commit();

            if(text != text2){
                localizacion.setText(text);
            }


            // We are going to get the address for the current position
            SmartLocation.with(this).geocoding().reverse(loc, new OnReverseGeocodingListener() {
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
            localizacion.setText(getString(R.string.aviso_localizacion));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOGTAG, "Recibida nueva ubicación!");

        //Mostramos la nueva ubicación recibida
        updateUI(location);
    }


    //Esto es del refreshLayout para actualizar solo con bajar el activity
       @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean actualizar = true;

                if(actualizar == true){
                    toggleLocationUpdates(actualizar);
                    actualizar = false;
                } else {
                    toggleLocationUpdates(actualizar);
                    actualizar = true;
                }

                swipeContainer.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onCancelarClick(String password) {

    }
}
