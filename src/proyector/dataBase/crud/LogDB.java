package proyector.dataBase.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import proyector.dataBase.Conexion;

public class LogDB {

    private final Connection conn;

    public LogDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Crear un registro permanente de las acciones realizadas
     *
     * @param id
     * @param tabla
     * @param tipo
     * @throws java.sql.SQLException
     */
    public void log(String id, String tabla, int tipo) throws SQLException {
        try {
            java.sql.Statement stmt = conn.createStatement();
            stmt.executeUpdate("CALL E_LOG('" + id + "','" + tabla + "','" + tipo + "');");
            System.out.println("\nLog...\n");
        } catch (SQLException ex) {
            System.out.println("\nError al escribir log PrestamoDB:" + ex + "\n\n");
        }
    }

    /**
     * Mostrar el registro permanente
     * @return 
     * @throws java.text.ParseException 
     */
    public String[][] showLog() throws ParseException {
        int count = countLog();
        String arr[][] = new String[count][5];
        PreparedStatement prep;
        ResultSet rs;
        Calendar c = Calendar.getInstance();
        try {
            prep = conn.prepareStatement("CALL E_SHOW_LOG();");
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < 5; j++) {
                    arr[i][j] = rs.getString(j + 1);
                    if(j == 4){
                        int[] miDte = formatFecha(arr[i][j]);
                        c.set(miDte[0], miDte[1], miDte[2], miDte[3], miDte[4]);
                        arr[i][j] = new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(c.getTime());
                    }
                }
                i++;
//                System.out.println("datos:::\n..:" + Arrays.toString(arr[i]));
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Deseando mostrar el log: " + e);
        }
        return arr;
    }
    
    public int countLog(){
        int count = 0;
        try {
            PreparedStatement prep = conn.prepareStatement("SELECT COUNT(ID_LOG) FROM E_LOG_MOVIMIENTOS;");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {count = rs.getInt(1);}
        } catch (SQLException e) {System.out.println("Error al recuperar cantidad de log:"+e);}
        return count;
    }
    
    public int[] formatFecha(String date) {
        int[] res = new int[5];
        res[0] = Integer.parseInt(date.substring(0, 4));              //año
        res[1] = Integer.parseInt(date.substring(5, 7)) - 1;         //mes
        res[2] = Integer.parseInt(date.substring(8, 10));            //dia
        res[3] = Integer.parseInt(date.substring(11, 13));        //hora
        res[4] = Integer.parseInt(date.substring(14, 16));       //minuto
        return res;
    }
}
