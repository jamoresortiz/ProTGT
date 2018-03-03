package com.joandma.protgt.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Activities.HomeActivity;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.Aviso;
import com.joandma.protgt.Models.Ruta;
import com.joandma.protgt.R;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mrdiaz on 27/02/2018.
 */

public class DialogConfirmacion extends DialogFragment {

    ImageView imageViewChecked;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Activity ctx;

    Ruta ruta;

    String location;

    Boolean comprobacion;

    //Mensaje de texto que se va a enviar por defecto
    String smsMensaje = "Mensaje de SMS por defecto";

    //Este sería el número que se coge por defecto al elegir un contacto de confianza
    String smsNumero = "653675459";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        prefs = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = prefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ctx = getActivity();
        final String token = prefs.getString(PreferenceKeys.USER_TOKEN, null);



        builder.setMessage(getString(R.string.mensaje_dialog_confirmacion))
                .setTitle(getString(R.string.titulo_dialog_confirmacion))
                .setPositiveButton(getString(R.string.boton_dialog_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {

                        location = prefs.getString(PreferenceKeys.LOCATION_LATLNG, null);

                        ruta = new Ruta();

                        ruta.setLocalizacion(location);

                        final InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                        Call<Aviso> call = api.addAviso("Bearer "+token, ruta);
                        
                        call.enqueue(new Callback<Aviso>() {
                            @Override
                            public void onResponse(Call<Aviso> call, Response<Aviso> response) {
                                if (response.isSuccessful()) {
                                    Aviso result = response.body();

                                    Toast.makeText(ctx, R.string.emergencia_activada, Toast.LENGTH_SHORT).show();

                                    imageViewChecked = ctx.findViewById(R.id.imageViewEmergencia);
                                    imageViewChecked.setImageResource(R.drawable.ic_checked);
                                    editor.putBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, true);
                                    editor.commit();
                                    dialog.cancel();

                                    final String id_aviso = result.getId();

                                    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

                                    
                                    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                                        

                                        @Override
                                        public void run() {
                                            comprobacion = prefs.getBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, false);

                                            if (comprobacion) {
                                                /*
                                                Algoritmo del ciclo

                                                - Tomar la lat, lng actualizada
                                                - Enviarla al servidor
                                                - Comprobar si tenemos que seguir enviando

                                             */

                                                location = prefs.getString(PreferenceKeys.LOCATION_LATLNG, null);
                                                ruta = new Ruta();
                                                ruta.setLocalizacion(location);

                                                Log.i("LOC", "" +location);

                                                Call<Aviso> scheduledCall = api.sendLocation("Bearer "+token, id_aviso, ruta);


                                                try {
                                                    Response<Aviso> scheduledResponse = scheduledCall.execute();
                                                    if (scheduledResponse.isSuccessful()){
                                                        Aviso aviso = scheduledResponse.body();

                                                    } else {
                                                        Toast.makeText(ctx, "Fallo crítico enviando la loc", Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                
                                            } else {
                                                Toast.makeText(ctx, "No envia location", Toast.LENGTH_SHORT).show();
                                            }
                                            
                                        }
                                    }, 0, 10, TimeUnit.SECONDS);

                                } else {
                                    Log.i("ERROR", "Fallo crítico creando aviso");
                                }
                            }

                            @Override
                            public void onFailure(Call<Aviso> call, Throwable t) {
                                Log.e("TAG", "onFailure login: " + t.toString());
                                Toast.makeText(ctx, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                            }
                        });


//                        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//
//                        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//
//                            @Override
//                            public void run() {
//
//                                /*
//                                    Algoritmo del ciclo
//
//                                    - Tomar la lat, lng actualizada
//                                    - Enviarla al servidor
//                                    - Comprobar si tenemos que seguir enviando
//
//                                 */
//
//                                location = prefs.getString(PreferenceKeys.LOCATION_LATLNG, null);
//                                ruta = new Ruta();
//                                ruta.setLocalizacion(location);
//
//                                Call<Aviso> scheduledCall = api.sendLocation("Bearer "+token, ruta);
//
//
//                                try {
//                                    Response<Aviso> scheduledResponse = scheduledCall.execute();
//                                    if (scheduledResponse.isSuccessful()){
//                                        Aviso aviso = scheduledResponse.body();
//
//                                        Toast.makeText(ctx, "Se mete aquí", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(ctx, "Fallo crítico enviando la loc", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        }, 0, 5, TimeUnit.SECONDS);

                        //////////////////// SMS /////////////////////

                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + smsNumero));
                        smsIntent.putExtra("sms_body", smsMensaje);
                        startActivity(smsIntent);

                        //////////////////////////////////////////////

                    }
                }).setNegativeButton(getString(R.string.boton_dialog_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
        });

        return builder.create();

    }



}
