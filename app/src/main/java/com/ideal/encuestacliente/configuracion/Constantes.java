package com.ideal.encuestacliente.configuracion;

public class Constantes {


    public static final String TABLA_AUTOPISTAS = "Rsin_Autopistas";
    public static final String AUTOPISTAS = "Autopistas";
    public static final String WS_PLAZA_COBRO = "PlazasCobro";
    public static final String TABLA_PLAZA_COBRO = "Esatis_PlazaCobro";
    public static final String TABLA_FORMA_PAGO = "Esatis_Forma_Pago";
    public static final String TABLA_CATALOGO_FRECUENCIA = "Esatis_catalogo_frecuencia";
    public static final String TABLA_CATALOGO_PREGUNTAS = "Esatis_catalogo_preguntas";
    public static final String TABLA_CATALOGO_VALORACION = "Esatis_Valoracion";
    public static final String TABLA_CATALOGO_AUTOPISTA_PREGUNTA = "Esatis_autopista_pregunta";
    public static final String TABLA_CATALOGO_UBICACIONES = "Esatis_Ubicaciones";

    public static final String TABLA_CATALOGO_DATOS_CLIENTE = "Esatis_Datos_Cliente";
    public static final String TABLA_CATALOGO_ENCUESTA = "Esatis_Encuesta";

    public static final String TABLA_USUARIOS = "Usuarios";

    public static final String USER_PREF = "USER_PREF";

    public static final String USER_PREF_DATA = "USER_PREF_DATA";

    public static final String APPLICATION_ID = "com.ideal.encuestaclientes";

    public static final String TELEPEAJE = "Telepeaje";

    public static final class campos
    {
        public static final String idAutopista         = "idAutopista";
        public static final String idAutopistaSQL         = "idAutopistaSQL";
        public static final String nombreAutopista     = "nombreAutopista";
        public static final String acronimoAutopista   = "acronimoAutopista";
        public static final String claveAutopista   = "claveAutopista";
        public static final String idOrden   = "idOrden";

    }

    public static final String usuarioCreacion     = "usuarioCreacion";
    public static final String usuarioModificacion = "usuarioModificacion";
    public static final String usuarioEliminacion  = "usuarioEliminacion";
    public static final String fechaCreacion       = "fechaCreacion";
    public static final String fechaModificacion   = "fechaModificacion";
    public static final String fechaEliminacion    = "fechaEliminacion";

    public static final String genericos =       usuarioCreacion     + " TEXT,"
            +    usuarioModificacion + " TEXT,"
            +    usuarioEliminacion  + " TEXT,"
            +    fechaCreacion       + " TEXT,"
            +    fechaModificacion   + " TEXT,"
            +    fechaEliminacion    + " TEXT ";

    public static final int idUsuario         =  4;
    public static final String nombreUsuario  =  "invitado_demo";

    public static final class camposDatosCliente
    {
        public static final String idAutopista         = "idAutopista";
        public static final String idPlazaCobro     = "idPlazaCobro";
        public static final String idUbicacion     = "idUbicacion";
        public static final String UbicacionDesc     = "UbicacionDesc";
        public static final String fecha   = "fecha";
        public static final String encuestador   = "encuestador";
        public static final String origen         = "origen";
        public static final String destino     = "destino";
        public static final String idFormaPago   = "idFormaPago";
        public static final String idFrecuenciaUso   = "idFrecuenciaUso";
        public static final String observacionesCliente   = "observaciones";
        public static final String latitud   = "latitud";
        public static final String longitud   = "longitud";
        public static final String altitud   = "altitud";

    }

    public static final class camposEncuestaClientes
    {
        public static final String idPregunta         = "idPregunta";
        public static final String idEncuesta     = "idEncuesta";
        public static final String idPreguntaCatalogo   = "idPreguntaCatalogo";
        public static final String idValoracion   = "idValoracion";
        public static final String observaciones         = "observaciones";
        public static final String clave         = "clave";

    }

    public static final class camposFormaPago
    {
        public static final String idFormaPago         = "idFormaPago";
        public static final String formaPago     = "formaPago";

    }

    public static final class camposFrecuencia
    {
        public static final String idFrecuenciaUso         = "idFrecuenciaUso";
        public static final String descFrecuencia     = "descFrecuencia";

    }

    public static final class camposValoracion
    {
        public static final String idValoracion         = "idValoracion";
        public static final String descValoraciones     = "descValoraciones";

    }

    public static final class camposUbicacion
    {
        public static final String idUbicacion         = "idUbicacion";
        public static final String ubicacion     = "ubicacion";

    }

    public static final class camposPreguntas
    {
        public static final String idPreguntaCatalogo         = "idPreguntaCatalogo";
        public static final String descPregunta     = "descPregunta";
        public static final String descObservaciones     = "descObservaciones";
    }


    public static final class camposAutopistasPreguntas
    {
        public static final String idAutopistaPregunta         = "idAutopistaPregunta";
        public static final String idAutopista     = "idAutopista";
        public static final String idPreguntaCatalogo     = "idPreguntaCatalogo";
    }

    public static final class camposPlazaCobro
    {
        public static final String idPlazaCobro         = "idPlazaCobro";
        public static final String idPlazaCobroSQL         = "idPlazaCobroSQL";
        public static final String idAutopista     = "idAutopista";
        public static final String plazaCobro     = "plazaCobro";
        public static final String razonSocialAutopista     = "razonSocialAutopista";
    }

    public static class camposUsuarios {
        public static String idUsuario                    = "idUsuario";
        public static String nombreUsuario                = "nombreUsuario";
        public static String nombre                      = "nombre";
        public static String idPuesto        = "idPuesto";
        public static String puesto          = "puesto";
    }
}
