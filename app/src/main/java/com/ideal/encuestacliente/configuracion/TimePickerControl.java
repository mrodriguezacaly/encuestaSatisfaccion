package com.ideal.encuestacliente.configuracion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ideal.encuestacliente.R;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerControl {


    private Context context;
    private String fechaArribo = "";
    private String fechaReporte ="";
    private String fechaSistema = "";

    public TimePickerControl(Context context) {
        this.context = context;
    }

    public void mostrarControlFechaAseguradora(final ConstraintLayout relativeLayout, final EditText edt){
        final String SEPARADOR = "-";
        final Calendar fecha = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        DatePickerDialog piker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anioTime, int mesTime, int diaTime) {

                fechaReporte = diaTime + SEPARADOR + (mesTime + 1) + SEPARADOR + anioTime;

                final int dia = fecha.get(Calendar.DAY_OF_MONTH);
                int mes = fecha.get(Calendar.MONTH);
                mes = mes + 1;
                final int year = fecha.get(Calendar.YEAR);
                String diaDosDigitos = dia+"";
                String mesDosDigitos = mes+"";

                if(dia<10){
                    diaDosDigitos = "0" + dia + "";
                }
                if(mes<10){
                    mesDosDigitos = "0" + mes + "";
                }

                fechaSistema = diaDosDigitos + SEPARADOR + (mesDosDigitos) + SEPARADOR + year;

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = format.parse(fechaReporte, new ParsePosition(0));
                Date date2 = format.parse(fechaSistema, new ParsePosition(0));

                Log.e("fechaDispositivo", date2+"");
                Log.e("fechaElegida", date1+"");
             //   Log.e("variacion", date1.compareTo(date2)+"");

                edt.setText(fechaSistema);

              //  mostrarControlTiempo(edt);

              /*  if (date1.compareTo(date2) <= 0) {
                    mostrarControlTiempo(edt);
                }else{
                    borrarFecha(edt);
                    Snackbar.make(relativeLayout, context.getString(R.string.mensajeHoraSuperior), Snackbar.LENGTH_LONG).show();
                }*/

            }
        }, fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DAY_OF_MONTH));
        piker.show();
    }


    public void mostrarControlTiempo(final EditText textInputEditText)
    {
        Calendar calendar = Calendar.getInstance();
        final int horaDia = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutoDia = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker timePicker, int hora, int minuto)
                    {


                        textInputEditText.setText(fechaSistema +" "+  completarMinutosHoras(hora) + ":" + completarMinutosHoras(minuto));
                    }
                },horaDia,minutoDia,android.text.format.DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    public String completarMinutosHoras(int minuto){
        String minutos = (minuto >= 0 && minuto <= 9) ? "0" + minuto : String.valueOf(minuto);
        return  minutos;
    }




}
