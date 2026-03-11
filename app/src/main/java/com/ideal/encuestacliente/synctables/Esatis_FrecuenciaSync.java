package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_FormaPagoHelper;
import com.ideal.encuestacliente.dataaccess.Esatis_FrecuenciaHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_FrecuenciaSync extends BaseSync
{
    public Esatis_FrecuenciaSync(){
        super(new Esatis_FrecuenciaHelper(), Constantes.TABLA_CATALOGO_FRECUENCIA, "");
    }
}
