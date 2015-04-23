package com.example.schs.charlestonwalkingtour;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

public class MarkerSettingsActivity extends ActionBarActivity {
    ArrayList<String> whereList;
    String where;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_settings);
        whereList = new ArrayList<String>();
        Button viewMapButton = (Button) findViewById(R.id.view_map_with_marker_settings_button);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), Map1844.class);
                mapIntent.putExtra("where",joinStrings());
                startActivity(mapIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_marker_settings, menu);
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
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_house:
                if (checked){
                    whereList.add("type = 'house'");
                }
                else
                    whereList.remove("type = 'house'");
                break;
            case R.id.checkbox_museum:
                if (checked){
                    whereList.add("type = 'museum'");
                }
                else
                    whereList.remove("type = 'museum'");
                break;
            case R.id.checkbox_church:
                if (checked){
                    whereList.add("type = 'church'");
                }
                else
                    whereList.remove("type = 'church'");
                break;
            case R.id.checkbox_government_building:
                if (checked){
                    whereList.add("type = 'government_building'");
                }
                else
                    whereList.remove("type = 'government_building'");
                break;
        }
    }
    private String joinStrings(){
        where="";
        for (int i=0;i<whereList.size();i++){
            if (where.length()==0)
                where = where+whereList.get(i);
            else
                where = where + " or " + whereList.get(i);
        }
        return where;
    }
}
