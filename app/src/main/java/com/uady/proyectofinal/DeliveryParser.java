package com.uady.proyectofinal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Antonio Soto on 19/05/2017.
 */

public class DeliveryParser {

    private static DeliveryParser parser = new DeliveryParser();

    private DeliveryParser(){
        ;
    }

    public static DeliveryParser getParser(){
        return parser;
    }

    public void setupDelivery( String jsonString ){

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonData = jsonObject.getJSONArray("data").getJSONObject(0);

            String name = jsonData.getJSONObject("status").getString("name");
            String description = jsonData.getJSONObject("shipping").getString("description");
            int quantity =
                    jsonData.getJSONObject("shipping").getJSONArray("packets").getJSONObject(0).
                    getInt("quantity");
            int size =
                    jsonData.getJSONObject("shipping").getJSONArray("packets").getJSONObject(0).
                    getInt("size");
            String fname =
                    jsonData.getJSONObject("client").getString("firstName") + " " +
                    jsonData.getJSONObject("client").getString("lastName1");
            double latClient =
                    jsonData.getJSONObject("client").getJSONObject("location").getDouble("lat");
            double lonClient =
                    jsonData.getJSONObject("client").getJSONObject("location").getDouble("lon");
            double latCompany =
                    jsonData.getJSONObject("company").getJSONObject("location").getDouble("lat");
            double lonCompany =
                    jsonData.getJSONObject("client").getJSONObject("location").getDouble("lon");

            Delivery.getDelivery().setStatusName( name );
            Delivery.getDelivery().setShippingDescription( description );
            Delivery.getDelivery().setShippingQuantity( quantity );
            Delivery.getDelivery().setShippingSize( size );
            Delivery.getDelivery().setClientFullname( fname );
            Delivery.getDelivery().setLatClient( latClient );
            Delivery.getDelivery().setLonClient( lonClient );
            Delivery.getDelivery().setLatCompany( latCompany );
            Delivery.getDelivery().setLonCompany( lonCompany );

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
