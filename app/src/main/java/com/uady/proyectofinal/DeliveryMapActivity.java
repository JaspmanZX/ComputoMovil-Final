package com.uady.proyectofinal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeliveryMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private Location deliverymanLocation;
    private Marker deliverymanMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_map);

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

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment2);
        mapFragment.getMapAsync(this);
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

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            //cambiar el marcador del repartidor
            deliverymanLocation = location;
            setupDeliverymanMarker();
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


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        setupCompanyMarker();
        setupClientMarker();
        moveToMerida();
    }

    private void setupCompanyMarker() {

        Marker companyMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(getIntent().getDoubleExtra("LatCompany", 0),
                        getIntent().getDoubleExtra("LonCompany", 0)))
                .title("Punto de recogida")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        );
    }

    private void setupClientMarker() {

        Marker companyMarker = map.addMarker(new MarkerOptions()
                .position(new LatLng(getIntent().getDoubleExtra("LatClient", 0),
                        getIntent().getDoubleExtra("LonClient", 0)))
                .title("Punto de entrega")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );
    }

    private void setupDeliverymanMarker(){

        if( deliverymanMarker == null ){
            deliverymanMarker = map.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(
                                    deliverymanLocation.getLatitude(),
                                    deliverymanLocation.getLongitude()
                            )
                    )
                    .title("Repartidor")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            );
        }

        deliverymanMarker.setPosition(
                new LatLng(deliverymanLocation.getLatitude(), deliverymanLocation.getLongitude())
        );
    }

    private void moveToMerida(){

        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(new LatLng(20.9802, -89.7029), 10);
        map.moveCamera(camUpd1);
    }
}
