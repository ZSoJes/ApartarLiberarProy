package proyector.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyector.dataBase.crud.PrestamoDB;

public class StoredProcedure{
    
    public static void insertarLog(Connection conn, String idUsuario, String tablaNombre, int tipo) throws SQLException{
        String accion = (tipo==1?"INSERT":(tipo==2?"UPDATE":(tipo==3?"DELETE":(tipo==4?"SELECT":(tipo==5?"REPORT":(tipo==6?"PRESTO":"RECBIO"))))));
        
        PreparedStatement prep = conn.prepareStatement("INSERT INTO E_LOG_MOVIMIENTOS (ID_USUARIO, ACCION, TABLA) VALUES (?, ?, ?)");
        prep.setString(1, idUsuario);
        prep.setString(2, accion);
        prep.setString(3, tablaNombre);
        prep.execute();
        //CREATE TABLE E_LOG_MOVIMIENTOS(ID_LOG INT NOT NULL PRIMARY KEY AUTO_INCREMENT, ID_USUARIO VARCHAR(15) NOT NULL, ACCION CHAR(6), TABLA VARCHAR(40), FECHA DATETIME DEFAULT CURRENT_TIMESTAMP);
    }
    
    public static ResultSet consultarLog(Connection conn) throws SQLException{
        String sql = "SELECT * FROM E_LOG_MOVIMIENTOS ORDER BY ID_LOG DESC";
        return conn.createStatement().executeQuery(sql);
    }
    
    public static void crearPrestamo(Connection conn, String idUsuario, int idProy, String idProf, int idAul){
        PreparedStatement prestStatm;
        PreparedStatement proyStatm;
        PreparedStatement profStatm;
        PreparedStatement aulStatm;
        
        String prestamo = "INSERT INTO E_PRESTAMOS(ID_SALIDA, ID_VIDEOPROYECTOR, ID_PROFESOR, ID_AULA) VALUES (?,?,?,?)";
        String proy = "UPDATE EV_ESTATUS SET NOMBRE  = ?, DISPONIBILIDAD = ? WHERE ID_VIDEOPROYECTOR = ?";
        String profe = "UPDATE E_PROFESORES SET ESTATUS = FALSE WHERE ID_PROFESOR = ?";
        String aula = "UPDATE E_AULAS SET ESTATUS = FALSE WHERE ID_AULA = ?";
        try{
            prestStatm = conn.prepareStatement(prestamo);
            proyStatm = conn.prepareStatement(proy);
            profStatm = conn.prepareStatement(profe);
            aulStatm = conn.prepareStatement(aula);
            
            prestStatm.setString(1, idUsuario);
            prestStatm.setInt(2, idProy);
            prestStatm.setString(3, idProf);
            prestStatm.setInt(4, idAul);
            proyStatm.setString(1, "EN PRESTAMO");
            proyStatm.setBoolean(2, false);
            proyStatm.setInt(3, idProy);
            profStatm.setString(1, idProf);
            aulStatm.setInt(1, idAul);
            
            prestStatm.execute();
            proyStatm.execute();
            profStatm.execute();
            aulStatm.execute();

            prestStatm.close();
            proyStatm.close();
            profStatm.close();
            aulStatm.close();
        }catch(SQLException e){System.out.println("Error en crear prestamo Procedure:"+e);}
    }
    
    public static void liberarPrestamoN(Connection conn, String idUsuario, int idProy, String idProf, int idAul, int idPrest){
        PreparedStatement prestStatm;
        PreparedStatement proyStatm;
        PreparedStatement profStatm;
        PreparedStatement aulStatm;
        
        String prestamo = "UPDATE E_PRESTAMOS SET ID_ENTRADA = ?, ESTATUS = FALSE WHERE ID_PRESTAMO = ?";
        String proyector = "UPDATE EV_ESTATUS SET NOMBRE = 'DISPONIBLE', DISPONIBILIDAD = TRUE WHERE ID_VIDEOPROYECTOR = ?";
        String profesor = "UPDATE E_PROFESORES SET ESTATUS = TRUE WHERE ID_PROFESOR = ?";
        String aula = "UPDATE E_AULAS SET ESTATUS = TRUE WHERE ID_AULA = ?";
        
        try{
            prestStatm = conn.prepareStatement(prestamo);
            proyStatm = conn.prepareStatement(proyector);
            profStatm = conn.prepareStatement(profesor);
            aulStatm = conn.prepareStatement(aula);
            
            prestStatm.setString(1, idUsuario);
            prestStatm.setInt(2, idPrest);
            proyStatm.setInt(1, idProy);
            profStatm.setString(1, idProf);
            aulStatm.setInt(1, idAul);
                    
            prestStatm.execute();
            proyStatm.execute();
            profStatm.execute();
            aulStatm.execute();
            
            prestStatm.close();
            proyStatm.close();
            profStatm.close();
            aulStatm.close();
        }catch(SQLException e){
            System.out.println("Error al liberar de forma normal prestamo en Procedure:"+e);
        }
    }
    
    public static ResultSet genReportProy(Connection conn, String[] datos){
        PreparedStatement prep;

        String reporte = "INSERT INTO E_REP_VIDEOPROYECTORES (ID_VIDEOPROYECTOR, ID_PROFESOR, TITULO, NOMBRE_ENCARGADO, AREA, DEPTO_REPARADOR, IMPREVISTO, DETALLES) "+
        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String idReporte = "SELECT ID_REPORTE_VIDEOPROYECTOR FROM E_REP_VIDEOPROYECTORES ORDER BY ID_REPORTE_VIDEOPROYECTOR DESC LIMIT 1";
        try{
            prep = conn.prepareStatement(reporte);
            prep.setInt(1, Integer.parseInt(datos[0]));//proy
            for (int i = 1; i < 8; i++) {
                prep.setString(i+1, datos[i]);
            }
            prep.execute();
            prep.close();
        }catch(SQLException e){
            System.out.println("\nError al generar el reporte del proyector Procedure:"+e);
        }
        try{
            return conn.createStatement().executeQuery(idReporte);
        }catch(SQLException e){
            System.out.println("\nError al recuperar ID reporte del proyector Procedure:"+e);
        }
        return null;
    }
    /**
     * SE REPORTA QUE EL PROYECTOR TIENE UN PROBLEMA POR LO TANTO EL AULA QUEDA LIBRE PARA SER INDICADA NUEVAMENTE
     * EL ESTATUS DEL PROYECTOR ASI COMO EL DEL PRESTAMO CAMBIAN 
     * SE INDICA PROFESOR CON ADEUDO Y ESTATUS TRUE
     * @param conn
     * @param idUsuario
     * @param idProy
     * @param idProf
     * @param idAul
     * @param idPrest
     * @param idReporte 
     */
    public static void liberarPrestamoReport(Connection conn, String idUsuario, int idProy, String idProf, int idAul, int idPrest, int idReporte){
        PreparedStatement prestStatm;
        PreparedStatement proyStatm;
        PreparedStatement profStatm;
        PreparedStatement aulStatm;
        
        String prestamo = "UPDATE E_PRESTAMOS SET ID_ENTRADA = ?, ESTATUS = FALSE, ESTATUS_DEVOLUCION = FALSE, ID_REPORTE_VIDEOPROYECTOR = ? WHERE ID_PRESTAMO = ?";
        String proyector = "UPDATE EV_ESTATUS SET NOMBRE = ?, DISPONIBILIDAD = FALSE WHERE ID_VIDEOPROYECTOR = ?";
        String profesor = "UPDATE E_PROFESORES SET ESTATUS = TRUE, ADEUDO = TRUE WHERE ID_PROFESOR = ?";
        String aula = "UPDATE E_AULAS SET ESTATUS = TRUE WHERE ID_AULA = ?";
        
        try{
            String[] arr = new PrestamoDB().getReporte(idReporte);
            
            prestStatm = conn.prepareStatement(prestamo);
            proyStatm = conn.prepareStatement(proyector);
            profStatm = conn.prepareStatement(profesor);
            aulStatm = conn.prepareStatement(aula);
            
            prestStatm.setString(1, idUsuario);
            prestStatm.setInt(2, idReporte);
            prestStatm.setInt(3, idPrest);
            
            String imprevisto = arr[7].equals("a")?"Reparación":(arr[7].equals("b")?"Mantenimiento":(arr[7].equals("c")?"En Garantía":"De baja"));
            proyStatm.setString(1, imprevisto);
            proyStatm.setInt(2,idProy);
            
            profStatm.setString(1, idProf);
            aulStatm.setInt(1, idAul);
                    
            prestStatm.execute();
            proyStatm.execute();
            profStatm.execute();
            aulStatm.execute();
            
            prestStatm.close();
            proyStatm.close();
            profStatm.close();
            aulStatm.close();
        }catch(SQLException e){
            System.out.println("Error al liberar de forma normal prestamo en Procedure:"+e);
        }
    }
    
    public static void setProyector(Connection conn, String[] data, String[] accesorios){
        String sqlProyector = "INSERT INTO E_VIDEOPROYECTORES(NOMBRE, MARCA, MODELO, NO_SERIE, ACCESORIOS) VALUES (?,?,?,?,?);";
        String sqlEstatus = "INSERT INTO EV_ESTATUS(ID_VIDEOPROYECTOR, NOMBRE) VALUES(?, ?);";
        String sqlServicio = "INSERT INTO EV_HORASSERVICIO(ID_VIDEOPROYECTOR) VALUES(?);";
        String sqlID = "SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES WHERE NO_SERIE = ?;";
        PreparedStatement prep;
        PreparedStatement prepS;
        
        try {
            prep = conn.prepareStatement(sqlProyector);
            for (int i = 0; i < data.length; i++) {
                prep.setString(i+1, data[i]);
            }
            prep.setObject(5, accesorios);
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al ingresar videoproyector, revise los datos ingresados setVideoProyector VideoproyectorDB: " + ex);
        }
        ResultSet rs;
        int id = 0;
        try {
            prep = conn.prepareStatement(sqlID);
            prep.setString(1, data[3]);
            rs = prep.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar id del nuevo Proyector:" + e);
        }
        try {
            prep = conn.prepareStatement(sqlEstatus);
            prepS= conn.prepareStatement(sqlServicio);
            
            prep.setInt(1, id);
            prep.setString(2, "DISPONIBLE");
            prepS.setInt(1, id);
            
            prep.execute();
            prepS.execute();
            prep.close();
            prepS.close();
        } catch (SQLException e) {
            System.out.println("error al ingresar en EV_ESTATUS y  EV_HORASSERVICIO procedure: " + e);
        }
    }
}
