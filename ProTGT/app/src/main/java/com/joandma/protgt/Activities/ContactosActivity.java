package com.joandma.protgt.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joandma.protgt.R;

public class ContactosActivity extends AppCompatActivity {

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;
    TextView tvNombreContacto, tvTelefonoContacto;
    Button buttonTerminarContacto;
    ImageView imageViewAgregarContacto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        buttonTerminarContacto = findViewById(R.id.buttonTerminarContacto);
        tvNombreContacto = findViewById(R.id.tvNombreContacto);
        tvTelefonoContacto = findViewById(R.id.tvTelefonoContacto);

        imageViewAgregarContacto = findViewById(R.id.ivAgregarContacto);

        imageViewAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContact();
            }
        });

        buttonTerminarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFinalizarRegistro = new Intent(ContactosActivity.this, HomeActivity.class);
                startActivity(intentFinalizarRegistro);
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

                tvNombreContacto.setText(nombre);
                tvTelefonoContacto.setText(telefono);

            }
        }


    }
}
