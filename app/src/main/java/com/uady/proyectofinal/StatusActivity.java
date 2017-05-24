package com.uady.proyectofinal;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class StatusActivity extends AppCompatActivity {
    final String URL = "http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery/status";
    EditText titulo;
    JSONObject cambiarEstado;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String credencial = Credentials.getInstance().getCredential();
        titulo = (EditText) findViewById(R.id.idText);
        int id = getIntent().getIntExtra("id",-1);
        cambiarEstado = new JSONObject();


        if(id != -1){
            titulo.setText("Pedido ID: "+id);
        }else{
            titulo.setText("Ocurrio un error");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void cancelarPedido(View view){
        try {
            cambiarEstado.put("idDelivery", id);
            cambiarEstado.put("statusCode", 6);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void entregarPedido(View view){
        try {
            cambiarEstado.put("idDelivery", id);
            cambiarEstado.put("statusCode", 5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void proseguirPedido(View view){
        try {
            cambiarEstado.put("idDelivery", id);
            cambiarEstado.put("statussCode", 4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class PatchAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            return PATCH(URL);
        }

        protected String PATCH(String url) {
            InputStream inputStream = null;
            String result = "Ningun valor";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPatch httpPatch = new HttpPatch(url);
                String json;
                JSONObject jsonObject = cambiarEstado;
                json = jsonObject.toString();
                StringEntity se = new StringEntity(json);
                httpPatch.setEntity(se);
                httpPatch.setHeader("Accept", "application/json");
                httpPatch.setHeader("Content-type", "application/json");
                httpPatch.addHeader("Authorization", "Bearer " + Credentials.getInstance().getCredential());
                HttpResponse httpResponse = httpclient.execute(httpPatch);
                inputStream = httpResponse.getEntity().getContent();

                if (inputStream != null)
                    result = inputStream.toString();
                else
                    result = "Did not work!";

            } catch (Exception e) {

            }
            return result;

        }
    }
}
