package com.example.jatin.wedding;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class BeforeMenu extends ActionBarActivity {

    CardView veg;
    CardView nonveg;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_menu);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        veg = (CardView) findViewById(R.id.card_veg);
        nonveg = (CardView) findViewById(R.id.card_nonveg);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Menu");

        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iveg = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(iveg);
            }
        });

        nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inonveg = new Intent(getApplicationContext(),MenuActivity.class);
                startActivity(inonveg);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_before_menu, menu);
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
