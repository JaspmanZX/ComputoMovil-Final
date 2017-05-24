package com.uady.proyectofinal;

/**
 * Created by victor on 23/05/17.
 */

public class Envio {
    private int id;
    private String fecha;
    private String compania;
    private String cliente;

    public Envio(int id, String fecha, String compania, String cliente) {
        this.id = id;
        this.fecha = fecha;
        this.compania = compania;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCompania() {return compania;}

    public String getCliente() {return cliente;}

}
