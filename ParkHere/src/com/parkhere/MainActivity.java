package com.parkhere;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*Parse.initialize(this, "rHQ443EdBXs3eySuO5Dls9wA8abPBPWTnS5CUlNe", "h4bMXEvZOwfjlCKnRrA8y8YZHhgF8OpMu57FaZDo");
		
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
		query.whereEqualTo("playerName", "Dan Stemkoski");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            //OK
		        } else {
		            //ERROR
		        }
		    }

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				
			}
		});*/
		
		/*Intent i = new Intent(MainActivity.this, ChooseActivity.class);
        startActivity(i);
        finish();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
