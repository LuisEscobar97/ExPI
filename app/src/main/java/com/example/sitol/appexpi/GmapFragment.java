package com.example.sitol.appexpi;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GmapFragment extends Fragment implements OnMapReadyCallback, Response.Listener<JSONArray>,Response.ErrorListener {
    private GoogleMap mMap;
     static Object listaEsta[]=null;
    ProgressDialog progressDialog;
    RequestQueue request;
    JsonArrayRequest jsonObjectRequest;
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);


    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            request= Volley.newRequestQueue(getContext());
        }

    }
    // este metodo era para caragar los datos pero nunca funciono como queise porque no sabia manerar manejra bien lo json
public void CargarDatos(){

        String url="http://192.168.0.16/BASEDATOS/ConsultaServicios.php";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                }
            }
        }
        );
      // jsonObjectRequest= new JsonArrayRequest(Request.Method.GET,url,null,this,this);
       request.add(stringRequest);
}

    @Override
    public void onErrorResponse(VolleyError error) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(getContext(),"error"+error,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResponse(JSONArray response) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(getContext(),"hola"+response,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;

            NavegadorPrincipal.locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.clear();
                        // aqui se mete el mototo para repintar los estacionamineto ya que cada vez que cambia la poscion del usuario va a estar limpiando el mapa y pintando su ubicacion actual asi que se vuelven a pintar
                        estacionamientos(mMap);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Positicn").flat(true));


                    }
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


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    NavegadorPrincipal.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, NavegadorPrincipal.locationListener);
                } else {
                    NavegadorPrincipal.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, NavegadorPrincipal.locationListener);
                    Location ultimaPosicion = NavegadorPrincipal.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    LatLng sydney = new LatLng(ultimaPosicion.getLatitude(), ultimaPosicion.getLongitude());
                    mMap.clear();
// este metodo me perimte meter la latitu y la logintud juto con los nombres de los estacionbamiento en el mapa
                    estacionamientos(mMap);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Your Position").flat(true));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }

        }catch(Exception e){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(getContext(),"No disponible",Toast.LENGTH_LONG).show();
            }
        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                        NavegadorPrincipal.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, NavegadorPrincipal.locationListener);
                    }
                }
            }
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public static void estacionamientos(GoogleMap mMap) {
         // en esta parte se meto valores por defecto de los estacionamimeto tendrias que bajar la latitud y la longitud de la base de datos y solo irlos agregando
        // lo agao creando un array list con la latitu, la longitud y el nombre de cada estaciomaniento una vez hecho eso, lo insertio en un arreglo de objetos dinamico con el cual es lo que me perite meter esa informacion en el mapa
        ArrayList<String> es1 = new ArrayList<>();
        ArrayList<String> es2 = new ArrayList<>();
        ArrayList<String> es3 = new ArrayList<>();
        ArrayList<String> es4 = new ArrayList<>();

        es1.add("19.2823608");
        es1.add("-99.5030706");
        es1.add("Plazas Outlet ");

        es2.add("19.2896841");
        es2.add("-99.5562698");
        es2.add("Plaza Sendero ");

        es3.add("19.2903221");
        es3.add("-99.6244606");
        es3.add("Galerias Toluca ");

        es4.add("19.2584464");
        es4.add("-99.6205986");
        es4.add("Galerias Metepec ");
        listaEsta = NavegadorPrincipal.inserta(listaEsta, es1);
        listaEsta = NavegadorPrincipal.inserta(listaEsta, es2);
        listaEsta = NavegadorPrincipal.inserta(listaEsta, es3);
        listaEsta = NavegadorPrincipal.inserta(listaEsta, es4);

        for (int i = 0; i < listaEsta.length; i++) {
            ArrayList<String> aux = (ArrayList<String>) listaEsta[i];
            Double lat = Double.parseDouble(aux.get(0));
            Double lon = Double.parseDouble(aux.get(1));
            LatLng sydney = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(sydney).title(aux.get(2)));
        }

    }

    public static Object[] inserta(Object[] arr, JSONArray obj) {
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
}
