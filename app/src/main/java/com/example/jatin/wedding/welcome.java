package com.example.jatin.wedding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class welcome extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ListView listView;
    Toolbar toolbar;
    // private String[] options;
    private ActionBarDrawerToggle drawerListener;
    private MyAdapter myAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        myAdapter=new MyAdapter(this);
        drawerListener=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(getApplicationContext(), "open", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(getApplicationContext(),"close",Toast.LENGTH_LONG).show();

            }
        };
        drawerLayout.setDrawerListener(drawerListener);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView= (ListView) findViewById(R.id.drawerList);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),myAdapter.options[position]+"",Toast.LENGTH_LONG).show();
        switch (myAdapter.options[position]){
            case "RSVP" :
                intent=new Intent(welcome.this,Rsvp.class);
                break;
            case "Menu" :
                intent=new Intent(welcome.this,MenuActivity.class);
                break;
            case "Music" :
                intent=new Intent(welcome.this,Music.class);
                break;
            case "Images" :
                intent=new Intent(welcome.this,ImageAll.class);
                break;
            case "About" :
                intent=new Intent(welcome.this,About.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);

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

        if (id == R.id.action_logout){
            SharedPreferences sharedpreferences = getSharedPreferences("Phone", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();

            Intent main = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(main);
        }

        return super.onOptionsItemSelected(item);
    }
}


class MyAdapter extends BaseAdapter {
    private Context context;
    String[] options;
    int[] images={R.drawable.rsvp,R.drawable.food,R.drawable.music,R.drawable.images,R.drawable.about};

    public MyAdapter(Context context){
        this.context=context;
        options=context.getResources().getStringArray(R.array.options);
    }
    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=null;
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.custom_row,parent, Boolean.parseBoolean(null));
        }
        else{
            row=convertView;
        }
        TextView title= (TextView) row.findViewById(R.id.textView6);
        ImageView image= (ImageView) row.findViewById(R.id.imageView);
        title.setText(options[position]);
        image.setImageResource(images[position]);
        return row;
    }
}