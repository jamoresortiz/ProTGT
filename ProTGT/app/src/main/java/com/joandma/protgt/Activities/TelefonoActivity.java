package com.joandma.protgt.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.joandma.protgt.R;

public class TelefonoActivity extends AppCompatActivity {

    Button siguiente;
    TextInputEditText telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        final Spinner pais = findViewById(R.id.spinnerPais);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.paises, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        pais.setAdapter(adapter);

        siguiente = findViewById(R.id.button_telefono_siguiente);
        telefono = findViewById(R.id.textInputTelefono);


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telefono.getText().toString().equals("")){
                    telefono.setError("Escriba su telefono por favor");
                } else if(pais.getSelectedItemPosition() == 0){
                    Toast.makeText(TelefonoActivity.this, "Seleccione un país por favor", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentTelefono = new Intent(TelefonoActivity.this, MensajeScreenActivity.class);
                    startActivity(intentTelefono);
                }
            }
        });
    }
}
