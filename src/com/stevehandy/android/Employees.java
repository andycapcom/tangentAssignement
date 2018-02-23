package com.stevehandy.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Employees extends Activity {
	//Set Error Status
	static boolean errored = false;
	private Button b1;
	String token;
	String employees;
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter ;
	ArrayList<String> listEmployees;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employees);
        mainListView = (ListView) findViewById( R.id.mainListView );
        b1 = (Button) findViewById(R.id.button1);
        Bundle b = getIntent().getExtras();
		token = b.getString("token");
		listEmployees = new ArrayList<String>(); 
		AsyncCallWS task = new AsyncCallWS();
		//Call execute 
		task.execute();
        
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
    			Intent intObj = new Intent(Employees.this,HomeActivity.class);
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
				employees = WebService.invokeEmployeesWS(token); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			System.out.println("response2"+ employees);
				try {
					JSONArray jsonArray = new JSONArray(employees);
					   
					if (jsonArray != null) { 
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
