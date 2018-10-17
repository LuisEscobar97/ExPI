package com.example.sitol.appexpi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inicio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Inicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inicio extends android.app.Fragment {
   static TextView tarifaBase;
    static TextView tarifaBaseCon;
    static TextView tarifaFrac;
    static TextView tarifaFracCon;
    static TextView lugar;
    static TextView lugarCon;
    static TextView sbTotal;
    static TextView sbTotalCon;
    static TextView tiempo;
    static TextView tiempoCon;
    static TextView info;
    static Button iniciar;
    static ImageView barras;
    static View vista;
    Boolean ban =true;
    Button bottom;
    static int aux=0;
    static int aux1=0;
    static int aux2=0;
    static String  temp="0";
    static String  temp2="00";
    static String  temp3="00";
   static final Handler handler= new Handler();
    public  static  Boolean bandera= false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Inicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Inicio newInstance(String param1, String param2) {
        Inicio fragment = new Inicio();
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

        vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        tarifaBase=(TextView)vista.findViewById(R.id.tiempoBase);
        tarifaBaseCon=(TextView)vista.findViewById(R.id.tiempoBaseCon);
        tarifaFrac=(TextView)vista.findViewById(R.id.tiempoFrac);
        tarifaFracCon=(TextView)vista.findViewById(R.id.tiempoFracCon);
        lugar=(TextView)vista.findViewById(R.id.lugar);
        lugarCon=(TextView)vista.findViewById(R.id.lugarCon);
        sbTotal=(TextView)vista.findViewById(R.id.sbTotal);
        sbTotalCon=(TextView)vista.findViewById(R.id.sbTotalCon);
        tiempo=(TextView)vista.findViewById(R.id.teimpo);
        tiempoCon=(TextView)vista.findViewById(R.id.tiempoCon);
        info=(TextView)vista.findViewById(R.id.informacion);
        iniciar=(Button)vista.findViewById(R.id.inicio);
        barras= vista.findViewById(R.id.imBarras);
        generarCodigoQR(3,"luis",  132465);

iniciar.setVisibility(View.INVISIBLE);
                // con este metodo se genra el codigo QR que sus parametros son el id, nombre, y numero e tarjeta del susuario
                generarCodigoQR(1,"luis",  132465);

/* //con esta parte de codigo es para descargar el codigo de barras de una carpeta del servidor si es que al final decidimos utilizar el codigo de barras
                ImageDownloader task= new ImageDownloader();
                Bitmap codigo;

                try {
                // para descargar solo necesotas poner como parametro la direccion junto con el nombre de la imagen
                    codigo= task.execute("http://192.168.53.208/Codigos/1.png").get();
                    barras.setImageBitmap(codigo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                */
                if (bandera){
                    aux=0;
                    bandera= false;
                    // esto hace visibles los dtaos que salen en la pantalla principal cuado se supone hay un servicio activo

                    tarifaBase.setVisibility(View.VISIBLE);
                    tarifaBaseCon.setVisibility(View.VISIBLE);
                    tarifaFrac.setVisibility(View.VISIBLE);
                    tarifaFracCon.setVisibility(View.VISIBLE);
                    lugar.setVisibility(View.VISIBLE);
                    lugarCon.setVisibility(View.VISIBLE);
                    sbTotal.setVisibility(View.VISIBLE);
                    sbTotalCon.setVisibility(View.VISIBLE);
                    tiempo.setVisibility(View.VISIBLE);
                    tiempoCon.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    NavegadorPrincipal.servicio=true;
                    // hace visible el boton para programar alarmas
                    NavegadorPrincipal.botonAlarma.setVisibility(View.VISIBLE);

// esta parte es para que empieze a correr el crono metro, funciona bien, pero digamos que presionas varias veces el boton para activar
// el cronometro se vuelev loco, busca una forma en la cual aun cuadno se cierre la pliacion siga corriendo, y otra con la cual puedas para
// el hilo, ya que para esto se usa un hilo para que funcione o usar algun otro tipo de hilo
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {


                            handler.postDelayed(this,1000);
                            aux+=1;
                            if (aux>9){

                                if (aux==60){
                                    aux=0;
                                    aux1+=1;
                                    temp="0"+String.valueOf(aux);
                                    if (aux1>9){
                                        temp2=String.valueOf(aux1);
                                    }else {

                                        temp2="0"+String.valueOf(aux1);

                                    }

                                }else{
                                    temp=String.valueOf(aux);
                                }
                            }else{
                                temp="0"+String.valueOf(aux);
                            }
                            tiempoCon.setText(temp3+":"+temp2+":"+temp);
                        }
                    };
                    handler.post(run);

                }else{
                    aux=0;
                    aux1=0;
                    aux2=0;
                    bandera= true;
                    tarifaBase.setVisibility(View.INVISIBLE);
                    tarifaBaseCon.setVisibility(View.INVISIBLE);
                    tarifaFrac.setVisibility(View.INVISIBLE);
                    tarifaFracCon.setVisibility(View.INVISIBLE);
                    lugar.setVisibility(View.INVISIBLE);
                    lugarCon.setVisibility(View.INVISIBLE);
                    sbTotal.setVisibility(View.INVISIBLE);
                    sbTotalCon.setVisibility(View.INVISIBLE);
                    tiempo.setVisibility(View.INVISIBLE);
                    tiempoCon.setVisibility(View.INVISIBLE);
                    info.setVisibility(View.INVISIBLE);
                    NavegadorPrincipal.botonAlarma.setVisibility(View.INVISIBLE);
                    NavegadorPrincipal.servicio=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(getContext(),"Trasacción realizada con exito",Toast.LENGTH_LONG).show();
                    }


                }


         /*
        final Handler handler= new Handler();


        if (bandera){
            tarifaBase.setVisibility(View.VISIBLE);
            tarifaBaseCon.setVisibility(View.VISIBLE);
            tarifaFrac.setVisibility(View.VISIBLE);
            tarifaFracCon.setVisibility(View.VISIBLE);
            lugar.setVisibility(View.VISIBLE);
            lugarCon.setVisibility(View.VISIBLE);
            sbTotal.setVisibility(View.VISIBLE);
            sbTotalCon.setVisibility(View.VISIBLE);
            tiempo.setVisibility(View.VISIBLE);
            tiempoCon.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            Runnable run = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this,1000);
                    aux+=1;
                    if (aux>9){  temp=String.valueOf(aux);
                    }else{
                        temp="0"+String.valueOf(aux);}
                    tiempoCon.setText(temp);
                }
            };
            handler.post(run);
        }else{
            tarifaBase.setVisibility(View.INVISIBLE);
            tarifaBaseCon.setVisibility(View.INVISIBLE);
            tarifaFrac.setVisibility(View.INVISIBLE);
            tarifaFracCon.setVisibility(View.INVISIBLE);
            lugar.setVisibility(View.INVISIBLE);
            lugarCon.setVisibility(View.INVISIBLE);
            sbTotal.setVisibility(View.INVISIBLE);
            sbTotalCon.setVisibility(View.INVISIBLE);
            tiempo.setVisibility(View.INVISIBLE);
            tiempoCon.setVisibility(View.INVISIBLE);
            info.setVisibility(View.INVISIBLE);
        }

*/
/*
        bottom= vista.findViewById(R.id.button);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //startActivity(new Intent(getContext(),Map.class));
                }
            }
        });
*/
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
public static void activarServicio(Boolean bande){
    if (bande){
        aux=0;
        bandera= false;
        // esto hace visibles los dtaos que salen en la pantalla principal cuado se supone hay un servicio activo
        NavegadorPrincipal.control=true;
        tarifaBase.setVisibility(View.VISIBLE);
        tarifaBaseCon.setVisibility(View.VISIBLE);
        tarifaFrac.setVisibility(View.VISIBLE);
        tarifaFracCon.setVisibility(View.VISIBLE);
        lugar.setVisibility(View.VISIBLE);
        lugarCon.setVisibility(View.VISIBLE);
        sbTotal.setVisibility(View.VISIBLE);
        sbTotalCon.setVisibility(View.VISIBLE);
        tiempo.setVisibility(View.VISIBLE);
        tiempoCon.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);
        NavegadorPrincipal.servicio=true;
        // hace visible el boton para programar alarmas
        NavegadorPrincipal.botonAlarma.setVisibility(View.VISIBLE);

// esta parte es para que empieze a correr el crono metro, funciona bien, pero digamos que presionas varias veces el boton para activar
// el cronometro se vuelev loco, busca una forma en la cual aun cuadno se cierre la pliacion siga corriendo, y otra con la cual puedas para
// el hilo, ya que para esto se usa un hilo para que funcione o usar algun otro tipo de hilo
        Runnable run = new Runnable() {
            @Override
            public void run() {


                handler.postDelayed(this,1000);
                aux+=1;
                if (aux>9){

                    if (aux==60){
                        aux=0;
                        aux1+=1;
                        temp="0"+String.valueOf(aux);
                        if (aux1>9){
                            temp2=String.valueOf(aux1);
                        }else {

                            temp2="0"+String.valueOf(aux1);

                        }

                    }else{
                        temp=String.valueOf(aux);
                    }
                }else{
                    temp="0"+String.valueOf(aux);
                }
                tiempoCon.setText(temp3+":"+temp2+":"+temp);
            }
        };
        handler.post(run);

    }else{
        NavegadorPrincipal.control=false;
        aux=0;
        aux1=0;
        aux2=0;
        bandera= true;
        tarifaBase.setVisibility(View.INVISIBLE);
        tarifaBaseCon.setVisibility(View.INVISIBLE);
        tarifaFrac.setVisibility(View.INVISIBLE);
        tarifaFracCon.setVisibility(View.INVISIBLE);
        lugar.setVisibility(View.INVISIBLE);
        lugarCon.setVisibility(View.INVISIBLE);
        sbTotal.setVisibility(View.INVISIBLE);
        sbTotalCon.setVisibility(View.INVISIBLE);
        tiempo.setVisibility(View.INVISIBLE);
        tiempoCon.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);
        NavegadorPrincipal.botonAlarma.setVisibility(View.INVISIBLE);
        NavegadorPrincipal.servicio=false;




    }
}
    public class  ImageDownloader extends AsyncTask<String ,Void,Bitmap>{
// esta clase me permite descargar alguna imagen que este algun carpeta del servidor

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);

                HttpURLConnection connection =(HttpURLConnection)url.openConnection();

                connection.connect();

                InputStream inputStream= connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return  bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void generarCodigoQR(int id, String nombre, int noTarjeta){
// esta parte te genera el coigo qr
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            // los ultimos paramtroscon para el tamaño de la imagen, el primero en base a que informacion se hace el codigo qr
            BitMatrix bitMatrix= multiFormatWriter.encode(String.valueOf(id)+" " + nombre + " " + String.valueOf(noTarjeta), BarcodeFormat.QR_CODE,700,700);
            BarcodeEncoder barcodeEncoder= new BarcodeEncoder();

            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barras.setImageBitmap(bitmap);

        }catch (WriterException e) {
            e.printStackTrace();
        }
}
}
