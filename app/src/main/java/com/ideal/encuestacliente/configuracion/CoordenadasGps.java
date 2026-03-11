package com.ideal.encuestacliente.configuracion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CoordenadasGps implements LocationListener
{
    private static final int REQUEST_CODE = 1000;
    private LocationManager locationManager;
    private Context context;
    private boolean gpsActivo;
    private Activity activity;
    private Location location = null;
    private double latitud;
    private double longitud;
    private double altitud;
    public double latitudChanged;
    public double longitudChanged;
    public double altitudChanged;

    public CoordenadasGps(Context context, Activity activity, TextInputEditText[] textInputEditTexts)
    {
        this.context = context;
        this.activity = activity;
        obtenerLocalización(textInputEditTexts);
    }

    public CoordenadasGps(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        obtenerLocalización();

    }

    public void obtenerLocalización(TextInputEditText[] textInputEditTexts){

       try{
            locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
       }catch (Exception e){
       }
       if(gpsActivo)
       {
           if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  ) {
               ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
               return;
           }

           locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000 * 60 ,10,this);
           location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

               if (location != null) {
                   longitud = location.getLongitude();
                   altitud = location.getAltitude();
                   latitud = location.getLatitude();

                   textInputEditTexts[0].setText(String.valueOf(longitud));
                   textInputEditTexts[1].setText(String.valueOf(altitud));
                   textInputEditTexts[2].setText(String.valueOf(latitud));
               }


       }

    }

    public ArrayList<String> obtenerLocalización(){

        ArrayList<String> cordenadas = new ArrayList<>();

        try{
            locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
            gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }catch (Exception e){
        }

        if(gpsActivo)
        {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  ) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
                //return;
            }

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 5000 * 60 ,10,this);
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            if (location != null) {
                longitud = location.getLongitude();
                altitud = location.getAltitude();
                latitud = location.getLatitude();

                Log.e("longitud", longitud+"");
                Log.e("altitud", altitud+"");
                Log.e("latitud", latitud+"");

                cordenadas.add(String.valueOf(longitud));
                cordenadas.add(String.valueOf(altitud));
                cordenadas.add(String.valueOf(latitud));
            }
        }

        return cordenadas;

    }

    @Override
    public void onLocationChanged(Location location) {
        latitudChanged = location.getLatitude();
        longitudChanged = location.getLongitude();
        altitudChanged = location.getAltitude();

        Log.e("latitudChanged", latitudChanged+"");
        Log.e("longitudChanged", longitudChanged+"");
        Log.e("altitudChanged", altitudChanged+"");

       /* p = new GeoPoint(
                (int) (lat * 1E6),
                (int) (lng * 1E6));

        mc.animateTo(p);
        mc.setZoom(17);*/
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
