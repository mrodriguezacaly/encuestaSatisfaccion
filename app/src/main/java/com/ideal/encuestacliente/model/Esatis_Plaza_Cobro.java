package com.ideal.encuestacliente.model;

public class Esatis_Plaza_Cobro
{

    public int    idPlazaCobro;
    public int    idPlazaCobroSQL;
    public int    idAutopista;
    public String plazaCobro;

    public String getRazonSocialAutopista() {
        return razonSocialAutopista;
    }

    public void setRazonSocialAutopista(String razonSocialAutopista) {
        this.razonSocialAutopista = razonSocialAutopista;
    }

    public String razonSocialAutopista;
    public String usuarioCreacion;

    public int getIdPlazaCobro() {
        return idPlazaCobro;
    }

    public void setIdPlazaCobro(int idPlazaCobro) {
        this.idPlazaCobro = idPlazaCobro;
    }

    public int getIdAutopista() {
        return idAutopista;
    }

    public void setIdAutopista(int idAutopista) {
        this.idAutopista = idAutopista;
    }

    public String getPlazaCobro() {
        return plazaCobro;
    }

    public void setPlazaCobro(String plazaCobro) {
        this.plazaCobro = plazaCobro;
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

    public String usuarioModificacion;
    public String usuarioEliminacion;
    public String fechaCreacion;
    public String fechaModificacion;
    public String fechaEliminacion;

    public int getIdPlazaCobroSQL() {
        return idPlazaCobroSQL;
    }

    public void setIdPlazaCobroSQL(int idPlazaCobroSQL) {
        this.idPlazaCobroSQL = idPlazaCobroSQL;
    }
}
