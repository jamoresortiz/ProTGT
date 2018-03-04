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
import com.joandma.protgt.Models.ModelsApiProTGT.Direccion;
import com.joandma.protgt.Models.ModelsApiProTGT.UserRegister;
import com.joandma.protgt.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DomicilioActivity extends AppCompatActivity {

    TextInputEditText provincia, localidad, calle, numero, piso, bloque, puerta;
    Long id_direccion;
    Button terminar;
    SharedPreferences.Editor editor;

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
                    provincia.setError(getString(R.string.error_provincia));
                } else if(localidad.getText().toString().equals("")){
                    localidad.setError(getString(R.string.error_localidad));
                } else if(calle.getText().toString().equals("")){
                    calle.setError(getString(R.string.error_calle));
                } else if(numero.getText().toString().equals("")){
                    numero.setError(getString(R.string.error_numero));
                } else {

                    final UserRegister newUser = new UserRegister();

                    final SharedPreferences prefs = DomicilioActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);


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
                                editor = prefs.edit();

                                editor.putString(PreferenceKeys.ADDRESS_ID, result.getId());
                                editor.putString(PreferenceKeys.ADDRESS_PROVINCIA, result.getProvincia());
                                editor.putString(PreferenceKeys.ADDRESS_LOCALIDAD, result.getLocalidad());
                                editor.putString(PreferenceKeys.ADDRESS_CALLE, result.getCalle());
                                editor.putInt(PreferenceKeys.ADDRESS_NUMERO, result.getNumero());

                                if (result.getPiso() != null)
                                    editor.putString(PreferenceKeys.ADDRESS_PISO, result.getPiso());
                                if (result.getBloque() != null)
                                    editor.putString(PreferenceKeys.ADDRESS_BLOQUE, result.getBloque());
                                if (result.getPuerta() != null)
                                    editor.putString(PreferenceKeys.ADDRESS_PUERTA, result.getPuerta());

                                editor.commit();

                                Intent intentDomicilio = new Intent(DomicilioActivity.this, ContactosActivity.class);
                                startActivity(intentDomicilio);


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
