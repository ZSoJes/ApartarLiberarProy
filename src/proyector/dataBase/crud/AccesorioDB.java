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
import java.util.Iterator;
import proyector.dataBase.Conexion;

/**
 *
 * @author Juan
 */
public class AccesorioDB {

    private final Connection conn;

    public AccesorioDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Permite ingresar un nuevo accesorio a la tabla E_ACCESORIOS solicitando
     * en un array NOMBRE * EXISTENCIAS en numero
     *
     * @param accesorio
     */
    public void setAccesorio(String accesorio[]) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_ACCESORIOS(NOMBRE, EXISTENCIAS, DISPONIBLE, DESCRIPCION) "
                    + "VALUES (?,?,?,?)");
            int exist = Integer.parseInt(accesorio[1]);
            prep.setString(1, accesorio[0]);
            prep.setInt(2, exist);
            prep.setInt(3, exist);
            prep.setString(4, accesorio[2]);
            prep.execute();
            prep.close();
            System.out.println("Se agrego nuevo Accesorio::...");
        } catch (SQLException e) {
            System.out.println("Error al agregar nuevo accesorio setAccesorio AccesorioDB: " + e);
        }
    }

    /**
     * Recupera la cantidad de accesorios si es true si es false recupera todos
     * los accesorios con disponibilidad
     *
     * @param todosOexist
     * @return
     */
    public int getCantAccesorios(boolean todosOexist) {
        int a = 0;
        try {
            PreparedStatement prep;
            if (todosOexist) {
                prep = conn.prepareStatement("SELECT COUNT(*) FROM E_ACCESORIOS");
            } else {
                prep = conn.prepareStatement("SELECT COUNT(*) FROM E_ACCESORIOS WHERE DISPONIBLE > 0");
            }
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar la cantidad de registros de accesorios getCantAccesorios AccesorioDB");
        }
        return a;
    }

    /**
     * Recupera un accesorio a traves del id
     *
     * @param id
     * @return
     */
    public String[] getAccesorio(String id) {
        String array[] = new String[5];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT * FROM E_ACCESORIOS WHERE ID_ACCESORIO = ?");
            prep.setInt(1, Integer.parseInt(id));
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[0] = rs.getString("ID_ACCESORIO");
                array[1] = rs.getString("NOMBRE");
                array[2] = rs.getString("EXISTENCIAS");
                array[3] = rs.getString("DISPONIBLE");
                array[4] = rs.getString("DESCRIPCION");
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de accesorios getAccesorios AccesorioDB");
        }
        return array;
    }

    /**
     * Recupera el ID de un accesorio a traves del nombre que sea indicado
     *
     * @param nombre
     * @return
     */
    public int getAccesorioID(String nombre) {
        int id = 0; //posible error si el elemento con id 0 no existe en db es posible error
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_ACCESORIO FROM E_ACCESORIOS WHERE NOMBRE = ?");
            prep.setString(1, nombre);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de accesorios getAccesorios AccesorioDB");
        }
        return id;
    }

    /**
     * Recupera los accesorios existentens si se indica false si se indica true
     * recupera solo los que tienen disponibilidad
     *
     * @param todosOexist
     * @return
     */
    public String[][] getAccesorios(boolean todosOexist) {
        String array[][] = new String[getCantAccesorios(true)][5];
        int i = 0;
        try {
            PreparedStatement prep;
            if (todosOexist) {
                array = new String[getCantAccesorios(false)][5];
                prep = conn.prepareStatement("SELECT * FROM E_ACCESORIOS WHERE DISPONIBLE > 0");
            } else {
                prep = conn.prepareStatement("SELECT * FROM E_ACCESORIOS");
            }
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_ACCESORIO");
                array[i][1] = rs.getString("NOMBRE");
                array[i][2] = rs.getString("EXISTENCIAS");
                array[i][3] = rs.getString("DISPONIBLE");
                array[i][4] = rs.getString("DESCRIPCION");
                i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de accesorio getAccesorios AccesorioDB: " + e);
        }
        return array;
    }

    /**
     * Recupera los productos disponibles para prestar de un accesorio
     * especifico se espera id del accesorio
     *
     * @param id
     * @return
     */
    public int getExistenciasAccesorio(int id) {
        int existencias = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT DISPONIBLE FROM E_ACCESORIOS WHERE ID_ACCESORIO = ?");
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existencias = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar las existencias del accesorio indicado getExistenciasAccesorio AccesorioDB:" + ex);
        }
        return existencias;
    }

    /**
     * Aumenta o disminuye el registro de accesorio en tanto a disponibilidad
     * true para aumentar false para disminuir
     *
     * @param agregarDismin
     * @param accesorio
     */
    public void updAccesorioAR(boolean agregarDismin, int accesorio) {
        try {
            PreparedStatement prep;
            if (agregarDismin) {
                prep = conn.prepareStatement("UPDATE E_ACCESORIOS SET DISPONIBLE = DISPONIBLE+1 WHERE ID_ACCESORIO = ? ");
            } else {
                prep = conn.prepareStatement("UPDATE E_ACCESORIOS SET DISPONIBLE = DISPONIBLE-1 WHERE ID_ACCESORIO = ? ");
            }
            prep.setInt(1, accesorio);
            prep.executeUpdate();
            prep.close();
            System.out.println("Se actualizo el accesorio indicado true para agregar:" + agregarDismin);
        } catch (SQLException e) {
            System.out.println("Error al actualizar registro de accesorio updAccesorio AccesorioDB: " + e);
        }
    }

    /**
     * Actualiza los datos de un accesorio utilizando un array con la
     * información organizada en el siguietne orden id accesorio * nombre *
     * existencias * descripcion
     *
     * @param datos
     */
    public void updAccesorio(String[] datos) {
        int exist = Integer.parseInt(datos[2]);
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_ACCESORIOS "
                    + "SET NOMBRE = ?, EXISTENCIAS = ?, DISPONIBLE = ?, DESCRIPCION = ? "
                    + "WHERE ID_ACCESORIO = ?");
            prep.setString(1, datos[1]);
            prep.setInt(2, exist);
            prep.setInt(3, exist);
            prep.setString(4, datos[3]);
            prep.setInt(5, Integer.parseInt(datos[0]));
            prep.executeUpdate();
            prep.close();
            System.out.println("Se actualizo el accesorio indicado");
        } catch (SQLException e) {
            System.out.println("Error al actualizar registro de accesorio updAccesorio AccesorioDB: " + e);
        }
    }

    /**
     * Recupera el registro de accesorios prestados
     * e indica el profesor que se llevo dicho material
     * @return 
     */
    public String[][] getRegAccesorios(boolean hoy) {
        int lengthDatos = 0;
        try {//obtener la cantidad de registros de hoy o los existentes de accesorios prestados
            PreparedStatement prep;
            String sql = "SELECT COUNT(*) FROM EPV_ACCESORIOS ";
            if(hoy){prep = conn.prepareStatement(sql.concat("WHERE CREADO > CURRENT_DATE()"));}
            else{prep = conn.prepareStatement(sql);}
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                lengthDatos = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar longitud de datos de EPV_ACCESORIOS getRegAccesorios AccesorioDB: " + e);
        }
        String[][] datos = new String[lengthDatos][5];
        //SELECT ID_EPV_ACCESORIO,(SELECT (SELECT CONCAT(NOMBRE,' ',A_PATERNO,' ',A_MATERNO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR ) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_PRESTAMO = EPV_ACCESORIOS.ID_PRESTAMO) AS PROFESOR, (SELECT NOMBRE FROM E_ACCESORIOS WHERE E_ACCESORIOS.ID_ACCESORIO = EPV_ACCESORIOS.ID_ACCESORIO) AS ACCESORIO, TO_CHAR(CREADO,'HH24:MI') AS CREADO_HORA, TO_CHAR(CREADO,'dd/MM/yyyy') AS CREADO_FECHA FROM EPV_ACCESORIOS ORDER BY ID_EPV_ACCESORIO DESC
        try {
            PreparedStatement prep;
            String sql = "SELECT ID_EPV_ACCESORIO, " +
                         "    (SELECT (SELECT CONCAT(NOMBRE,\' \',A_PATERNO,\' \',A_MATERNO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR) " +
                         "    FROM E_PRESTAMOS " +
                         "    WHERE E_PRESTAMOS.ID_PRESTAMO = EPV_ACCESORIOS.ID_PRESTAMO) AS PROFESOR, " +
                         "    (SELECT NOMBRE FROM E_ACCESORIOS WHERE E_ACCESORIOS.ID_ACCESORIO = EPV_ACCESORIOS.ID_ACCESORIO) AS ACCESORIO, " +
                         "    TO_CHAR(CREADO,'HH24:MI') AS CREADO_HORA, " +
                         "    TO_CHAR(CREADO,'dd/MM/yyyy') AS CREADO_FECHA " +
                         "FROM EPV_ACCESORIOS ";
            if (hoy){//obtener los registros de hoy o los existentes de accesorios prestados
                prep = conn.prepareStatement(sql.concat("WHERE CREADO > CURRENT_DATE() ORDER BY ID_EPV_ACCESORIO DESC"));
            }else{
                prep = conn.prepareStatement(sql.concat("ORDER BY ID_EPV_ACCESORIO DESC"));
            }
            ResultSet rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                datos[i][0] = rs.getString(1);
                datos[i][1] = rs.getString(2);
                datos[i][2] = rs.getString(3);
                datos[i][3] = rs.getString(4);
                datos[i][4] = rs.getString(5);
                i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar el registro de accesorios de getRegAccesorios AccesorioDB: " + e);
        }
        return datos;
    }
    
    public String[] getAccDeProf(String idProfe){
        ArrayList<String> alAccesorios = new ArrayList<String>();
        String[] misAcc = {};
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT " +
                                         "    (SELECT NOMBRE FROM E_ACCESORIOS WHERE E_ACCESORIOS.ID_ACCESORIO = EPV_ACCESORIOS.ID_ACCESORIO) " +
                                         "FROM EPV_ACCESORIOS " +
                                         "WHERE " +
                                         "    EPV_ACCESORIOS.ID_PRESTAMO = " +
                                         "    (SELECT ID_PRESTAMO FROM E_PRESTAMOS WHERE ID_PROFESOR = ? AND ESTATUS = TRUE)");
            prep.setString(1, idProfe);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                alAccesorios.add(rs.getString(1));
            }
            misAcc = new String[alAccesorios.size()];
            Iterator<String> iteraArrL = alAccesorios.iterator();
            int i = 0;
            while(iteraArrL.hasNext()){
                    misAcc[i] = iteraArrL.next();
                    System.out.println("accesorio:"+misAcc[i]);
                    i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los articulos solicitados por el profesor con credencial: " + idProfe + " : " + e);
        }
        alAccesorios.clear();
        return misAcc;
    }
    
    public void destroyAcc(int id) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_ACCESORIOS WHERE ID_ACCESORIO = ?");
            prep.setInt(1, id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al borrar proyector destroyVideoproy VideoproyectorDB: " + ex);
        }
    }
    
    public void reporteAcc(String profe, String titulo, String detalles, int[] acc){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_REP_ACCESORIOS(ID_PROFESOR, TITULO, DETALLES) VALUES(?, ?, ?)");
            prep.setString(1, profe);
            prep.setString(2, titulo);
            prep.setString(3, detalles);
            prep.executeUpdate();
            prep.close();
        }catch(SQLException ex){
            System.out.println("\nError al REPORTAR reporteACC AccesorioDB:" + ex + "\n\n");
        }
        int id = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_REPORTE_ACCESORIO FROM E_REP_ACCESORIOS ORDER BY ID_REPORTE_ACCESORIO DESC LIMIT 1");
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            prep.close();
            rs.close();
        }catch(SQLException ex){
            System.out.println("\nError al REPORTAR reporteACC AccesorioDB:" + ex + "\n\n");
        }
        for(int ac : acc){
            try{
                PreparedStatement prep;
                prep = conn.prepareStatement("INSERT INTO EREPA_ACCESORIOS(ID_ACCESORIO, ID_REPORTE_ACCESORIO) VALUES(?, ?)");
                prep.setInt(1, ac);//accesorios
                prep.setInt(2, id);
                prep.executeUpdate();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al REPORTAR reporteACC AccesorioDB:" + ex + "\n\n");
            }
            try{
                PreparedStatement prep;
                prep = conn.prepareStatement("UPDATE E_ACCESORIOS SET EXISTENCIAS=EXISTENCIAS-1,DISPONIBLE=DISPONIBLE-1 WHERE ID_ACCESORIO = ?");
                prep.setInt(1, ac);//accesorios
                prep.executeUpdate();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al REPORTAR reporteACC AccesorioDB:" + ex + "\n\n");
            }
        }
    }
    
    /**
     * Indica si el articulo ya existe en la DB a traves del nombre
     *
     * @param nombre
     * @return
     */
    public boolean accesorioExist(String nombre) {
        boolean art = false;
        ResultSet rs;
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_ACCESORIOS WHERE LOWER(NOMBRE) LIKE LOWER(?);");
            prep.setString(1, nombre);
            rs = prep.executeQuery();
            while (rs.next()) {
                art = rs.getInt(1)>0?true:false;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar accesorio accesorioExist AccesorioDB:"+e);
        }
        return art;
    }
    
    /**
     * Metodo encargado de ingresar accesorios prestados al registro
     * EPV_ACCESORIOS
     *
     * @param accesorioID
     * @param prestamoID
     */
    public void setPrestamoAccesorio(int accesorioID, int prestamoID) {
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("INSERT INTO EPV_ACCESORIOS(ID_ACCESORIO, ID_PRESTAMO) VALUES (?,?);");
            prep.setInt(1, accesorioID);
            prep.setInt(2, prestamoID);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al insertar prestamo en registro");
        }
    }
}

