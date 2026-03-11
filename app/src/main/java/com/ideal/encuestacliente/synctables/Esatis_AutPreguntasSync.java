package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_AutPreguntasHelper;
import com.ideal.encuestacliente.dataaccess.Esatis_PreguntasHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_AutPreguntasSync extends BaseSync
{
    public Esatis_AutPreguntasSync(){
        super(new Esatis_AutPreguntasHelper(), Constantes.TABLA_CATALOGO_AUTOPISTA_PREGUNTA, "");
    }
}
