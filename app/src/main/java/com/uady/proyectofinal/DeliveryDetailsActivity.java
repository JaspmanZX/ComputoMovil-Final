package com.uady.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uady.proyectofinal.HTTPMessages.GETDeliveryDetails;

public class DeliveryDetailsActivity extends AppCompatActivity {

    //private Delivery delivery;
    private TextView client;
    private TextView delivery_status;
    private TextView delivery_quantity;
    private TextView delivery_size;
    private TextView delivery_description;
    private int delivery_id = 65; //eventualmente se leerá del intent para obtener la id del delivery elegido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emptyness);

        client = (TextView) findViewById(R.id.client_view);
        delivery_status = (TextView) findViewById(R.id.delivery_status);
        delivery_quantity = (TextView) findViewById(R.id.delivery_quantity);
        delivery_size = (TextView) findViewById(R.id.delivery_size);
        delivery_description = (TextView) findViewById(R.id.delivery_description);

        new GETDeliveryDetails(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery/" + delivery_id);
    }

    public void displayDeliveryDetalis(){

        Delivery delivery = Delivery.getDelivery();
        client.setText(delivery.getClientFullname());
        delivery_status.setText(delivery.getStatusName());
        delivery_description.setText(delivery.getShippingDescription());
        delivery_quantity.setText(String.valueOf(delivery.getShippingQuantity()));
        delivery_size.setText(String.valueOf(delivery.getShippingSize()));

    }

    public void showMap(View v){

        Intent intent = new Intent(this, DeliveryMapActivity.class);
        intent.putExtra("LatClient",Delivery.getDelivery().getLatClient());
        intent.putExtra("LonClient",Delivery.getDelivery().getLonClient());
        intent.putExtra("LatCompany",Delivery.getDelivery().getLatCompany());
        intent.putExtra("LonCompany",Delivery.getDelivery().getLonCompany());

        startActivity(intent);
    }

    public void goToChangeState(View view){

        Intent intent = new Intent(this, StatusActivity.class);

        intent.putExtra("id", this.delivery_id);
        startActivity(intent);

    }
}
