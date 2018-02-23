package com.stevehandy.android;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;	

import com.prgguru.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
	
public class HomeActivity extends Activity {
	static boolean errored = false;
	String me;
	String employees;
	String token;
	TextView tvWelcome;
	TextView tvEmployee;
	Button b1;
	Button b2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		Bundle b = getIntent().getExtras();
		token = b.getString("token");
		tvWelcome = (TextView) findViewById(R.id.textView2);
		tvEmployee = (TextView) findViewById(R.id.textView4);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		AsyncCallWS task = new AsyncCallWS();
		//Call execute 
		task.execute();
		b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
    			Intent intObj = new Intent(HomeActivity.this,Profile.class);
    			intObj.putExtra("token", token);
				startActivity(intObj);
            }
        });
		
		b2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intObj = new Intent(HomeActivity.this,Employees.class);
            	intObj.putExtra("token", token);
				startActivity(intObj);
            }
        });
	}
	
	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			try {
				me = WebService.invokeProfileWS(token);
				employees =  WebService.invokeEmployeesWS(token); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Intent intObj = new Intent(CheckLoginActivity.this,HomeActivity.class);
			//System.out.println("response"+ employees);
		
				//Based on Boolean value returned from WebService
				try {
					JSONObject jsonObj = new JSONObject(me);
					if(jsonObj.has("id")){
						//Navigate to Home Screen
					    String firstname = jsonObj.getString("first_name");
					    String lastname = jsonObj.getString("last_name");
					    tvWelcome.setText("Welcome "+firstname+" "+lastname);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					JSONArray jsonArray = new JSONArray(employees);
					int noEmployees = jsonArray.length();
					if(noEmployees > 0){
					//Set number of employees
						tvEmployee.setText(""+noEmployees);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}

		@Override
		//Make Progress Bar visible
		protected void onPreExecute() {
			//webservicePG.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
}
