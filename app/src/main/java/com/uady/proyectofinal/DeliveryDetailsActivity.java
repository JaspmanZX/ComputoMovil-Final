package com.uady.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.uady.proyectofinal.HTTPMessages.GETDeliveryDetails;

public class DeliveryDetailsActivity extends AppCompatActivity {

    //private Delivery delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyness);

        int delivery_id = 65; //eventualmente se leerá del intent para obtener la id del delivery elegido
        new GETDeliveryDetails(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery/" + delivery_id);
    }

    public void displayDeliveryDetalis(){

        Delivery.getDelivery();
    }

    public void showMap(View v){

        Intent intent = new Intent(this, DeliveryMapActivity.class);
        intent.putExtra("LatClient",Delivery.getDelivery().getLatClient());
        intent.putExtra("LonClient",Delivery.getDelivery().getLonClient());
        intent.putExtra("LatCompany",Delivery.getDelivery().getLatCompany());
        intent.putExtra("LonCompany",Delivery.getDelivery().getLonCompany());

        startActivity(intent);
    }
}
