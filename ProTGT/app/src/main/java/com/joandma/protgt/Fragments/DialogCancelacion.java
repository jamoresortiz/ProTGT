package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.joandma.protgt.Interfaces.ICancelacionDialog;
import com.joandma.protgt.R;

/**
 * Created by mrdiaz on 27/02/2018.
 */

public class DialogCancelacion extends DialogFragment {

    ImageView imageViewEmergencia;
    EditText editTextPassword;
    ICancelacionDialog mListener;

    public DialogCancelacion(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Ya no tienes una emergencia?")
                .setTitle("Parar emergencia")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "Emergencia desactivada", Toast.LENGTH_SHORT).show();
                        imageViewEmergencia = getActivity().findViewById(R.id.imageViewEmergencia);
                        imageViewEmergencia.setImageResource(R.drawable.ic_emergency);

                        String pass = editTextPassword.getText().toString();
                        mListener.onCancelarClick(pass);

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_cancel, null);

        editTextPassword = v.findViewById(R.id.editTextPasswordCancel);

        builder.setView(v);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (ICancelacionDialog) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement IDialogCancelacion");
        }

    }

}
