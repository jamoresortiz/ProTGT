package com.joandma.protgt.Models;

import java.util.Date;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

class Ruta {
    private Long id;
    private String localizacion;
    private Date fecha_envio_loc;

    public Ruta(String localizacion, Date fecha_envio_loc) {
        this.localizacion = localizacion;
        this.fecha_envio_loc = fecha_envio_loc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Date getFecha_envio_loc() {
        return fecha_envio_loc;
    }

    public void setFecha_envio_loc(Date fecha_envio_loc) {
        this.fecha_envio_loc = fecha_envio_loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ruta ruta = (Ruta) o;

        if (id != null ? !id.equals(ruta.id) : ruta.id != null) return false;
        if (localizacion != null ? !localizacion.equals(ruta.localizacion) : ruta.localizacion != null)
            return false;
        return fecha_envio_loc != null ? fecha_envio_loc.equals(ruta.fecha_envio_loc) : ruta.fecha_envio_loc == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (localizacion != null ? localizacion.hashCode() : 0);
        result = 31 * result + (fecha_envio_loc != null ? fecha_envio_loc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "id=" + id +
                ", localizacion='" + localizacion + '\'' +
                ", fecha_envio_loc=" + fecha_envio_loc +
                '}';
    }
}
