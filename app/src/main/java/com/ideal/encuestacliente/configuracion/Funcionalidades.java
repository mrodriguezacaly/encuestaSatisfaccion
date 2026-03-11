package com.ideal.encuestacliente.configuracion;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.R;

import java.util.ArrayList;

public class Funcionalidades {
    public static void snackBar(LinearLayout layout,String mensaje){
        Snackbar.make(layout,mensaje, Snackbar.LENGTH_LONG).show();
    }

    public static ArrayAdapter<String> getAdapter(Context context, String[] arrayPlazasCobro){
        return new ArrayAdapter<>(context.getApplicationContext(),R.layout.custom_spinner,arrayPlazasCobro);
    }

    public static String getValueSpinner(Spinner spinner){
        return spinner.getSelectedItem().toString();
    }
    public static int obtenerPosicionSpinner(ArrayList<String> datos , String elementoBuscar ){
        for(int i = 0;i < datos.size();i++ ){
            if(datos.get(i).equals(elementoBuscar)){
                return  i;
            }
        }
        return  -1;
    }

    public static void mensajeSucces(String msg, TextView textView, LinearLayout linearLayout){
        linearLayout.setVisibility(View.VISIBLE);
        textView.setText(msg+"\u2713");
    }
    public static void mensajeFail(String msg, TextView textView){
        textView.setText(msg);
    }
    public static void attr(TextInputEditText textInputEditText, int sizeString){
        InputFilter[] filterArray = new InputFilter[2];
        filterArray[0] = new InputFilter.LengthFilter(sizeString);
        filterArray[1] = new InputFilter.AllCaps();
        textInputEditText.setFilters(filterArray);
    }
    public static  void cambiarIcono(Context context, FloatingActionButton button){
        button.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_salir));
    }
    public static void cambiarColorProgressBar(ProgressBar progressBar, Context context){
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(100);
        progressBar.getProgressDrawable().setColorFilter(
                Color.parseColor("#374454"), android.graphics.PorterDuff.Mode.SRC_IN);
    }
    public static void mostrarImageView(ImageView imageView){
        imageView.setVisibility(View.VISIBLE);
    }

}
