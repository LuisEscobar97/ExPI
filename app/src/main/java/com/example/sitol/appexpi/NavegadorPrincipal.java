package com.example.sitol.appexpi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NavegadorPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Inicio.OnFragmentInteractionListener,Historial.OnFragmentInteractionListener, GmapFragment.OnFragmentInteractionListener, TimePickerDialog.OnTimeSetListener
,Response.Listener<JSONObject>, Response.ErrorListener{
    static LocationManager locationManager;
    static LocationListener locationListener;
    static Boolean activ=false;
    static Boolean inicio=false;
    static Toolbar toolbar;
    private static NotificationHelper mNotificationHelper;
    static  Boolean servicio= false;
   static Boolean control= false;
    SQLiteDatabase sqLiteDatabase=null;
    static TextView cabecera;
    static Usario usuario;
    final Handler handler= new Handler();
    int seg=0;
    int min=0;
    int hora=0;
    RequestQueue rq;
    JsonRequest jrq;
  public static FloatingActionButton botonAlarma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegador_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        Fragment frag = null;
        frag = new Inicio();
        fm.beginTransaction().replace(R.id.contenedor, frag).commit();
        // este objecto ayuda para la crecacion de las notificaciones
        mNotificationHelper = new NotificationHelper(this);


        botonAlarma = (FloatingActionButton) findViewById(R.id.fab);
        // esto es una simulacion de que si hay un servicio activo haga visible el boton de las alarmas
        if (servicio) {

            botonAlarma.setVisibility(View.VISIBLE);
        } else {
            botonAlarma.setVisibility(View.INVISIBLE);
        }
        botonAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // esto me despliega el cuadro para programar las alarmas
                DialogFragment timerPicke = new TimerPickerFragment();
                timerPicke.show(getSupportFragmentManager(), "");
            }
        });
        // crearBbase(null);
        crearUsuario();
        if (usuario == null) {
            Log.i("usuario", "nulo");
        }
        // esto es para la geolocalizacion
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        locationManager = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);
        rq= Volley.newRequestQueue(getBaseContext());
        Runnable run = new Runnable() {
            @Override
            public void run() {


                handler.postDelayed(this,1000);
                seg+=1;
                if (seg%5==0){
                    buscarServicios ();

                }else{

                }

            }
        };
        handler.post(run);

    }
    private void buscarServicios (){
        String url="http://192.168.2.8/BASEDATOS/LOGINE.php?id="+String.valueOf(2);
        url=url.replace(" ","%20");
        Log.d("sql",url);
        jrq=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Log.i("servicio", "nulo");
        Inicio.activarServicio(false);
    }

    @Override
    public void onResponse(JSONObject response) {
       // Toast.makeText(getApplicationContext(), "Se encontro al usuario", Toast.LENGTH_LONG).show();
        Inicio.bandera=true;
        Log.i("servicio", "no nulo");


        if (control){

        }else{
            Inicio.activarServicio(true);
            sendChanel3("Servicios","Servicio nuevo activo");

        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.navegador_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm =getFragmentManager();
        android.support.v4.app.Fragment fragment= null;
        Fragment frag= null;

        if (id == R.id.nav_camera) {
            if (inicio){
                frag= new Inicio();
                fm.beginTransaction().replace(R.id.contenedor,frag).commit();
                inicio=false;
                toolbar.setTitle("Inicio");}

        } else if (id == R.id.nav_gallery) {
            activ=true;
            inicio =true;
            frag= new Historial();
            fm.beginTransaction().replace(R.id.contenedor,frag).commit();
            toolbar.setTitle("Historial");

        } else if (id == R.id.nav_slideshow) {
            inicio=true;
            frag= new GmapFragment();
            fm.beginTransaction().replace(R.id.contenedor,frag).commit();
            toolbar.setTitle("Estacionamientos");

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static Object[] inserta(Object[] arr, ArrayList<String> obj) {
        Object arrn[] = null;
        if (arr == null) {
            arrn = new Object[1];
            arrn[0] = obj;
        } else {
            arrn = new Object[arr.length + 1];
            System.arraycopy(arr, 0, arrn, 0, arr.length);
            arrn[arr.length] = obj;
        }
        return arrn;

    }

    // este metodo permite guardar la programacion de la alaram
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        //Toast.makeText(getApplicationContext(),"Hora "+hourOfDay+" minuto " + minute,Toast.LENGTH_LONG).show();

        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        String  time="";
        //en esta  variable se guarda la hora en que va va sonar la alarma de manera de string para poderla mostrar
        time += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        // este metodo genera la notificacion
        sendChanel1("Alarma Programada","Su alarma sonará  a las: " + time);
        startAlarm(c);
        updateTimeText(c);
    }
        // este es un canal para las nototficaciones
    public static void sendChanel1(String titulo, String mensaje){
        NotificationCompat.Builder nb=  mNotificationHelper.getChanelotification(titulo,mensaje);
        mNotificationHelper.getManager().notify(1,nb.build());
    }
    // este es otro canal para las nototficaciones
    public static void sendChanel2(String titulo, String mensaje){
        NotificationCompat.Builder nb=  mNotificationHelper.getChanel2otification(titulo,mensaje);
        mNotificationHelper.getManager().notify(2,nb.build());

    }
    public static void sendChanel3(String titulo, String mensaje){
        NotificationCompat.Builder nb=  mNotificationHelper.getChanel3otification(titulo,mensaje);
        mNotificationHelper.getManager().notify(3,nb.build());

    }

    public void updateTimeText(Calendar c){
        String  time="";
        //en esta  variable se guarda la hora en que va va sonar la alarma de manera de string para poderla mostrar
        time += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

    }
    // este metodo inicia la alarma programada
    public  void startAlarm(Calendar c){
        AlarmManager alarmManager= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlertReciver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,1,intent,0);
        if (c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

    }
    // este metood canacxela la alarma programada
    public  void cancelAlarm(Calendar c){
        AlarmManager alarmManager= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent= new Intent(this,AlertReciver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.cancel(pendingIntent);

    }
    // este metodo crea o abre la base de datso donde va a estar alojada la infomacion del usuario si no enteindes algo checa los video que te mande sobre sqillite
    public  void crearBbase(Usario usario){
        int  ids=2;
        boolean band= true;

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Expi", MODE_PRIVATE, null);


            sqLiteDatabase.execSQL("Create table if not exists usuario (id int(10))");
            Cursor c = sqLiteDatabase.rawQuery("Select * from usuario", null);
            int idC = c.getColumnIndex("id");
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

           if (c!=null){
               Toast.makeText(getApplicationContext(), "hay usuario"+String .valueOf(c.getInt(idC)), Toast.LENGTH_LONG).show();
           }else{
               Toast.makeText(getApplicationContext(), "no hay usuario", Toast.LENGTH_LONG).show();
           }


        }catch(Exception e){
            e.printStackTrace();

            // sqLiteDatabase.execSQL("insert into usuario (nombre,  apellidoPaterno, apellidoMaterno,correo, telefono, noTarjeta,id ) values(" + "'Luis','Escobar','Valdes','sitolui@outlook.com',7227022978,7894561230,ids)");
            //Toast.makeText(getApplicationContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }
// este metodk cera un usaurio en base a los datos que hay en la base de datos de sqlite, algo que tendiar que hacer es validar esto para saber si hay una sesion iniciada o no para lo del login o abra directamente el navegador
    public void crearUsuario(){
        int aux=0;
        try {
            sqLiteDatabase = this.openOrCreateDatabase("Usuarios", MODE_PRIVATE, null);
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
                usuario=usuarioaux;
                Log.i("usuarios",usuario.getNombre() + usuario.getApPaterno());
                break;
                   }
                c.moveToNext();
                aux++;

                }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void insertarUsuario(int ida){
        int aux=0;
        try {
            sqLiteDatabase = this.openOrCreateDatabase("Expi", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("Create table if not exists usuario (id int(10))");
            sqLiteDatabase.execSQL("insert into usuario (id ) values(" + "ida)");




        }catch (Exception e){
            e.printStackTrace();
        }
    }


    }



