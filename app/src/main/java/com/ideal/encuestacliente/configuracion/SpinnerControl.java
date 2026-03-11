package com.ideal.encuestacliente.configuracion;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;


import com.ideal.encuestacliente.R;

import java.util.ArrayList;

public class SpinnerControl extends Application
{
    private Context context;
    public SpinnerControl(Context context){
        this.context = context;
    }
    public ArrayAdapter<String> obtenerAdaptador(String[] array){
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(context, R.layout.rowescuelas,array);
        adaptador.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adaptador.notifyDataSetChanged();
        return adaptador;
    }

    public static  void mostrarSpinner(Spinner spinner) {

           if(spinner.getVisibility() == View.INVISIBLE) {
               spinner.setVisibility(View.VISIBLE);
           }
    }
    public static  void ocultarSpinner(Spinner spinner) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        spinner.setLayoutParams(lp);

        if(spinner.getVisibility() == View.VISIBLE) {
            spinner.setVisibility(View.INVISIBLE);

        }
    }
    public static ArrayList<Integer> obtenerTamañoSpinner(Spinner spinner){
        ArrayList<Integer> tamañoSpinner = new ArrayList<>();
        int ancho =  spinner.getWidth();
        int alto  = spinner.getHeight();

        tamañoSpinner.add(ancho);
        tamañoSpinner.add(alto);

        return tamañoSpinner;
    }
    public static void redimensionaSpinner(Spinner spinner){

         ArrayList<Integer> tamanioSpinner  = obtenerTamañoSpinner(spinner);
         if( (tamanioSpinner.get(0) == 320 && tamanioSpinner.get(1) == 28) || (tamanioSpinner.get(0) == 0 && tamanioSpinner.get(1) == 0) ){
             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
             //params.height = 70;
             spinner.setLayoutParams(params);
         }
    }

}
