package com.ideal.encuestacliente.controladores;
public class LoginManager {

    public boolean validarCampos(String usuario, String password) {
        return usuario != null && !usuario.isEmpty()
                && password != null && !password.isEmpty();
    }

    public boolean esLoginValido(String usuario, String password) {
        // Simulación para pruebas (puedes adaptar)
        return usuario.equals("nsanchez") && password.equals("n5a7c8");
    }
}