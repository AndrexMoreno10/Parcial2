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
import modelo.Articulo;

/**
 *
 * @author Julian
 */
public class ControladorGestionArticulo {

    ClsConexion conexion = new ClsConexion();

    public ControladorGestionArticulo() {

    }

    public boolean agregar(String nombre, String codigo, int precio, int cantidad, String descripción, String categoría) {
        Articulo articulo = new Articulo(nombre, codigo, precio, cantidad, descripción, categoría);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into articulo(nombre, codigo, precio, cantidad, descripción, categoría) "
                    + "values('" + articulo.getNombre() + "','"
                    + articulo.getCodigo() + "','"
                    + articulo.getPrecio() + "','"
                    + articulo.getCantidad() + "','"
                    + articulo.getDescripción() + "',"
                    + articulo.getCategoría() + ")");//consulta

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
                    executeQuery("select nombre,codigo, precio, cantidad, descripción,"
                            + "categoria from articulo where "
                            + "codigo='" + codigo + "'")); //consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("codigo"));
                temp.add(conexion.getResultadoDB().getInt("precio") + "");
                temp.add(conexion.getResultadoDB().getInt("cantidad") + "");
                temp.add(conexion.getResultadoDB().getString("descripcion"));
                temp.add(conexion.getResultadoDB().getString("categoria"));

            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionArticulo.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificar(String nombre, String codigo, int precio, int cantidad, String descripción, String categoría) {
        Articulo articulo = new Articulo(nombre, codigo, precio, cantidad, descripción, categoría);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update articulo set categoria='" + articulo.getCategoría()
                    + "',precio='" + articulo.getPrecio() + "',"
                    + "',nombre='" + articulo.getNombre() + "',"
                    + "cantidad=" + articulo.getCantidad() + "',"
                    + "descripcion=" + articulo.getDescripción() + "',"
                    + " where codigo='" + articulo.getCodigo() + "'");//consulta
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
            conexion.getSentenciaSQL().execute("delete from articulo where codigo='" + codigo + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Nombre", "Codigo", "Precio", "Cantidad", "Descripcion", "Categoria"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,codigo,precio,cantidad,descripcion"
                            + "cantidad from articulo order by codigo"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("codigo"),
                    conexion.getResultadoDB().getInt("precio"),
                    conexion.getResultadoDB().getInt("cantidad"),
                    conexion.getResultadoDB().getString("descripcion"),
                    conexion.getResultadoDB().getString("categoria")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionArticulo.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

}
