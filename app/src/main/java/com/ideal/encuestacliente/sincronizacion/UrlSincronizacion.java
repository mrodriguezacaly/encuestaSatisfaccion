package com.ideal.encuestacliente.sincronizacion;


import com.ideal.encuestacliente.BuildConfig;

public class UrlSincronizacion {

    /*
     *   La variable servidor nos permite conectarnos a QA o producción
     *   dependiendo de la terminación de la variable
     *   1. Para conectarse a QA utilize wsAPIformatos_qa en la variable servidor
     *   2. Para conectarse a Producción utilize wsAPIformatos en la variable servidor
     * */
    private static final String servidor = "EncuestaSatisfaccion_qa";
    private static final String servidorCatalogos = "EncuestaSatisfaccion_qa";
    private static final String servidorAutopistas = "wsAPIformatos_qa";
    private static final String servidorCatalogosEncuesta = "EncuestaSatisfaccion_qa";
    private static final String servidor_up = "EncuestaSatisfaccion_qa";

    /*
     * Esta URL nos permite autenticar al usuario ingresado en el lógin en el servidio de Sharepoint
     */
    public static final String URL_SHAREPOINT = "https://ideal.sigi.com.mx/authws/Enviar/Credenciales";


    /*
     * Estas URL apuntan al webservice para subir la información capturada por los usuarios
     * */
    private static final String URL_BASE_WS = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidor_up + "/";
    //http://ideal.sigi.com.mx/wsFormatosSGI/EncuestaSatisfaccion_qa/api/DatosCliente

    public static final String URL_SINCRONIZACION_ESATIS_DATOS_CLIENTE = URL_BASE_WS + "api/DatosCliente";
    public static final String URL_SINCRONIZACION_ESATIS_ENCUESTA = URL_BASE_WS + "api/Encuesta";
    public static final String URL_ACCESO_USUARIO = URL_BASE_WS + "api/AccesoUsuarios";

    public static final String URL_BASE_WS_DOWN_PLAZA_COBRO = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidorCatalogosEncuesta + "/api/Catalogos/";

    /*
     * Esta URL apunta al webservice para descargar los catálogos de la aplicación móvil
     * */
    public static final String URL_BASE_WS_DOWN = "https://ideal.sigi.com.mx/" + servidor + "/api/webservice/";
    public static final String URL_BASE_WS_DOWN_CATALOGOS = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidorCatalogos + "/api/Catalogos/";
    //http://ideal.sigi.com.mx/wsFormatosSGI/EncuestaSatisfaccion_qa/api/Catalogos/Esatis_Forma_Pago

    public static final String URL_BASE_WS_DOWN_AUTOPISTAS = "https://ideal.sigi.com.mx/" + servidorAutopistas + "/api/webservice/";

    public static final String URL_BASE_WS_DOWN_AUTOPISTAS_ENCUESTA = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidor + "/api/Catalogos/";

    //http://ideal.sigi.com.mx/wsFormatosSGI/EncuestaSatisfaccion_qa/api/Catalogos/?tabla=Autopistas&Usuario=usuarioAlpe
    //Se agrega filtro de usuarios por autopistas
}
