package com.ideal.encuestacliente.synctables;

import com.ideal.encuestacliente.configuracion.Constantes;
import com.ideal.encuestacliente.dataaccess.UsuariosHelper;
import com.ideal.encuestacliente.sincronizacion.BaseSync;

public class UsuariosSync extends BaseSync {

    public UsuariosSync() {
        super(new UsuariosHelper(), Constantes.TABLA_USUARIOS, "");
    }
}
