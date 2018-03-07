package com.joandma.protgt.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.joandma.protgt.API.ServiceGeneratorSMS;
import com.joandma.protgt.Constant.PreferenceKeys;
import com.joandma.protgt.Models.ModelsApiSMSPubli.Message;
import com.joandma.protgt.Models.ModelsApiSMSPubli.Sms;
import com.joandma.protgt.Models.ModelsApiSMSPubli.SmsResponse;
import com.joandma.protgt.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeScreenActivity extends AppCompatActivity {

    int numCode = 0;
    Button clickButton;
    TextInputEditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_screen);


        clickButton = findViewById(R.id.buttonEnviarCodigo);

        SharedPreferences prefs = MensajeScreenActivity.this.getSharedPreferences("datos", Context.MODE_PRIVATE);

        String telefono = prefs.getString(PreferenceKeys.USER_TELEFONO, null);
        telefono = telefono.replace(" ","");
        telefono = telefono.replace("+","");
        telefono = "34"+telefono;

        //Toast.makeText(this, "NUMERO " +telefono, Toast.LENGTH_SHORT).show();

        generateCode();

        final Sms sms = new Sms();

        Message message = new Message();

        message.setTo(telefono);
        message.setFrom("PRO-TGT");
        message.setText(getString(R.string.verificacion) +" " +numCode);

        sms.getMessages().add(message);
        //sms.setFake(1);
        sms.setApi_key(PreferenceKeys.SMS_APIKEY);

        InterfaceRequestApi api = ServiceGeneratorSMS.createService(InterfaceRequestApi.class);
        Call<SmsResponse> call = api.sendSms(sms);

        final String finalTelefono = telefono;
        call.enqueue(new Callback<SmsResponse>() {
            @Override
            public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response) {

                SmsResponse smsResponse = response.body();

                if (smsResponse.getStatus().equalsIgnoreCase("ok")){
                    Toast.makeText(MensajeScreenActivity.this, "NUMERO: " + finalTelefono, Toast.LENGTH_SHORT).show();


                } else {
                    Log.i("MSGERROR","MSG: " +smsResponse.getError_msg());                        }

                codigo = findViewById(R.id.textInputCodigo);

            }

            @Override
            public void onFailure(Call<SmsResponse> call, Throwable t) {
            }
        });


        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo.getText().toString().equals(String.valueOf(numCode))){
                    Intent i = new Intent(MensajeScreenActivity.this, DomicilioActivity.class);
                    startActivity(i);

                } else {
                    codigo.setError(getString(R.string.error_codigo_sms));
                }

            }
        });

    }

    public void generateCode() {
        numCode = (int) (Math.floor(Math.random()*(9999-1000))+1000);
    }
}
