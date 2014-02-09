package com.parkhere;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerActivity extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_timer, container, false);
        
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
			}
		});
        return rootView;
	}
	
}
