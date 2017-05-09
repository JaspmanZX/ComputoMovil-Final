package com.uady.proyectofinal;

import android.app.Activity;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginRepartidor extends AppCompatActivity {
    public static EditText Correo;
    public Validaciones valid = new Validaciones();
    public TextView estadoCorreo;
    public static EditText Contrasena;
    public static String deviceID;
    Button IniciarS;
    Button CrearC;

    TextView estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IniciarS.setEnabled(false);
        setContentView(R.layout.activity_login_repartidor);
        Correo = (EditText) findViewById(R.id.input_usuario);
        Contrasena = (EditText) findViewById(R.id.input_contrasena);
        IniciarS = (Button) findViewById(R.id.boton_iniciar_sesion);
        CrearC = (Button) findViewById(R.id.buton_crear_cuenta);
        estado = (TextView) findViewById(R.id.texto_olvidaste_contrasena);
        estadoCorreo = (TextView) findViewById(R.id.textEmailF);
        deviceID = Secure.ANDROID_ID;
        if (isConnected()) {
            estado.setBackgroundColor(0xFF00CC00);
            estado.setText("Conectado");
        } else {
            estado.setText("NO conectado");
        }

    }

    public void btnIniciarSesion(View v) {
        HttpAsyncTask IniciarSesion = new HttpAsyncTask();//.execute("http://petstore.swagger.io/v2/pet/findByStatus?status=sold");
        IniciarSesion.execute("http://69.46.5.165:8081/dlv1601/public/docs#!/user/login");
    }


    public static String POST(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
            String aux1 = Correo.getText().toString();
            String aux2 = Contrasena.getText().toString();
            String json = "{\n" +
                    "  \"grant_type\": " + "password" + ",\n" +
                    "  \"client_id\": " + "f3d259ddd3ed8ff3843839b" + ",\n" +
                    "  \"client_secret\": " + "4c7f6f8fa93d59c45502c0ae8c4a95b" + ",\n" +
                    "  \"username\": " + aux1 + ",\n" +
                    "  \"password\": " + aux2 + ",\n" +
                    "    \"rememberMe\": true,\n" +
                    "  \"device\": {\n" +
                    "      \"code\": " + deviceID + ",\n" +
                    "      \"token\": \"" + "AIzaSyD9vf5e1CVJjajc1_a_xej1XMlhwxX_jyA" + "\"\n" +
                    "    }\n" +
                    "}";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject(json);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0]);
        }

        // onPostExecute despliega el resultado.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

            //Toast.makeText(getBaseContext(), result , Toast.LENGTH_LONG).show();
            finish();

        }
    }

    private void checkLogin(String email, String pass) {

        if (!valid.isEmailFormated(email)) {
            IniciarS.setEnabled(false);
            estadoCorreo.setBackgroundColor(Color.RED);
            estadoCorreo.setText("El formato de correo es incorrecto");

        } else {
            //Boton inicio de sesión enabled
            IniciarS.setEnabled(true);
            estadoCorreo.setText("");
            estadoCorreo.setBackgroundColor(0x00000000);
            if (valid.passCheck(pass))
                IniciarS.setEnabled(false);
            else
                IniciarS.setEnabled(true);
        }
    }
}