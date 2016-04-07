package com.android.test.sigbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SmsReceiveActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    private boolean isMapReady = false;
    int currentZoom = 0;
    String sender;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receive);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        String data=intent.getStringExtra("data");
        if (data!=null)
        {
            String[] message=data.split(" ");
            double lat=Double.parseDouble(message[1]);
            double lon=Double.parseDouble(message[2]);

            sender=message[0];
            latLng=new LatLng(lat, lon);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        isMapReady = true;
        addMarkerAt(sender, latLng);
    }

    public Marker addMarkerAt(String tag, LatLng cdn)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cdn));
        return mMap.addMarker(new MarkerOptions().position(cdn).title(tag));
    }
}