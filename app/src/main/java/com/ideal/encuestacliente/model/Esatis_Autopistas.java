package com.ideal.encuestacliente.model;

public class Esatis_Autopistas
{
    public int    idAutopista;
    public String nombreAutopista;
    public String acronimoAutopista;
    public int claveAutopista;
    public String usuarioCreacion;
    public String usuarioModificacion;
    public String usuarioEliminacion;
    public String fechaCreacion;
    public String fechaModificacion;
    public String fechaEliminacion;
    public int    idOrden;

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdAutopistaSQL() {
        return idAutopistaSQL;
    }

    public void setIdAutopistaSQL(int idAutopistaSQL) {
        this.idAutopistaSQL = idAutopistaSQL;
    }

    public int idAutopistaSQL;

    public int getIdAutopista() {
        return idAutopista;
    }

    public void setIdAutopista(int idAutopista) {
        this.idAutopista = idAutopista;
    }

    public String getNombreAutopista() {
        return nombreAutopista;
    }

    public void setNombreAutopista(String nombreAutopista) {
        if(nombreAutopista.isEmpty()){
            nombreAutopista="";
        }
        this.nombreAutopista = nombreAutopista;
    }

    public String getAcronimoAutopista() {
        return acronimoAutopista;
    }

    public void setAcronimoAutopista(String acronimoAutopista) {
        if(acronimoAutopista.isEmpty()){
            acronimoAutopista="";
        }
        this.acronimoAutopista = acronimoAutopista;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        if(usuarioCreacion.isEmpty()) {
           usuarioCreacion ="";
        }
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        if(usuarioModificacion.isEmpty()){
            usuarioModificacion="";
        }
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getUsuarioEliminacion() {
        return usuarioEliminacion;
    }

    public void setUsuarioEliminacion(String usuarioEliminacion) {
        if(usuarioEliminacion.isEmpty()) {
            usuarioEliminacion="";
        }
        this.usuarioEliminacion = usuarioEliminacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        if(fechaCreacion.isEmpty()){
            fechaCreacion="";
        }
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        if(fechaModificacion.isEmpty()){
            fechaModificacion="";
        }
        this.fechaModificacion = fechaModificacion;
    }

    public String getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(String fechaEliminacion) {
        if(fechaEliminacion.isEmpty()){
            fechaEliminacion ="";
        }
        this.fechaEliminacion = fechaEliminacion;
    }


    public int getClaveAutopista() {
        return claveAutopista;
    }

    public void setClaveAutopista(int claveAutopista) {
        this.claveAutopista = claveAutopista;
    }

}
