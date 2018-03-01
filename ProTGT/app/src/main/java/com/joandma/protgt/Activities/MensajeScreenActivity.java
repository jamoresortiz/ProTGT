package com.joandma.protgt.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joandma.protgt.R;

public class MensajeScreenActivity extends AppCompatActivity {

    int numCode = 0;
    Button clickButton;
    TextInputEditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_screen);

        Intent intent = new Intent(MensajeScreenActivity.this, MensajeScreenActivity.class);

        PendingIntent pi = PendingIntent.getActivity(MensajeScreenActivity.this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);

        generateCode();

        builder.setContentTitle("SMS Código")
                .setContentText(String.valueOf(numCode))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pi)
                .setVibrate(new long[]{500, 500, 500, 500, 500})
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());


        clickButton = findViewById(R.id.buttonEnviarCodigo);


        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigo = findViewById(R.id.textInputCodigo);

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
