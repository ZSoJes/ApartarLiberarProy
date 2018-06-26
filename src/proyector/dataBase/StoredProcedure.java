package proyector.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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
}
