package com.barclays1;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.models.AadhaarCard;

public class BiometricActivity extends AppCompatActivity {

    ImageView qrcode ;
    AadhaarCard card ;
    EditText et;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        card = (AadhaarCard) getIntent().getParcelableExtra("card");
        qrcode = (ImageView) findViewById(R.id.qrcode);
        et = (EditText) findViewById(R.id.aadhaar_number);
        btn = (Button) findViewById(R.id.auth_button);
//        if(card != null) {
//            et.setText(card.getFormattedUID());
//
//            qrcode.setImageBitmap(String2QRBitmap.sting2QRBitmap(card.getAddress()));
//        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BiometricActivity.this,Finger.class );
                startActivity(i);
            }
        });

    }

}
