/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.dataBase.crud;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
     * @param accesorios 
     */
    public void setVideoProyector(String[] data, String[] accesorios) {
        
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("INSERT INTO E_VIDEOPROYECTORES(NOMBRE, MARCA, MODELO, NO_SERIE, ACCESORIOS) VALUES (?,?,?,?,?)");
            prep.setString(1, data[0]);
            prep.setString(2, data[1]);
            prep.setString(3, data[2]);
            prep.setString(4, data[3]);
            prep.setObject(5, accesorios);
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
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO FROM E_VIDEOPROYECTORES ORDER BY NOMBRE");
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

    public int getProyectoresCount(boolean estatus) {
        int cantidad = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) "
                    + "FROM E_VIDEOPROYECTORES WHERE ID_VIDEOPROYECTOR IN (SELECT ID_VIDEOPROYECTOR FROM EV_ESTATUS WHERE DISPONIBILIDAD = ?)");
            prep.setBoolean(1, estatus);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) { cantidad = rs.getInt(1); }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar CANTIDAD DE LOS REGISTROS DE PROYECTORES getProyectores BOOLEAN videoproyectorDB: " + ex);
        }
        return cantidad;
    }
    /**
     * Recupera los registros de los videoproyectores con estatus disponible/no disponible > true false
     * ID * Nombre * Marca * Modelo * No_serie * Creado
     * @param estatus
     * @return 
     */
    public String[][] getProyectores(boolean estatus) {
        String[][] datos = new String[getProyectoresCount(estatus)][6];
        int i = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO "
                    + "FROM E_VIDEOPROYECTORES WHERE ID_VIDEOPROYECTOR IN (SELECT ID_VIDEOPROYECTOR FROM EV_ESTATUS WHERE DISPONIBILIDAD = ?)");
            prep.setBoolean(1, estatus);
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
            System.out.println("Error al recuperar LOS REGISTROS DE PROYECTORES getProyectores BOOLEAN videoproyectorDB: " + ex);
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
        String[] datos = new String[7];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO, ACCESORIOS FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?");
            prep.setString(1, noSerie);

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_VIDEOPROYECTOR");
                datos[1] = rs.getString("NOMBRE");
                datos[2] = rs.getString("MARCA");
                datos[3] = rs.getString("MODELO");
                datos[4] = rs.getString("NO_SERIE");
                datos[5] = rs.getString("CREADO");
                datos[6] = rs.getString("ACCESORIOS");
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
        int cant = getCantProy();
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
    
        /**
     * Recupera el tiempo de un videoproyector en servicio los datos presentados
     * con el formato hrs min o min de acuedo a la cantidad de tiempo que fue solicitado
     * <p>El array presenta los datos de la siguiente forma { NombreProyector, Total, Semestre, Mes }</p>
     * @param idProyector
     * @return
     */
    public String[] getProyectorServicio(String idProyector) {
        String[] servicio = new String[4]; // { Nombre proyector, total, semestre, mes }
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT " +
                "(SELECT NOMBRE FROM E_VIDEOPROYECTORES WHERE E_VIDEOPROYECTORES.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR) AS PROYECTOR, " +
                "(SELECT CONCAT((TOTAL/ 60), 'hrs ', (TOTAL % 60), 'min' )) AS TOTAL, " +
                "(SELECT CONCAT((MES/ 60), 'hrs ', (MES % 60), 'min' )) AS MES, " +
                "(SELECT CONCAT((SEMESTRE/ 60), 'hrs ', (SEMESTRE % 60), 'min' )) AS SEMESTRE " +
                "FROM EV_HORASSERVICIO WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, idProyector);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    servicio[i] = rs.getString(i+1);   
                }
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar las horas de servicio de videoproyector getProyectorServicio PrestamoDB: " + ex);
        }
        return servicio;
    }
    
    /**
     * Asigna el tiempo de los videoproyectores en servicio suma en prestamos
     * realizados resolviendo en minutos
     *
     */
    public void setProyectorServicio() {
        PreparedStatement prep;
        int day;
        String miDate1, miDate2;
        Calendar c = Calendar.getInstance(); //calendar toma los meses del 0(enero) a 11(dic)
        int actualMes = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        if (actualMes < 7) {
            c.set(Calendar.MONTH, 05);
            miDate1 = year + "-01-01 00:00:00";
            miDate2 = year + "-06-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
        } else {
            c.set(Calendar.MONTH, 11);
            miDate1 = year + "-07-01 00:00:00";
            miDate2 = year + "-12-" + c.getActualMaximum(Calendar.DAY_OF_MONTH) + " 23:59:59";
        }
        System.out.println("\n\n..::: Reportando Horas Servicio Proyector :::...\nTotal, Semetres y Mes, Semestre:"+ miDate1 + "  -  " + miDate2 + "\n");
        try {
            String sql = "UPDATE EV_HORASSERVICIO SET "+
                "TOTAL = (SELECT SUM ( DATEDIFF('MI', CREADO, ACTUALIZADO) ) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR), "+
                "SEMESTRE = (SELECT SUM(DATEDIFF('MI', CREADO, ACTUALIZADO)) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR AND CREADO BETWEEN ? AND ?), "+
                "MES = (SELECT SUM(DATEDIFF('MI', CREADO, ACTUALIZADO)) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR AND E_PRESTAMOS.CREADO BETWEEN CONCAT(EXTRACT(YEAR FROM CURRENT_TIMESTAMP()),'-',EXTRACT(MONTH FROM CURRENT_TIMESTAMP()),'-01 00:00:00') AND CONCAT(SUBSTRING(DATEADD(M,1,CURRENT_TIMESTAMP()) - DAY(DATEADD(M,1,CURRENT_TIMESTAMP())), 1, 10), ' 23:59:59'))  "+
                "WHERE EV_HORASSERVICIO.ID_VIDEOPROYECTOR IN (SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES);";
            prep = conn.prepareStatement(sql);
            prep.setString(1, miDate1);
            prep.setString(2, miDate2);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error generando total, semestre, mes en tabla EV_HORASSERVICIO setProyectorServicio PrestamoDB: " + e);
        }
    }
    
    /**
     * A partir de indicar el id del proyector  y del imprevisto indicado en a, b, c, d
     * este termina su estado a reparación
     * @param proye 
     * @param imp  
     */
    public void setReparacionPry(String proye, String imp){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE EV_ESTATUS SET NOMBRE = ?, DISPONIBILIDAD = ? WHERE ID_VIDEOPROYECTOR = "
                    + "(SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?)");
            String imprevisto = imp.equals("a")?"Reparación":(imp.equals("b")?"Mantenimiento":(imp.equals("c")?"En Garantía":"De baja"));
            prep.setString(1, imprevisto);
            prep.setBoolean(2, false);
            prep.setString(3, proye);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB:" + ex + "\n\n");
        }
    }
    
    public void updReparacionPryFree(int proye){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE EV_ESTATUS SET NOMBRE = ?, DISPONIBILIDAD = ? WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, "DISPONIBLE");
            prep.setBoolean(2, true);
            prep.setInt(3, proye);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB:" + ex + "\n\n");
        }
    }
    
    public void reportePry(int proye, String[] datos){
        PreparedStatement prep;
        ResultSet rs;
        try{
            prep = conn.prepareStatement("INSERT INTO E_REP_VIDEOPROYECTORES(ID_VIDEOPROYECTOR, TITULO, NOMBRE_ENCARGADO, AREA, DEPTO_REPARADOR, IMPREVISTO, DETALLES) VALUES(?, ?, ?, ?, ?, ?, ?)");
            prep.setInt(1, proye);
            prep.setString(2, datos[0]);
            prep.setString(3, datos[1]);
            prep.setString(4, datos[2]);
            prep.setString(5, datos[3]);
            prep.setString(6, datos[4]);
            prep.setString(7, datos[5]);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB part1:" + ex + "\n\n");
        }
        int idReporte = getIDReporte();
        int idPrestamo = 0;
        String idProf = null;
        String sqlProf = "SELECT ID_PRESTAMO, ID_PROFESOR FROM E_PRESTAMOS WHERE ID_VIDEOPROYECTOR = ? ORDER BY CREADO DESC LIMIT 1;";
        try {
            prep = conn.prepareStatement(sqlProf);
            prep.setInt(1, proye);
            rs = prep.executeQuery();
            while(rs.next()){
                idPrestamo = rs.getInt(1);
                idProf = rs.getString(2);
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB part2:" + e + "\n\n");
        }
        
        try {
            prep = conn.prepareStatement("UPDATE E_REP_VIDEOPROYECTORES " +
            "SET ID_PROFESOR = ?" +
            "WHERE ID_REPORTE_VIDEOPROYECTOR = ?;");
            prep.setString(1, idProf);
            prep.setInt(2, idReporte);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB part3:" + e + "\n\n");
        }
        try {
            prep = conn.prepareStatement("UPDATE E_PRESTAMOS " +
            "SET ESTATUS_DEVOLUCION = TRUE, " +
            "ID_REPORTE_VIDEOPROYECTOR = ? " +
            "WHERE ID_PRESTAMO = ?;");
            prep.setInt(1, idReporte);
            prep.setInt(2, idPrestamo);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("\nError al REPORTAR MANTENIMIENTO SETREPARACIONPRY VIDEOPROYECTORDB part4:" + e + "\n\n");
        }
    }
    
    public int getIDReporte(){
        PreparedStatement prep;
        ResultSet rs;
        int id = 0;
        try {
            prep = conn.prepareStatement("SELECT ID_REPORTE_VIDEOPROYECTOR FROM E_REP_VIDEOPROYECTORES ORDER BY CREADO DESC LIMIT 1");
            rs = prep.executeQuery();
            while(rs.next()){ id = rs.getInt(1); }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar el id del ultimo reporte generado");
        }
        return id;
    }
    
        public String[] unicogetProyectorUnico() {
        String[] datos = new String[7];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_VIDEOPROYECTOR, NOMBRE, MARCA, MODELO, NO_SERIE, CREADO, ACCESORIOS FROM E_VIDEOPROYECTORES ORDER BY CREADO DESC LIMIT 1");

            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                datos[0] = rs.getString("ID_VIDEOPROYECTOR");
                datos[1] = rs.getString("NOMBRE");
                datos[2] = rs.getString("MARCA");
                datos[3] = rs.getString("MODELO");
                datos[4] = rs.getString("NO_SERIE");
                datos[5] = rs.getString("CREADO");
                datos[6] = rs.getString("ACCESORIOS");
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar datos de un proyector getProyector VideoproyectorDB: " + ex);
        }
        return datos;
    }
//    public void setReporteAPrestamo(String noSerie, String credencialUsuario, boolean estatusDevolucion) throws SQLException{
//        String[] otros = new PrestamoDB().getPrestamo(noSerie);
//        PreparedStatement prep;
//        ResultSet rs;
//        int idReporte = getIDReporte();
//        try {
//            String miSQL = "UPDATE E_PRESTAMOS SET ID_ENTRADA = ?, ESTATUS = FALSE, ESTATUS_DEVOLUCION = ?, ID_REPORTE_VIDEOPROYECTOR = "+ idReporte +" WHERE ID_PRESTAMO = ?";
//            prep = conn.prepareStatement(miSQL);
//            prep.setString(1, credencialUsuario);
//            prep.setBoolean(2, estatusDevolucion);
//            prep.setInt(3, Integer.parseInt(otros[0]));
//            prep.executeUpdate();
//            prep.close();
//        } catch (SQLException ex) {
//            System.out.println("\nError al actualizar PRESTAMO a updPrestamo E_PRESTAMOS:" + ex + "\n\n");
//        }
//    }
}
