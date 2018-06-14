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
import java.util.Arrays;
import java.util.HashMap;
import proyector.comboItem;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class AulaDB {
    
    private final Connection conn;

    public AulaDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }
    
    /**
     * ingresa nueva informacion a la tabla aulas
     * @param nombre 
     */
    public void setAula(String nombre){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_AULAS(NOMBRE) VALUES (?)");
            prep.setString(1, nombre);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al ingresar aula, revise los datos ingresados: " + ex);
        }
    }

    /**
     * recupera la cantidad de registros en la tabla aulas
     * @return 
     */
    public int getRegistros(){
        int registros = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_AULAS");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) registros = rs.getInt(1);
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar dato getRegistros: " + ex);
        }
        return registros;
    }
    
    /**
     * Recupera las aulas existentes
     * @return 
     */
    public String[][] getAulas(){
        String[][] array = new String[getRegistros()][2];
        int i = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_AULA, NOMBRE FROM E_AULAS");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_AULA");
                array[i][1] = rs.getString("NOMBRE");
                i++;
            }
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar aulas getAulas AulaDB: " + ex);
        }
        return array;
    }
    
    /**
     * Recupera el nombre de un aula en especifico
     * @param id
     * @return 
     */
    public String getAula(int id){
        String nombre = "";
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT NOMBRE FROM E_AULAS WHERE ID_AULA = ?");
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) nombre = rs.getString("NOMBRE");
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar el aula: " + ex);
        }
        return nombre;
    }
    
    /**
     * Actualiza un registro de la tabla aulas
     * @param id
     * @param dato 
     */
    public void updAula(int id, String dato){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_AULAS SET NOMBRE = ? WHERE ID_AULA = ?");
            prep.setString(1, dato);
            prep.setInt(2, id);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al ingresar el nuevo nombre: " + ex);
        }
    }
    /**
     * Combo Box With Hidden Data, crea un key, value para el comboBox
     * que se encargara de eliminar los datos de aulas
     * @return 
     */
    public HashMap<String, Integer> primerCombo(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        PreparedStatement prep;
        ResultSet rs;
        try{
        prep = conn.prepareStatement("SELECT ID_AULA, NOMBRE FROM E_AULAS ORDER BY ID_AULA");
        rs = prep.executeQuery();
        comboItem cmi;
        while (rs.next()){
            cmi = new comboItem(rs.getInt(1), rs.getString(2));
            map.put(cmi.getAulaNombre(), cmi.getIdAula());
        }
        rs.close();
        prep.close();
        }catch(SQLException ex){
            System.out.println("Error al generar comboBox aula: " + ex);
        }
        return map;
    }
    
    /**
     * Combo Box With Hidden Data, crea un key, value para el comboBox
     * que se encargara de eliminar los datos de aulas
     * @return 
     */
    public HashMap<String, Integer> primerCombo(boolean status){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        PreparedStatement prep;
        ResultSet rs;
        try{
            prep = conn.prepareStatement("SELECT ID_AULA, NOMBRE FROM E_AULAS WHERE ESTATUS = ? ORDER BY ID_AULA");
            prep.setBoolean(1, status);
            rs = prep.executeQuery();
            comboItem cmi;
            while (rs.next()){
                cmi = new comboItem(rs.getInt(1), rs.getString(2));
                map.put(cmi.getAulaNombre(), cmi.getIdAula());
            }
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al generar comboBox aula: " + ex);
        }
        return map;
    }
    
    /**
     * Elimina un aula por medio de su id
     * @param id 
     */
    public void destroyAula(int id){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_AULAS WHERE ID_AULA = ?");
            prep.setInt(1, id);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al borrar elemento: " + ex);
        }
    }
}
