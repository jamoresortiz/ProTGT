package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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

        builder.setMessage(getString(R.string.mensaje_dialog_logout))
                .setTitle(getString(R.string.titulo_dialog_logout))
                .setPositiveButton(getString(R.string.boton_dialog_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        editor.putString(PreferenceKeys.USER_TOKEN, null);
                        editor.putString(PreferenceKeys.USER_NAME, null);
                        editor.putString(PreferenceKeys.ADDRESS_ID, null);
                        editor.putBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, false);
                        editor.commit();


                        Intent intentLogOut = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intentLogOut);
                        dialog.cancel();
                    }
                }).setNegativeButton(getString(R.string.boton_dialog_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();

    }
}
