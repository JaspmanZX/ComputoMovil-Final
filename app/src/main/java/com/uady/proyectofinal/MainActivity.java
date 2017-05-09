package com.uady.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*  loginButton = (Button)findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });*/
    }

 public void goToLogin(View view){
     Intent intent = new Intent(this,LoginRepartidor.class);
     startActivity(intent);
 }
}
