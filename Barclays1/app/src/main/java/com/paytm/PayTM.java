package com.paytm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.barclays1.R;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

public class PayTM extends Activity
{
	private int randomInt = 0;
	private PaytmPGService Service = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("LOG", "onCreate of MainActivity");
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_main);

		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(1000);
		
		Service = PaytmPGService.getStagingService(); //for testing environment
		/*Service = PaytmPGService.getStagingService();*/ //for production environment 
		

		/*PaytmMerchant constructor takes two parameters
		1) Checksum generation url
		2) Checksum verification url
		Merchant should replace the below values with his values*/

        PaytmMerchant Merchant = new PaytmMerchant("https://pguat.paytm.com/merchant-chksum/ChecksumGenerator","https://pguat.paytm.com/merchant-chksum/ValidateChksum");
        	
        //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
        		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//these are mandatory parameters
		paramMap.put("REQUEST_TYPE", "DEFAULT");
		paramMap.put("ORDER_ID", String.valueOf(randomInt));
		paramMap.put("MID", "klbGlV59135347348753");
		paramMap.put("CUST_ID", "CUST123");
		paramMap.put("CHANNEL_ID", "WAP");
		paramMap.put("INDUSTRY_TYPE_ID", "Retail");
		paramMap.put("WEBSITE", "paytm");
		paramMap.put("TXN_AMOUNT", "1");
		paramMap.put("THEME", "merchant");
		
		
						
		PaytmOrder Order = new PaytmOrder(paramMap);


		Service.initialize(Order, Merchant,null);
//		Service.startPaymentTransaction(this,false,new PaytmPaymentTransactionCallback()
//		{
//
//
//            @Override
//            public void onTransactionSuccess(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onTransactionFailure(String s, Bundle bundle) {
//
//            }
//
//            @Override
//            public void networkNotAvailable() {
//
//            }
//
//            @Override
//            public void clientAuthenticationFailed(String s) {
//
//            }
//
//            @Override
//            public void someUIErrorOccurred(String s) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int i, String s, String s1) {
//
//            }
//        });
	}
}
