package com.joandma.protgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.joandma.protgt.Models.UserRegister;
import com.joandma.protgt.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    TextView registro;
    TextInputEditText email, password;
    Button iniciarSesion;
    String token;

    UserRegister result;

    InterfaceRequestApi api;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = LoginActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        token = prefs.getString(PreferenceKeys.USER_TOKEN, null);

        if (token != null){

            Intent intentConToken = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intentConToken);
            finish();

//            api = ServiceGenerator.createService(InterfaceRequestApi.class);
//
//            Call<UserRegister> call = api.detailUser("Bearer "+token);
//
//            call.enqueue(new Callback<UserRegister>() {
//                @Override
//                public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
//                    if (response.isSuccessful()){
//
//                        result = response.body();
//
//                        //TODO POR ACABAR
//                        Log.i("USER", "user: " +result);
//
//                        editor = prefs.edit();
//
//                        editor.putString(PreferenceKeys.USER_NAME, result.getNombre());
//                        editor.putString(PreferenceKeys.USER_SURNAME, result.getApellidos());
//                        editor.putString(PreferenceKeys.USER_EMAIL, result.getEmail());
//                        editor.putString(PreferenceKeys.USER_PAIS, result.getPais());
//                        editor.putString(PreferenceKeys.USER_TELEFONO, result.getTelefono());
//
//
//                        Intent intentConToken = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intentConToken);
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Error crítico", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<UserRegister> call, Throwable t) {
//                    Log.e("TAG","onFailure login: "+t.toString());
//                }
//            });

        }

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

                    UserRegister userLoged = new UserRegister();

                    userLoged.setEmail(email.getText().toString());
                    userLoged.setPassword(password.getText().toString());


                    api = ServiceGenerator.createService(InterfaceRequestApi.class);

                    Call<UserRegister> call = api.loginUser(userLoged);

                    call.enqueue(new Callback<UserRegister>() {
                        @Override
                        public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {

                            if (response.isSuccessful()){
                                result = response.body();


                                editor = prefs.edit();

                                editor.putString(PreferenceKeys.USER_TOKEN, result.getToken());

                                editor.commit();

                                Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intentLogin);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserRegister> call, Throwable t) {
                            Log.e("TAG","onFailure login: "+t.toString());
                        }
                    });

                }
                break;

        }

    }
}


