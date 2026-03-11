package com.ideal.encuestacliente.model;

public class Rsin_SharePointUsuarios
{
    public String usuario;
    public String password;
    public boolean estatusConexion;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstatusConexion() {
        return estatusConexion;
    }

    public void setEstatusConexion(boolean estatusConexion) {
        this.estatusConexion = estatusConexion;
    }
}
