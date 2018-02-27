package com.joandma.protgt.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamores on 26/02/2018.
 */

public class UserRegister {

    private String _id;
    private String token;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String pais;
    private String telefono;
    private List<String> address_id;

    public UserRegister(String token, String nombre, String apellidos, String email, String password, String pais, String telefono, List<String> address_id) {
        this.token = token;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.pais = pais;
        this.telefono = telefono;
        this.address_id = new ArrayList<>();
    }

    public UserRegister() {
        this.address_id = new ArrayList<>();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<String> getAddress_id() {
        return address_id;
    }

    public void setAddress_id(List<String> address_id) {
        this.address_id = address_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRegister that = (UserRegister) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (apellidos != null ? !apellidos.equals(that.apellidos) : that.apellidos != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (pais != null ? !pais.equals(that.pais) : that.pais != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null)
            return false;
        return address_id != null ? address_id.equals(that.address_id) : that.address_id == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (address_id != null ? address_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRegister{" +
                "_id='" + _id + '\'' +
                ", token='" + token + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                ", telefono='" + telefono + '\'' +
                ", address_id=" + address_id +
                '}';
    }
}
