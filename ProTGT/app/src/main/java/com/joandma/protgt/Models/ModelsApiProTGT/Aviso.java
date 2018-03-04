package com.joandma.protgt.Models.ModelsApiProTGT;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

public class Aviso {
    private String _id;
    @SerializedName("rutas")
    private List<String> ruta_id;
    private String user;
    private int estado;

    public Aviso(List<Ruta> ruta_id, String idUser, int estado) {
        this.ruta_id = new ArrayList<String>();
        this.user = idUser;
        this.estado = estado;
    }

    public Aviso() {
        this.ruta_id = new ArrayList<>();
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public List<String> getRuta_id() {
        return ruta_id;
    }

    public void setRuta_id(List<String> ruta_id) {
        this.ruta_id = ruta_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
        if (ruta_id != null ? !ruta_id.equals(aviso.ruta_id) : aviso.ruta_id != null)
            return false;
        return user != null ? user.equals(aviso.user) : aviso.user == null;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (ruta_id != null ? ruta_id.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + estado;
        return result;
    }

    @Override
    public String toString() {
        return "Aviso{" +
                "id=" + _id +
                ", ruta_id=" + ruta_id +
                ", user=" + user +
                ", estado=" + estado +
                '}';
    }
}
