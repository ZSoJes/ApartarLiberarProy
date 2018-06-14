/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.dataBase.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class LeerInicio {

    private final Connection conn;

    public LeerInicio() throws SQLException {
        Conexion conexion = new Conexion();
        conn = conexion.getConexion();
    }

    /**
     * Recuperar la cantidad de registros en usuarios
     * @return 
     */
    public int getRegistros(){
        int registros = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) registros = rs.getInt(1);
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar dato getRegistros: " + ex);
        }
        return registros;
    }
    
    public String[][] getUsuarios(){
        String[][] array = new String[getRegistros()][6];
        int i = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, ADMINDR, CREADO FROM E_USUARIOS");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_USUARIO");
                array[i][1] = rs.getString("NOMBRE");
                array[i][2] = rs.getString("A_PATERNO");
                array[i][3] = rs.getString("A_MATERNO");
                array[i][4] = rs.getString("ADMINDR");
                array[i][5] = rs.getString("CREADO");
                i++;
            }
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("error al recibir datos: " + ex);
        }
        return array;
    }
    
    /**
     * Revisar si existen usuarios administradores
     *
     * @return
     */
    public boolean getUsuariosAdmin() {
        int conteo = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE ADMINDR = TRUE");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                conteo = rs.getInt(1);
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Actualmente no se recupero usuarios administradores LeerInicio getUsuariosAdmin:" + ex);
        }
        return conteo > 0;
    }

    /**
     * Revisar cuantos usuarios administradores existen
     *
     * @return
     */
    public int getUsuariosAdminNUM() {
        int conteo = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE ADMINDR = TRUE");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                conteo = rs.getInt(1);
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Actualmente no se recupero usuarios administradores LeerInicio getUsuariosAdmin:" + ex);
        }
        return conteo;
    }
    /**
     * Revisar si el usuario indicado es administrador
     *
     * @return
     */
    public boolean getUsuarioNvl(String user) {
        boolean esAdmin = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ADMINDR FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, user);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                esAdmin = rs.getBoolean(1);
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Actualmente no se recupero usuarios administradores LeerInicio getUsuarioNvl:" + ex);
        }
        return esAdmin;
    }
    
    /**
     * Recuperar informacion de un usuario deseado
     *
     * @return
     */
    public String[] getUsuario(String id) {
        String datos[] = new String[6];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, ADMINDR, CREADO FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("ID_USUARIO");
                datos[1] = rs.getString("NOMBRE");
                datos[2] = rs.getString("A_PATERNO");
                datos[3] = rs.getString("A_MATERNO");
                datos[4] = rs.getString("ADMINDR");
                datos[5] = rs.getString("CREADO");
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Actualmente no se recupero usuarios administradores LeerInicio getUsuario:" + ex);
        }
        return datos;
    }

    /**
     * comprueba a traves del id si el usuario ya esta registrado y devuelve un
     * valor boolean 
     *
     * @param id
     * @return
     */
    public boolean getExisteUsuario(String id) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error, no se recupero la información: " + ex);
        }
        return existe;
    }

    public boolean getEsAdminUsuario(String id){
        boolean loEs = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE ID_USUARIO = ? AND ADMINDR = TRUE");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                loEs = rs.getInt(1) > 0;
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException e) {
        }
        return loEs;
    }
    /**
     * regresa la contraseña cifrada del usuario indicado para comparar con
     * bcrypt
     *
     * @param id
     * @return
     */
    public String getPass(String id) {
        String datos = "";
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT PASSWORD FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                datos = rs.getString("PASSWORD");
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error, no se recupero la informacion: " + ex);
        }
        return datos;
    }
    
    public int[] leerMeses(int year){
        int[] reg = new int[12];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 01  THEN 1 ELSE 0 END)  AS ENE, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 02  THEN 1 ELSE 0 END)  AS FEB, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 03  THEN 1 ELSE 0 END)  AS MAR, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 04  THEN 1 ELSE 0 END)  AS ABR, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 05  THEN 1 ELSE 0 END)  AS MAY, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 06  THEN 1 ELSE 0 END)  AS JUN, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 07  THEN 1 ELSE 0 END)  AS JUL, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 08  THEN 1 ELSE 0 END)  AS AGO, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 09  THEN 1 ELSE 0 END)  AS SEP, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 10  THEN 1 ELSE 0 END)  AS OCT, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 11  THEN 1 ELSE 0 END)  AS NOV, " +
                "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 12  THEN 1 ELSE 0 END)  AS DIC " +
                "FROM (SELECT *  FROM E_PRESTAMOS  WHERE ESTATUS = FALSE AND EXTRACT(YEAR FROM CREADO) = ?)");
            prep.setInt(1, year);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                for (int i = 0; i < 12; i++) {
                    reg[i] = rs.getInt(i+1);
                }
            }
        } catch (SQLException e) {
            System.out.println("error al recuperar registros del anio leerMeses LeerInicio: " + e);
        }
        return reg;
    }
    
    public void updUsuarioPass(String usuarioCredencial, String pass){
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET PASSWORD = ? WHERE ID_USUARIO = ?");
            prep.setString(1, pass);
            prep.setString(2, usuarioCredencial);
            prep.execute();
            prep.close();
            System.out.println("La información ha sido actualizada!\n::Para el usuario:" + usuarioCredencial);
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos del usuario verifique:" + e);
        }
    }
}
