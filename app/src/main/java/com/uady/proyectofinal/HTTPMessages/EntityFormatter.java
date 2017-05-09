package com.uady.proyectofinal.HTTPMessages;

import com.uady.proyectofinal.RegistrationData;

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

        String entityFormat =
                "{\n" +
                        "  \"name\": \""+data.getName()+"\",\n" +
                        "  \"email\": \""+data.getEmail()+"\",\n" +
                        "  \"password\": \""+data.getPassword()+"\",\n" +
                        "  \"lastName1\": \""+data.getLastName()+"\",\n" +
                        "  \"lastName2\": \"\",\n" +
                        "  \"birthday\": \""+data.getBirthday()+"\",\n" +
                        "  \"phone\": \""+data.getPhone()+"\",\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": "+data.getLatitude()+",\n" +
                        "    \"lon\": "+data.getLongitude()+"\n" +
                        "  },\n" +
                        "  \"photo\": {\n" +
                        "     \"base64\":\""+data.getPhoto()+"\",\n" +
                        "    \"extension\": \"jpg\"\n" +
                        "  }\n" +
                        "}";

        return entityFormat;
    }
}
