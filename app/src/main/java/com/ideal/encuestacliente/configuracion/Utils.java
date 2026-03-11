package com.ideal.encuestacliente.configuracion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ideal.encuestacliente.BuildConfig;
import com.ideal.encuestacliente.R;


import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class Utils
{
    public static void syncFailMessage(TextView textView) {
        textView.setTextColor(Color.RED);
    }
    public static void syncSuccessMessage(TextView textView)
    {
        textView.setText(textView.getText().toString()  + " " + "\u2713");
    }
    public static void mostrarMensaje(TextView textView){
        if(textView.getVisibility() == View.INVISIBLE)
            textView.setVisibility(View.VISIBLE);
    }
    public static void ocultarMensaje(TextView textView){
        if(textView.getVisibility() == View.VISIBLE)
            textView.setVisibility(View.INVISIBLE);
    }
    public static void mostrarTextInputLayout(TextInputLayout textInputLayout){
        if(textInputLayout.getVisibility() == View.INVISIBLE)
            textInputLayout.setVisibility(View.VISIBLE);
    }
    public static void mostrarTextInput(TextInputLayout textInputLayout){
        if(textInputLayout.getVisibility() == View.GONE)
            textInputLayout.setVisibility(View.VISIBLE);
    }
    public static void ocultarTextInputLayout(TextInputLayout textInputLayout){

        if(textInputLayout.getVisibility() == View.VISIBLE)
            textInputLayout.setVisibility(View.INVISIBLE);
    }
    public static void ocultarTextInput(TextInputLayout textInputLayout){

        if(textInputLayout.getVisibility() == View.VISIBLE)
            textInputLayout.setVisibility(View.GONE);
    }


    public static void  Toast(Context context,String mensaje){
        Toast toast = Toast.makeText(context.getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
       // Toast.makeText(context.getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
    public static void limpiarTextview(TextView textView){
        textView.setText("");
    }
    public static void mostrarLinearLayout(LinearLayout linearLayout)
    {
        if(linearLayout.getVisibility() == View.INVISIBLE || linearLayout.getVisibility() == View.GONE)
            linearLayout.setVisibility(View.VISIBLE);
    }
    public static void ocultarLinearLayout(LinearLayout linearLayout)
    {
        if(linearLayout.getVisibility() == View.VISIBLE)
            linearLayout.setVisibility(View.GONE);
    }



    public static int obtenerTamañoLinear(LinearLayout linearLayout){
        int alto  =  linearLayout.getHeight();
        return alto;
    }
    public static void maxHieghtLinearLayout(LinearLayout linearLayout)
    {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            //params.height = 1150;
            //params.width  = 3000;
            linearLayout.setLayoutParams(params);
    }

    public static void mostrarLinearAtencion(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //params.height = 125;
        linearLayout.setLayoutParams(params);
    }
    public static void ocultarLinearAtencion(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //params.height = 2;
        linearLayout.setLayoutParams(params);
    }

    public static void mostrarSpinner(Spinner spinner)
    {
        spinner.setVisibility(View.VISIBLE);
    }

    public static void minHeightLinearLayout(LinearLayout linearLayout)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = 20;
        params.width  = 3000;
        linearLayout.setLayoutParams(params);
    }
    public static void mostrarLinearLayoutAseguradora(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //params.height = 300;
        linearLayout.setLayoutParams(params);
    }
    public static void ocultarLinearLayoutAseguradora(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //params.height = 30;
        linearLayout.setLayoutParams(params);
    }

    public static void ocultarPasajeros(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 2;
        linearLayout.setLayoutParams(params);
    }
    public static void mostrarPasajeros(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 1000;
        linearLayout.setLayoutParams(params);
    }



    public static void mostrarLinearLayoutDecesosLesionados(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        linearLayout.setLayoutParams(params);
    }
    public static void ocultarLinearLayoutDecesosLesionados(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 30;
        linearLayout.setLayoutParams(params);
    }
    public static void mostrarLinearLayoutDeclaracion(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //params.height = 300;
        linearLayout.setLayoutParams(params);
    }
    public static void ocultarLinearLayoutDeclaracion(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        //params.height = 30;
        linearLayout.setLayoutParams(params);
    }


    public static void mostrarLinearLayoutFotografias(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 100;
        linearLayout.setLayoutParams(params);
    }
    public static void ocultarLinearLayoutFotografias(LinearLayout linearLayout)
    {
        linearLayout.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = 1;
        linearLayout.setLayoutParams(params);
    }
    public static boolean validaCamposCompletosReporteSiniestros(TextInputEditText[] editTexts, Spinner[] spinners){
           return (validaTextInputEditTextsCompletos(editTexts) && validaSpinnersCompletos(spinners)) ? true : false;
    }
    public static boolean validaCamposCompletosConductores(TextInputEditText[] editTexts, Spinner[] spinners){
        return (validaTextInputEditTextsCompletos(editTexts) && validaSpinnersCompletos(spinners)) ? true : false;
    }
    public static boolean validaCamposCompletosVehiculo(TextInputEditText[] editTexts, Spinner[] spinners){
        return (validaTextInputEditTextsCompletos(editTexts) && validaSpinnersCompletos(spinners)) ? true : false;
    }

    public static boolean validaTextInputEditTextsCompletos(TextInputEditText[] textInputEditTexts){
        try {

            for (int i = 0; i < textInputEditTexts.length; i++) {
                if (textInputEditTexts[i].getText().toString().equals(""))
                    return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    public static boolean validaSpinnersCompletos(Spinner[] spinners){
        try{
            for(int i = 0;i < spinners.length;i++){
                if(spinners[i].getSelectedItem().toString().equals("")){
                    return false;
                }
            }
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public static void habilitarBoton(MaterialButton materialButton){
        materialButton.setEnabled(true);
    }
    public static void dehabilitarBoton(MaterialButton materialButton){
        materialButton.setEnabled(false);
    }


    public  static  String obtieneCadena(String cadena){
        String cadenaCompleta =  "";
        char[] caracteres =  cadena.toCharArray();
        for(int i = 0;i <  caracteres.length;i++)
        {
            String caracter =  String.valueOf(caracteres[i]);
            if(!caracter.equals(" ") ) {
                cadenaCompleta += caracter;
            }
            else{
                break;
            }
        }
        return cadenaCompleta;
    }

/*    public static String nomFormato(){

        ArrayList<Rsin_Versiones> rsin_versiones =
                ControladorReporteSiniestros.obtenerVersion();

        String formatoNonbre  = "F-PST-006-J";

        for(final Rsin_Versiones rsin_version:rsin_versiones)
        {
            formatoNonbre = rsin_version.getAcronimoVersion();
        }

        return formatoNonbre;
    }

    public static String nomVersion(){

        ArrayList<Rsin_Versiones> rsin_versiones =
                ControladorReporteSiniestros.obtenerVersion();

        String nomVersion  = "";

        for(final Rsin_Versiones rsin_version:rsin_versiones)
        {
            nomVersion = rsin_version.getNombreVersionMovil();
        }

        return nomVersion;
    }*/

    public static void Spinner(Spinner spinner, String[] datosSpinner, Context contexto){
        spinner.setAdapter(null);
        SpinnerControl spinnerControl = new SpinnerControl(contexto);
        ArrayAdapter<String> adaptador = spinnerControl.obtenerAdaptador(datosSpinner);
        spinner.setAdapter(adaptador);

    }
    public static void sincronizaciónCorrecta(Context context ,LinearLayout linearLayout, TextView textView, GifImageView gifImageView){

        //String texto = textView.getText().toString();
        String texto = textView.getText().toString() + " " + "\u2713";
        textView.setText("");
        int color = ContextCompat.getColor(context,R.color.colorPrimary);
        linearLayout.setBackgroundColor(color);
        textView.setTextColor(Color.WHITE);
        textView.setText(texto);
        gifImageView.setVisibility(View.INVISIBLE);
    }


    public static void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void disableSpiner(Spinner spiner) {
        spiner.setFocusable(false);
        spiner.setEnabled(false);
        spiner.setBackgroundColor(Color.TRANSPARENT);
    }

    public static void disableRadio(RadioButton radio) {
        radio.setFocusable(false);
        radio.setEnabled(false);
        radio.setBackgroundColor(Color.TRANSPARENT);
    }

    public static Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static  String invertirFecha(String fecha){
        String GUION="-";
        String nuevaFecha = "";
        String[] caracteres = fecha.split(GUION);
        nuevaFecha =  caracteres[2]+GUION+caracteres[1]+GUION+caracteres[0];
        return  nuevaFecha;
    }

    /**
     * Ajusta los márgenes para evitar Edge-to-Edge en las vistas (Android 15+).
     *
     * @param view Vista desde donde se llama la función para ajustar los márgenes.
     */
    public static void setMarginInsets(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.topMargin = insets.top;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    /**
     * Obtain JSON string from object
     *
     * @param data Object to serialize
     * @return JSON string from Object
     */
    public static <T> String serialize(T data) {
        Gson gson = new Gson();

        String json = null;
        try {
            json = gson.toJson(data);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * Obtain Object from JSON string
     *
     * @param json      JSON string to convert to Object
     * @param typeClass Class of the Object to convert
     * @return Object from JSON string
     */
    public static <T> T deserialize(String json, Class<T> typeClass) {
        Gson gson = new Gson();

        T data = null;
        try {
            data = gson.fromJson(json, typeClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getDatetimeAndEnvironment(Context context) {
        if (BuildConfig.DEBUG) {
            return context.getString(R.string.show_datetime_app_version, "qa");
        } else {
            return context.getString(R.string.show_datetime_app_version, "prod");
        }
    }

    /**
     * Checks whether the current Android version is 10 (Android Q) or higher.
     *
     * @return {@code true} if the current Android version is 10 or higher, {@code false} otherwise.
     */
    public static boolean isAndroid10OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * Checks whether the current Android version is 11 (Android R) or higher.
     *
     * @return {@code true} if the current Android version is 11 or higher, {@code false} otherwise.
     */
    public static boolean isAndroid11OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    /**
     * Checks whether the current Android version is 12 (Android S) or higher.
     *
     * @return {@code true} if the current Android version is 12 or higher, {@code false} otherwise.
     */
    public static boolean isAndroid12OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    /**
     * Checks whether the current Android version is 13 (Android Tiramisu) or higher.
     *
     * @return {@code true} if the current Android version is 13 or higher, {@code false} otherwise.
     */
    public static boolean isAndroid13OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

}
