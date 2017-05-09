package com.uady.proyectofinal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uady.proyectofinal.HTTPMessages.EntityFormatter;
import com.uady.proyectofinal.HTTPMessages.HttpPostAsyncTask;

/**
 * Created by José on 09/05/2017.
 */

public class RegisterStepThreeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private Location currentLocation;
    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen_3);

        boolean enabled = ((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!enabled){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        verifyLocationPermissions(this);

        MyLocationListener myLocationListener = new MyLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000,
                1,
                myLocationListener
        );

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        this.goToCurrentLocation();
    }

    public void finishRegistrationProcess(){

        String latitude = String.valueOf(currentLocation.getLatitude());
        String longitude = String.valueOf(currentLocation.getLongitude());

        RegistrationData.getInstance().setLatitude(latitude);
        RegistrationData.getInstance().setLongitude(longitude);

        this.postDeliveryMan();
    }

    private void postDeliveryMan(){

        RegistrationData registrationData = RegistrationData.getInstance();

        String entity = EntityFormatter.getFormatter().deliveryToJSON(registrationData);

        HttpPostAsyncTask httpPostAsyncTask = new HttpPostAsyncTask(entity);
        String url = "http://69.46.5.165:8081/dlv1601/public/api/deliveryman/register";
        httpPostAsyncTask.POST(url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
    }

    private void goToCurrentLocation(){

        if(currentLocation != null){
            LatLng merida = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(merida) //Centramos el mapa en Merida
                    .zoom(19) //Establecemos el zoom en 19
                    .bearing(45) //Establecemos la orientación con el noreste arriba
                    .build();
            CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
            map.animateCamera(camUpd);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            currentLocation = location;
            this.setupMarker();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        private void setupMarker(){

            if(currentMarker == null){
                currentMarker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                        .title("")
                        .snippet(""));
            }
            currentMarker.setPosition(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        }
    }

    public void verifyLocationPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }
    }
}
