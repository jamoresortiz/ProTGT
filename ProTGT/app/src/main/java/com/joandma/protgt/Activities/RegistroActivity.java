package com.joandma.protgt.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.joandma.protgt.R;

public class RegistroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final Spinner pais = findViewById(R.id.spinnerPais);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.paises, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        pais.setAdapter(adapter);



    }
}
