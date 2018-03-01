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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joandma.protgt.API.InterfaceRequestApi;
import com.joandma.protgt.API.ServiceGenerator;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.VerifyModel;
import com.joandma.protgt.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelefonoActivity extends AppCompatActivity {

    Button siguiente;
    EditText telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);

        final Spinner pais = findViewById(R.id.spinnerPais);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.paises, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        pais.setAdapter(adapter);

        siguiente = findViewById(R.id.button_telefono_siguiente);
        telefono = findViewById(R.id.editTextTelefono);


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telefono.getText().toString().equals("")){
                    telefono.setError(getString(R.string.error_telefono));
                } else {

                    VerifyModel verifyModel = new VerifyModel();

                    verifyModel.setTelefono(telefono.getText().toString());

                    InterfaceRequestApi api = ServiceGenerator.createService(InterfaceRequestApi.class);

                    Call<ResponseBody> call = api.verifyEmailTelephone(verifyModel);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                SharedPreferences prefs = TelefonoActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString(PreferenceKeys.USER_TELEFONO, telefono.getText().toString());
                                editor.putString(PreferenceKeys.USER_PAIS, pais.getSelectedItem().toString());

                                editor.commit();
                                Intent intentTelefono = new Intent(TelefonoActivity.this, MensajeScreenActivity.class);
                                startActivity(intentTelefono);
                            } else {
                                telefono.setError(getString(R.string.error_telefono_registrado));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("TAG","onFailure login: "+t.toString());
                        }
                    });


                }
            }
        });
    }
}
