package com.example.jatin.wedding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    Button btn;
    EditText phone;
    String phoneNumber;
    String TAG="check";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone= (EditText) findViewById(R.id.editText);
        btn= (Button) findViewById(R.id.button);

        final ProgressDialog pDialog = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=phone.getText().toString();
                // Tag used to cancel the request
                String tag_json_obj = "json_obj_req";
                String url = "http://10.10.20.169:82/newTrend/login.php?number="+phoneNumber;
                pDialog.setMessage("Loading...");
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
                                        //number is registered
                                        SharedPreferences sharedPreferences = getSharedPreferences("Phone", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user",phoneNumber);
                                        editor.putString("state","Login");
                                        editor.commit();
                                        Intent intent =new Intent(MainActivity.this,welcome.class);
                                        startActivity(intent);

                                    }
                                    else{
                                        //number is not registered
                                        Toast.makeText(getApplicationContext(),"Your number is not registered",Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
