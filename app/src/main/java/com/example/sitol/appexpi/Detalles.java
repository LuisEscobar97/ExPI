package com.example.sitol.appexpi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {
    TextView tarifaBase;
    TextView tarifaBaseCon;
    TextView tarifaFrac;
    TextView tarifaFracCon;
    TextView lugar;
    TextView lugarCon;
    TextView fecha;
    TextView descuento;
    TextView total;
    TextView sbTotal;
    TextView sbTotalCon;
    TextView tiempo;
    TextView tiempoCon;
    TextView info;
    TextView pago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        tarifaBase=(TextView)findViewById(R.id.pb);
        lugar=(TextView)findViewById(R.id.lu);
        tiempo= (TextView)findViewById(R.id.t);
        tarifaFrac= (TextView)findViewById(R.id.pp);
        fecha= (TextView)findViewById(R.id.fe);
        total=(TextView)findViewById(R.id.to);
        sbTotal=(TextView)findViewById(R.id.st);
        descuento= (TextView)findViewById(R.id.des);
        pago=(TextView)findViewById(R.id.pa);

        tarifaBase.setText("$"+ Historial.dat.get(0));
        tarifaFrac.setText("$"+ Historial.dat.get(1));
        tiempo.setText( Historial.dat.get(2));
        sbTotal.setText("$"+ Historial.dat.get(3));
        descuento.setText( Historial.dat.get(4));
        total.setText("$"+ Historial.dat.get(5));
        pago.setText("$"+ Historial.dat.get(6));
        lugar.setText(Historial.dat.get(7));
        fecha.setText(Historial.dat.get(8));
    }
}
