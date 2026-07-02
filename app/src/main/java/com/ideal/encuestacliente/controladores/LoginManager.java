package com.ideal.encuestacliente.controladores;

public class LoginManager {
    public boolean validarCampos(String usuario, String password) {
        return usuario != null && !usuario.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }

    public boolean esLoginValido(String usuario, String password) {
        return "nsanchez".equals(usuario) && "n5a7c8".equals(password);
    }
}