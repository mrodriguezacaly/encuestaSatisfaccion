package com.ideal.encuestacliente.model;

public class Esatis_Cliente
{

    public int    idEncuesta;
    public int    idAutopista;
    public int    idPlazaCobro;
    public int    idUbicacion;
    public String UbicacionDesc;

    public String getUbicacionDesc() {
        return UbicacionDesc;
    }

    public void setUbicacionDesc(String ubicacionDesc) {
        UbicacionDesc = ubicacionDesc;
    }

    public int getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public double latitud;
    public double longitud;
    public double altitud;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String observaciones;

    public int getIdIndicadorSinc() {
        return idIndicadorSinc;
    }

    public void setIdIndicadorSinc(int idIndicadorSinc) {
        this.idIndicadorSinc = idIndicadorSinc;
    }

    public int    idIndicadorSinc;
    public String fecha;
    public String encuestador;
    public String origen;
    public String destino;
    public int idFormaPago;
    public int idFrecuenciaUso;
    public String usuarioCreacion;
    public String usuarioModificacion;
    public String usuarioEliminacion;
    public String fechaCreacion;
    public String fechaModificacion;
    public String fechaEliminacion;


    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdAutopista() {
        return idAutopista;
    }

    public void setIdAutopista(int idAutopista) {
        this.idAutopista = idAutopista;
    }

    public int getIdPlazaCobro() {
        return idPlazaCobro;
    }

    public void setIdPlazaCobro(int idPlazaCobro) {
        this.idPlazaCobro = idPlazaCobro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEncuestador() {
        return encuestador;
    }

    public void setEncuestador(String encuestador) {
        this.encuestador = encuestador;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public int getIdFrecuenciaUso() {
        return idFrecuenciaUso;
    }

    public void setIdFrecuenciaUso(int idFrecuenciaUso) {
        this.idFrecuenciaUso = idFrecuenciaUso;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public void setUsuarioEliminacion(String usuarioEliminacion) {
        this.usuarioEliminacion = usuarioEliminacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(String fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }





}
