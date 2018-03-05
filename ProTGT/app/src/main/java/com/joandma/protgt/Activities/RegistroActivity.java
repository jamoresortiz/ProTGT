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
import android.widget.Toast;

import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.ModelsApiProTGT.VerifyModel;
import com.joandma.protgt.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    Button buttonRegistro;
    TextInputEditText nombre, apellidos, correo, pass, repPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        nombre = findViewById(R.id.textInputNombre);
        apellidos = findViewById(R.id.textInputApellidos);
        correo = findViewById(R.id.textInputCorreo);
        pass = findViewById(R.id.textInputPass);
        repPass = findViewById(R.id.textInputPassRep);


        buttonRegistro = findViewById(R.id.button_telefono_siguiente);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombre.getText().toString().equals("")){
                    nombre.setError(getString(R.string.error_nombre));
                } else  if(apellidos.getText().toString().equals("")){
                    apellidos.setError(getString(R.string.error_apellidos));
                } else if(correo.getText().toString().equals("")){
                    correo.setError(getString(R.string.error_email_registro));
                } else if(pass.getText().toString().equals("")) {
                    pass.setError(getString(R.string.error_password_registro));
                } else if(repPass.getText().toString().equals("") || !repPass.getText().toString().equals(pass.getText().toString())){
                    repPass.setError(getString(R.string.error_passwords_repetidas));
                } else {

                    InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);


                    VerifyModel verifyModel = new VerifyModel();
                    String email = correo.getText().toString();

                    verifyModel.setEmail(email);

                    Call<ResponseBody> call = api.verifyEmailTelephone(verifyModel);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.code() == 200){
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
                                correo.setError(getString(R.string.error_email_registrado));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("TAG","onFailure login: "+t.toString());
                            Toast.makeText(RegistroActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });


    }
}
