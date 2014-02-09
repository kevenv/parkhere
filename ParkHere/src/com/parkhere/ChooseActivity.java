package com.parkhere;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ChooseActivity extends Fragment {
/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		
		ImageView img = (ImageView) findViewById(R.id.imageView1);
		img.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	//TODO:
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}
*/
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_choose, container, false);
        
		ImageView img = (ImageView) rootView.findViewById(R.id.chooseParking);
        
		img.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	getFragmentManager().beginTransaction().replace(R.id.frame_container ,new DriverActivity()).addToBackStack( "driver" ).commit();
		    }
		});
		
        return rootView;
	}
}
