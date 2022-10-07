/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.ClsConexion;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.Venta;

/**
 *
 * @author Julian
 */
public class ControladorGestionVenta {

    ClsConexion conexion = new ClsConexion();

    public ControladorGestionVenta() {

    }

    public boolean agregar(String codigo, String fechaDeVenta, int unidadesVendidas, String nombreDelArtículo, int valorTotal) {
        Venta venta = new Venta(codigo, fechaDeVenta, unidadesVendidas, nombreDelArtículo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into venta(codigo,fechaDeVenta,unidadesVendidas,nombreDelArtículo,valorTotal) "
                    + "values('" + venta.getCodigo() + "','"
                    + venta.getFechaDeVenta() + "','"
                    + venta.getUnidadesVendidas() + "',"
                    + venta.getNombreDelArtículo() + "',"
                    + venta.getValorTotal() + ")");//consulta

            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscar(String codigo) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo,fechaDeVenta,unidadesVendidas,nombreDelArtículo,"
                            + "valorTotal from venta where "
                            + "codigo='" + codigo + "'")); //consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("codigo"));
                temp.add(conexion.getResultadoDB().getString("fechaDeVenta"));
                temp.add(conexion.getResultadoDB().getInt("unidadesVendidas") + "");
                temp.add(conexion.getResultadoDB().getString("nombreDelArtículo"));
                temp.add(conexion.getResultadoDB().getInt("valorTotal") + "");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionVenta.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificar(String codigo, String fechaDeVenta, int unidadesVendidas, String nombreDelArtículo, int valorTotal) {
        Venta venta = new Venta(codigo, fechaDeVenta, unidadesVendidas, nombreDelArtículo, valorTotal);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update venta set fechaDeVenta='" + venta.getFechaDeVenta()
                    + "',unidadesVendidas='" + venta.getUnidadesVendidas() + "',"
                    + "nombreDelArtículo=" + venta.getNombreDelArtículo() + "',"
                    + "valorTotal=" + venta.getValorTotal()
                    + " where codigo='" + venta.getCodigo() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String codigo) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from venta where codigo='" + codigo + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Codigo", "Nombre del artículo", "fecha de Venta", "Unidades Vendidas", "Valor total"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigo,fechaDeVenta,unidadesVendidas,nombreDelArtículo"
                            + "valorTotal from venta order by codigo"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("codigo"),
                    conexion.getResultadoDB().getString("fechaDeVenta"),
                    conexion.getResultadoDB().getInt("unidadesVendidas"),
                    conexion.getResultadoDB().getString("nombreDelArticulo"),
                    conexion.getResultadoDB().getInt("valorTotal")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionVenta.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

}
