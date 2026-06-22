package com.ideal.encuestacliente;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.ideal.encuestacliente.controladores.LoginManager;

import org.junit.Test;

public class LoginManagerTest {

    private final LoginManager lm = new LoginManager();

    @Test
    public void loginCorrecto() {
        assertTrue(lm.validarCampos("nsanchez", "n5a7c8"));
    }

    @Test
    public void usuarioVacio() {
        assertFalse(lm.validarCampos("", "n5a7c8"));
    }

    @Test
    public void passwordVacio() {
        assertFalse(lm.validarCampos("nsanchez", ""));
    }

    @Test
    public void ambosVacios() {
        assertFalse(lm.validarCampos("", ""));
    }

    @Test
    public void usuarioNull() {
        assertFalse(lm.validarCampos(null, "n5a7c8"));
    }

    @Test
    public void passwordNull() {
        assertFalse(lm.validarCampos("nsanchez", null));
    }

    @Test
    public void usuarioConEspacios() {
        assertFalse(lm.validarCampos("   ", "n5a7c8"));
    }

    @Test
    public void passwordConEspacios() {
        assertFalse(lm.validarCampos("nsanchez", "   "));
    }
}