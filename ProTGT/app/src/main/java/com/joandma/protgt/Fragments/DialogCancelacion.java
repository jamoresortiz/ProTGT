package com.joandma.protgt.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Interfaces.ICancelacionDialog;
import com.joandma.protgt.R;

/**
 * Created by mrdiaz on 27/02/2018.
 */

public class DialogCancelacion extends DialogFragment {

    ImageView imageViewEmergencia;
    EditText editTextKey;
    ICancelacionDialog mListener;

    String key;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public DialogCancelacion(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        prefs = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        editor = prefs.edit();

        //TODO PONERLO EN ARCHIVO STRING
        key = getString(R.string.key);

        builder.setMessage(getString(R.string.mensaje_dialog_cancelacion))
                .setTitle(getString(R.string.titulo_dialog_cancelacion))
                .setPositiveButton(getString(R.string.boton_dialog_aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String pass = editTextKey.getText().toString();
//                        if (pass.equals(key)) {
//                            mListener.onCancelarClick(pass);
//                        }else {
//                            Toast.makeText(getActivity(), "Palabra clave incorrecta", Toast.LENGTH_SHORT).show();
//                        }

                        if (pass.equals(key)) {
                            Toast.makeText(getActivity(), getString(R.string.emergencia_desactivada), Toast.LENGTH_SHORT).show();

                            imageViewEmergencia = getActivity().findViewById(R.id.imageViewEmergencia);
                            imageViewEmergencia.setImageResource(R.drawable.ic_emergency);
                            editor.putBoolean(PreferenceKeys.BOOLEAN_COMPROBACION, false);
                            editor.commit();

                            dialog.dismiss();
                        } else{
                            Toast.makeText(getActivity(), getString(R.string.error_palabra_incorrecta), Toast.LENGTH_SHORT).show();
                        }


                    }
                }).setNegativeButton(getString(R.string.boton_dialog_cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_cancel, null);



        editTextKey = v.findViewById(R.id.editTextKeyCancel);

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
