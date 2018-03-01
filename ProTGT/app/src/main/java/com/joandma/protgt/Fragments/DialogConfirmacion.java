package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        prefs = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = prefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Estás seguro/a que es una alerta?")
                .setTitle("Confirmación de alerta")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {

                        String location = prefs.getString(PreferenceKeys.LOCATION_LATLNG, null);
                        String token = prefs.getString(PreferenceKeys.USER_TOKEN, null);

                        Ruta ruta = new Ruta();
                        Aviso aviso = new Aviso();

                        ruta.setLocalizacion(location);

                        InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                        Call<Aviso> call = api.addAviso("Bearer "+token, ruta);
                        
                        call.enqueue(new Callback<Aviso>() {
                            @Override
                            public void onResponse(Call<Aviso> call, Response<Aviso> response) {
                                if (response.isSuccessful()) {

                                    Toast.makeText(getActivity(), "Emergencia activada", Toast.LENGTH_SHORT).show();


                                    imageViewChecked = getActivity().findViewById(R.id.imageViewEmergencia);
                                    imageViewChecked.setImageResource(R.drawable.ic_checked);
                                    editor.putBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, true);
                                    editor.commit();
                                    dialog.cancel();

                                } else {
                                    Log.i("ERROR", "Fallo crítico creando aviso");
                                }
                            }

                            @Override
                            public void onFailure(Call<Aviso> call, Throwable t) {
                                Log.e("TAG", "onFailure login: " + t.toString());
                            }
                        });


                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "Alerta cancelada", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
        });

        return builder.create();

    }
}
