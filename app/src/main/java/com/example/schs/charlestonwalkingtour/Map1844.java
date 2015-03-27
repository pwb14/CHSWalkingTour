package com.example.schs.charlestonwalkingtour;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

public class Map1844 extends FragmentActivity {
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Database_Sqliteopenhelper dbHelper = new Database_Sqliteopenhelper(this);
    private static final LatLng Charleston = new LatLng(32.78, -79.93); //getResources().getString(R.string.chas_*)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1844);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        String name, imglink, desc;
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//        LatLng NEWARK = new LatLng(40.714086, -74.228697);
//
//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.charleston_map_1844))
//                .position(NEWARK, 8600f, 6500f);
//        mMap.addGroundOverlay(newarkMap);
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {

    /* Define the URL pattern for the tile images */

                String s = String.format("https://raw.githubusercontent.com/pwb14/mapTiles/master/%d/%d/%d.png",
                        zoom, x, y);

                if (!checkTileExists(x, y, zoom)) {
                    return null;
                }

                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }

            /*
             * Check that the tile server supports the requested x, y and zoom.
             * Complete this stub according to the tile range you support.
             * If you support a limited range of tiles at different zoom levels, then you
             * need to define the supported x, y range at each zoom level.
             */
            private boolean checkTileExists(int x, int y, int zoom) {
                int minZoom = 12;
                int maxZoom = 16;

                if ((zoom < minZoom || zoom > maxZoom)) {
                    return false;
                }

                return true;
            }
        };

        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Charleston,13));

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.fetchAllMarkers(db); // already at first
        boolean a = true;
        while(a){//cursor != null){
            name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
            Double lon = cursor.getDouble(cursor.getColumnIndex("long"));
            imglink = cursor.getString(cursor.getColumnIndex("imglink"));
            desc = cursor.getString(cursor.getColumnIndex("desc"));
            LatLng location = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(location).title(name).snippet(desc+"Image URL: "+imglink));
            a = false;
            //cursor.moveToNext();

        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent moreInfoIntent = new Intent(getApplicationContext(), MoreInfoActivity.class);
                moreInfoIntent.putExtra("name",marker.getTitle());
                moreInfoIntent.putExtra("desc",marker.getSnippet().toString().split("Image URL: ")[0]);
                moreInfoIntent.putExtra("imglink",marker.getSnippet().toString().split("Image URL: ")[1]);
                startActivity(moreInfoIntent);
            }
        });

    }

}
