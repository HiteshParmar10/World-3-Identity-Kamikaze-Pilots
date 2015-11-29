package com.barclays1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.models.AadhaarCard;
import com.models.RoundedImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HostnameVerifier;

import xmlparsing.AadhaarXMLParser;

public class MainActivity extends AppCompatActivity {
    public static Context c ;
    Button biometric= null, done, sendOTP;
    RoundedImageView profilePic = null;
    String TAG = "MAIN_ACTIVITY" ;
    Spinner gender;
    CheckBox chkbox ;
    AadhaarCard card ;

    public static boolean otpResponseState, biometricState, firstnameState, lastnameState, dobState, uidState, emailState, passwordState, confirmpasswordState,
                        ageState, phonenumState, addressState, chkboxState, otpState, allgood ;
    EditText firstname,lastname,uid,email, password, confirmpassword, age,dob, phonenum, otp;
    CheckBox iAgree;
    private static int RESULT_LOAD_IMAGE = 1, RESULT_SCAN_QR = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = this;
        card = null;//ew AadhaarCard() ;
        firstname = (EditText) findViewById(R.id.et_firstname);
        lastname = (EditText)findViewById(R.id.et_lastname);
        uid = (EditText)findViewById(R.id.et_uid);
        age = (EditText)findViewById(R.id.et_age);
        dob = (EditText)findViewById(R.id.et_dob);
        email = (EditText) findViewById(R.id.et_email);
        password =(EditText) findViewById(R.id.et_password);
        confirmpassword = (EditText)findViewById(R.id.et_confirm_password);
        phonenum = (EditText)findViewById(R.id.et_phonenum);
        otp = (EditText)findViewById(R.id.et_otp);
        chkbox = (CheckBox) findViewById(R.id.cb_iagree);
        sendOTP = (Button) findViewById(R.id.btn_sendotp);
        biometric = (Button) findViewById(R.id.btn_biometric);
        profilePic = (RoundedImageView) findViewById(R.id.iv_profile);
        done = (Button) findViewById(R.id.btn_signup_done);
        gender = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter
                .createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){//uid.getText().toString().length() == 12) {
                    Intent i = new Intent(MainActivity.this, BiometricActivity.class);
                    Bundle bundle = new Bundle();
                    if (card != null)
                        bundle.putParcelable("card", card);
                    i.putExtras(bundle);
                    startActivity(i);
                }
                else{
                    Toast.makeText(c, "Enter UID first", Toast.LENGTH_LONG).show();
                }
            }
        });
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String aadhar = ((EditText) findViewById(R.id.et_uid)).getText().toString();
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

                TextView t1 = (TextView) findViewById(R.id.tv_otpstatus);
                t1.setText(("Sending OTP."));

                String res[] = new String[0];

                try {
                    res = new RequestOTPTask().execute(aadhar, son1).get();
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
                checkDone();
                if(allgood){
                    card = new AadhaarCard();
                    card.name = firstname.getText().toString()+" "+lastname.getText().toString();
                    //card.yob = dob.getText().toString().substring(dob.getText().toString().lastIndexOf("/"));
                    //card.gender = gender.getSelectedItem().toString();
                    card.dob = dob.getText().toString();

                    Intent i = new Intent(MainActivity.this, SignUpSuccessful.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(c, "Enter data in all fields", Toast.LENGTH_LONG).show();
                }

            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Your Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                try {

                    Intent intent =new Intent("com.google.zxing.client.android.SCAN");//me.scan.android.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar code
                    Log.d(TAG, "Starting Scanning Activity");
                    startActivityForResult(intent, RESULT_SCAN_QR);


                } catch (Exception e) {
                    Log.d(TAG, e.toString() + e.getMessage());
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);

                }
            }
        });
    }

    private void checkDone() {
        if(!firstname.getText().toString().equals("")){
            firstnameState = true ;
        }
        if(!lastname.getText().toString().equals("")){
            lastnameState = true ;
        }
        if(!uid.getText().toString().equals("")){
            uidState = true ;
        }
        if(!email.getText().toString().equals("")){
            emailState = true ;
        }
        if(!password.getText().toString().equals("") && password.getText().toString().equals(confirmpassword.getText().toString())){
            passwordState = true ;
        }
        if(!dob.getText().toString().equals("")){
            dobState = true ;
        }
        if(!age.getText().toString().equals("")){
            ageState = true ;
        }
        if(!dob.getText().toString().equals("")){
            dobState = true ;
        }
        if(!phonenum.getText().toString().equals("")){
            phonenumState = true ;
        }

        if(!otp.getText().toString().equals("")){
            otpState = true ;
        }
        if(!chkbox.isChecked()){
            chkboxState = true ;
            Toast.makeText(c, "Please agree to the perform eKYC", Toast.LENGTH_LONG).show();
        }
        if(true
//                firstnameState &&
//                lastnameState &&  uidState &&
//                passwordState && confirmpasswordState &&
//               chkboxState //&& otpState
                ){
            allgood = true ;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_SCAN_QR) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Log.d(TAG, contents);
                try {

                    card = new AadhaarXMLParser().parse(contents);
                    Toast.makeText(this, "Card Parsed ", Toast.LENGTH_SHORT).show();
                    firstname.setText(card.name.split(" ")[0]);
                    lastname.setText(card.name.substring(card.name.lastIndexOf(" ")));
                    uid.setText(card.uid);
//                    Date dt = new Date();
//                    int approxage = dt.getYear() - Integer.parseInt(card.yob);
//                    age.setText(String.valueOf(approxage));
                    dob.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "QR Code is not aadhaar card!", Toast.LENGTH_LONG).show();
                }

            }
            if (resultCode == RESULT_CANCELED) {
                //handle cancel
            }
        }
        else if(requestCode == RESULT_LOAD_IMAGE){
            if(resultCode == RESULT_OK && data != null){
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

    public static class RequestOTPTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            Log.d("Request OTP Async", "YEahh");
            //get the various required fields

            String son1 = params[1];

            Boolean otp_sent = false;
            DefaultHttpClient client = new DefaultHttpClient();
            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            String result[] = new String[1];
            //adding ssl capabilities
            SchemeRegistry registry = new SchemeRegistry();
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
            registry.register(new Scheme("https", socketFactory, 443));
            SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);

            DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());


            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000); //Timeout Limit



            HttpResponse response;

            try {
                HttpPost post = new HttpPost("https://ac.khoslalabs.com/hackgate/hackathon/otp");
                StringEntity se = new StringEntity(son1);
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(se);
                response = httpClient.execute(post);

                    /*Checking response */
                if(response!=null){



                    Log.d("RESPONSE", "FOUND");
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    String body = handler.handleResponse(response);
                    Log.d("stream", body);

                    if(body.indexOf("success\":true") > 0) {
                        otp_sent= true;
                        otpResponseState = true ;
                        result[0] = "OTP Sent!";
                    }
                    if(body.indexOf("success\":false") > 0) {

                        result[0] = "OTP not received. Temp OTP used. Scan Aadhaar !";
                        SmsManager smsManager = SmsManager.getDefault();
                        TelephonyManager tMgr = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
                        String mPhoneNumber = tMgr.getLine1Number();
                        smsManager.sendTextMessage("9821275515", null, "Your OTP is 1234", null, null);
                    }
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            if(otp_sent==true) {Log.d("AsyncTask", "OTP succesfully sent");return result; }


            result[0] = "Check Connectivity.";
            return result;

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
