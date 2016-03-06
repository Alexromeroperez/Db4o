package com.arp.db4o;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.arp.db4o.gestor.GestorDb4o;
import com.arp.db4o.pojo.Posicion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 05/03/2016.
 */
public class Servicio extends Service implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener{

    private GoogleApiClient cliente;
    private Location ultimaLocalizacion;
    private LocationRequest peticionLocalizaciones;
    private GestorDb4o gdb;

    /************Servicio*****************/
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        conexion();
        peticionLocalizaciones = new LocationRequest();
        peticionLocalizaciones.setSmallestDisplacement(2);
        peticionLocalizaciones.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
/***************Mapa**********************/
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        ultimaLocalizacion = LocationServices.FusedLocationApi.getLastLocation(cliente);
        peticionLocalizaciones = new LocationRequest();
        peticionLocalizaciones.setSmallestDisplacement(2);
        peticionLocalizaciones.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //LocationServices.FusedLocationApi.requestLocationUpdates(cliente, peticionLocalizaciones, this);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(cliente,peticionLocalizaciones, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Posicion pos=new Posicion();
        pos.setLatitud(location.getLatitude());
        pos.setLongitud(location.getLongitude());
        pos.setFecha(new GregorianCalendar().getTime());
        Toast.makeText(this,"posicion "+pos.toString(),Toast.LENGTH_SHORT).show();
        gdb=new GestorDb4o(this);
        gdb.insert(pos);
        gdb.close();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void conexion(){
        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            cliente = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            cliente.connect();
        }else {
                Toast.makeText(this, "No hay conexion", Toast.LENGTH_LONG).show();
            }
        }
}
