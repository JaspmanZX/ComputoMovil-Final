package com.uady.proyectofinal;

import android.os.AsyncTask;
import android.util.Log;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by victor on 24/05/17.
 */

public class PATCHDeliveryStatus extends AsyncTask<String, Void, String> {
    DeliveryStatusActivity activity;
    public PATCHDeliveryStatus(DeliveryStatusActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        String aux1 = this.activity.getIdStatus();
        String aux2 = this.activity.getStatusCambiar();
        System.out.println("ESTOY EN DOINBACk");
        System.out.println(aux1);
        System.out.println(aux2);


        return PATCH(urls[0], aux1, aux2);
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("ESTOY EN ONPOSTEXECUTE");

        //if(!result.equals("Problemas!")){
            System.out.println(result);
            //se genera el objeto que contiene la info de delivery
                this.activity.displayResult(result);

//        }

    }

    public static String PATCH(String url, String aux1, String aux2) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make PATCH request to the given URL
            HttpPatch httpPost = new HttpPatch(url);

            String json = "{\n" +
                    "  \"idDelivery\": " + aux1 + ",\n" +
                    "  \"statusCode\": " + aux2 + "\n" +
                    "}";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject(json);
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.addHeader("Authorization", "Bearer " + Credentials.getInstance().getCredential());//Se anadio las credenciales


            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            System.out.println("-----------------------------------------------------------------");
            System.out.println(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
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
