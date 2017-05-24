package com.uady.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DeliveryStatusActivity extends AppCompatActivity {
    public String idStatus;
    public String statusCambiar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);
        idStatus= "2";
        statusCambiar = "5";
        new POSTDeliveryStatus(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery/status");
        System.out.println("EJECUTANDO POST!!");
    }

    public String getIdStatus(){
        return idStatus;
    }
    public String getStatusCambiar(){
        return statusCambiar;
    }

    public void displayResult(String result){
        System.out.println("EJECUTANDO El Display!!");
        System.out.println("Resultado: " + result);

        Toast.makeText(getBaseContext(), result , Toast.LENGTH_LONG).show();

    }
}
