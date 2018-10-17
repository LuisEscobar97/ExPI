package com.example.sitol.appexpi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity  implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText txtcontrasenia,txtcorreo;
    Button btnlogin;
    RequestQueue rq;
    JsonRequest jrq;
    ProgressDialog pDialog;
    Usario cat;
    SQLiteDatabase sqLiteDatabase=null;
    private String URL_LISTA_HISTORIAL ="http://192.168.0.16/BASEDATOS/consultarHistorial.php?id=2";
    private ArrayList<ObjHistorial> instList= new ArrayList<ObjHistorial>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtcorreo=(EditText)  findViewById(R.id.txtlogincorreo);
        txtcontrasenia=(EditText) findViewById(R.id.txtlogincontrasenia);
        btnlogin= (Button) findViewById(R.id.btnlogin);
        rq= Volley.newRequestQueue(getBaseContext());

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new GetUsuario().execute();


            }
        });
    }
    public void Limpiar(){

        txtcorreo.setText("");

        txtcontrasenia.setText("");

    }
    private void Iniciarsecion (){
        String url="http://192.168.0.16/BASEDATOS/LOGINE.php?correo="+txtcorreo.getText().toString()+"&contrasenia="+txtcontrasenia.getText().toString();
        url=url.replace(" ","%20");
        Log.d("sql",url);
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo encontrar el  usuario o contraseña esta mal", Toast.LENGTH_LONG).show();
        Limpiar();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se encontro al usuario", Toast.LENGTH_LONG).show();
        Limpiar();
        Intent i = new Intent(this, NavegadorPrincipal.class);

        startActivity(i);
    }
    private class GetUsuario extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String url="http://192.168.0.16/BASEDATOS/loginexpi.php?correo="+txtcorreo.getText().toString()+"&contrasenia="+txtcontrasenia.getText().toString();
            url=url.replace(" ","%20");
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(url , ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray institu = jsonObj.getJSONArray("historial");

                        for (int i = 0; i < institu.length(); i++) {
                            JSONObject catObj = (JSONObject) institu.get(i);


                            cat = new Usario(Integer.parseInt(catObj.getString("idusuario")),1,Integer.parseInt(catObj.getString("estadousuario")),catObj.getString("nombre"),catObj.getString("email"),catObj.getString("am"),catObj.getString("ap"),Long.parseLong(catObj.getString("notarjeta")),
                                    Long.parseLong(catObj.getString("notarjeta")),Long.parseLong(catObj.getString("telefono")));

                            insertarUsuario(cat.getIdU());
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "¿No ha recibido ningún dato desde el servidor!");

            }

            return null;
        }
        ///////////////////////////////////////////////////aqui le lo que se supone va crear mis historial///////////
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //Toast.makeText(getApplicationContext(), "Se encontro al usuario", Toast.LENGTH_LONG).show();
            Limpiar();
            crearBbase(null);
            Intent i = new Intent(getApplicationContext(), NavegadorPrincipal.class);
            startActivity(i);

        }
    }
    public  void crearBbase(Usario usario){
        int  ids=2;
        boolean band= true;

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Expi", MODE_PRIVATE, null);


            sqLiteDatabase.execSQL("Create table if not exists usuario (id int(10))");
            Cursor c = sqLiteDatabase.rawQuery("Select * from usuario", null);
            int idC = c.getColumnIndex("id");
            Log.i("usuarios",String .valueOf(idC));
            /*
           int nom=c.getColumnIndex("nombre");
           int apP= c.getColumnIndex("apellidoPaterno");
           int apm= c.getColumnIndex("apellidoMaterno");
           int co=c.getColumnIndex("correo");
           int tel= c.getColumnIndex("telefono");
           int no= c.getColumnIndex("noTarjeta");
           int cba=c.getColumnIndex("codigoBarras");
           int esu=c.getColumnIndex("estadousuario");
           int tu= c.getColumnIndex("tipoUsario");
           */
            c.moveToFirst();

            while (c != null) {
                Toast.makeText(getApplicationContext(), "hay usuario"+String .valueOf(c.getInt(idC)), Toast.LENGTH_LONG).show();
                c.moveToNext();


                }



        }catch(Exception e){
            e.printStackTrace();

            // sqLiteDatabase.execSQL("insert into usuario (nombre,  apellidoPaterno, apellidoMaterno,correo, telefono, noTarjeta,id ) values(" + "'Luis','Escobar','Valdes','sitolui@outlook.com',7227022978,7894561230,ids)");
           // Toast.makeText(getApplicationContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
    // este metodk cera un usaurio en base a los datos que hay en la base de datos de sqlite, algo que tendiar que hacer es validar esto para saber si hay una sesion iniciada o no para lo del login o abra directamente el navegador
    public void crearUsuario(){
        int aux=0;
        try {
            sqLiteDatabase = this.openOrCreateDatabase("Expi", MODE_PRIVATE, null);
            Cursor c = sqLiteDatabase.rawQuery("Select * from usuario", null);
            int idC = c.getColumnIndex("id");
            int nom=c.getColumnIndex("nombre");
            int apP= c.getColumnIndex("apellidoPaterno");
            int apm= c.getColumnIndex("apellidoMaterno");
            int co=c.getColumnIndex("correo");
            int tel= c.getColumnIndex("telefono");
            int no= c.getColumnIndex("noTarjeta");
            int cba=c.getColumnIndex("codigoBarras");
            int esu=c.getColumnIndex("estadousuario");
            int tu= c.getColumnIndex("tipoUsario");
            c.moveToFirst();
            while (c != null) {
                if (aux==0){
                    Usario usuarioaux= new Usario(c.getInt(idC),1,1,c.getString(nom),c.getString(co),c.getString(apm),c.getString(apP),c.getInt(no),87945623,c.getInt(tel));


                }
                c.moveToNext();
                aux++;

            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void insertarUsuario(int ida){

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Expi", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("Create table if not exists usuario (id int(10))");
            sqLiteDatabase.execSQL("insert into usuario (id ) values(" + "ida)");




        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "no se inserto", Toast.LENGTH_LONG).show();

        }
    }
}
