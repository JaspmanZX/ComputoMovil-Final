package com.uady.proyectofinal.HTTPMessages;

import com.uady.proyectofinal.RegistrationData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by José on 03/05/2017.
 */

public class EntityFormatter {

    private static final EntityFormatter entityFormatter = new EntityFormatter();

    private EntityFormatter(){
        ;
    }

    public static EntityFormatter getFormatter(){

        return entityFormatter;
    }

    public String deliveryToJSON(RegistrationData data){

        JSONObject jsonMain = new JSONObject();
        try {
            jsonMain.put( "name", data.getName() );
            jsonMain.put( "email", data.getEmail() );
            jsonMain.put( "password", data.getPassword() );
            jsonMain.put( "lastName1", data.getLastName() );
            jsonMain.put( "lastName2", data.getLastName() );
            jsonMain.put( "birthday", data.getBirthday() );
            jsonMain.put( "phone", data.getPhone() );

            JSONObject jsonLocation = new JSONObject();
            jsonLocation.put( "lat", data.getLatitude() );
            jsonLocation.put( "lon", data.getLongitude() );
            jsonMain.put( "location", jsonLocation );

            JSONObject jsonPhoto = new JSONObject();
            jsonPhoto.put( "base64", data.getPhoto() );
            jsonPhoto.put( "extension", "jpg" );
            jsonMain.put( "photo", jsonPhoto );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String entityFormat = jsonMain.toString();

        return entityFormat;
    }
}
