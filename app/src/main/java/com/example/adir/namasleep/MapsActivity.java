package com.example.adir.namasleep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
  //  private MapView mMapView;
    String name="";
    AlertDialog.Builder alert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        alert=new AlertDialog.Builder(this);

        load();
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in india and move the camera


        LatLng hodu = new LatLng(28.641107,  77.212676);
        mMap.addMarker(new MarkerOptions().position(hodu).title("Marker in hodu"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hodu));
        // marker listener
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        mMap.setMyLocationEnabled(true);

 
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("GuestHouse");
                try {
                    List<ParseObject> info;
                    query.whereEqualTo("LatLtn", marker.getPosition());
                    info = query.find();
                    for (final ParseObject itemRow : info) {
                        alert.setTitle("Guest House" + itemRow.getString("name"));
                        name = itemRow.getString("name");
                        alert.setMessage("GoTo GuestHouse");
                        alert.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getBaseContext(), GuestHouseProfileActivity.class);
                                intent.putExtra("name", name);
                                startActivity(intent);

                            }
                        })
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                }).create();
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });





      //  mMapView.setClickable(false);



     //  UiSettings.setZoomControlsEnabled(true);
     //  UiSettings.setTiltGesturesEnabled(true);
    //   UiSettings.setRotateGesturesEnabled(true);
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void load(){
        ParseQuery<ParseObject> query=ParseQuery.getQuery("GuestHouse");
        try {
            List<ParseObject> items;
            items=query.find();
            for(ParseObject itemRow:items){
                LatLng latLng = new LatLng(Double.valueOf(itemRow.getString("Lat")),Double.valueOf(itemRow.getString("Ltn")));
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onSearch(View view) {
        EditText location_tf=(EditText)findViewById(R.id.TFaddress);
        String location=location_tf.getText().toString();
        List<Address> addressList = null;
        if (location!=null || !location.equals("")){
            Geocoder geocoder=new Geocoder(this);

            try {
                addressList =geocoder.getFromLocationName(location , 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }else {
            Toast.makeText(this,"can't be empty",Toast.LENGTH_SHORT).show();
        }

    }

    public void changeType(View view){
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

    public void onZoom (View view){
        if (view.getId() == R.id.Bzoomin){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }

        if(view.getId() == R.id.Bzoomout){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }

    }

}
