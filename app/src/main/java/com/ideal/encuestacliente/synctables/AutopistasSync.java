package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Rsin_AutopistaHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class AutopistasSync extends BaseSync
{
    public AutopistasSync(){
        super(new Rsin_AutopistaHelper(), Constantes.TABLA_AUTOPISTAS);
    }
}
