/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanGSot
 */
public class Conexion {
    private static final String JDBC_NAME = "org.h2.Driver";

    private static final String USER = "DDA";
    private static final String PASS = "73125616 Admin5616";
    
    private static final String FILESQL    = "./src/proyector/dataBase/database.sql";
    private static final String NEW_DB_URL = "jdbc:h2:file:./src/proyector/dataBase/db;INIT=CREATE SCHEMA IF NOT EXISTS db\\;"
                                           + "RUNSCRIPT FROM '" + FILESQL +"';CIPHER=AES";//;FILE_LOCK=SOCKET";
    private static final String DB_URL = "jdbc:h2:file:./src/proyector/dataBase/db;IFEXISTS=TRUE;CIPHER=AES";//;FILE_LOCK=SOCKET";

    private Connection conn;
    
    /**
     * Metodo encargado de indicar los triggers necesarios en la base de datos
     * este metodo solo es cargado cuando se usa por primera vez el sistema
     * @param conn
     * @throws SQLException 
     */
    public static void lanzarTriggers(Connection conn) throws SQLException {
        String triggerNomTabla[][] = {{"E_DEPARTAMENTOS", "ACTUALIZAR_EN_DEPARTAMENTOS"}, 
            {"E_PROFESORES", "ACUTALIZAR_EN_PROFESORES"},
            {"E_AULAS","ACUTALIZADO_EN_AULAS"}, 
            {"E_USUARIOS", "ACUTALIZADO_EN_USUARIOS"}, 
            {"E_VIDEOPROYECTORES", "ACUTALIZADO_EN_VIDEOPROYECTORES"},
            {"E_PRESTAMOS","ACUTALIZADO_EN_PRESTAMOS"},
            {"E_REP_VIDEOPROYECTORES","ACUTALIZADO_EN_ACCESORIOS"}};

        /*insertando triggers*/
        Statement stat = conn.createStatement();
        for (int i = 0; i < triggerNomTabla.length; i++) {
            stat.execute("CREATE TRIGGER " + triggerNomTabla[i][1] + " BEFORE UPDATE "
                    + "ON " + triggerNomTabla[i][0] + " FOR EACH ROW "
                    + "CALL \"proyector.dataBase.TriggerActualizar\" ");
        }
    }
    
    public static void crearProcedure(Connection conn) throws SQLException {
        Statement stat = conn.createStatement();
        stat.execute("CREATE ALIAS IF NOT EXISTS E_LOG FOR \"proyector.dataBase.StoredProcedure.insertarLog\" ");
        stat.execute("CREATE ALIAS IF NOT EXISTS E_SHOW_LOG FOR \"proyector.dataBase.StoredProcedure.consultarLog\" ");
    }
    
    /**
     * Metodo encargado de crear la conexion a la base de datos
     * tambien se encarga de la creacion de la base de datos en caso de no existir
     * @throws SQLException 
     */
    public Conexion() throws SQLException{
        try {
            Class.forName(JDBC_NAME).newInstance();
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("CONEXION A LA BASE DE DATOS...");
            //crearProcedure(conn);
        } catch (SQLException ex) {
            conn = DriverManager.getConnection(NEW_DB_URL, USER, PASS);
            System.out.println("CREANDO Y CONECTANDO A LA BASE DE DATOS...");
            lanzarTriggers(conn);
            crearProcedure(conn);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println("\n\nError al crear base de datos o al ingresar sql o al conectar: " + ex + "\n\n");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo encargado de regresar la conexion a la base de datos 
     * para realizar operaciones sql
     * @return 
     */
    public Connection getConexion() {
        return this.conn;
    }
}
