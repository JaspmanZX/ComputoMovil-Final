package com.uady.proyectofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeliverysActivity extends AppCompatActivity {
    ArrayList<Envio> listaEnvios = new ArrayList<Envio>();
    String[] arrayEnv;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverys);
        lv=(ListView)findViewById(R.id.idListView);
        System.out.println("ENTRO A LA ACTIVIDAD!!!!");

        new GETDeliverys(this).execute("http://69.46.5.165:8081/dlv1601/public/api/deliveryman/delivery?quantity=40&offset=0");
        System.out.println("EJECUTANDO GETDELIVERYS!!");

    }

    public void displayDeliverys(String result) throws JSONException {
        // 3. build jsonObject
        listaEnvios.clear();
        System.out.println("DISPLAY DELIVERYS!!!");

        JSONObject lista = new JSONObject(result);
        int numResul = lista.getJSONObject("meta").getInt("resultsFound");
        System.out.println(numResul);
        JSONArray arregloEnvios = lista.getJSONArray("data");

        for(int i=0; i<numResul; i++){
            JSONObject aux =  arregloEnvios.getJSONObject(i);
            int id  = aux.getInt("id");
            String date = aux.getString("date");

            String company = aux.getJSONObject("company").getString("name");
            String client = aux.getJSONObject("client").getString("firstName") + " " + aux.getJSONObject("client").getString("lastName1");
            Envio auxEnvio = new Envio(id, date, company, client);
            listaEnvios.add(auxEnvio);
        }
        actualizarTabla();
        Toast.makeText(getBaseContext(), "Se encontraron " + numResul + " envios." , Toast.LENGTH_LONG).show();

    }

    public void actualizarTabla(){
        String respuesta = "";
        /*
        //*********************************Prueba********************************************
        listaEnvios.add(new Envio(2, "21-02-2016", "Dildos de lujo", "Lozano Bebé"));
        listaEnvios.add(new Envio(3, "12-05-2017", "CSI", "Victor Burgos"));
        listaEnvios.add(new Envio(53, "02-02-2017", "Master Chif", "Maik"));


        //*****************************************************************************
        */
        int tamano = listaEnvios.size();
        if(tamano==0)
            tamano = 2;
        arrayEnv = new String[tamano];
        int i=0;
        if(listaEnvios.isEmpty()) {
            respuesta = "SIN ENVIOS";
            arrayEnv[i] = respuesta;
            respuesta = "SIN ENVIOS";
            arrayEnv[i+1] = respuesta;
            System.out.println("TABLA VACIA!!!!");
        }else{
        while(i<tamano){
            respuesta = "id: " + listaEnvios.get(i).getId() + " Fecha: " + listaEnvios.get(i).getFecha() + '\n' + "Compañia: " + listaEnvios.get(i).getCompania() + " Cliente: " + listaEnvios.get(i).getCliente();
            arrayEnv[i] = respuesta;
            i++;
        }}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayEnv);
        lv.setAdapter(adapter);
    }
}
