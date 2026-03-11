package com.ideal.encuestacliente.tablas;

import com.ideal.encuestacliente.configuracion.Constantes;

public class Tabla_usuario extends BaseTabla {
    public static final String TABLA = "Usuario" ;


    public static final class campos
    {
        public static final  String idUsuario       = "idUsuario";
        public static final  String nombreUsuario   = "nombreUsuario";
        public static final  String password        = "password";
    }
    public static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TABLA
            + "("
            +    campos.idUsuario           + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +    campos.nombreUsuario       + " TEXT,"
            +    campos.password            + " TEXT,"
            +    Constantes.genericos
            +  ")" ;

    public Tabla_usuario() {
        super(TABLA, CREATE);
    }
}
