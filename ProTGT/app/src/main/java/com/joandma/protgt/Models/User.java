package com.joandma.protgt.Models;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

public class User {
    private Long id;
    private String token;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
    private String pais;
    private String telefono;
    private List<Direccion> listaDirecciones;

    public User(String token, String nombre, String apellidos, String email, String password, String pais, String telefono, List<Address> listaDirecciones) {
        this.token = token;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.pais = pais;
        this.telefono = telefono;
        this.listaDirecciones = new ArrayList<>();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Direccion> getListaDirecciones() {
        return listaDirecciones;
    }

    public void setListaDirecciones(List<Direccion> listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (token != null ? !token.equals(user.token) : user.token != null) return false;
        if (nombre != null ? !nombre.equals(user.nombre) : user.nombre != null) return false;
        if (apellidos != null ? !apellidos.equals(user.apellidos) : user.apellidos != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (pais != null ? !pais.equals(user.pais) : user.pais != null) return false;
        if (telefono != null ? !telefono.equals(user.telefono) : user.telefono != null)
            return false;
        return listaDirecciones != null ? listaDirecciones.equals(user.listaDirecciones) : user.listaDirecciones == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (listaDirecciones != null ? listaDirecciones.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pais='" + pais + '\'' +
                ", telefono='" + telefono + '\'' +
                ", listaDirecciones=" + listaDirecciones +
                '}';
    }
}
