package com.joandma.protgt.Models;

/**
 * Created by Jorge Amores on 28/02/2018.
 */

public class ContactoConfianza {
    private String _id;
    private String telefono;
    private String nombre;

    public ContactoConfianza() {
    }

    public ContactoConfianza(String telefono, String nombre) {
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactoConfianza that = (ContactoConfianza) o;

        if (_id != null ? !_id.equals(that._id) : that._id != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null)
            return false;
        return nombre != null ? nombre.equals(that.nombre) : that.nombre == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactoConfianza{" +
                "_id='" + _id + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
