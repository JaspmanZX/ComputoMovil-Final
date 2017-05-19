package com.uady.proyectofinal.HTTPMessages;

import android.os.AsyncTask;
import android.util.Log;

import com.uady.proyectofinal.Credentials;
import com.uady.proyectofinal.DeliveryDetailsActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jorge on 19/05/17.
 */

public class GETDeliveryDetails extends AsyncTask<String, Void, String> {

    DeliveryDetailsActivity activity;

    public GETDeliveryDetails(DeliveryDetailsActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        return GET(urls[0]);
    }

    // onPostExecute despliega el resultado.
    @Override
    protected void onPostExecute(String result) {

        if(!result.equals("Problemas!")){
            System.out.println(result);
            //se genera el objeto que contiene la info de delivery
            this.activity.displayDeliveryDetalis();
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
            get.addHeader("Authorization", "Basic " + Credentials.getInstance().getCredential());
            HttpResponse httpResponse = httpclient.execute(get);
// recibe respuesta
            inputStream = httpResponse.getEntity().getContent();
// procesa resp a string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Problemas!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
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
