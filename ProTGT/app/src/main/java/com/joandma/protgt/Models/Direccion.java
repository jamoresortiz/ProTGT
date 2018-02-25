package com.joandma.protgt.Models;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

public class Direccion {
    private Long id;
    private String provincia;
    private String localidad;
    private String calle;
    private int numero;
    private String piso;
    private String bloque;
    private String puerta;

    public Direccion(String provincia, String localidad, String calle, int numero, String piso, String bloque, String puerta) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.bloque = bloque;
        this.puerta = puerta;
    }

    public Direccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direccion direccion = (Direccion) o;

        if (numero != direccion.numero) return false;
        if (id != null ? !id.equals(direccion.id) : direccion.id != null) return false;
        if (provincia != null ? !provincia.equals(direccion.provincia) : direccion.provincia != null)
            return false;
        if (localidad != null ? !localidad.equals(direccion.localidad) : direccion.localidad != null)
            return false;
        if (calle != null ? !calle.equals(direccion.calle) : direccion.calle != null) return false;
        if (piso != null ? !piso.equals(direccion.piso) : direccion.piso != null) return false;
        if (bloque != null ? !bloque.equals(direccion.bloque) : direccion.bloque != null)
            return false;
        return puerta != null ? puerta.equals(direccion.puerta) : direccion.puerta == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (provincia != null ? provincia.hashCode() : 0);
        result = 31 * result + (localidad != null ? localidad.hashCode() : 0);
        result = 31 * result + (calle != null ? calle.hashCode() : 0);
        result = 31 * result + numero;
        result = 31 * result + (piso != null ? piso.hashCode() : 0);
        result = 31 * result + (bloque != null ? bloque.hashCode() : 0);
        result = 31 * result + (puerta != null ? puerta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", provincia='" + provincia + '\'' +
                ", localidad='" + localidad + '\'' +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", piso='" + piso + '\'' +
                ", bloque='" + bloque + '\'' +
                ", puerta='" + puerta + '\'' +
                '}';
    }
}
