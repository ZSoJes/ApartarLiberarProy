package proyector.dataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.h2.tools.TriggerAdapter;

public class TriggerActualizar extends TriggerAdapter{
    
    @Override
    public void fire(Connection conn, ResultSet oldRow, ResultSet newRow) throws SQLException{
        newRow.updateTimestamp("ACTUALIZADO", Timestamp.from(Instant.now()));
    }
}
