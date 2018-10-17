package com.example.sitol.appexpi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Historial.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Historial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Historial extends android.app.Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, AdapterView.OnItemSelectedListener {
    static Object detalles[]= null;
    static int n;
    ProgressDialog pDialog;
    ObjHistorial cat;
    private ArrayList<ObjHistorial> instList= new ArrayList<ObjHistorial>();
    List<Integer> ids = new ArrayList<Integer>();

    private String URL_LISTA_HISTORIAL ="http://192.168.2.8/BASEDATOS/consultarHistorial.php?id=2";
    View vista;
    ListView listView;
    static ArrayList<String> dat= new ArrayList<>();
   /* static DatosLista datos[]= new DatosLista[]{
            new DatosLista("Plazas Outlet Lerma","25/10/17", R.drawable.lerma,null),
            new DatosLista("Galerias Toluca","10/09/17", R.drawable.toluca,null),
            new DatosLista("Plaza Sendero Toluca","01/09/17", R.drawable.sendero,null),
            new DatosLista("Galerias Metepec","15/08/17", R.drawable.metepc,null)
    };*/
    // cree un arreglo de tipo datos lista
    static DatosLista hist[];
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Historial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Historial.
     */
    // TODO: Rename and change types and number of parameters
    public static Historial newInstance(String param1, String param2) {
        Historial fragment = new Historial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista= inflater.inflate(R.layout.fragment_historial, container, false);
        listView=vista.findViewById(R.id.listOpc);
        new GetInstitucion().execute();
        // esto lo comente para ver quitar los datos por defecto que le habia metido
        /*
        AdaptadorTit adaptadorTit= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adaptadorTit = new AdaptadorTit(getContext(),datos);
        }
        listView.setAdapter(adaptadorTit);
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                n=i;
                dat.clear();
                // en esta parte al dar cli se meten en un arraylist, los dtaos que van en los detalles
                dat.add(String.valueOf(hist[i].getLista().getTabase()));
                dat.add(String.valueOf(hist[i].getLista().getTaagregado()));
                dat.add(hist[i].getLista().getTiempo());
                dat.add(String.valueOf(hist[i].getLista().getTotal()));
                dat.add("pagado");
                dat.add(String.valueOf(hist[i].getLista().getTotal()));
                dat.add(String.valueOf(hist[i].getLista().getTotal()));
                dat.add(hist[i].getTitulo());
                dat.add(hist[i].getSubtitulo());

                Intent intent = new Intent(getActivity().getApplicationContext(),Detalles.class);
                startActivity(intent);
            }
        });
        return vista; }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    class AdaptadorTit extends ArrayAdapter<DatosLista> {
 // esta es una clase adapradora que me permite personalizar el listview
        public AdaptadorTit(Context context, DatosLista [] datos){
            super(context,R.layout.item_list,datos);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater=LayoutInflater.from(getContext());
            View item= inflater.inflate(R.layout.item_list,null);

            TextView lblT=(TextView)item.findViewById(R.id.lblT);
            lblT.setText("Lugar: "+hist[position].getTitulo());

            TextView lblst=(TextView)item.findViewById(R.id.lblst);
            lblst.setText("Fecha: "+hist[position].getSubtitulo());

            ImageView imagen=(ImageView)item.findViewById(R.id.foto);
            imagen.setImageResource(hist[position].getImagen());
            return (item);
        }
    }

    private class GetInstitucion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pDialog = new ProgressDialog(getContext());
            }
            pDialog.setMessage("Obtencion El Historial");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_LISTA_HISTORIAL, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray institu = jsonObj.getJSONArray("historial");

                        for (int i = 0; i < institu.length(); i++) {
                            JSONObject catObj = (JSONObject) institu.get(i);
                            cat = new ObjHistorial(catObj.getString("tiempo"),catObj.getString("fechaentrada"), catObj.getString("fechasalida"),
                                    catObj.getString("notarjeta"),catObj.getString("tiempobase"),catObj.getString("tiempoagrado"),catObj.getString("nombreestacionamiento"),
                                    catObj.getString("nombre"),catObj.getDouble("total"),catObj.getDouble("tarifabase"),catObj.getDouble("tarifatiempoagregado"),
                                    catObj.getInt("estadoSer"));
                            instList.add(cat);
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
            pDialog.hide();
                ///con esto me llenas los datos de cada posicion en mi historial
               llenarHistorial();
               //es mi objeto daptdaor el cual me permite adaptar lo que tengo en un arreglo y mostrarlo en listview
                AdaptadorTit adaptadorTit= null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    /// aqui se mete el arreglo con los datos para poder adaptarlo al list view
                    adaptadorTit = new AdaptadorTit(getContext(),hist);
    //se adapta el listview con el objeto adaptador
                listView.setAdapter(adaptadorTit);
            }

        }
    }
public void llenarHistorial(){
        // con esto se llena los datos del hitorial
        DatosLista dato;
        int aux=0;
        hist= new DatosLista[instList.size()];
        for (int i=0;i<instList.size(); i++){
            if (instList.get(i).estacionamiento.trim().equals("Plazas Outlet")){
                aux=R.drawable.lerma;
            }else if (instList.get(i).estacionamiento.trim().equals(
                    "Galerias Metepec")){
                aux=R.drawable.metepc;
            }else if (instList.get(i).estacionamiento.trim().equals(
                    "Galerias Toluca")){
                aux=R.drawable.toluca;
            }else if (instList.get(i).estacionamiento.trim().equals(
                    "Plaza Sendero")){
                aux=R.drawable.sendero;
            }

        //el primer paramatro se llama titulo en el objeto y ahi va el nombre del estacionamiento, el segudo parametro se llama subtitulo y va la fecha que se hizo el servicoo, el segun parametro va la imagen
            dato =  new DatosLista(instList.get(i).estacionamiento,instList.get(i).fentrada,aux,instList.get(i));

            hist[i]=dato;
        }
}
}
