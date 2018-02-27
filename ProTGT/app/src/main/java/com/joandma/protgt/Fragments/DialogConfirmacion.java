package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.joandma.protgt.R;

/**
 * Created by mrdiaz on 27/02/2018.
 */

public class DialogConfirmacion extends DialogFragment {

    ImageView imageViewChecked;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Estás seguro/a que es una alerta?")
                .setTitle("Confirmación de alerta")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Emergencia activada", Toast.LENGTH_SHORT).show();
                        imageViewChecked = getActivity().findViewById(R.id.imageViewEmergencia);
                        imageViewChecked.setImageResource(R.drawable.ic_checked);
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Alerta cancelada", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
        });

        return builder.create();

    }
}
