package com.barclays1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.Wallet;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    Button sendOTP, done;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        c = this;
        sendOTP = (Button) findViewById(R.id.btn_login_sendotp);
        done = (Button) findViewById(R.id.btn_towallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String aadhar = ((EditText) findViewById(R.id.et_login_uid)).getText().toString();
                if (aadhar.length() != 12) {
                    Toast toast = Toast.makeText(c, "Please enter a valid 12-digit aadhar number", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String son1 = "{ \"aadhaar-id\": \"" + aadhar + "\", \"channel\":\"SMS\", \"location\": { \"type\": \"gps\", \"latitude\": \"73.2\", \"longitude\": \"22.3\", \"altitude\": \"0\" } }";

                String[] inputArr = new String[]{
                        aadhar,
                        son1,
                };

                TextView t1 = (TextView) findViewById(R.id.tv_login_otpstatus);
                t1.setText(("Sending OTP."));

                String res[] = new String[0];

                try {
                    res = new MainActivity.RequestOTPTask().execute(aadhar, son1).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                t1.setText(res[0]);

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Wallet.class);
                startActivity(i);
            }
        });


    }

}
