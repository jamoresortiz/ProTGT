package com.joandma.protgt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.joandma.protgt.R;

public class TelefonoActivity extends AppCompatActivity {

    Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        final Spinner pais = findViewById(R.id.spinnerPais);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.paises, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        pais.setAdapter(adapter);

        siguiente = findViewById(R.id.button_telefono_siguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTelefono = new Intent(TelefonoActivity.this, MensajeScreenActivity.class);
                startActivity(intentTelefono);
            }
        });
    }
}
