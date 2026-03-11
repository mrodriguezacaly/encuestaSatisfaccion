package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_PreguntasHelper;
import com.ideal.encuestacliente.dataaccess.Esatis_ValoracionHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_ValoracionSync extends BaseSync
{
    public Esatis_ValoracionSync(){
        super(new Esatis_ValoracionHelper(), Constantes.TABLA_CATALOGO_VALORACION, "");
    }
}
