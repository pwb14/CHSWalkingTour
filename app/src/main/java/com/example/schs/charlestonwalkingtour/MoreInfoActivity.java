package com.example.schs.charlestonwalkingtour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class MoreInfoActivity extends ActionBarActivity {
    Database_Sqliteopenhelper dbHelper = new Database_Sqliteopenhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Intent intent = getIntent();


        ImageView imageView = (ImageView) findViewById(R.id.moreInfoImageView);
        TextView textViewTitle = (TextView) findViewById(R.id.moreInfoTitle);
        TextView textViewDesc = (TextView) findViewById(R.id.moreInfoDesc);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.fetchImageURL(db, intent.getStringExtra("name"));
        String imglink=cursor.getString(0);

        Picasso.with(this).load(imglink).into(imageView);
        textViewTitle.setText(intent.getStringExtra("name"));
        textViewDesc.setText(intent.getStringExtra("desc"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more_info, menu);
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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
