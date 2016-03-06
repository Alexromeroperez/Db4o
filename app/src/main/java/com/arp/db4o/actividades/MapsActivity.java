package com.arp.db4o.actividades;

import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.arp.db4o.R;
import com.arp.db4o.pojo.Posicion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Posicion> posiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        posiciones=getIntent().getParcelableArrayListExtra("posiciones");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(posiciones.size()!=0) {
            for (Posicion p : posiciones) {
                LatLng pos = new LatLng(p.getLatitud(), p.getLongitud());
                mMap.addMarker(new MarkerOptions().position(pos));
            }
            LatLng pos = new LatLng(posiciones.get(0).getLatitud(), posiciones.get(0).getLongitud());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        }else {
            Toast.makeText(this, "Hoy no tienes ninguna ruta", Toast.LENGTH_SHORT).show();
        }
    }
}
