package com.joandma.protgt.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
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
import com.joandma.protgt.Models.Direccion;
import com.joandma.protgt.Models.User;
import com.joandma.protgt.Models.UserRegister;
import com.joandma.protgt.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DomicilioActivity extends AppCompatActivity {

    TextInputEditText provincia, localidad, calle, numero, piso, bloque, puerta;
    Long id_direccion;
    Button terminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domicilio);

        provincia = findViewById(R.id.textInputProvincia);
        localidad = findViewById(R.id.textInputLocalidad);
        calle = findViewById(R.id.textInputCalle);
        numero = findViewById(R.id.textInputNumero);
        piso = findViewById(R.id.textInputPiso);
        bloque = findViewById(R.id.textInputBloque);
        puerta = findViewById(R.id.textInputPuerta);
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

                    final UserRegister newUser = new UserRegister();

                    SharedPreferences prefs = DomicilioActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);
                    final String nombre = prefs.getString(PreferenceKeys.USER_NAME, null);
                    final String apellidos = prefs.getString(PreferenceKeys.USER_SURNAME, null);
                    final String email = prefs.getString(PreferenceKeys.USER_EMAIL, null);
                    final String password = prefs.getString(PreferenceKeys.USER_PASSWORD, null);
                    final String pais = prefs.getString(PreferenceKeys.USER_PAIS, null);
                    final String telefono = prefs.getString(PreferenceKeys.USER_TELEFONO, null);


                    Direccion direccion = new Direccion();

                    direccion.setProvincia(provincia.getText().toString());
                    direccion.setLocalidad(localidad.getText().toString());
                    direccion.setCalle(calle.getText().toString());
                    direccion.setNumero(Integer.parseInt(numero.getText().toString()));
                    if (!piso.getText().toString().equals(""))
                        direccion.setPiso(piso.getText().toString());
                    if (!bloque.getText().toString().equals(""))
                        direccion.setBloque(bloque.getText().toString());
                    if (!puerta.getText().toString().equals(""))
                        direccion.setPuerta(puerta.getText().toString());

                    final InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                    Call<Direccion> call = api.addDireccion(direccion);

                    call.enqueue(new Callback<Direccion>() {
                        @Override
                        public void onResponse(Call<Direccion> call, Response<Direccion> response) {
                            if (response.code() == 201){
                                Direccion result = response.body();

                                newUser.setNombre(nombre);
                                newUser.setApellidos(apellidos);
                                newUser.setEmail(email);
                                newUser.setPassword(password);
                                newUser.setPais(pais);
                                newUser.setTelefono(telefono);
                                newUser.getAddress_id().add(result.getId());

                                Call<UserRegister> call2 = api.registerUser(newUser);
                                
                                call2.enqueue(new Callback<UserRegister>() {
                                    @Override
                                    public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                                        if (response.isSuccessful()){
                                            Intent intentDomicilio = new Intent(DomicilioActivity.this, ContactosActivity.class);
                                            startActivity(intentDomicilio);
                                        } else {
                                            Toast.makeText(DomicilioActivity.this, "Fallo crítico", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserRegister> call, Throwable t) {
                                        Log.e("TAG","onFailure newUser: "+t.toString());
                                    }
                                });

                            } else {
                                Toast.makeText(DomicilioActivity.this, "Fallo crítico", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Direccion> call, Throwable t) {
                            Log.e("TAG","onFailure newAddress: "+t.toString());
                        }
                    });


                    
                }

            }
        });

    }
}
