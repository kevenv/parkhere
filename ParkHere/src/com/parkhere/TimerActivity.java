package com.parkhere;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.cc;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

public class TimerActivity extends Fragment {
	
	private SupportMapFragment fragment;
	private View rootView;
	private EditText cardNumber;
	private EditText emailAdress;
	private EditText monthYear;
	private EditText cvc;
	private ImageView signUp;
	private ImageView justPay;
	public static final String PUBLISHABLE_KEY = "pk_test_ZPrfjZOfRtBG80mgFFa8MUzM";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_timer, container, false);
        
        //MapFragment findViewById = (MapFragment) getFragmentManager().findFragmentById(R.id.mapTimer);
        ImageView payForTheSpot = (ImageView)rootView.findViewById(R.id.payForTheSpot);
        final TextView timer = (TextView)rootView.findViewById(R.id.textOfTimer);
        
        new CountDownTimer(1000 * 60 * 10, 1000) {
		     public void onTick(long millisUntilFinished) {
		         long minutes = millisUntilFinished / (1000 * 60);
		         if(millisUntilFinished == 1000 * 60 * 10) {
		        	 timer.setText("10:00");
			     }
		         
		         timer.setText("0" + minutes + ":" + padSeconds(millisUntilFinished % (1000 * 60)));
		     }
		     
		     public String padSeconds(long seconds){
		    	 String result = "" + seconds;
		    	 if(result.length()>2){
			         return result.substring(0, 2);
			     }
		    	 return result;
		     }
		     
		     public void onFinish() {
		         timer.setText("00:00");
		     }
		  }.start();
        
        payForTheSpot.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				RelativeLayout paymentForm = (RelativeLayout)rootView.findViewById(R.id.paymentForm);
				paymentForm.setVisibility(View.VISIBLE);
				
				justPay.setVisibility(View.VISIBLE);
				
				signUp.setVisibility(View.VISIBLE);
				
				ImageView banner = (ImageView) rootView.findViewById(R.id.imageView1);
				//banner.setVisibility(View.INVISIBLE);
				
				TextView timer = (TextView) rootView.findViewById(R.id.textOfTimer);
				//timer.setVisibility(View.INVISIBLE);
				
				ImageView payForTheSpot = (ImageView) rootView.findViewById(R.id.payForTheSpot);
				payForTheSpot.setVisibility(View.INVISIBLE);
			}
		});
        return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    FragmentManager fm = getChildFragmentManager();
	    fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapTimer);
	    if (fragment == null) {
	        fragment = SupportMapFragment.newInstance();
	        fm.beginTransaction().replace(R.id.mapTimer, fragment).commit();
	    }
	}
	
	public int getMonth() {
		CharSequence month = monthYear.getText().subSequence(0, 2);
		int monthInt = Integer.parseInt(month.toString());
		return monthInt;
	}
	
	public int getYear() {
		CharSequence year = monthYear.getText().subSequence(3, 5);
		int yearInt = Integer.parseInt(year.toString());
		return 2000 + yearInt;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LatLng currentPosition = new LatLng(45.50404601, -73.6208);
		
        LatLng position = new LatLng(45.50530926,-73.6184);
        GoogleMap map = fragment.getMap();
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinsmall))
        .position(position));
        
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallmepin))
        .position(currentPosition));
        map.getUiSettings().setZoomControlsEnabled(false);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
		
		cardNumber = (EditText)rootView.findViewById(R.id.cardNumber);
		emailAdress = (EditText)rootView.findViewById(R.id.emailAdress);
		monthYear = (EditText)rootView.findViewById(R.id.monthYear);
		cvc = (EditText)rootView.findViewById(R.id.cvc);
		signUp = (ImageView) rootView.findViewById(R.id.signupId);
		justPay = (ImageView) rootView.findViewById(R.id.justPayId);
		
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Card card = new Card(cardNumber.getText().toString(), getMonth(), getYear(), cvc.getText().toString());

				boolean validation = card.validateCard();
				if (validation) {
					new Stripe().createToken(card, PUBLISHABLE_KEY,
							new TokenCallback() {
								
								@Override
								public void onSuccess(Token token) {
									new Handler().postDelayed(new Runnable() {
							            @Override
							            public void run() {
							            	getFragmentManager().beginTransaction()
											.remove(fragment)
											.replace(R.id.frame_container, new SuccessActivity())
											.commit();
							            }
							        }, 0);
								}
								
								@Override
								public void onError(Exception error) {
									Toast.makeText(getActivity().getApplicationContext(), "Invalid Card Number", 3000).show();
								}
							});
				} else {
					Toast.makeText(getActivity().getApplicationContext(), "Invalid Card Number", 3000).show();
				}
			}
		};
		
		signUp.setOnClickListener(onClickListener);
		justPay.setOnClickListener(onClickListener);
		
	}
	
	public void onDestroyView() {
	   super.onDestroyView();   
	   FragmentTransaction ft = getFragmentManager().beginTransaction();
	   ft.remove(fragment);
	   ft.commit();
	}
	
}
