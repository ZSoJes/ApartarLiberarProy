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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class UsuarioReadDB {

    private final Connection conn;

    public UsuarioReadDB() throws SQLException {
        Conexion conexion = new Conexion();
        conn = conexion.getConexion();
    }

    /**
     * Recuperar la cantidad de registros en usuarios
     * @param usuariosDispNODisp
     * @return 
     */
    public int getCountUsuarios(boolean usuariosDispNODisp){
        int registros = 0;
        PreparedStatement prep;
        ResultSet rs;
        try{
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE DISPONIBLE = ?");
            prep.setBoolean(1, usuariosDispNODisp);
            rs = prep.executeQuery();
            while (rs.next()) registros = rs.getInt(1);
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar dato getRegistros: " + ex);
        }
        return registros;
    }
    
    public String[][] getUsuarios(){
        String[][] array = new String[getCountUsuarios(true)][7];
        int i = 0;
        PreparedStatement prep;
        ResultSet rs;
        try{
            prep = conn.prepareStatement("SELECT ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, ADMINDR, CREADO, DISPONIBLE FROM E_USUARIOS where DISPONIBLE = TRUE");
            rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_USUARIO");
                array[i][1] = rs.getString("NOMBRE");
                array[i][2] = rs.getString("A_PATERNO");
                array[i][3] = rs.getString("A_MATERNO");
                array[i][4] = rs.getString("ADMINDR");
                array[i][5] = rs.getString("CREADO");
                array[i][6] = rs.getString("DISPONIBLE");
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
    public int getUsuariosAdmin() {
        int conteo = 0;
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_USUARIOS WHERE ADMINDR = TRUE AND DISPONIBLE = TRUE");
            rs = prep.executeQuery();
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
     * @param user
     * @return
     */
    public boolean getUsuarioNvl(String user) {
        boolean esAdmin = false;
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ADMINDR FROM E_USUARIOS WHERE ID_USUARIO = ? AND DISPONIBLE = TRUE");
            prep.setString(1, user);
            rs = prep.executeQuery();
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
     * @param id
     * @return
     */
    public String[] getUsuario(String id) {
        String datos[] = new String[6];
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, ADMINDR, CREADO FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, id);
            rs = prep.executeQuery();
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
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*)>0 FROM E_USUARIOS WHERE ID_USUARIO = ? and DISPONIBLE = TRUE");
            prep.setString(1, id);
            rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getBoolean(1);
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error, no se recupero la información: " + ex);
        }
        return existe;
    }

    public boolean getEsAdminUsuario(String id){
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ADMINDR FROM E_USUARIOS WHERE ID_USUARIO = ? and DISPONIBLE = TRUE");
            prep.setString(1, id);
            rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getBoolean(1);
            }
            //cerrar conexiones
            prep.close();
            rs.close();
        } catch (SQLException e) {System.out.println("Error en encontrar usuario: " + e);}
        return false;
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
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT PASSWORD FROM E_USUARIOS WHERE ID_USUARIO = ?");
            prep.setString(1, id);
            rs = prep.executeQuery();
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
    
    public void bulkData(String ruta, int tabla){
        try {
            PreparedStatement prep;
            System.out.println("ruta: " + ruta.replace('\\', '/'));
            String[] tablaNombre = tablaSeleccionada(tabla);
            String sql = "CALL CSVWRITE('" + ruta.replace('\\', '/').concat("/"+tablaNombre[0]) + "', '"+ tablaNombre[1] + "',STRINGDECODE('charset=windows-1252'))";
            prep = conn.prepareStatement(sql);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar multiples registros a la db Profesores: " + e);
        }
    }
    
    
    public String[] tablaSeleccionada(int tabla){
        String[] tablaNombre = new String[2];
        Calendar c = Calendar.getInstance();
        DateFormat fm = new SimpleDateFormat("yyyyMMdd_hhmma", Locale.ENGLISH);
        switch(tabla){
            case 1:
                tablaNombre[0] = "departamentos";
                tablaNombre[1] = "SELECT ID_DEPARTAMENTO, NOMBRE, ABBREV, ENCARGADO FROM E_DEPARTAMENTOS";
            break;
            case 2:
                tablaNombre[0] = "profesores";
                tablaNombre[1] = "SELECT ID_PROFESOR, ID_DEPARTAMENTO, NOMBRE, A_PATERNO, A_MATERNO, SIN_ADEUDO FROM E_PROFESORES";
            break;
            case 3:
                tablaNombre[0] = "videoproyectores";
                tablaNombre[1] = "SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE FROM E_VIDEOPROYECTORES";
            break;
            case 4:
                tablaNombre[0] = "aulas";
                tablaNombre[1] = "E_AULAS";
            break;
            case 5:
                tablaNombre[0] = "articulos";
                tablaNombre[1] = "SELECT ID_ACCESORIO, NOMBRE, EXISTENCIAS FROM E_ACCESORIOS";
            break;
            case 6:
                tablaNombre[0] = "usuarios";
                tablaNombre[1] = "SELECT ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, PASSWORD, ADMINDR FROM E_USUARIOS";
            break;
        }
        tablaNombre[0] = tablaNombre[0].concat(fm.format(c.getTime()) + ".csv");
        return tablaNombre;
    }
    
    public void bulkDataDB(String ruta){
        try {
            PreparedStatement prep;
            System.out.println("ruta: " + ruta.replace('\\', '/'));
            String sql = "BACKUP TO ('" + ruta.replace('\\', '/').concat("/backupDB.zip") + "')";
            prep = conn.prepareStatement(sql);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar multiples registros a la db Profesores: " + e);
        }
    }
}
