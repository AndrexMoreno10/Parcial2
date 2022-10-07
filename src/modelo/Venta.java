/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author USUARIO
 */
public class Venta {

    private String codigo;
    private String fechaDeVenta;
    private int unidadesVendidas;
    private String nombreDelArtículo;
    private int valorTotal;

    public Venta(String codigo, String fechaDeVenta, int unidadesVendidas, String nombreDelArtículo, int valorTotal) {
        this.fechaDeVenta = fechaDeVenta;
        this.codigo = codigo;
        this.unidadesVendidas = unidadesVendidas;
        this.nombreDelArtículo = nombreDelArtículo;
        this.valorTotal = valorTotal;
    }

    public String getFechaDeVenta() {
        return fechaDeVenta;
    }

    public void setFechaDeVenta(String fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getUnidadesVendidas() {
        return unidadesVendidas;
    }

    public void setUnidadesVendidas(int unidadesVendidas) {
        this.unidadesVendidas = unidadesVendidas;
    }

    public String getNombreDelArtículo() {
        return nombreDelArtículo;
    }

    public void setNombreDelArtículo(String nombreDelArtículo) {
        this.nombreDelArtículo = nombreDelArtículo;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

}
