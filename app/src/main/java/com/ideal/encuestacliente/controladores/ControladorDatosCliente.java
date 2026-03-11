package com.ideal.encuestacliente.controladores;

import android.content.Context;

import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.tablas.BaseDaoEncuesta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ControladorDatosCliente {

    public static int obtieneIdAutopistas(String nombreAutopista, Context contexto)
    {
        int idAutopista = 0;
        BaseDaoEncuesta con = new BaseDaoEncuesta(contexto);
        List listaAutopistas = con.consultaIdAutopista(nombreAutopista);

        for(Object datosAutpista: listaAutopistas){
            Esatis_Autopistas elementos=(Esatis_Autopistas) datosAutpista;
            idAutopista = elementos.getIdAutopistaSQL();
        }
        return idAutopista;
    }

    public static String obtieneNombreAutopista(int idAutopista, Context contexto){
        String nombreAutopista = "";
        BaseDaoEncuesta con = new BaseDaoEncuesta(contexto);
        List listaAutopistas = con.consultaIdAutopista(idAutopista);

        for(Object datosAutpista: listaAutopistas){
            Esatis_Autopistas elementos=(Esatis_Autopistas) datosAutpista;
            nombreAutopista = elementos.getNombreAutopista();
        }

       return  nombreAutopista;
    }

    public static int obtieneIdUltimaEncuesta(Context contexto){
        int idEncuestaSync = 0;
        BaseDaoEncuesta con = new BaseDaoEncuesta(contexto);
        List listaDatosClientesUltima = con.consultaDatosCliente();

        for(Object infoCliente: listaDatosClientesUltima){
            Esatis_Cliente elem= (Esatis_Cliente) infoCliente;
            idEncuestaSync = elem.getIdEncuesta();
        }

        return  idEncuestaSync;
    }

    public static int obtieneIdUltimaPregunta(Context contexto){
        int idPreguntaSync = 0;
        BaseDaoEncuesta con = new BaseDaoEncuesta(contexto);
        List listaDatosPreguntasUltima = con.consultaPreguntasCliente();

        for(Object infoCliente: listaDatosPreguntasUltima){
            Esatis_Encuesta elem= (Esatis_Encuesta) infoCliente;
            idPreguntaSync = elem.getIdPregunta();
        }

        return  idPreguntaSync;
    }


}
