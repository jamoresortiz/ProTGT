package com.joandma.protgt.Models.ModelsApiProTGT;

/**
 * Created by jamores on 26/02/2018.
 */

public class VerifyModel {
    private String email;
    private String telefono;

    public VerifyModel(String email, String telefono) {
        this.email = email;
        this.telefono = telefono;
    }

    public VerifyModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
