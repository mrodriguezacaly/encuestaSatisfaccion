package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.Esatis_FormaPagoHelper;
import com.ideal.encuestacliente.dataaccess.Rsin_AutopistaHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;


public class Esatis_FormaPagoSync extends BaseSync
{
    public Esatis_FormaPagoSync(){
        super(new Esatis_FormaPagoHelper(), Constantes.TABLA_FORMA_PAGO, "");
    }
}
