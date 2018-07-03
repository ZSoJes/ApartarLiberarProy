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
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class DepartamentoDB {

    private final Connection conn;

    public DepartamentoDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Recupera la cantidad de departamentos existentes
     * @return
     */
    public int getCantDepartamentos() {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_DEPARTAMENTOS");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la cantidad de datos Departamentos: " + ex);
        }
        return 0;
    }

    /**
     * Recupera un departamento especifico
     * @param id
     * @return 
     */
    public String[] getDepartamento(int id) {
        String[] datos = new String[3];
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT NOMBRE, ABBREV, ENCARGADO FROM E_DEPARTAMENTOS WHERE ID_DEPARTAMENTO = ?");
            prep.setInt(1, id);
            rs = prep.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("NOMBRE");
                datos[1] = rs.getString("ABBREV");
                datos[2] = rs.getString("ENCARGADO");
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar departamento: " + ex);
        }
        return datos;
    }
    
    public String[] getDepartamentoAbb(String ab) {
        String[] datos = new String[3];
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT * FROM E_DEPARTAMENTOS WHERE LOWER(ABBREV) = LOWER(?)");
            prep.setString(1, ab);
            rs = prep.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("NOMBRE");
                datos[1] = rs.getString("ABBREV");
                datos[2] = rs.getString("ENCARGADO");
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar departamento: " + ex);
        }
        return datos;
    }

    /**
     * Recupera los departamentos con los siguientes datos
     * Id * Nombre * Abreviatura * Encargado
     * @return
     */
    public String[][] getDepartamentos() {
        String[][] datos = new String[getCantDepartamentos()][4];
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ID_DEPARTAMENTO, NOMBRE, ABBREV, ENCARGADO FROM E_DEPARTAMENTOS");
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                datos[i][0] = rs.getString("NOMBRE");
                datos[i][1] = rs.getString("ABBREV");
                datos[i][2] = rs.getString("ENCARGADO");
                datos[i][3] = rs.getString("ID_DEPARTAMENTO");
                i++;
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar los datos de Departamentos: " + ex);
        }
        return datos;
    }

    /**
     * Ingresa un nuevo departamento indicando nombre abreviatura y encargado
     * @param datos
     */
    public void setDepartamento(String[] datos) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_DEPARTAMENTOS (NOMBRE, ABBREV, ENCARGADO) VALUES (?,?,?)");
            prep.setString(1, datos[0]);
            prep.setString(2, datos[1]);
            prep.setString(3, datos[2]);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Erro al generar registro de Departamento: " + ex);
        }
    }

    /**
     * Elimina un departamento por medio de su id
     * @param id
     */
    public void destroyDepartamento(int id) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_DEPARTAMENTOS WHERE ID_DEPARTAMENTO = ?");
            prep.setInt(1, id);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al borrar elemento: " + ex);
        }
    }

    /**
     * Actualiza un departamento con un id especifico y los siguientes datos
     * Nombre * Abbrev * Encargado
     * @param id
     * @param datos
     */
    public void updDepartamento(int id, String[] datos) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_DEPARTAMENTOS SET NOMBRE = ?, ABBREV = ?, ENCARGADO = ? WHERE ID_DEPARTAMENTO = ?");
            prep.setString(1, datos[0]);
            prep.setString(2, datos[1]);
            prep.setString(3, datos[2]);
            prep.setInt(4, id);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar datos en Departamento: " + ex);
        }
    }
}
