package com.stevehandy.android;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.stevehandy.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Profile extends Activity {
	//Set Error Status
	static boolean errored = false;
	private Button b1;
	TextView tvName, bDay, tvPosition;	
	String me;
	String token;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Bundle b = getIntent().getExtras();
		token = b.getString("token");
		b1 = (Button) findViewById(R.id.button1);
		tvName = (TextView) findViewById(R.id.textView2);
		bDay = (TextView) findViewById(R.id.textView3);
		tvPosition = (TextView) findViewById(R.id.textView4);
        AsyncCallWS task = new AsyncCallWS();
		//Call execute 
		task.execute();
		b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
    			Intent intObj = new Intent(Profile.this,HomeActivity.class);
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
				me = WebService.invokeEmployeeWS(token); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			System.out.println("response2"+ me);
				try {
					JSONObject jsonObject = new JSONObject(me);
					JSONObject jsonObject2 = jsonObject.getJSONObject("user");
					JSONObject jsonObject3 = jsonObject.getJSONObject("position");
					String name = jsonObject2.getString("first_name")+" "+jsonObject2.getString("last_name");
					String position = jsonObject3.getString("name");
					String birthdate = jsonObject.getString("birth_date");
					tvName.setText("Name: "+name);
					tvPosition.setText("Position: "+position);
					bDay.setText("Birthday: "+birthdate);
					//JSONArray jsonArray = new JSONArray(me);
					   
					/*if (jsonArray != null) { 
					   int len = jsonArray.length();
					   for (int i=0;i<len;i++){ 
						   JSONObject jsonObject1 = jsonArray.getJSONObject(i);
						   JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
						   String name = jsonObject2.getString("first_name")+" "+jsonObject2.getString("last_name");
						   System.out.println("response2"+ name);
						   listEmployees.add(name);
						// Create ArrayAdapter using the planet list.  
					        listAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.row, listEmployees);    
					          
					        // Set the ArrayAdapter as the ListView's adapter.  
					        mainListView.setAdapter( listAdapter );
					   } 
					}*/
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}

		@Override
		//Make Progress Bar visible
		protected void onPreExecute() {
			
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
   
}
