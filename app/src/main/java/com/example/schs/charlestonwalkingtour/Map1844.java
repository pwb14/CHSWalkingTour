package com.example.schs.charlestonwalkingtour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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
    TileOverlay tileOverlay;
    Database_Sqliteopenhelper dbHelper = new Database_Sqliteopenhelper(this);
    private static final LatLng Charleston = new LatLng(32.78, -79.93); //getResources().getString(R.string.chas_*)
    String whereClause;
    private static final String map1844 = "https://raw.githubusercontent.com/pwb14/mapTiles/master/%d/%d/%d.png";
    private static final String map1855 = "https://raw.githubusercontent.com/pwb14/mapTiles1855/master/%d/%d/%d.png";
    private static final String map1901 = "https://raw.githubusercontent.com/pwb14/mapTiles1901/master/%d/%d/%d.png";
    private String currentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1844);
        whereClause=getIntent().getStringExtra("where");
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
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        CameraPosition currentPosition = mMap.getCameraPosition();
        float zoom = currentPosition.zoom;
        double lat = currentPosition.target.latitude;
        double lon = currentPosition.target.longitude;
        outState.putFloat("zoom",zoom);
        outState.putDouble("lat",lat);
        outState.putDouble("lon",lon);
        outState.putString("mapURL",currentMap);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle inState){
        super.onRestoreInstanceState(inState);
        currentMap = inState.getString("mapURL");
        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(setTilesURL(currentMap)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(inState.getDouble("lat"),inState.getDouble("lon")),inState.getFloat("zoom")));

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
    private TileProvider setTilesURL (final String url){

        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
    /* Define the URL pattern for the tile images */
                String s = String.format(url,
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
        return tileProvider;
    }


    private void setUpMap() {
        String name, imglink, desc;



        tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(setTilesURL(currentMap)));




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Charleston,13));
        mMap.setMyLocationEnabled(true);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.fetchAllMarkers(db,whereClause); // already at first
       // boolean a = true;
        while(cursor != null && cursor.moveToNext() == true) {//cursor != null){
            name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Double lat = cursor.getDouble(cursor.getColumnIndex("lat"));
            Double lon = cursor.getDouble(cursor.getColumnIndex("long"));
            imglink = cursor.getString(cursor.getColumnIndex("imglink"));
            desc = cursor.getString(cursor.getColumnIndex("desc"));
            LatLng location = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(name)
                    .snippet(desc)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_marker)));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent moreInfoIntent = new Intent(getApplicationContext(), MoreInfoActivity.class);
                moreInfoIntent.putExtra("name",marker.getTitle());
                moreInfoIntent.putExtra("desc", marker.getSnippet().toString());
                startActivity(moreInfoIntent);
            }
        });
        Button changeMapButton = (Button) findViewById(R.id.change_map_button);
        changeMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"1844", "1855", "1901", "Today"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Map1844.this);
                builder.setTitle(R.string.selectAMap);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        tileOverlay.clearTileCache();
                        tileOverlay.remove();
                        if (item == 0) {
                            tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                .tileProvider(setTilesURL(map1844)));
                            currentMap=map1844;
                        }
                        if (item == 1) {
                            tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                .tileProvider(setTilesURL(map1855)));
                            currentMap=map1855;
                        }
                        if (item == 2) {
                            tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                                    .tileProvider(setTilesURL(map1901)));
                            currentMap=map1901;

                        }
                        if (item == 3) {
                            currentMap="";

                        }

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

}
