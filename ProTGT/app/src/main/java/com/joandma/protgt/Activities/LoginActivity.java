package com.joandma.protgt.Activities;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joandma.protgt.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    TextView registro;
    TextInputEditText email, password;
    Button iniciarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registro = findViewById(R.id.textViewRegistro);
        iniciarSesion = findViewById(R.id.button_iniciarSesion);
        email = findViewById(R.id.textInputEmail);
        password = findViewById(R.id.textInputPassword);

        eventListener();


    }

    private void eventListener() {
        registro.setOnClickListener(this);
        iniciarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int seleccion = v.getId();

        switch (seleccion) {
            case R.id.textViewRegistro:
                Intent intentRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intentRegistro);

            case R.id.button_iniciarSesion:
                if (email.getText().toString().length() == 0) {
                    email.setError("Escriba el email por favor");
                } else if (password.getText().toString().length() == 0) {
                    password.setError("Escriba la contraseña por favor");
                } else {
                    Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intentLogin);
                    finish();
                }

        }

    }
}


