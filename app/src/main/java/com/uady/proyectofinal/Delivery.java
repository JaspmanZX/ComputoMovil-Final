package com.uady.proyectofinal;

/**
 * Created by Antonio Soto on 19/05/2017.
 */

public class Delivery {

    private static Delivery delivery = new Delivery();

    private String statusName;
    private String shippingDescription;
    private int shippingQuantity;
    private int shippingSize;
    private String clientFullname;

    private double latClient;
    private double lonClient;
    private double latCompany;
    private double lonCompany;

    private Delivery(){
        ;
    }

    public static Delivery getDelivery(){
        return delivery;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getShippingDescription() {
        return shippingDescription;
    }

    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public int getShippingQuantity() {
        return shippingQuantity;
    }

    public void setShippingQuantity(int shippingQuantity) {
        this.shippingQuantity = shippingQuantity;
    }

    public int getShippingSize() {
        return shippingSize;
    }

    public void setShippingSize(int shippingSize) {
        this.shippingSize = shippingSize;
    }

    public String getClientFullname() {
        return clientFullname;
    }

    public void setClientFullname(String clientFullname) {
        this.clientFullname = clientFullname;
    }

    public double getLatClient() {
        return latClient;
    }

    public void setLatClient(double latClient) {
        this.latClient = latClient;
    }

    public double getLonClient() {
        return lonClient;
    }

    public void setLonClient(double lonClient) {
        this.lonClient = lonClient;
    }

    public double getLatCompany() {
        return latCompany;
    }

    public void setLatCompany(double latCompany) {
        this.latCompany = latCompany;
    }

    public double getLonCompany() {
        return lonCompany;
    }

    public void setLonCompany(double lonCompany) {
        this.lonCompany = lonCompany;
    }
}
