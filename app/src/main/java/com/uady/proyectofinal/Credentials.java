package com.uady.proyectofinal;

/**
 * Created by victor on 21/05/17.
 */

public class Credentials {
    private String credential;
    private static Credentials ourInstance = new Credentials();

    public static Credentials getInstance(){
        return getOurInstance();
    }

    private Credentials(){}

    public static Credentials getOurInstance(){
        return ourInstance;
    }

    public static void setOurInstance(Credentials ourInstance){
        Credentials.ourInstance = ourInstance;
    }

    public String getCredential(){
        return credential;
    }

    public void setCredential(String credential){
        this.credential = credential;
    }
}
