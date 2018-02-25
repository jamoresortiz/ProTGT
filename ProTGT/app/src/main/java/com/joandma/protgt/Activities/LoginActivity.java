package com.joandma.protgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.User;
import com.joandma.protgt.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                break;

            case R.id.button_iniciarSesion:
                if (email.getText().toString().equals("")) {
                    email.setError("Escriba el email por favor");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Escriba la contraseña por favor");
                } else {

                    User userLoged = new User();

                    userLoged.setEmail(email.getText().toString());
                    userLoged.setPassword(password.getText().toString());


                    InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                    Call<User> call = api.loginUser(userLoged);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if (response.isSuccessful()){
                                User result = response.body();

                                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString(PreferenceKeys.USER_TOKEN, result.getToken());
                                editor.putString(PreferenceKeys.USER_NAME, result.getNombre());
                                editor.putString(PreferenceKeys.USER_SURNAME, result.getApellidos());
                                editor.putString(PreferenceKeys.USER_EMAIL, result.getEmail());
                                editor.putString(PreferenceKeys.USER_PAIS, result.getPais());
                                editor.putString(PreferenceKeys.USER_TELEFONO, result.getTelefono());

                                editor.commit();

                                Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intentLogin);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("TAG","onFailure login: "+t.toString());
                        }
                    });

                }
                break;

        }

    }
}


