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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;

/**
 *
 * @author Julian
 */
public class ControladorGestionUsuario {

    ClsConexion conexion = new ClsConexion();

    public ControladorGestionUsuario() {

    }

    public boolean agregar(String nombre, String apellido, String cedula, String correo, String contraseña) {
        Usuario usuario = new Usuario(nombre, apellido, cedula, correo, contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("insert into usuario(nombre,apellido,cedula,correo,contraseña) "
                    + "values('" + usuario.getNombre() + "','"
                    + usuario.getApellido() + "','"
                    + usuario.getCedula() + "','"
                    + usuario.getCorreo() + "',"
                    + usuario.getContraseña() + ")");//consulta

            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public List<String> buscar(String cedula) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,cedula,correo,"
                            + "contraseña from usuario where "
                            + "cedula='" + cedula + "'")); //consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contraseña"));
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionUsuario.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificar(String nombre, String apellido, String cedula, String correo, String contraseña) {
        Usuario usuario = new Usuario(nombre, apellido, cedula, correo, contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update usuario set nombre='" + usuario.getNombre()
                    + "',apellido='" + usuario.getApellido() + "',"
                    + "correo=" + usuario.getCorreo() + "',"
                    + "contraseña=" + usuario.getContraseña()
                    + " where cedula='" + usuario.getCedula() + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String cedula) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from usuario where cedula='" + cedula + "'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Nombre", "Apellido", "cedula", "Correo", "Contraseña"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,cedula,correo,"
                            + " contraseña from usuario order by cedula"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("apellido"),
                    conexion.getResultadoDB().getString("cedula"),
                    conexion.getResultadoDB().getString("correo"),
                    conexion.getResultadoDB().getString("contraseña")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionUsuario.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }

    public boolean buscarLogin(String correo, String contraseña) {

        List<Usuario> usuarios = new ArrayList<Usuario>();

        conexion.conectar();

        try {

            conexion.setResultadoDB(conexion.getSentenciaSQL().executeQuery("select cedula, nombre, apellido, correo, contraseña "
                    + "from usuario"));

            while (conexion.getResultadoDB().next()) {

                String cedula = conexion.getResultadoDB().getString("cedula");
                String nombre = conexion.getResultadoDB().getString("nombre");
                String apellido = conexion.getResultadoDB().getString("apellido");
                String corre = conexion.getResultadoDB().getString("correo");
                String contra = conexion.getResultadoDB().getString("contraseña");

                Usuario temp = new Usuario(cedula, nombre, apellido, corre, contra);

                usuarios.add(temp);

            }

            for (int i = 0; i < usuarios.size(); i++) {

                if (usuarios.get(i).getCorreo().equals(correo)) {

                    if (usuarios.get(i).getContraseña().equals(contraseña)) {
                        conexion.desconectar();
                        return true;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Este usuario no se encuentra en el sistema,"
                            + " porfavor registre he intente nuevamente");
                }
            }
            conexion.desconectar();
            return true;

        } catch (SQLException ex) {

            Logger.getLogger(ControladorGestionUsuario.class.getName()).log(Level.SEVERE, null, ex);
            conexion.desconectar();
            return false;

        }

    }

    public List<String> buscarCorreo(String correo) {

        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,cedula,correo,"
                            + "contraseña from usuario where "
                            + "correo='" + correo + "'")); //consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contraseña"));
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {
            Logger.getLogger(ControladorGestionUsuario.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }
}
