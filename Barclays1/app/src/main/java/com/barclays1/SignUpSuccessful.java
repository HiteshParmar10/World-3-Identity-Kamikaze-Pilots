package com.barclays1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.paytm.Wallet;

public class SignUpSuccessful extends AppCompatActivity {
    Button btn1, btn2, towallet ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_successful);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn1 = (Button) findViewById(R.id.btn_have_certi);
        btn2 = (Button) findViewById(R.id.btn_apply);
        towallet = (Button) findViewById(R.id.btn_towallet_fromsignup);
        setSupportActionBar(toolbar);
        towallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpSuccessful.this, Wallet.class);
                startActivity(i);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpSuccessful.this, DigitalCertificates.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpSuccessful.this, Apply.class);
                startActivity(i);
            }
        });

    }

}
