/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.dataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.h2.tools.TriggerAdapter;
/**
 *
 * @author JuanGSot
 */
public class TriggerActualizar extends TriggerAdapter{
    
    @Override
    public void fire(Connection conn, ResultSet oldRow, ResultSet newRow) throws SQLException{
        newRow.updateTimestamp("ACTUALIZADO", Timestamp.from(Instant.now()));
    }
}
