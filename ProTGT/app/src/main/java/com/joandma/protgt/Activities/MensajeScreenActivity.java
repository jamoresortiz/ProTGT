package com.joandma.protgt.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joandma.protgt.R;

public class MensajeScreenActivity extends AppCompatActivity {

    Button enviarCodigo;
    TextInputEditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_screen);


        enviarCodigo = findViewById(R.id.buttonEnviarCodigo);
        codigo = findViewById(R.id.textInputCodigo);

        enviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo.getText().toString().equals("")){
                    codigo.setError("Por favor escriba el codigo del mensaje");
                } else {
                    Intent i = new Intent(MensajeScreenActivity.this, DomicilioActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}
