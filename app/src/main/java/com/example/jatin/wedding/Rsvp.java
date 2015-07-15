package com.example.jatin.wedding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;


public class Rsvp extends ActionBarActivity {
    Spinner spinner;
    Button btn;
    String TAG="check";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinner);
        final ProgressDialog pDialog = new ProgressDialog(this);
        SharedPreferences preferences= getSharedPreferences("Phone", MODE_PRIVATE);
        final String userPhone=preferences.getString("user",null);
        List<String> list = new ArrayList<String>();
        for(int i=1;i<101;i++) {
            list.add(i+"");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        btn= (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a= (String) spinner.getSelectedItem();
                Toast.makeText(getApplicationContext(),"You are coming with "+a+" more.",Toast.LENGTH_LONG).show();
                String tag_json_obj = "json_obj_req";
                String url = "http://10.10.20.169:82/newTrend/rsvp.php?rsvp="+a+"&user="+userPhone;
                pDialog.setMessage("Sending the request...");
                pDialog.show();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray dataSet = (JSONArray) response.get("data");
                                    JSONObject json=dataSet.getJSONObject(0);
                                    String check= (String) json.get("check");
                                    if(check.equals("yes")){
                                        //done


                                    }
                                    else{
                                        //not done
                                        Toast.makeText(getApplicationContext(),"Something went wrong,Please try again !!",Toast.LENGTH_LONG).show();
                                    }


                                    Log.d(TAG, response.toString());
                                    pDialog.hide();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                        pDialog.hide();
                    }
                });

// Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rsvp, menu);
        return true;
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
