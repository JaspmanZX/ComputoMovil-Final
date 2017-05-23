package com.uady.proyectofinal;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by victor on 21/05/17.
 */

public class GETDeliverys extends AsyncTask<String, Void, String> {

    DeliverysActivity activity;
    public GETDeliverys(DeliverysActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {

        if(!result.equals("Problemas!")){
            System.out.println(result);
            //se genera el objeto que contiene la info de delivery
            this.activity.displayDeliverys(result);
        }

    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            // HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // GET operacion
            HttpGet get = new HttpGet(url);
            get.addHeader("Authorization", "Bearer " + Credentials.getInstance().getCredential());
            HttpResponse httpResponse = httpclient.execute(get);
            //recibe respuesta
            inputStream = httpResponse.getEntity().getContent();

            //Procesa resp a string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Problemas!";

        }catch(Exception e){
            Log.d("InputStream", e.getLocalizedMessage());

        }
        //Toast.makeText(getBaseContext(), result , Toast.LENGTH_LONG).show();
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

}
