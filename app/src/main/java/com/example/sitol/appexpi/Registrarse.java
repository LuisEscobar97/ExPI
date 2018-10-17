package com.example.sitol.appexpi;

import android.content.Intent;
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

import org.json.JSONObject;

public class Registrarse extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;
    EditText txtcorreo,txtcontrasenia,txtnombre,txtapellidop,txtapellidom, txttelefono;
    Button btnregistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        rq= Volley.newRequestQueue(getBaseContext());
        txtcorreo=(EditText)  findViewById(R.id.txtcorreo);
        txtcontrasenia=(EditText) findViewById(R.id.txtcontrasenia);
        txtnombre=(EditText)  findViewById(R.id.txtnombre);
        txtapellidop =(EditText) findViewById(R.id.txtapaterno);
        txtapellidom = (EditText) findViewById(R.id.txtamaterno);
        txttelefono= (EditText) findViewById(R.id.txtcel);
        btnregistro= (Button) findViewById(R.id.btnregistrouser);

        btnregistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RegistroUser ();
            }
        });

    }


    public void Limpiar(){

        txtcorreo.setText("");
        txtapellidop.setText("");
        txtapellidom.setText("");
        txtnombre.setText("");
        txtcontrasenia.setText("");
        txtcorreo.setText("");
        txttelefono.setText("");
    }

    public void RegistroUser (){

        String url = "https://192.168.172.218/cuentaNueva.php?contrasenia=" + txtcontrasenia.getText().toString() + "&nombre="+ txtnombre.getText().toString()+
                "&apellidoP=" + txtapellidop.getText().toString() +
                "&apellidoM=" + txtapellidom.getText().toString() + "&correo=" +  txtcorreo.getText().toString()+
                "&telefono=" + txttelefono.getText().toString();
        url=url.replace(" ","%20");

        Log.d("sql",url);

        jrq = new JsonObjectRequest(Request.Method.GET, url, null,  this, this);
        rq.add(jrq);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se pudo registrar el  usuario", Toast.LENGTH_LONG).show();
        Limpiar();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se registro correctamente", Toast.LENGTH_LONG).show();
        Limpiar();
        Intent i = new Intent(this, NavegadorPrincipal.class);

        startActivity(i);
    }
}
