package com.uady.proyectofinal.HTTPMessages;

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

    public String deliveryToJSON(
            String name,
            String email,
            String password,
            String lastName1,
            String lastName2,
            String birthday,
            String phone,
            String lat,
            String lon,
            String base64
    ){

        String entityFormat =
                "{\n" +
                        "  \"name\": \""+name+"\",\n" +
                        "  \"email\": \""+email+"\",\n" +
                        "  \"password\": \""+password+"\",\n" +
                        "  \"lastName1\": \""+lastName1+"\",\n" +
                        "  \"lastName2\": \""+lastName2+"\",\n" +
                        "  \"birthday\": \""+birthday+"\",\n" +
                        "  \"phone\": \""+phone+"\",\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": "+lat+",\n" +
                        "    \"lon\": "+lon+"\n" +
                        "  },\n" +
                        "  \"photo\": {\n" +
                        "     \"base64\":\""+base64+"\",\n" +
                        "    \"extension\": \"jpg\"\n" +
                        "  }\n" +
                        "}";

        return entityFormat;
    }
}
