package com.ideal.encuestacliente.configuracion;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.model.Rsin_Localizacion;

public class Localizacion implements LocationListener {
    private static final int REQUEST_CODE       = 101;
    private Context context;
    private Context contexto;
    private LocationManager locationManager;
    private String proveedor;
    private Activity activity;
    private  Activity actividad;
    private boolean netWorkOn;
    private TextInputEditText[] textInputEditTexts;
    AlertDialog alertDialog = null;

    public Localizacion(Context context, Activity activity, TextInputEditText[] textInputEditTexts){
        this.textInputEditTexts = textInputEditTexts;
        this.context = context;
        this.activity = activity;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.NETWORK_PROVIDER;
        netWorkOn = locationManager.isProviderEnabled(proveedor);

        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
             return; }

        locationManager.requestLocationUpdates(proveedor,1000,1,this);
        obtenerLocalización();
    }
    public Localizacion(Context context, Activity activity){
        contexto = context;
        actividad = activity;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.NETWORK_PROVIDER;
        netWorkOn = locationManager.isProviderEnabled(proveedor);

        if(ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(actividad,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        //locationManager.requestLocationUpdates(proveedor,1000,1,this);
        permisos();
    }

    public void obtenerLocalización(){

        if(netWorkOn)
        {
            Rsin_Localizacion rsin_localizacion = null;
            rsin_localizacion = new Rsin_Localizacion();
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
                return;
            }
            Location location = locationManager.getLastKnownLocation(proveedor);

            if(location != null){

                textInputEditTexts[0].setText(location.getLongitude()+"");
                textInputEditTexts[1].setText(location.getAltitude()+"");
                textInputEditTexts[2].setText(location.getLatitude()+"");

            }
        }
    }
    public void permisos(){
        if(netWorkOn) {
            if(ActivityCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(actividad,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
                return;
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
          obtenerLocalización();
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { }
    @Override
    public void onProviderEnabled(String s) { }
    @Override
    public void onProviderDisabled(String s) { }



}
