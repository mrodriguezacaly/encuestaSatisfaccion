package com.ideal.encuestacliente.sincronizacion;

import com.ideal.encuestacliente.BuildConfig;

public class Urls {
    /*
     *   La variable servidor nos permite conectarnos a QA o producción
     *   dependiendo de la terminación de la variable
     *   1. Para conectarse a QA utilize ActaConvenio_qa en la variable servidor
     *   2. Para conectarse a Producción utilize ActaConvenio en la variable servidor
     */
    private static final String servidor = "EncuestaSatisfaccion_qa";
    private static final String servidorAutenticacion = "Autenticacion_qa";

    /*
     * Esta URL apunta al webservice para descargar los catálogos de la aplicación móvil
     * */
    public static final String URL_BASE_WS_DOWN = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidor + "/api/Catalogos/";
    // public static final String URL_AUTENTICACION = "http://ideal.sigi.com.mx/wsFormatosSGI/"+servidorAutenticacion+"/api/AccesoFormatosSGI";
    public static final String URL_AUTENTICACION = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidorAutenticacion + "/api/AccesoFormatosSGI";

    private static final String servidorActa = "ActaConvenio";
    public static final String URL_BASE_WS_DOWN_AUT = "https://ideal.sigi.com.mx/wsFormatosSGI/" + servidorActa + "/api/Catalogos/";


}
