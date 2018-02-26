package com.joandma.protgt.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

public class Aviso {
    private String _id;
    private List<Ruta> listaRutas;
    private User user;
    private int estado;

    public Aviso(List<Ruta> listaRutas, User idUser, int estado) {
        this.listaRutas = new ArrayList<>();
        this.user = idUser;
        this.estado = estado;
    }

    public Aviso() {
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public List<Ruta> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(List<Ruta> listaRutas) {
        this.listaRutas = listaRutas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aviso aviso = (Aviso) o;

        if (estado != aviso.estado) return false;
        if (_id != null ? !_id.equals(aviso._id) : aviso._id != null) return false;
        if (listaRutas != null ? !listaRutas.equals(aviso.listaRutas) : aviso.listaRutas != null)
            return false;
        return user != null ? user.equals(aviso.user) : aviso.user == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (listaRutas != null ? listaRutas.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + estado;
        return result;
    }

    @Override
    public String toString() {
        return "Aviso{" +
                "id=" + _id +
                ", listaRutas=" + listaRutas +
                ", user=" + user +
                ", estado=" + estado +
                '}';
    }
}
