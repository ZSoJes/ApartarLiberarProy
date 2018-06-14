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
import java.util.ArrayList;
import java.util.HashMap;
import proyector.comboItemProy;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class VideoproyectorDB {

    private final Connection conn;

    public VideoproyectorDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Regresa true/false si existe el videoproyector indicado por su no_serie
     *
     * @param noSerie
     * @return
     */
    public boolean getExisteProyector(String noSerie) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?");
            prep.setString(1, noSerie);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al indicar si existe proyector getExisteProyector VideoproyectorDB: " + ex);
        }
        return existe;
    }

    /**
     * Regresa true/false existe el videoproyector indicado existe por nombre
     *
     * @param nombre
     * @return
     */
    public boolean getExisteNomProyector(String nombre) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_VIDEOPROYECTORES WHERE NOMBRE = ?");
            prep.setString(1, nombre);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Error al indicar si existe proyector por nombre getExisteNomProyector VideoproyectorDB: " + ex);
        }
        return existe;
    }

    /**
     * Regresa la cantidad de videoproyectores existentes
     *
     * @return
     */
    public int getCantProy() {
        int cant = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_VIDEOPROYECTORES");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cant = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la cantidad de datos getCantProy VideoproyectorDB: " + ex);
        }
        return cant;
    }

    /**
     * Asignar valores a un videoproyector nuevo
     * @param data 
     */
    public void setVideoProyector(String[] data) {
        
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("INSERT INTO E_VIDEOPROYECTORES(NOMBRE, MARCA, MODELO, NO_SERIE) VALUES (?,?,?,?)");
            prep.setString(1, data[0]);
            prep.setString(2, data[1]);
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al ingresar videoproyector, revise los datos ingresados setVideoProyector VideoproyectorDB: " + ex);
        }
        try {
            String id = getProyectorID(data[3]);
            prep = conn.prepareStatement("INSERT INTO EV_ESTATUS(ID_VIDEOPROYECTOR, NOMBRE) VALUES(?, ?)");
            prep.setString(1, id);
            prep.setString(2, "DISPONIBLE");
            prep.execute();
            prep.close();
        } catch (SQLException e) {
            System.out.println("error al ingresar en EV_ESTATUS setVideoProyector VideoproyectorDB: " + e);
        }
        try {
            String id = getProyectorID(data[3]);
            prep = conn.prepareStatement("INSERT INTO EV_HORASSERVICIO(ID_VIDEOPROYECTOR) VALUES(?)");
            prep.setString(1, id);
            prep.execute();
            prep.close();
        } catch (SQLException e) {
            System.out.println("error al ingresar en EV_HORASSERVICIO setVideoProyector VideoproyectorDB: " + e);
        }
    }

    /**
     * Recupera los registros de los videoproyectores
     * ID * Nombre * Marca * Modelo * No_serie * Creado
     * @return 
     */
    public String[][] getProyectores() {
        String[][] datos = new String[getCantProy()][6];
        int i = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO FROM E_VIDEOPROYECTORES");
            ResultSet rs = prep.executeQuery();
            
            while (rs.next()) {
                datos[i][0] = rs.getString("ID_VIDEOPROYECTOR");
                datos[i][1] = rs.getString("NOMBRE");
                datos[i][2] = rs.getString("MARCA");
                datos[i][3] = rs.getString("MODELO");
                datos[i][4] = rs.getString("NO_SERIE");
                datos[i][5] = rs.getString("CREADO");
                i++;
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar TODOS LOS REGISTROS DE PROYECTORES getProyectores videoproyectorDB: " + ex);
        }
        return datos;
    }

    /**
     * Permite recuperar el numero de serie con el que escanea un proyetor a
     * partir de su id este debe ser tipo cadena
     *
     * @param id
     * @return
     */
    public String getProyectorNoSerie(String id) {
        String noSerie = "";
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT NO_SERIE FROM E_VIDEOPROYECTORES WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, id);

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                noSerie = rs.getString(1);
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar NO SERIE getProyectorNoSerie VideoproyectorDB: " + ex);
        }
        return noSerie;
    }

    /**
     * Permite recuperar el identificador de un proyector a partir de su numero
     * de serie de tipo cadena
     *
     * @param noSerie
     * @return
     */
    public String getProyectorID(String noSerie) {
        String id = "";
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?");
            prep.setString(1, noSerie);

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                id = rs.getString(1);
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar ID proyector getProyectorID videoproyectorDB: " + ex);
        }
        return id;
    }

    /**
     * Recupera un videoproyector a partir del No_Serie 
     * Id * Nombre * Marca * Modelo * No_Serie * Creado
     *
     * @param noSerie
     * @return
     */
    public String[] getProyector(String noSerie) {
        String[] datos = new String[6];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?");
            prep.setString(1, noSerie);

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_VIDEOPROYECTOR");
                datos[1] = rs.getString("NOMBRE");
                datos[2] = rs.getString("MARCA");
                datos[3] = rs.getString("MODELO");
                datos[4] = rs.getString("NO_SERIE");
                datos[5] = rs.getString("CREADO");
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar datos de un proyector getProyector VideoproyectorDB: " + ex);
        }
        return datos;
    }

    /**
     * Eliminar el registro de un videoproyector a partir del no_serie
     * @param noSerie 
     */
    public void destroyVideoproy(String noSerie) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?");
            prep.setString(1, noSerie);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al borrar proyector destroyVideoproy VideoproyectorDB: " + ex);
        }
    }

    /**
     * Actualiza la informacion del videoproyector ingresando id y una serie de datos
     * Nombre * Marca * Modelo * No_serie
     * @param id
     * @param datos 
     */
    public void updVideoproyector(int id, String[] datos) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_VIDEOPROYECTORES SET NOMBRE = ? , MARCA = ?, MODELO = ?, NO_SERIE = ?"
                    + " WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, datos[0]);
            prep.setString(2, datos[1]);
            prep.setString(3, datos[2]);
            prep.setString(4, datos[3]);
            prep.setInt(5, id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar datos en updVideoproyector VideoproyectorDB: " + ex);
        }
    }

    /**
     * Retorna los videoproyectores que existen de acuerdo a su estatus
     * disponible = true / no disponible = false
     *
     * @param disponible
     * @return
     */
    public int evStatusDisponible(boolean disponible) {
        int cant = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM EV_ESTATUS WHERE DISPONIBILIDAD = ?");
            prep.setBoolean(1, disponible);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cant = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar la cantidad de datos evStatusDisponible VideoproyectorDB: " + ex);
        }
        return cant;
    }

    /**
     * Regresa el estatus de un proyector especifico a partir de su id si no se
     * encuentran los datos regresa un array string empty
     *
     * @param id
     * @return
     */
    public String[] showMeEvStatus(String id) {
        String[] datos = {"", "", "", ""};
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT * FROM EV_ESTATUS WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_ESTATUS");
                datos[1] = rs.getString("ID_VIDEOPROYECTOR");
                datos[2] = rs.getString("NOMBRE");
                datos[3] = rs.getString("DISPONIBILIDAD");
            }
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error al recuperar datos showMeEvStatus VideoproyectorDB: " + ex);
        }
        return datos;
    }

    public HashMap<String, String> primerCombo() {
        HashMap<String, String> map = new HashMap<>();
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, NO_SERIE FROM E_VIDEOPROYECTORES ORDER BY ID_VIDEOPROYECTOR");

            rs = prep.executeQuery();
            comboItemProy cmi;
            while (rs.next()) {
                cmi = new comboItemProy(rs.getString(2), rs.getString(3));
                if (Boolean.valueOf(showMeEvStatus(rs.getString(1))[3])) {
                    map.put(cmi.getNoSerie(), cmi.getVidNombre());
                }
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al generar comboBox VideoproyectorDB: " + ex);
        }
        return map;
    }
    
    public HashMap<String, String> devolucionCombo() {
        HashMap<String, String> map = new HashMap<>();
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR FROM E_PRESTAMOS WHERE ESTATUS = TRUE ORDER BY ID_PRESTAMO");

            rs = prep.executeQuery();
            comboItemProy cmi;
            while (rs.next()) {
                String noSerie = getProyectorNoSerie(rs.getString(1));
                String[] proy = getProyector(noSerie);
                cmi = new comboItemProy(proy[1], proy[4]);
                map.put(cmi.getNoSerie(), cmi.getVidNombre());
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al generar comboBox VideoproyectorDB: " + ex);
        }
        return map;
    }
    
    public String[][] hrsServicio(){
        PreparedStatement prep;
        ResultSet rs;
        int cant = 0;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*) FROM EV_HORASSERVICIO");
            rs = prep.executeQuery();
            while(rs.next()){ cant = rs.getInt(1); }
            rs.close();
            prep.close();
        } catch (Exception e) { System.out.println("Error al recuperar la cantidad de datos de ev_horasservicio hrsServicio: " + e);}
        String[][] datos = new String[cant][4];
        try {
            prep = conn.prepareStatement("SELECT " +
                                        "(SELECT NOMBRE FROM E_VIDEOPROYECTORES WHERE E_VIDEOPROYECTORES.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR) AS PROYECTOR, " +
                                        "(SELECT CONCAT((TOTAL/ 60), 'hrs ', (TOTAL % 60), 'min' )) AS TOTAL, " +
                                        "(SELECT CONCAT((MES/ 60), 'hrs ', (MES % 60), 'min' )) AS MES, " +
                                        "(SELECT CONCAT((SEMESTRE/ 60), 'hrs ', (SEMESTRE % 60), 'min' )) AS SEMESTRE " +
                                        "FROM EV_HORASSERVICIO");
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                datos[i][0] = rs.getString(1);
                datos[i][1] = rs.getString(2);
                datos[i][2] = rs.getString(3);
                datos[i][3] = rs.getString(4);
                i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al solicitar datos de ev_horasservicio hrsServicio(): " + e);
        }
        return datos;
    } 
}
