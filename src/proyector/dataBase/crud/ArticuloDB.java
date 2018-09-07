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
public class ArticuloDB {

    private final Connection conn;

    public ArticuloDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Permite ingresar un nuevo articulo solicitando en un array NOMBRE *
     * EXISTENCIAS en numero
     *
     * @param articulo
     */
    public void setArticulo(String[] articulo) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_ARTICULOS(NOMBRE, EXISTENCIAS, DESCRIPCION) VALUES (?,?,?)");
            prep.setString(1, articulo[0]);
            prep.setInt(2, Integer.parseInt(articulo[1]));
            prep.setString(3, articulo[2]);
            prep.execute();
            prep.close();
            System.out.println("  (>>) Nuevo registro de articulo::...");
        } catch (SQLException e) {
            System.out.println("  ($) Error al agregar nuevo articulo setArticulo articuloDB: " + e);
        }
    }

    /**
     * Recupera la cantidad de articulos si es true, si es false recupera todos
     * los articulos con disponibilidad
     *
     * @param todosOexist
     * @return
     */
    public int getCantArticulos(boolean todosOexist) {
        int a = 0;
        ResultSet rs;
        try {
            PreparedStatement prep;
            if (todosOexist) {
                prep = conn.prepareStatement("SELECT COUNT(*) FROM E_ARTICULOS");
            } else {
                prep = conn.prepareStatement("SELECT COUNT(*) FROM E_ARTICULOS WHERE EXISTENCIAS > 0");
            }
            rs = prep.executeQuery();
            while (rs.next()) {
                a = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("  ($) Error al recuperar la cantidad de registros de articulos getCantArticulos articuloDB " + e);
        }
        return a;
    }

    /**
     * Recupera un articulo a traves del id
     *
     * @param id
     * @return
     */
    public String[] getArticulo(String id) {
        String array[] = new String[4];
        ResultSet rs;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT * FROM E_ARTICULOS WHERE ID_ARTICULO = ?");
            prep.setInt(1, Integer.parseInt(id));
            rs = prep.executeQuery();
            while (rs.next()) {
                array[0] = rs.getString("ID_ARTICULO");
                array[1] = rs.getString("NOMBRE");
                array[2] = rs.getString("EXISTENCIAS");
                array[3] = rs.getString("DESCRIPCION");
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de articulos getArticulos ArticuloDB " + e);
        }
        return array;
    }

    /**
     * Recupera el ID de un articulo a traves del nombre que sea indicado
     *
     * @param nombre
     * @return
     */
    public int getArticuloID(String nombre) {
        int id = 0; //posible error si el elemento con id 0 no existe en db es posible error
        ResultSet rs;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_ARTICULO FROM E_ARTICULOS WHERE LOWER(NOMBRE) = LOWER(?)");
            prep.setString(1, nombre);
            rs = prep.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de articulos getArticulos ArticuloDB");
        }
        return id;
    }

    /**
     * Recupera los articulos existentens si se indica false, si se indica true
     * recupera solo los que tienen disponibilidad
     *
     * @param todosOexist
     * @return
     */
    public String[][] getArticulos(boolean todosOexist) {
        String array[][] = new String[getCantArticulos(true)][4];
        int i = 0;
        ResultSet rs;
        try {
            PreparedStatement prep;
            if (todosOexist) {
                array = new String[getCantArticulos(false)][4];
                prep = conn.prepareStatement("SELECT * FROM E_ARTICULOS WHERE EXISTENCIAS > 0");
            } else {
                prep = conn.prepareStatement("SELECT * FROM E_ARTICULOS");
            }
            rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_ARTICULO");
                array[i][1] = rs.getString("NOMBRE");
                array[i][2] = rs.getString("EXISTENCIAS");
                array[i][3] = rs.getString("DESCRIPCION");
                i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los registros de articulos getArticulos ArticuloDB: " + e);
        }
        return array;
    }

    /**
     * Recupera la cantidad disponible para prestar de un articulo
     * especifico<br>Se espera id del articulo
     *
     * @param id
     * @return
     */
    public int getExistenciasArticulo(int id) {
        int existencias = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT EXISTENCIAS FROM E_ARTICULOS WHERE ID_ARTICULO = ?");
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existencias = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar las existencias del articulo indicado getExistenciasArticulo ArticuloDB:" + ex);
        }
        return existencias;
    }

    /**
     * Aumenta o disminuye el registro de articulo en tanto a
     * disponibilidad<br><b>true</b> para aumentar<br><b>false</b> para
     * disminuir
     *
     * @param agregarDismin
     * @param articulo
     */
    public void updArticuloAR(boolean agregarDismin, int articulo) {
        try {
            PreparedStatement prep;
            if (agregarDismin) {
                prep = conn.prepareStatement("UPDATE E_ARTICULOS SET EXISTENCIAS = EXISTENCIAS + 1 WHERE ID_ARTICULO = ? ");
            } else {
                prep = conn.prepareStatement("UPDATE E_ARTICULOS SET EXISTENCIAS = EXISTENCIAS - 1 WHERE ID_ARTICULO = ? ");
            }
            prep.setInt(1, articulo);
            prep.executeUpdate();
            prep.close();
            System.out.println("Se actualizo el articulo indicado true para agregar:" + agregarDismin);
        } catch (SQLException e) {
            System.out.println("Error al actualizar registro de articulo updArticulo ArticuloDB: " + e);
        }
    }

    /**
     * Actualiza los datos de un articulo utilizando un array con
     * la<br>información organizada en el siguietne orden:<b><ul><li>id
     * articulo</li><li>Nombre</li><li>existencias</li><li>descripcion</li></ul></b>
     *
     * @param datos
     */
    public void updArticulo(String[] datos) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_ARTICULOS "
                    + "SET NOMBRE = ?, EXISTENCIAS = ?, DESCRIPCION = ? "
                    + "WHERE ID_ARTICULO = ?");
            prep.setString(1, datos[1]);
            prep.setInt(2, Integer.parseInt(datos[2]));
            prep.setString(3, datos[3]);
            prep.setInt(4, Integer.parseInt(datos[0]));
            prep.executeUpdate();
            prep.close();
            System.out.println("Datos del articulo actualizados!");
        } catch (SQLException e) {
            System.out.println("Error al actualizar registro de articulos updArticulo articuloDB: " + e);
        }
    }

    public String[][] getRegArticulos(boolean regDeHoy, String fch1, String fch2) {
        ResultSet rs;
        PreparedStatement prep;
        String array[][] = null;
        int lengthDatos = 0;

        try {
            if (regDeHoy) {
                prep = conn.prepareStatement("SELECT COUNT(*) FROM EPV_ARTICULOS WHERE CREADO BETWEEN CAST(? AS TIMESTAMP) AND CAST(? AS TIMESTAMP)");
                prep.setString(1, fch1);
                prep.setString(2, fch2);
                rs = prep.executeQuery();
                while (rs.next()) {
                    lengthDatos = rs.getInt(1);
                }
                rs.close();
                prep.close();
                array = new String[lengthDatos][5];
            } else {
                array = new String[getCantArticulos(true)][5];
            }
            //----------------------------------------------------------------------------------------------------
            String sql = "SELECT ID_EPV_ARTICULO, "
                    + "(SELECT (SELECT CONCAT(NOMBRE,' ',A_PATERNO,' ',A_MATERNO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR) "
                    + "FROM E_PRESTAMOS "
                    + "WHERE E_PRESTAMOS.ID_PRESTAMO = EPV_ARTICULOS.ID_PRESTAMO) AS PROFESOR, "
                    + "(SELECT NOMBRE FROM E_ARTICULOS WHERE E_ARTICULOS.ID_ARTICULO = EPV_ARTICULOS.ID_ARTICULO) AS ARTICULO, "
                    + "TO_CHAR(CREADO,'HH:MI AM') AS CREADO_HORA, "
                    + "TO_CHAR(CREADO,'dd/MM/yyyy') AS CREADO_FECHA "
                    + "FROM EPV_ARTICULOS ";
            if (regDeHoy) {
                sql = sql.concat("WHERE CREADO BETWEEN CAST(? AS TIMESTAMP) AND CAST(? AS TIMESTAMP)");
            }
            prep = conn.prepareStatement(sql);
            if(regDeHoy){
                prep.setString(1, fch1);
                prep.setString(2, fch2);
            }
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < 5; j++) {
                    array[i][j] = rs.getString(j + 1);
                }
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error al recuperar articulos getRegArticulos Simple ArticuloDB " + e);
        }
        return array;
    }

    /**
     * Recupera el registro de articulos prestados e indica el profesor que se
     * llevo dicho material require indicar una fecha si indica true
     * que no tengan reporte
     * @param aplicarFecha
     * @param fechaUno
     * @param fechaDos
     * @param filtroReporte
     * @return
     */
    public String[][] getRegArticulos(String fechaUno, String fechaDos) {
        int lengthDatos = 0;
        String[][] datos = null;
        ResultSet rs;
        try {//obtener la cantidad de registros de hoy o los existentes de accesorios prestados
            PreparedStatement prep;
            String sql = "SELECT COUNT(*) FROM EPV_ARTICULOS ";
            
                sql = sql.concat("WHERE CREADO BETWEEN CAST(? AS TIMESTAMP) AND CAST(? AS TIMESTAMP) AND ESTATUS = TRUE");
                //if(filtroReporte){
                //    sql = sql.concat(" AND ESTATUS = TRUE");
                //}
                prep = conn.prepareStatement(sql);
                prep.setString(1, fechaUno);
                prep.setString(2, fechaDos);
            

            rs = prep.executeQuery();
            while (rs.next()) {
                lengthDatos = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar longitud de datos de EPV_ARTICULOS getRegArticulos ArticuloDB: " + e);
        }
        
        try {
            PreparedStatement prep;
            datos = new String[lengthDatos][5];
            String sql = "SELECT ID_EPV_ARTICULO, "
                    + "    (SELECT (SELECT CONCAT(NOMBRE,\' \',A_PATERNO,\' \',A_MATERNO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR) "
                    + "    FROM E_PRESTAMOS "
                    + "    WHERE E_PRESTAMOS.ID_PRESTAMO = EPV_ARTICULOS.ID_PRESTAMO) AS PROFESOR, "
                    + "    (SELECT NOMBRE FROM E_ARTICULOS WHERE E_ARTICULOS.ID_ARTICULO = EPV_ARTICULOS.ID_ARTICULO) AS ARTICULO, "
                    + "    TO_CHAR(CREADO,'HH:MI AM') AS CREADO_HORA, "
                    + "    TO_CHAR(CREADO,'dd/MM/yyyy') AS CREADO_FECHA "
                    + "FROM EPV_ARTICULOS WHERE CREADO BETWEEN CAST(? AS TIMESTAMP) AND CAST(? AS TIMESTAMP) AND ESTATUS = TRUE ORDER BY ID_EPV_ARTICULO DESC";
                prep = conn.prepareStatement(sql);
                prep.setString(1, fechaUno);
                prep.setString(2, fechaDos);
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < 4; j++) {
                    datos[i][j] = rs.getString(j+1);   
                }
                
                i++;
                
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar el registro de ARTICULOS de getRegARTICULOS ArticuloDB: " + e);
        }
        return datos;
    }

    public String[] getReg(String id) {
        String[] datos = new String[4];
        String sql = "SELECT * FROM EPV_ARTICULOS WHERE ID_EPV_ARTICULO = ?";
        PreparedStatement prep;
        ResultSet rs;
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, Integer.parseInt(id));
            rs = prep.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 4; i++) {
                    datos[i] = rs.getString(i + 1);
                }
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar registro getRep ArticuloDB: " + e);
        }
        return datos;
    }

    public String[] getArtDeProf(String idProfe) {
        ArrayList<String> alArticulos = new ArrayList<>();
        String[] misArtic = {};
        ResultSet rs;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT "
                    + "    (SELECT NOMBRE FROM E_ARTICULOS WHERE E_ARTICULOS.ID_ARTICULO = EPV_ARTICULOS.ID_ARTICULO) "
                    + "FROM EPV_ARTICULOS "
                    + "WHERE "
                    + "    EPV_ARTICULOS.ID_PRESTAMO = "
                    + "    (SELECT ID_PRESTAMO FROM E_PRESTAMOS WHERE ID_PROFESOR = ? AND ESTATUS = TRUE)");
            prep.setString(1, idProfe);
            rs = prep.executeQuery();
            while (rs.next()) {
                alArticulos.add(rs.getString(1));
            }
            misArtic = new String[alArticulos.size()];
            Iterator<String> iteraArrL = alArticulos.iterator();
            int i = 0;
            while (iteraArrL.hasNext()) {
                misArtic[i] = iteraArrL.next();
                System.out.println("ARTICULO:" + misArtic[i]);
                i++;
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los articulos solicitados por el profesor con credencial: " + idProfe + " : " + e);
        }
        alArticulos.clear();
        return misArtic;
    }

    public void destroyArt(int id) {
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_ARTICULOS WHERE ID_ARTICULO = ?");
            prep.setInt(1, id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al borrar proyector destroyVideoproy VideoproyectorDB: " + ex);
        }
    }

    public void reporteArt(String profe, String titulo, String detalles, int articuloID, int registroID) {
        ResultSet rs;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_REP_ARTICULOS(ID_PROFESOR, TITULO, DETALLES) VALUES(?, ?, ?)");
            prep.setString(1, profe);
            prep.setString(2, titulo);
            prep.setString(3, detalles);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al REPORTAR reporteART ARTICULODB:" + ex + "\n\n");
        }
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE EPV_ARTICULOS SET ESTATUS = FALSE WHERE ID_EPV_ARTICULO = ?");
            prep.setInt(1, registroID);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al indicar registro como reportado ARTICULODB:" + ex + "\n\n");
        }
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO EREPA_ARTICULOS(ID_ARTICULO, ID_REPORTE_ARTICULO) VALUES(?, (SELECT ID_REPORTE_ARTICULO FROM E_REP_ARTICULOS ORDER BY ID_REPORTE_ARTICULO DESC LIMIT 1))");
            prep.setInt(1, articuloID);//articulos
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al REPORTAR reporteACC articuloDB:" + ex + "\n\n");
        }
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_ARTICULOS SET EXISTENCIAS = EXISTENCIAS - 1 WHERE ID_ARTICULO = ?");
            prep.setInt(1, articuloID);//articulos
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al REPORTAR reporteACC ARTICULODB:" + ex + "\n\n");
        }
    }

    public String profIDPrestamo(String prestamoID) {
        String id = null;
        PreparedStatement prep;
        ResultSet rs;
        String sql = "SELECT ID_PROFESOR FROM E_PRESTAMOS WHERE ID_PRESTAMO = ?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, prestamoID);
            rs = prep.executeQuery();
            while (rs.next()) {
                id = rs.getString(1);
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar id profesor de prestamo profIDPrestamo: " + e);
        }
        return id;
    }

    /**
     * Indica si el articulo ya existe en la DB a traves del nombre
     *
     * @param nombre
     * @return
     */
    public boolean articuloExist(String nombre) {
        boolean art = false;
        ResultSet rs;
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*)>0 FROM E_ARTICULOS WHERE LOWER(NOMBRE) LIKE LOWER(?);");
            prep.setString(1, nombre);
            rs = prep.executeQuery();
            while (rs.next()) {
                art = rs.getBoolean(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al verificar articulos articuloExist ArticuloDB:" + e);
        }
        return art;
    }

    /**
     * Metodo encargado de ingresar articulos prestados al registro
     * EPV_ARTICULOS
     *
     * @param articuloID
     * @param prestamoID
     */
    public void setPrestamoArticulo(int articuloID, int prestamoID) {
        PreparedStatement prep;
        try {
            prep = conn.prepareStatement("INSERT INTO EPV_ARTICULOS(ID_ARTICULO, ID_PRESTAMO) VALUES (?,?);");
            prep.setInt(1, articuloID);
            prep.setInt(2, prestamoID);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al insertar prestamo en registro: " + ex);
        }
    }

    public ArrayList<String[]> getListaR() {
        ArrayList<String[]> arrList = new ArrayList<String[]>();
        PreparedStatement prep;
        ResultSet rs;
        String sql = "SELECT "
                + "INTER.ID_REPORTE_ARTICULO,"
                + "SELECT NOMBRE FROM E_ARTICULOS WHERE E_ARTICULOS.ID_ARTICULO = INTER.ID_ARTICULO AS ARTICULO,"
                + "SELECT CONCAT(NOMBRE, ' ', A_PATERNO, ' ', A_MATERNO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = EREPART.ID_PROFESOR AS PROFESOR, "
                + "EREPART.TITULO AS TITULO, "
                + "TO_CHAR(INTER.CREADO, 'DD-MM-YYYY  HH:MI AM') AS CREADO "
                + "FROM EREPA_ARTICULOS AS INTER "
                + "INNER JOIN E_REP_ARTICULOS EREPART ON EREPART.ESTATUS = TRUE AND INTER.ID_REPORTE_ARTICULO = EREPART.ID_REPORTE_ARTICULO;";
        try {
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                String dato = null;
                String[] row = new String[5];
                for (int i = 0; i < 5; i++) {
                    dato = rs.getString(i + 1);
                    row[i] = dato;
                }
                arrList.add(row);
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar los reportes activos... \n: : : " + e);
        }
        return arrList;
    }

    public void liberarReporte(String text, int id) {
        ResultSet rs;
        PreparedStatement prep;
        String sql1 = "UPDATE E_REP_ARTICULOS SET RESOLUCION = ?, ESTATUS = FALSE WHERE ID_REPORTE_ARTICULO = ? ";
        try {
            prep = conn.prepareStatement(sql1);
            prep.setString(1, text);
            prep.setInt(2, id);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\n\n> > > Error al liberar articulo: \n" + ex + "\n\n");
        }
    }

    public int comprobarArtEnPrestYRep(int idArticulo) { //COMPROBAR ARTICULO EN PRESTAMO Y REPORTES OBSERVANDO LA CANTIDAD DE EXISTENCIAS
        int contador = 0;
        ResultSet rs;
        PreparedStatement prep;
        String artPrestados = "SELECT COUNT(*) FROM EPV_ARTICULOS WHERE ESTATUS > TRUE AND ID_ARTICULO = ?;"
                + "SELECT COUNT(*) FROM E_REP_ARTICULOS WHERE estatus = true and ID_REPORTE_ARTICULO IN (SELECT ID_REPORTE_ARTICULO FROM EREPA_ARTICULOS WHERE ID_ARTICULO = ?);";
        //+ "SELECT EXISTENCIAS FROM E_ARTICULOS WHERE ID_ARTICULO = ?;";
        try {
            prep = conn.prepareStatement(artPrestados);
            prep.setInt(1, idArticulo);
            prep.setInt(2, idArticulo);
            prep.setInt(3, idArticulo);
            rs = prep.executeQuery();
            while (rs.next()) {
                contador += rs.getInt(1);
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al generar multiples select comprobarArtEnPrestYRep ArticuloDB \n>>>" + e);
        }
        return contador;
    }

    public int comprobarExistenciasArt(int idArticulo) {
        int existencias = 0;
        try {
            ResultSet rs;
            PreparedStatement prep;
            String articulosExistentes = "SELECT EXISTENCIAS FROM E_ARTICULOS WHERE ID_ARTICULO = ?;";
            prep = conn.prepareStatement(articulosExistentes);
            prep.setInt(1, idArticulo);
            rs = prep.executeQuery();
            while (rs.next()) {
                existencias = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener existencias del articulo " + e);
        }
        return existencias;
    }
}
