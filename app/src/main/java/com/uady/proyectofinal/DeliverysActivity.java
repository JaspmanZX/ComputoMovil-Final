package com.uady.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DeliverysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverys);

        //int delivery_id = 65; //eventualmente se leerá del intent para obtener la id del delivery elegido
        //new GETDeliverys(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery");
    }

    public void btnInfo(View v) {
        new GETDeliverys(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery?quantity=40&offset=0");
    }

    public void displayDeliverys(String result){
        Toast.makeText(getBaseContext(), result , Toast.LENGTH_LONG).show();
        
    }
}
