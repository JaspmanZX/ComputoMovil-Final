package com.uady.proyectofinal.HTTPMessages;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by José on 03/05/2017.
 */

public class HttpPostAsyncTask extends AsyncTask<String, Void, String>{

    private String entity;

    public HttpPostAsyncTask( String entity ){

        this.entity = entity;
    }

    public void POST(String url){

        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");

        StringEntity entity = null;

        try {
            entity = new StringEntity( this.entity );
            post.setEntity( entity );
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            response = client.execute(post);

            System.out.println(response.getStatusLine().getStatusCode());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String doInBackground(String... urls) {
        POST(urls[0]);
        return null;
    }
}
