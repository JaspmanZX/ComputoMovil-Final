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
        //=======================================================================================
        result = "{\n"+
                "\"meta\": {\n"+
                "\"resultsFound\": 2,\n"+
                "\"offset\": 10,\n"+
                "\"limit\": 10\n"+
                "},\n"+
                "\"data\": [\n"+
                "{\n"+
                "\"id\": 1,\n"+
                "\"date\": \"2016-06-16\",\n"+
                "\"hour\": 360,\n"+
                "\"company\": {\n"+
                "\"id\": 1,\n"+
                "\"name\": \"Supreme Pizza\"\n"+
                "},\n"+
                "\"status\": {\n"+
                "\"name\": \"PorConfirmar\",\n"+
                "\"code\": 2,\n"+
                "\"description\": \"This delivery has been asigned to at least one deliveryman and is awaiting for confirmation\"\n"+
                "},\n"+
                "\"client\": {\n"+
                "\"firstName\": \"Marge\",\n"+
                "\"lastName1\": \"Simons\",\n"+
                "\"lastName2\": \"Soler\",\n"+
                "\"phone\": \"9994490032\"\n"+
                "}\n"+
                "},\n"+
                "{\n"+
                "\"id\": 2,\n"+
                "\"date\": \"2016-11-12\",\n"+
                "\"hour\": 360,\n"+
                "\"company\": {\n"+
                "\"id\": 1,\n"+
                "\"name\": \"Dominos\"\n"+
                "},\n"+
                "\"status\": {\n"+
                "\"name\": \"PorConfirmar\",\n"+
                "\"code\": 2,\n"+
                "\"description\": \"This delivery has been asigned to at least one deliveryman and is awaiting for confirmation\"\n"+
                "},\n"+
                "\"client\": {\n"+
                "\"firstName\": \"Homero\",\n"+
                "\"lastName1\": \"Simons\",\n"+
                "\"lastName2\": \"Soler\",\n"+
                "\"phone\": \"9994490032\"\n"+
                "}\n"+
                "}\n"+

                "]\n"+
                "}\n";
        //=======================================================================================================================
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
