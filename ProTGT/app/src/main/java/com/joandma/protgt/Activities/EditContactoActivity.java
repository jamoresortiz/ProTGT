package com.joandma.protgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.R;

public class EditContactoActivity extends AppCompatActivity {

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    TextView tvNombreContactoEdit, tvTelefonoContactoEdit;
    Button buttonEditarContacto;
    ImageView imageViewElegirContacto;

    String nombreContacto, telefonoContacto;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto);

        prefs = EditContactoActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);


        buttonEditarContacto = findViewById(R.id.buttonEditarContacto);
        imageViewElegirContacto = findViewById(R.id.ivElegirContacto);

        tvNombreContactoEdit = findViewById(R.id.tvNombreContactoEdit);
        tvTelefonoContactoEdit = findViewById(R.id.tvTelefonoContactoEdit);

        nombreContacto = prefs.getString(PreferenceKeys.CONTACT_NAME, null);
        telefonoContacto = prefs.getString(PreferenceKeys.CONTACT_TELEFONO, null);

        tvNombreContactoEdit.setText(nombreContacto);
        tvTelefonoContactoEdit.setText(telefonoContacto);



        imageViewElegirContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContact();
            }
        });

        buttonEditarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditContact = new Intent(EditContactoActivity.this, SettingsActivity.class);
                startActivity(intentEditContact);
                finish();
            }
        });

    }


    public void selectContact() {
        // Start an activity for the user to pick a phone number from contacts
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int nombreIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
                int telefonoIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String nombre = cursor.getString(nombreIndex);
                String telefono = cursor.getString(telefonoIndex);
                // Do something with the phone number

                tvNombreContactoEdit.setText(nombre);
                tvTelefonoContactoEdit.setText(telefono);

            }
        }


    }
}
