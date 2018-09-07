/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.dataBase.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class UsuarioCreateDB {
    private final Connection conn;
    
    public UsuarioCreateDB() throws SQLException{
        Conexion conexion = new Conexion();
        conn = conexion.getConexion();
    }
    /**
     * Limpia las cadenas que contengan acentos
     * @param palabra
     * @return 
     */
    public String sinAcento(String palabra){
        String abc = Normalizer.normalize(palabra, Normalizer.Form.NFD);
        String limpio = abc.replaceAll("[^\\p{ASCII}]", "");
        return limpio;
    }
    
    /**
     * Devuelve un identificador aleatorio irrepetible
     * @param datos
     * @return 
     * @throws java.sql.SQLException 
     */
    public String getID(String[] datos) throws SQLException{
        Random randomGenerator = new Random();
        String date = new SimpleDateFormat("ddMMyy").format(new Date());
        String id = "";
        UsuarioReadDB leer = new UsuarioReadDB();
        while(true){                        //generando id
            id = datos[1].split("")[0];
            id += datos[1].split("")[1];
            id += datos[2].split("")[0]; 
            id += datos[0].split("")[0]; 
            id += date;
            id += randomGenerator.nextInt(100);
            if (!(leer.getExisteUsuario(id))) break; //comprueba que no sea igual a un dato existente en base de datos
        }
        id = sinAcento(id).toUpperCase();
        System.out.println("id: " + id);
        return id;
    }
    
    /**
     * Control de creacion de usuario con un ID especifico
     * @param datos
     * @param nvlAcceso 
     * @param idPersonal
     */
    public void setUsuario(String[] datos, boolean nvlAcceso, String idPersonal){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_USUARIOS(ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, PASSWORD, ADMINDR) VALUES (?,?,?,?,?,?)");
            prep.setString(1, idPersonal);
            for (int i = 0; i < datos.length; i++) prep.setString(i+2, datos[i]);//agrega desde la posicion 2 porque el id fue colocado en 1, el array se recorre desde la posicion 0
            prep.setBoolean(6, nvlAcceso);
            prep.execute();
            prep.close();
            JOptionPane.showMessageDialog(null, "Su usuario fue registrado como:\n\n"+ idPersonal +"\n\n a partir de ahora puede acceder con este usuario y su contraseña!");
        }catch(SQLException ex){
            System.out.println("Error al crear usuario, revise los datos ingresados: " + ex);
            JOptionPane.showMessageDialog(null, "NO SE GENERO EL REGISTRO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Control de creacion de usuario con nuevo ID
     * @param datos
     * @param nvlAcceso 
     * @return  
     * @throws java.sql.SQLException  
     */
    public String  setUsuario(String[] datos, boolean nvlAcceso) throws SQLException{
        String id = getID(datos);
        
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_USUARIOS(ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, PASSWORD, ADMINDR) VALUES (?,?,?,?,?,?)");
            prep.setString(1, id);
            for (int i = 0; i < datos.length; i++) prep.setString(i+2, datos[i]);//agrega desde la posicion 2 porque el id fue colocado en 1, el array se recorre desde la posicion 0
            prep.setBoolean(6, nvlAcceso);
            prep.executeUpdate();
            prep.close();
            JOptionPane.showMessageDialog(null, "Su usuario fue registrado como:\n\n"+ id +"\n\n a partir de ahora puede acceder con este usuario y su contraseña!");
            
        }catch(SQLException ex){
            System.out.println("Error al crear usuario, revise los datos ingresados: " + ex);
            JOptionPane.showMessageDialog(null, "NO SE GENERO EL REGISTRO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }
    
    /**
     * Permite indicar true o false para el nivel de acceso 
     * de administrador = true, a usurio = false a 
     * un especifico usuario = id
     * @param usuario
     * @param acceso 
     */
    public void setUsuarioAdmin(String usuario, boolean acceso){
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET ADMINDR = ? WHERE ID_USUARIO = ?");
            prep.setBoolean(1, acceso);
            prep.setString(2, usuario);
            prep.executeUpdate();
            //cerrar conexiones
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar a administrador/usuario CrearInicio setUsuarioAdmin:" + ex);
        }
    }
    
    
    /**
     * Control de actualizacion de usuario CAMBIAR DATOS
     * se solicita un id 
     * este debe recibir un array con NOMBRE, APATERNO, AMATERNO
     * @param id 
     * @param datos
     * @throws java.sql.SQLException
     */
    public void updUsuario(String id, String[] datos) throws SQLException{
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET NOMBRE = ?, A_PATERNO = ?, A_MATERNO = ? WHERE ID_USUARIO = ? ");
            prep.setString(1, datos[0]);
            prep.setString(2, datos[1]);
            prep.setString(3, datos[2]);
            prep.setString(4, id);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al actualizar a administrador/usuario CrearInicio updUsuario CAMBIARDATOS:" + ex);
        }
    }
    
    /**
     * Control de actualizacion de usuario CAMBIAR CREDENCIAL
     * se solicita [newId, oldId]
     * @param id 
     * @throws java.sql.SQLException 
     */
    public void  updUsuario(String[] id) throws SQLException{
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET ID_USUARIO = ? WHERE ID_USUARIO = ? ");
            prep.setString(1, id[0]);
            prep.setString(2, id[1]);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al actualizar a administrador/usuario CrearInicio updUsuario CAMBIAR CREDENCIAL:" + ex);
        }
    }
    
    
    
    /**
     * Control de actualizacion de usuario CAMBIAR CONTRASEÑA
     * se solicita un id 
     * se solicita el password
     * @param id
     * @param pass 
     * @throws java.sql.SQLException 
     */
    public void  updUsuario(String id, String pass) throws SQLException{
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET PASSWORD = ? WHERE ID_USUARIO = ? ");
            prep.setString(1, pass);
            prep.setString(2, id);
            prep.executeUpdate();
            prep.close();          
        }catch(SQLException ex){
            System.out.println("Error al actualizar a administrador/usuario CrearInicio updUsuario CAMBIAR CONTRASEÑA:" + ex);
        }
    }
    
    public void deleteRelifeUsuario(String id, boolean estatus){
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("UPDATE E_USUARIOS SET DISPONIBLE = ?  WHERE ID_USUARIO = ?");
            prep.setBoolean(1, estatus);
            prep.setString(2, id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario de la base de datos: " + e);
        }
    }
}
