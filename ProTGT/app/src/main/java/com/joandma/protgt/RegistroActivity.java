package com.joandma.protgt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegistroActivity extends AppCompatActivity {

    Button buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final Spinner pais = findViewById(R.id.spinnerPais);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.paises, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        pais.setAdapter(adapter);

        buttonRegistro = findViewById(R.id.buttonRegistro);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroActivity.this, MensajeScreenActivity.class);
                startActivity(i);
            }
        });


    }
}
