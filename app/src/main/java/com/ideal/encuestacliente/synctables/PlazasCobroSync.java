package com.ideal.encuestacliente.synctables;


import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.PlazasCobroHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;

public class PlazasCobroSync extends BaseSync {
    public PlazasCobroSync() {
        super(new PlazasCobroHelper(), Constantes.TABLA_PLAZA_COBRO, "");
    }
}
