package com.example.appcosto;

import java.sql.Blob;

public class ProductInfo {

    private String nombre;
    private String marca;
    private int cantidad;
    private Blob fotoProducto;
    private Blob fotoPrecio;
    private float precio;
    private boolean descuento;
    private String detallesDescuento;
    private boolean promocion;
    private String detallesPromocion;
    private String tienda;
    private String ubicacionSucursal;

    public ProductInfo(String nombre,
                       String marca,
                       int cantidad,
                       Blob fotoProducto,
                       Blob fotoPrecio,
                       float precio,
                       boolean descuento,
                       String detallesDescuento,
                       boolean promocion,
                       String detallesPromocion,
                       String tienda,
                       String ubicacionSucursal) {
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.fotoProducto = fotoProducto;
        this.fotoPrecio = fotoPrecio;
        this.precio = precio;
        this.descuento = descuento;
        this.detallesDescuento = detallesDescuento;
        this.promocion = promocion;
        this.detallesPromocion = detallesPromocion;
        this.tienda = tienda;
        this.ubicacionSucursal = ubicacionSucursal;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", cantidad=" + cantidad +
                ", fotoProducto=" + fotoProducto +
                ", fotoPrecio=" + fotoPrecio +
                ", precio=" + precio +
                ", descuento=" + descuento +
                ", detallesDescuento='" + detallesDescuento + '\'' +
                ", promocion=" + promocion +
                ", detallesPromocion='" + detallesPromocion + '\'' +
                ", tienda='" + tienda + '\'' +
                ", ubicacionSucursal='" + ubicacionSucursal + '\'' +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Blob getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(Blob fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public Blob getFotoPrecio() {
        return fotoPrecio;
    }

    public void setFotoPrecio(Blob fotoPrecio) {
        this.fotoPrecio = fotoPrecio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    public String getDetallesDescuento() {
        return detallesDescuento;
    }

    public void setDetallesDescuento(String detallesDescuento) {
        this.detallesDescuento = detallesDescuento;
    }

    public boolean isPromocion() {
        return promocion;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public String getDetallesPromocion() {
        return detallesPromocion;
    }

    public void setDetallesPromocion(String detallesPromocion) {
        this.detallesPromocion = detallesPromocion;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getUbicacionSucursal() {
        return ubicacionSucursal;
    }

    public void setUbicacionSucursal(String ubicacionSucursal) {
        this.ubicacionSucursal = ubicacionSucursal;
    }
}
