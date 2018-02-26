package com.joandma.protgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.User;
import com.joandma.protgt.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                    InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                    Call<User> call = api.verifyEmailTelephone(correo.getText().toString());

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()){
                                SharedPreferences prefs = RegistroActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString(PreferenceKeys.USER_NAME, nombre.getText().toString());
                                editor.putString(PreferenceKeys.USER_SURNAME, apellidos.getText().toString());
                                editor.putString(PreferenceKeys.USER_EMAIL, correo.getText().toString());
                                editor.putString(PreferenceKeys.USER_PASSWORD, pass.getText().toString());

                                editor.commit();

                                Intent i = new Intent(RegistroActivity.this, TelefonoActivity.class);
                                startActivity(i);
                            } else {
                                correo.setError("Email ya registrado");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("TAG","onFailure login: "+t.toString());
                        }
                    });


                }
            }
        });


    }
}
