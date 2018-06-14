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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import proyector.dataBase.Conexion;

/**
 *
 * @author Juan
 */
public class PrestamoDB {

    private final Connection conn;

    public PrestamoDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    /**
     * Generar un nuevo prestamo 
     * inhabilita el video proyector en su tabla ev_estatus 
     * inhabilita profesor colocando estatus false
     * inhabilita aula colocando estatus false
     *
     * @param datos 
     * - id usuario que autoriza el prestamo 
     * - id videoproyector 
     * - id profesor
     * - id aula
     * @param accesorios
     */
    public void setPrestamo(String[] datos, int[] accesorios) {
        try {
            int proye = Integer.parseInt(new VideoproyectorDB().getProyectorID(datos[1]));
            String prof = datos[2];
            int aula = Integer.parseInt(datos[3]);

            PreparedStatement prep;
            try{
                prep = conn.prepareStatement("INSERT INTO E_PRESTAMOS(ID_SALIDA, ID_VIDEOPROYECTOR,ID_PROFESOR,ID_AULA) VALUES (?,?,?,?)");
                prep.setString(1, datos[0]);
                prep.setInt(2, proye);//vid
                prep.setString(3, prof);//prof
                prep.setInt(4, aula);//aula
                prep.execute();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al ingresar nueva informacion setPrestamo E_PRESTAMOS PrestamoDB:" + ex + "\n\n");
            }
            
            try{
                prep = conn.prepareStatement("UPDATE EV_ESTATUS SET NOMBRE = ?, DISPONIBILIDAD = ? WHERE ID_VIDEOPROYECTOR = ?");
                prep.setString(1, "EN PRESTAMO");
                prep.setBoolean(2, false);
                prep.setInt(3, proye);
                prep.executeUpdate();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al ingresar nueva informacion setPrestamo EV_ESTATUS PrestamoDB:" + ex + "\n\n");
            }
            
            try{
                prep = conn.prepareStatement("UPDATE E_PROFESORES SET ESTATUS = FALSE WHERE ID_PROFESOR = ?");
                prep.setString(1, prof);
                prep.executeUpdate();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al ingresar nueva informacion setPrestamo E_PROFESORES PrestamoDB:" + ex + "\n\n");
            }
            
            try{
                prep = conn.prepareStatement("UPDATE E_AULAS SET ESTATUS = FALSE WHERE ID_AULA = ?");
                prep.setInt(1, aula);
                prep.executeUpdate();
                prep.close();
            }catch(SQLException ex){
                System.out.println("\nError al ingresar nueva informacion setPrestamo E_AULAS PrestamoDB:" + ex + "\n\n");
            }
            
            int prestamo = 0;
            try{
                prep = conn.prepareStatement("SELECT ID_PRESTAMO FROM E_PRESTAMOS ORDER BY ID_PRESTAMO DESC LIMIT 1");
                ResultSet rs = prep.executeQuery();
                while (rs.next()) {
                    prestamo = rs.getInt(1);
                }
            }catch(SQLException ex){ 
                System.out.println("\nError al ingresar nueva informacion setPrestamo obtener id prestamo: " +ex);
            }
           if (accesorios.length > 0){
               for(int acc : accesorios){
                setPrestamoAccesorio(acc, prestamo);
                AccesorioDB accesorioDB = new AccesorioDB();
                accesorioDB.updAccesorioAR(false, acc);
               }
           }
            
            System.out.println("Se genero NUEVO PRESTAMO!!!");
        } catch (SQLException ex) {
            System.out.println("\nError al ingresar nueva informacion setPrestamo PrestamoDB:" + ex + "\n\n");
        }
    }
    
    /**
     * Metodo encargado de ingresar accesorios prestados al registro EPV_ACCESORIOS
     * @param accesorioID
     * @param prestamoID 
     */
    public void setPrestamoAccesorio(int accesorioID, int prestamoID){
        PreparedStatement prep;
        try{
            prep = conn.prepareStatement("INSERT INTO EPV_ACCESORIOS(ID_ACCESORIO, ID_PRESTAMO) VALUES (?,?);");
            prep.setInt(1, accesorioID);
            prep.setInt(2, prestamoID);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al insertar prestamo en registro");
        }
    }
    /**
     * Libera un prestamo 
     * habilita videoproyector 
     * habilita profesor 
     * habilita aula
     * @param noSerie
     * @param credencialUsuario
     */
    public void updPrestamo(String noSerie, String credencialUsuario) {
        String[] otros = getPrestamo(noSerie);
        PreparedStatement prep;
        try{
            prep = conn.prepareStatement("UPDATE E_PRESTAMOS SET ID_ENTRADA = ?, ESTATUS = FALSE WHERE ID_PRESTAMO = ?");
            prep.setString(1, credencialUsuario);
            prep.setInt(2, Integer.parseInt(otros[0]));
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al actualizar PRESTAMO a updPrestamo E_PRESTAMOS:" + ex + "\n\n");
        }
        try{
            prep = conn.prepareStatement("UPDATE EV_ESTATUS SET NOMBRE = 'DISPONIBLE', DISPONIBILIDAD = TRUE WHERE ID_VIDEOPROYECTOR = ?");
            prep.setInt(1, Integer.parseInt(otros[1]));
            prep.execute();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al actualizar PRESTAMO a updPrestamo EV_ESTATUS:" + ex + "\n\n");
        }
        try{
            prep = conn.prepareStatement("UPDATE E_PROFESORES SET ESTATUS = TRUE WHERE ID_PROFESOR = ?");
            prep.setString(1, otros[2]);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al actualizar PRESTAMO a updPrestamo E_PROFESORES:" + ex + "\n\n");
        }
        try{
            prep = conn.prepareStatement("UPDATE E_AULAS SET ESTATUS = TRUE WHERE ID_AULA = ?");
            prep.setInt(1, Integer.parseInt(otros[3]));
            prep.executeUpdate();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("\nError al actualizar PRESTAMO a updPrestamo E_AULAS:" + ex + "\n\n");
        }
        
        
        ArrayList<Integer> newAcc = new ArrayList<>(getAccesoriosPrestamo(Integer.parseInt(otros[0])));
        if (newAcc.size() > 0){
            try{
                AccesorioDB accesorioDB = new AccesorioDB();
                for (int acc : newAcc) {
                    accesorioDB.updAccesorioAR(true, acc);
                }
            }catch(SQLException ex){
                System.out.println("Error al actualizar accesorios para liberarlos updPrestamo PrestamoDB: " + ex);
            }
        }
        System.out.println("Se ACTUALIZO PRESTAMO YA SE LIBERO!!!");
    }
    
    public ArrayList<Integer> getAccesoriosPrestamo(int prestamoID){
        ArrayList<Integer> accesorioID = new ArrayList<>();
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_ACCESORIO FROM EPV_ACCESORIOS WHERE ID_PRESTAMO = ?");
            prep.setInt(1, prestamoID);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                accesorioID.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar accesorios solicitados en prestamo getAccesoriosPrestamo PrestamoDB: " + ex);
        }
        return accesorioID;
    }
    /**
     * Regresa los prestamos existentes 
     * que se encuentren activos/inactivos cuando
     * estatus sea true = activos / false = inactivos
     * @param estatus
     * @return 
     */
    public int getPrestamosCount(boolean estatus) {
        int prestamo = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = ?");
            prep.setBoolean(1, estatus);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                prestamo = rs.getInt(1);
            }
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar dato getPrestamosCount PrestamoDB: " + ex);
        }
        return prestamo;
    }
    
    /**
     * A NOMBRE DE UN PROFESOR
     * Regresa si cuenta con prestamos activos
     * @param idProfe
     * @return 
     */
    public boolean getExistePrestamo(String idProfe) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = TRUE AND ID_PROFESOR = ?");
            prep.setString(1, idProfe);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar dato getExistePrestamo PrestamoDB: " + ex);
        }
        return existe;
    }
    
    /**
     * A NOMBRE DE UN AULA esperando id
     * Regresa si cuenta con prestamos activos
     * @param idAula
     * @return 
     */
    public boolean getExistePrestamoAula(int idAula) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = TRUE AND ID_AULA = ?");
            prep.setInt(1, idAula);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar dato getExistePrestamoAula PrestamoDB: " + ex);
        }
        return existe;
    }
    
    /**
     * AL NO_SERIE DE UN VIDEOPROYECTOR
     * Regresa si cuenta con prestamos activos
     * @param proyectorNoSerie
     * @return 
     */
    public boolean getExistePrestamoProy(String proyectorNoSerie) {
        boolean existe = false;
        try {
            PreparedStatement prep;
            String proyectorId = new VideoproyectorDB().getProyectorID(proyectorNoSerie);
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = TRUE AND ID_VIDEOPROYECTOR = ?");
            prep.setString(1, proyectorId);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar dato getExistePrestamoProy PrestamoDB: " + ex);
        }
        return existe;
    }

    public boolean getExistePrestamoDep(int idDep){
        boolean existe = false;
        try {
            PreparedStatement prep;
            String sql = "SELECT COUNT(*) FROM E_PRESTAMOS " +
                         "WHERE ESTATUS = TRUE AND " +
                         "E_PRESTAMOS.ID_PROFESOR IN (SELECT ID_PROFESOR FROM E_PROFESORES WHERE E_PROFESORES.ID_DEPARTAMENTO = ?);";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, idDep);
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                existe = rs.getInt(1) > 0;
            }
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar dato getExistePrestamoDep PrestamoDB: " + e);
        }
        return existe;
    }
    /**
     * Recupera un prestamo
     * VERIFICA A PARTIR DEL NO_SERIE DE VIDEOPROYECTOR
     * @param proyectorNoSerie
     * @return
     */
    public String[] getPrestamo(String proyectorNoSerie) {
        String[] array = new String[4];
        try {
            PreparedStatement prep;
            int videoProyector = Integer.parseInt(new VideoproyectorDB().getProyectorID(proyectorNoSerie));
            prep = conn.prepareStatement("SELECT ID_PRESTAMO, ID_VIDEOPROYECTOR, ID_PROFESOR, ID_AULA FROM E_PRESTAMOS WHERE ID_VIDEOPROYECTOR = ? AND ESTATUS = TRUE ");
            prep.setInt(1, videoProyector);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[0] = rs.getString("ID_PRESTAMO");
                array[1] = rs.getString("ID_VIDEOPROYECTOR");
                array[2] = rs.getString("ID_PROFESOR");
                array[3] = rs.getString("ID_AULA");
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("error al recibir datos getPrestamo PrestamoDB: " + ex);
        }
        return array;
    }

    /**
     * Recupera los prestamos que estan 
     * estatus = true >> activos
     * y los guarda en un arreglo bidimensional, es necesario indicar si usar order by = true o no = false
     * @param estatus
     * @param order
     * @return
     */
    public String[][] getPrestamos(boolean estatus, boolean order) {
        int cuantosPres = getPrestamosCount(estatus);
        String[][] array = new String[cuantosPres][8];
        try {
            PreparedStatement prep;
            if (order){
                prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = ? ORDER BY ID_PRESTAMO DESC");
            }else{
                prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = ?");
            }
            prep.setBoolean(1, estatus);
            ResultSet rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                array[i][0] = rs.getString("ID_PRESTAMO");
                array[i][1] = rs.getString("ID_SALIDA");
                array[i][2] = rs.getString("ID_ENTRADA");
                array[i][3] = rs.getString("ID_VIDEOPROYECTOR");
                array[i][4] = rs.getString("ID_PROFESOR");
                array[i][5] = rs.getString("ID_AULA");
                array[i][6] = rs.getString("CREADO");
                array[i][7] = rs.getString("ACTUALIZADO");
                i++;
            }
            rs.close();
            prep.close();        
        } catch (SQLException ex) {
            System.out.println("error al recibir datos getPrestamos PrestamoDB: " + ex);
        }
        return array;
    }
    
    /**
     * Recupera los prestamos que estan 
     * estatus = false >> inactivos
     * y los guarda en un arreglo bidimensional, usa order by 
     * ES IMPORTANTE INDICAR EL FILTRO
     * 1 - DIA, 2 - MES, 3 - SEMESTRE, 4 - AÑO
     * 
     * @param date1
     * @param date2
     * @return
     */
    public String[][] getPrestamos(String date1, String date2) {
        int cuantosPres = 0;
        try {
            PreparedStatement prep;
            //prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN \'"+ date1 + "\' AND \'" + date2 + "\' ORDER BY ID_PRESTAMO DESC ");
            //prep = conn.prepareStatement("SELECT COUNT(ID_PRESTAMO) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN \'" +date1 +"\' AND \'"+ date2 +"\' GROUP BY ID_PRESTAMO ORDER BY ID_PRESTAMO DESC");
            prep = conn.prepareStatement("SELECT COUNT(ID_PRESTAMO) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ? GROUP BY ID_PRESTAMO ORDER BY ID_PRESTAMO DESC");
            prep.setString(1, date1);
            prep.setString(2, date2);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cuantosPres += rs.getInt(1);
            }
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar la cantidad de registros existentes en prestamos entre una fecha definida getPrestamos date1Date2: " + e);
        }
        System.out.println("Se encontraron : " + cuantosPres + " registros entre estas fechas: " + date1 + "  -  " + date2);
        String[][] array = new String[cuantosPres][8];
        try {
            PreparedStatement prep;
            //prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN \'"+ date1 + "\' AND \'" + date2 + "\' ORDER BY ID_PRESTAMO DESC ");
            prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ? ORDER BY ID_PRESTAMO DESC ");
            prep.setString(1, date1);
            prep.setString(2, date2);
            //yyyy-mm-dd
            ResultSet rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                array[i][0] = rs.getString("ID_PRESTAMO");
                array[i][1] = rs.getString("ID_SALIDA");
                array[i][2] = rs.getString("ID_ENTRADA");
                array[i][3] = rs.getString("ID_VIDEOPROYECTOR");
                array[i][4] = rs.getString("ID_PROFESOR");
                array[i][5] = rs.getString("ID_AULA");
                array[i][6] = rs.getString("CREADO");
                array[i][7] = rs.getString("ACTUALIZADO");
                i++;
            }
            rs.close();
            prep.close();        
        } catch (SQLException ex) {
            System.out.println("error al recibir datos getPrestamos PrestamoDB: " + ex);
        }
        return array;
    }
    
    /**
     * Recupera cuantos prestamos se encuentran 
     * estatusPrestamo = true >> activos
     * @param idUusuario
     * @param estatusPrestamo
     * @return 
     */
    public int getCantPrestamosUsuario(String idUusuario, boolean estatusPrestamo) {
        int cantidad = 0;
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = ? AND (ID_SALIDA = ? OR ID_ENTRADA = ?)");
            prep.setBoolean(1, estatusPrestamo);
            prep.setString(2, idUusuario);
            prep.setString(3, idUusuario);
            
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }
            prep.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar dato getCantPrestamoUsuario PrestamoDB: " + ex);
        }
        return cantidad;
    }
    
    /**
     * Recupera los prestamos esperando estatusPrestamo
     * de acuerdo a un usuario especifico
     * @param idUsuario
     * @param estatusPrestamo
     * @return
     */
    public String[][] getPrestamosUsuario(String idUsuario, boolean estatusPrestamo) {
        int prestamos = getCantPrestamosUsuario(idUsuario, estatusPrestamo);
        System.out.println("Los prestamos de este usuario son: " + prestamos);
        String[][] array = new String[prestamos][7];
        
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_PRESTAMO, ID_SALIDA, ID_ENTRADA, ID_VIDEOPROYECTOR, ID_PROFESOR, ID_AULA, CREADO FROM E_PRESTAMOS WHERE ESTATUS = ? AND (ID_SALIDA = ? OR ID_ENTRADA = ?)");
            prep.setBoolean(1, estatusPrestamo);
            prep.setString(2, idUsuario);
            prep.setString(3, idUsuario);

            ResultSet rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                array[i][0] = rs.getString("ID_PRESTAMO");
                array[i][1] = rs.getString("ID_SALIDA");
                array[i][2] = rs.getString("ID_ENTRADA");
                array[i][3] = rs.getString("ID_VIDEOPROYECTOR");
                array[i][4] = rs.getString("ID_PROFESOR");
                array[i][5] = rs.getString("ID_AULA");
                array[i][6] = rs.getString("CREADO");
                i++;
            }
            rs.close();
            prep.close();
        } catch (SQLException ex) {
            System.out.println("error al recibir datos getPrestamosDeUsuario PrestamoDB: " + ex);
        }
        return array;
    }
    
    /**
     * Recupera el tiempo de un videoproyector en servicio
     * los datos presentados son en minutos
     * @param idProyector
     * @return 
     */
    public int[] getProyectorServicio(String idProyector){
        int[] servicio = new int[5];
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT * FROM EV_HORASSERVICIO WHERE ID_VIDEOPROYECTOR = ?");
            prep.setString(1, idProyector);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                servicio[0] = rs.getInt(1); //id
                servicio[1] = rs.getInt(2); //id_proy
                servicio[2] = rs.getInt(3); //total
                servicio[3] = rs.getInt(4); //semestre
                servicio[4] = rs.getInt(5); //mes
            }
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar las horas de servicio de videoproyector getProyectorServicio PrestamoDB: " + ex);
        }
        return servicio;
    }
    
    /**
     * Asigna el tiempo de un videoproyector en servicio suma en 
     * prestamos realizados resolviendo en minutos
     * @param idProye 
     */
    public void setProyectorServicio(String idProye){
        PreparedStatement prep;
        int day;
        String miDate1, miDate2;
        Calendar c = Calendar.getInstance(); //calendar toma los meses del 0(enero) a 11(dic)
        int actualMes = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        if (actualMes < 7) {
            c.set(Calendar.MONTH, 05);
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            miDate1 = year + "-01-01";
            miDate2 = year + "-06-" + day;
        } else {
            c.set(Calendar.MONTH, 11);
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            miDate1 = year + "-07-01";
            miDate2 = year + "-12-" + day;
        }
        
        try {
            prep = conn.prepareStatement("UPDATE EV_HORASSERVICIO " +
            "SET TOTAL = (SELECT SUM ( DATEDIFF('MI', CREADO, ACTUALIZADO) ) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = ?), " +
            "SEMESTRE = (SELECT SUM(DATEDIFF('MI', CREADO, ACTUALIZADO)) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = ? AND CREADO BETWEEN ? AND ?), " +
            "MES = (SELECT SUM(DATEDIFF('MI', CREADO, ACTUALIZADO)) FROM E_PRESTAMOS WHERE E_PRESTAMOS.ID_VIDEOPROYECTOR = ? AND E_PRESTAMOS.CREADO BETWEEN CONCAT(EXTRACT(YEAR FROM CURRENT_TIMESTAMP()),'-',EXTRACT(MONTH FROM CURRENT_TIMESTAMP()),'-01') AND DATEADD(M,1,CURRENT_TIMESTAMP()) - DAY(DATEADD(M,1,CURRENT_TIMESTAMP()))) " +
            "WHERE EV_HORASSERVICIO.ID_VIDEOPROYECTOR = ?");
            prep.setString(1, idProye);
            prep.setString(2, idProye);
            prep.setString(3, miDate1);
            prep.setString(4, miDate2);
            prep.setString(5, idProye);
            prep.setString(6, idProye);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error generando total, semestre, mes en tabla EV_HORASSERVICIO setProyectorServicio PrestamoDB: " + e);
        }
    }
}
