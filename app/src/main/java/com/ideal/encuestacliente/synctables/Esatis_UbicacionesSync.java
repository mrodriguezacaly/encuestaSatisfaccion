package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_UbicacionHelper;
import com.ideal.encuestacliente.dataaccess.Esatis_ValoracionHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_UbicacionesSync extends BaseSync
{
    public Esatis_UbicacionesSync(){
        super(new Esatis_UbicacionHelper(), Constantes.TABLA_CATALOGO_UBICACIONES, "");
    }
}
