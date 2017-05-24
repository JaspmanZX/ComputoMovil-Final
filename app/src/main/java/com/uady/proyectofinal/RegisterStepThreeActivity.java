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
import android.view.View;
import android.widget.Toast;

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
    private Marker currentMarker;
    private LatLng latLng;

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

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    private void moveToMerida(){

        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(20.9802, -89.7029), 10);
        map.moveCamera(camUpd1);
    }

    public void finishRegistrationProcess(View view){

        if( latLng != null ){

            RegistrationData.getInstance().setLatitude( latLng.latitude );
            RegistrationData.getInstance().setLongitude( latLng.longitude );

            this.postDeliveryMan();
            this.returnToMainView();
        }
    }

    private void postDeliveryMan(){

        RegistrationData registrationData = RegistrationData.getInstance();

        String entity = EntityFormatter.getFormatter().deliveryToJSON(registrationData);

        HttpPostAsyncTask httpPostAsyncTask = new HttpPostAsyncTask(entity);
        String url = "http://69.46.5.165:8081/dlv1601/public/api/deliveryman/register";
        httpPostAsyncTask.execute(url);
    }

    private void returnToMainView(){

        Toast.makeText(getApplicationContext(), "Repartidor añadido exitosamente.", Toast.LENGTH_SHORT);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng newLatLng) {;

                setupMarker(newLatLng);
                goToLatLng(newLatLng);
                latLng = newLatLng;
            }
        });

        moveToMerida();
    }

    private void goToLatLng(LatLng latlng){

        if(latlng != null){
            LatLng merida = new LatLng(latlng.latitude, latlng.longitude);
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(merida) //Centramos el mapa en Merida
                    .zoom(19) //Establecemos el zoom en 19
                    .bearing(45) //Establecemos la orientación con el noreste arriba
                    .build();
            CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
            map.animateCamera(camUpd);
        }
    }

    private void setupMarker(LatLng latLng){

        if(currentMarker == null){
            currentMarker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(latLng.latitude, latLng.longitude))
                    .title("")
                    .snippet(""));
        }
        currentMarker.setPosition(latLng);
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
