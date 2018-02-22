package com.joandma.protgt.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.joandma.protgt.R;

public class RegistroActivity extends AppCompatActivity {

    Button buttonRegistro;
    TextInputEditText nombre, apellidos, correo, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = findViewById(R.id.textInputNombre);
        apellidos = findViewById(R.id.textInputApellidos);
        correo = findViewById(R.id.textInputCorreo);
        pass = findViewById(R.id.textInputPass);

        buttonRegistro = findViewById(R.id.button_telefono_siguiente);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("")){
                    nombre.setError("Escriba el nombre por favor");
                } else  if(apellidos.getText().toString().equals("")){
                    apellidos.setError("Escriba los apellidos por favor");
                } else if(correo.getText().toString().equals("")){
                    correo.setError("Escriba el correo por favor");
                } else if(pass.getText().toString().equals("")){
                    pass.setError("Escriba la contraseña por favor");
                } else {
                    Intent i = new Intent(RegistroActivity.this, TelefonoActivity.class);
                    startActivity(i);
                }
            }
        });


    }
}
