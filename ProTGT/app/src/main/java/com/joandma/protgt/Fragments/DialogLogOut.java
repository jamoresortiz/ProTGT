package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.joandma.protgt.Activities.LoginActivity;
import com.joandma.protgt.Activities.SettingsActivity;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.R;

/**
 * Created by mrdiaz on 27/02/2018.
 */

public class DialogLogOut extends DialogFragment {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        prefs = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = prefs.edit();

        builder.setMessage("¿Estás seguro/a que quieres cerrar sesión?")
                .setTitle("Cerrar Sesión")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        editor.putString(PreferenceKeys.USER_TOKEN, null);
                        editor.putString(PreferenceKeys.USER_NAME, null);
                        editor.putString(PreferenceKeys.ADDRESS_ID, null);
                        editor.commit();


                        Intent intentLogOut = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intentLogOut);
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();

    }
}
