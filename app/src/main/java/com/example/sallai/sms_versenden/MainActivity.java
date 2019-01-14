package com.example.sallai.sms_versenden;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int SMS_Permission = 1;
    ImageButton b;
    WebView w;
    TextView t1;
    TextView t2;

    int i = 0;

    boolean sabsi = false;
    boolean sophie = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.imageButton);
        w = findViewById(R.id.webview);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
    }

    public void btnClickedPhoto(View view) {
        i++;

        switch(i){
            case 1: b.setImageResource(R.mipmap.sabsi_1);
                w.getSettings().setJavaScriptEnabled(true);
                w.getSettings().setDomStorageEnabled(true);
                w.loadUrl("https://www.instagram.com/p/BsjdY2InkHu/?utm_source=ig_share_sheet&igshid=4zxvbca024i7/oembed");
                t1.setText("Alter: ##");
                t2.setText("Telefonnummer: ###");
                sabsi = true;
                break;
            case 2: b.setImageResource(R.mipmap.sophie_1);
                i = 0;
                w.getSettings().setJavaScriptEnabled(true);
                w.getSettings().setDomStorageEnabled(true);
                w.loadUrl("https://www.instagram.com/p/BpatsXqlWuG/?utm_source=ig_share_sheet&igshid=mti7gq1pynn/oembed");
                t1.setText("Alter: ##");
                t2.setText("Telefonnummer: ###");
                sophie = true;
                break;
        }
    }

    public void btnClickedSMS2(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

        } else {
            requestPemission();
        }
        if(sabsi == true) {
            SmsManager smsManager = SmsManager.getDefault();
            String senden = "+436507945957";
            String message = "SMS gesendet.";
            smsManager.sendTextMessage(senden, null, message, null, null);
        }
        if(sophie == true) {
            SmsManager smsManager = SmsManager.getDefault();
            String senden = "+436641689620";
            String message = "SMS gesendet.";
            smsManager.sendTextMessage(senden, null, message, null, null);
        }
    }

    private void requestPemission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS}, SMS_Permission );
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SMS_Permission );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == SMS_Permission ){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granded", Toast.LENGTH_LONG);
            } else
                Toast.makeText(this,"Permission not granded", Toast.LENGTH_LONG);
        }
    }
}
