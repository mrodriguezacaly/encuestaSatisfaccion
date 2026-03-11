package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_FrecuenciaHelper;
import com.ideal.encuestacliente.dataaccess.Esatis_PreguntasHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_PreguntasSync extends BaseSync
{
    public Esatis_PreguntasSync(){
        super(new Esatis_PreguntasHelper(), Constantes.TABLA_CATALOGO_PREGUNTAS, "");
    }
}
