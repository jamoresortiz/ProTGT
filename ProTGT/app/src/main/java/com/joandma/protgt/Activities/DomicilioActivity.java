package com.joandma.protgt.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joandma.protgt.R;

public class DomicilioActivity extends AppCompatActivity {

    TextInputEditText provincia, localidad, calle, numero;
    Button terminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        provincia = findViewById(R.id.textInputProvincia);
        localidad = findViewById(R.id.textInputLocalidad);
        calle = findViewById(R.id.textInputCalle);
        numero = findViewById(R.id.textInputNumero);
        terminar = findViewById(R.id.buttonTerminar);

        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(provincia.getText().toString().equals("")){
                    provincia.setError("Escriba la provincia por favor");
                } else if(localidad.getText().toString().equals("")){
                    localidad.setError("Escriba la localidad por favor");
                } else if(calle.getText().toString().equals("")){
                    calle.setError("Escriba la calle por favor");
                } else if(numero.getText().toString().equals("")){
                    numero.setError("Escriba el número por favor");
                } else {
                    Intent intentDomicilio = new Intent(DomicilioActivity.this, HomeActivity.class);
                    startActivity(intentDomicilio);
                }

            }
        });

    }
}
