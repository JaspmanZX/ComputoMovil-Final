package com.uady.proyectofinal;

/**
 * Created by jorge on 19/05/17.
 */
public class Credentials {

    private String credential;
    private static Credentials ourInstance = new Credentials();

    public static Credentials getInstance() {
        return getOurInstance();
    }

    private Credentials() {
    }

    public static Credentials getOurInstance() {
        return ourInstance;
    }


    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }
}
