package com.example.jatin.wedding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class Music extends ActionBarActivity {
    TextView songname;
    TextView artistname;
    TextView note;
    Button btn;
    String Log="check";
    String songName,artistName,notes;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        songname = (TextView) findViewById(R.id.songname);
        artistname = (TextView) findViewById(R.id.artistname);
        note= (TextView) findViewById(R.id.note);
        btn= (Button) findViewById(R.id.submit1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songName=songname.getText().toString().replace(" ","_");;
                artistName=artistname.getText().toString().replace(" ","_");;
                notes=note.getText().toString().replace(" ","_");;
                // Tag used to cancel the request
                String tag_json_obj = "json_obj_req";

                String url = "http://10.10.20.169:82/newTrend/songRequest.php?name="+songName+"&artist="+artistName+"&note="+notes;
//                String url = "http://10.10.20.169:82/newTrend/songRequest.php?name="+songName+"&artist="+artistName+"&note="+notes;
                Toast.makeText(getApplicationContext(),"1 "+songName+"  "+artistName+"  "+notes,Toast.LENGTH_LONG).show();

                final ProgressDialog pDialog = new ProgressDialog(Music.this);
                pDialog.setMessage("Loading...");
                pDialog.show();

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(),response+"",Toast.LENGTH_LONG).show();
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Log.d(TAG, "Error: " + error.getMessage());
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
        getMenuInflater().inflate(R.menu.menu_music, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {

            Intent home = new Intent(getApplicationContext(),welcome.class);
            startActivity(home);

            /*Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            // NavUtils.navigateUpFromSameTask(this);
            // finish();*/
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
