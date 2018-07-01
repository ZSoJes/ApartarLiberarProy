package proyector.dataBase.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
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
     * Generar un nuevo prestamo inhabilita los siguientes elementos:
     * -videoproyector -profesor -aula
     *
     * @param datos = { idUsuario, idProy, idProf, idAul }
     * @param accesorios
     */
    public void setPrestamo(String[] datos, int[] accesorios) {
        try {
            datos[1] = new VideoproyectorDB().getProyectorID(datos[1]);
            PreparedStatement prep;
            try {
                prep = conn.prepareStatement("CALL CREATE_PRESTAMO(?, ?, ?, ?)");
                prep.setString(1, datos[0]);
                prep.setInt(2, Integer.parseInt(datos[1]));
                prep.setString(3, datos[2]);
                prep.setInt(4, Integer.parseInt(datos[3]));
                prep.execute();
                prep.close();
            } catch (SQLException e) {
                System.out.println("Error al realizar el prestamo con procedure PrestamoDB:" + e);
            }

            if (accesorios.length > 0) {
                int prestamo = 0;
                try {
                    prep = conn.prepareStatement("SELECT ID_PRESTAMO FROM E_PRESTAMOS ORDER BY ID_PRESTAMO DESC LIMIT 1");
                    ResultSet rs = prep.executeQuery();
                    while (rs.next()) {
                        prestamo = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println("\nError al ingresar nueva informacion setPrestamo obtener id prestamo: " + ex);
                }
                AccesorioDB accesorioDB = new AccesorioDB();
                for (int acc : accesorios) {
                    accesorioDB.setPrestamoAccesorio(acc, prestamo);
                    accesorioDB.updAccesorioAR(false, acc);
                }
            }
            System.out.println("\n...: : : NUEVO PRESTAMO GENERADO EXITOSAMENTE : : :...\n");
        } catch (SQLException ex) {
            System.out.println("\nError al ingresar nueva informacion setPrestamo PrestamoDB:" + ex + "\n\n");
        }
    }

    /**
     * Libera un prestamo QUE NO TENGA UN REPORTE habilita lo siguiente:
     * -videoproyector -profesor -aula
     *
     * @param noSerie
     * @param idUsuario
     */
    public void updPrestamo(String noSerie, String idUsuario) {
        try {
            String[] datos = getPrestamo(noSerie); // datos = { idPrest, idProy, idProf, idAul }
            PreparedStatement prep;
            try {
                prep = conn.prepareStatement("CALL FREE_PRESTAMO_N(?, ?, ?, ?, ?)");
                prep.setString(1, idUsuario);
                prep.setInt(2, Integer.parseInt(datos[1]));
                prep.setString(3, datos[2]);
                prep.setInt(4, Integer.parseInt(datos[3]));
                prep.setInt(5, Integer.parseInt(datos[0]));
                prep.execute();
                prep.close();
            } catch (SQLException e) {
                System.out.println("Error al liberar normal prestamo updPrestamo:" + e);
            }

            ArrayList<Integer> articulosPrestados = new ArrayList<>(getAccesoriosPrestamo(Integer.parseInt(datos[0])));
            if (articulosPrestados.size() > 0) {
                try {
                    AccesorioDB accesorioDB = new AccesorioDB();
                    for (Iterator<Integer> it = articulosPrestados.iterator(); it.hasNext();) {
                        accesorioDB.updAccesorioAR(true, it.next());
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al actualizar accesorios para liberarlos updPrestamo PrestamoDB: " + ex);
                }
            }
            System.out.println("\n...: : : PRESTAMO LIBERADO EXITOSAMENTE : : :...\n");
        } catch (NumberFormatException e) {
            System.out.println("\n\nError al momento de liberar el prestamo PrestamoDB:" + e);
        }
    }
    
    public void updPrestamoConReporte(String noSerie, String idUsuario, String[] datosR){
        try{
        String[] datosP = getPrestamo(noSerie); // datos = { idPrest, idProy, idProf, idAul }
        PreparedStatement prep;
        ResultSet rs;
        int idReporte = 0;
        try {
            prep = conn.prepareStatement("CALL GEN_REP_PROY(?)");
            prep.setObject(1, new String[]{datosP[1], datosP[2], datosR[0], datosR[1], datosR[2], datosR[3], datosR[4], datosR[5]  }); //idProy, idProf, TITULO, NOMBRE_ENCARGADO, AREA, DEPTO_REPARADOR, IMPREVISTO, DETALLES
            rs = prep.executeQuery();
            while( rs.next()){
                idReporte = rs.getInt(1);
            }
            rs.close();
            prep.close();
            System.out.println("\n..:::El id del reporte es:"+idReporte);
        } catch (SQLException e) {
            System.out.println("Error al recuperar el ultimo reporte:"+e);
        }
        
        //devolucion de proyector con su reporte asignado
        try {
                prep = conn.prepareStatement("CALL FREE_PRESTAMO_REPORT_PRY(?, ?, ?, ?, ?, ?)");
                prep.setString(1, idUsuario);
                prep.setInt(2, Integer.parseInt(datosP[1]));
                prep.setString(3, datosP[2]);
                prep.setInt(4, Integer.parseInt(datosP[3]));
                prep.setInt(5, Integer.parseInt(datosP[0]));
                prep.setInt(6, idReporte);
                prep.execute();
                prep.close();
            } catch (SQLException e) {
                System.out.println("Error al liberar con reporte prestamo updPrestamo:" + e);
            }
        //devolucion de articulos
        ArrayList<Integer> articulosPrestados = new ArrayList<>(getAccesoriosPrestamo(Integer.parseInt(datosP[0])));
            if (articulosPrestados.size() > 0) {
                try {
                    AccesorioDB accesorioDB = new AccesorioDB();
                    for (Iterator<Integer> it = articulosPrestados.iterator(); it.hasNext();) {
                        accesorioDB.updAccesorioAR(true, it.next());
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al actualizar accesorios para liberarlos updPrestamo PrestamoDB: " + ex);
                }
            }
        }catch(NumberFormatException e){
            System.out.println("Error al generar el reporte junto a la liberacion del prestamo updPrestamoConReporte:"+e);
        }
    }

    public ArrayList<Integer> getAccesoriosPrestamo(int prestamoID) {
        ArrayList<Integer> accesorioID = new ArrayList<>();
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_ACCESORIO FROM EPV_ACCESORIOS WHERE ID_PRESTAMO = ?");
            prep.setInt(1, prestamoID);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                accesorioID.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar accesorios solicitados en prestamo getAccesoriosPrestamo PrestamoDB: " + ex);
        }
        return accesorioID;
    }



    /**
     *A NOMBRE DE UN AULA, PROFESOR, VIDEOPROYECTOR, DEPARTAMENTO con profesores que hayan solicitado prestamo, esperando EL ID CORRESPONDIENTE regresa si esta disponible para realizar un prestamo
     * <p>se espera: <ul><li>{1, idAula int}</li><li>{2, idProfesor String}</li><li>{3, NoSerieProyector String}</li><li>{4, idDepto int}</li></ul><br>opcTabla se llena de acuerdo a la opciones anteriores 1 para indicar aula, 2 profesor y asi </p>
     * REVISAR SI UN PROYECTOR SE ENCUENTRA DISPONIBLE DESDE E_PRESTAMOS INDICAR NumSerie y 5 para esta accion
     * @param idElemento
     * @param opcTabla
     * @return
     */
    public boolean getPrestamoActivo(String idElemento, int opcTabla) {
        boolean disponible = false;
        PreparedStatement prep;
        ResultSet rs;
        try {
            String prestAul = "SELECT COUNT(*)>0 FROM E_AULAS WHERE ESTATUS = TRUE AND ID_AULA = ?"; //PRESTAMO AULA
            String prestProf = "SELECT COUNT(*)>0 FROM E_PROFESORES WHERE ESTATUS = TRUE AND ID_PROFESOR = ?";  //PRESTAMO PROFESOR
            String prestProy = "SELECT DISPONIBILIDAD FROM  EV_ESTATUS WHERE ID_VIDEOPROYECTOR = (SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES WHERE NO_SERIE  = ?)";  // PRESTAMO PROYECTOR DISPONIBLE
            String prestDep = "SELECT COUNT(*)>0 FROM E_PRESTAMOS WHERE ESTATUS = TRUE AND E_PRESTAMOS.ID_PROFESOR IN (SELECT ID_PROFESOR FROM E_PROFESORES WHERE E_PROFESORES.ID_DEPARTAMENTO = ?);"; //PRESTAMOS ACTIVOS DE PROFESORES PERTENECIENTES A UN DEPTO
            String prestProyDisponible = "SELECT COUNT(*)>0 FROM E_PRESTAMOS WHERE ESTATUS = TRUE AND ID_VIDEOPROYECTOR = (SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES WHERE NO_SERIE  = ?)"; //SE ENCUENTRA PRESTADO EL PROYECTOR?
            String prestamo = (opcTabla==1?prestAul:(opcTabla==2?prestProf:(opcTabla==3?prestProy:(opcTabla==4?prestDep:prestProyDisponible))));
            prep = conn.prepareStatement(prestamo);
            prep.setString(1, idElemento);
            rs = prep.executeQuery();
            while (rs.next()) { disponible = rs.getBoolean(1);}
            prep.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error al recuperar DISPONIBILIDAD getPrestamoActivo PrestamoDB: " + ex);
        }
        return disponible;
    }

    /**
     * Recupera un prestamo VERIFICA A PARTIR DEL NO_SERIE DE VIDEOPROYECTOR
     *
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
     * Recupera los prestamos que estan estatus = true >> activos y los guarda
     * en un arreglo bidimensional, es necesario indicar si usar order by = true
     * o no = false
     *
     * @param estatus
     * @param order
     * @return
     */
    public String[][] getPrestamos(boolean estatus, boolean order) {
        PreparedStatement prep;
        ResultSet rs;
        int prestamos = 0;
        int i = 0;
        try {
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = ?");
            prep.setBoolean(1, estatus);
            rs = prep.executeQuery();
            while (rs.next()) {
                prestamos = rs.getInt(1);
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener la cantida de registros para getPrestamos PrestamoDB:" + e);
        }

        String[][] array = new String[prestamos][8];
        try {
            if (order) {
                prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = ? ORDER BY ID_PRESTAMO DESC");
            } else {
                prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = ?");
            }
            prep.setBoolean(1, estatus);
            rs = prep.executeQuery();
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
            System.out.println("Error al recibir datos getPrestamos PrestamoDB: " + ex);
        }
        return array;
    }

    public int getCantPrestamosSQLA(String date1, String date2) {
        int cuantosPres = 0;
        try {
            PreparedStatement prep;
            String sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, date1);//yyyy-mm-dd
            prep.setString(2, date2);//yyyy-mm-dd
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cuantosPres = rs.getInt(1);
            }
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar la cantidad de registros existentes en prestamos entre una fecha definida getPrestamos date1Date2: " + e);
        }
        return cuantosPres;
    }

    public int getCantPrestamosSQLB(String date1, String date2, int eleccion, String texto) {
        int cuantosPres = 0;
        try {
            PreparedStatement prep;
            String sql = sqlCountForzada(eleccion);
            prep = conn.prepareStatement(sql);
            prep.setString(1, "%%" + texto + "%%");
            prep.setString(2, date1);//yyyy-mm-dd
            prep.setString(3, date2);//yyyy-mm-dd
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                cuantosPres = rs.getInt(1);
            }
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al recuperar la cantidad de registros existentes en prestamos entre una fecha definida getPrestamos date1Date2: " + e);
        }
        return cuantosPres;
    }

    /**
     * Recupera los prestamos que estan estatus = false >> inactivos y los
     * guarda en un arreglo bidimensional, usa order by ES IMPORTANTE INDICAR EL
     * FILTRO 1 - DIA, 2 - MES, 3 - SEMESTRE, 4 - AÑO ES IMPORTANTE INDICAR QUE
     * DATOS 0 - TODOS, 1 - QUIEN ENTREGO, 2 - QUIEN RECIBIO, 3- NOMBRE
     * VIDEOPROYECTOR, 4 - ID PROFESOR, 5 - AULA Y UN TEXTO EN CASO DE ELEGIR
     * DEL 1 AL 5 DEL MENU ANTERIOR
     *
     * @param date1
     * @param date2
     * @param eleccion
     * @param texto
     * @return
     */
    public String[][] getPrestamos(String date1, String date2) {
        int count = getCantPrestamosSQLA(date1, date2);
        String[][] array = new String[count][8];
        try {
            PreparedStatement prep;
            String sql = sqlForzada(0);
            prep = conn.prepareStatement("SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ? ORDER BY CREADO DESC");
            prep.setString(1, date1);//yyyy-mm-dd
            prep.setString(2, date2);//yyyy-mm-dd
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

    public String sqlCountForzada(int eleccion) {
        String sql = "SELECT * FROM E_PRESTAMOS";
        switch (eleccion) {
            case 0:
                sql = "SELECT COUNT(ID_PRESTAMO) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ?";
                break;
            case 1: //Nombre del usuario que entrego   >>>indicar texto nombre usuario
                sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_SALIDA IN "
                        + "(SELECT ID_USUARIO FROM E_USUARIOS WHERE LOWER(CONCAT(NOMBRE, ' ',A_PATERNO, ' ',A_MATERNO )) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ?";
                break;
            case 2: //Nombre del usuario que recibio   >>>indicar texto nombre usuario
                sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_ENTRADA IN "
                        + "(SELECT ID_USUARIO FROM E_USUARIOS WHERE LOWER(CONCAT(NOMBRE, ' ',A_PATERNO, ' ',A_MATERNO )) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ?";
                break;
            case 3: //Nombre de VideoProyector   >>>indicar texto nombre proyector
                sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND E_PRESTAMOS.ID_VIDEOPROYECTOR IN "
                        + "(SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES "
                        + "WHERE LOWER(NOMBRE) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ?";
                break;
            case 4:  //ID del Profesor   >>>indicar texto credencial
                sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_PROFESOR IN "
                        + "(SELECT ID_PROFESOR FROM E_PROFESORES WHERE ID_PROFESOR LIKE '?') AND CREADO BETWEEN ? AND ?";
                break;
            case 5:  //Aula   >>>indicar texto nombre aula
                sql = "SELECT COUNT(*) FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_AULA IN "
                        + "(SELECT ID_AULA FROM E_AULAS WHERE LOWER(STRINGDECODE(NOMBRE)) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ?";
                break;
        }
        return sql;
    }

    public String sqlForzada(int eleccion) {
        String sql = "SELECT COUNT(*) FROM E_PRESTAMOS";
        switch (eleccion) {
            case 0:
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND CREADO BETWEEN ? AND ? ORDER BY CREADO DESC ";
                break;
            case 1: //Nombre del usuario que entrego   >>>indicar texto nombre usuario
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_SALIDA IN "
                        + "(SELECT ID_USUARIO FROM E_USUARIOS WHERE LOWER(CONCAT(NOMBRE, ' ',A_PATERNO, ' ',A_MATERNO )) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ? ORDER BY CREADO";
                break;
            case 2: //Nombre del usuario que recibio   >>>indicar texto nombre usuario
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_ENTRADA IN "
                        + "(SELECT ID_USUARIO FROM E_USUARIOS WHERE LOWER(CONCAT(NOMBRE, ' ',A_PATERNO, ' ',A_MATERNO )) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ? ORDER BY CREADO";
                break;
            case 3: //Nombre de VideoProyector   >>>indicar texto nombre proyector
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND E_PRESTAMOS.ID_VIDEOPROYECTOR IN "
                        + "(SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES "
                        + "WHERE LOWER(NOMBRE) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ? ORDER BY CREADO";
                break;
            case 4:  //ID del Profesor   >>>indicar texto credencial
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_PROFESOR IN "
                        + "(SELECT ID_PROFESOR FROM E_PROFESORES WHERE ID_PROFESOR LIKE '?') AND CREADO BETWEEN ? AND ? ORDER BY CREADO";
                break;
            case 5:  //Aula   >>>indicar texto nombre aula
                sql = "SELECT * FROM E_PRESTAMOS WHERE ESTATUS = FALSE AND ID_AULA IN "
                        + "(SELECT ID_AULA FROM E_AULAS WHERE LOWER(STRINGDECODE(NOMBRE)) LIKE LOWER('?')) AND CREADO BETWEEN ? AND ? ORDER BY CREADO";
                break;
        }
        return sql;
    }

    /**
     * Recupera cuantos prestamos se encuentran estatusPrestamo = true >>
     * activos
     *
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
     * Recupera los prestamos esperando estatusPrestamo de acuerdo a un usuario
     * especifico
     *
     * @param idUsuario
     * @param estatusPrestamo
     * @return
     */
    public String[][] getPrestamosUsuario(String idUsuario, boolean estatusPrestamo) {
        int prestamos = getCantPrestamosUsuario(idUsuario, estatusPrestamo);
        System.out.println("Los prestamos de este usuario son: " + prestamos);
        String[][] array = new String[prestamos][7];

        try {
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

    public String[][] getReportejTable() {
        int rowSize = 0;
        String[][] miArr = null;
        try {
            PreparedStatement prep;
            String sql = "SELECT "
                    + "ID_REPORTE_VIDEOPROYECTOR, "
                    + "(SELECT NOMBRE FROM E_VIDEOPROYECTORES WHERE E_REP_VIDEOPROYECTORES.ID_VIDEOPROYECTOR = E_VIDEOPROYECTORES.ID_VIDEOPROYECTOR ) AS VIDEOPROYECTOR, "
                    + "TITULO, "
                    + "IMPREVISTO, "
                    + "TO_CHAR(CREADO, 'dd/MM/yyyy HH:MI AM') as CREADO "
                    + "FROM E_REP_VIDEOPROYECTORES "
                    + "ORDER BY CREADO DESC";
            prep = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = prep.executeQuery();

            try {
                rs.last();
                rowSize = rs.getRow();
                rs.beforeFirst();
            } catch (SQLException e) {
                System.out.println("Error al intentar obtener cuantos registros se encuentran:" + e);
            }
            miArr = new String[rowSize][5];
            int i = 0;
            while (rs.next() && (i < rowSize)) {
                for (int j = 0; j < 5; j++) {
                    miArr[i][j] = rs.getString(j + 1);
                    if (j == 3) {
                        miArr[i][j] = (miArr[i][j] == null ? "" : (miArr[i][j].equals("a") ? "Reparación" : (miArr[i][j].equals("b") ? "Mantenimiento" : (miArr[i][j].equals("c") ? "En Garantía" : "De baja"))));
                    }
                }
                System.out.println("Array: " + Arrays.toString(miArr[i]));
                i++;
            }
            prep.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Error al crear arreglo de datos de reportes videoproyector: " + e);
        }
        return miArr;
    }

    public String[] getReporte(int id) {
        String[] miArr = new String[10];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT * FROM E_REP_VIDEOPROYECTORES WHERE ID_REPORTE_VIDEOPROYECTOR = ?");
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 10; i++) {
                    miArr[i] = rs.getString(i + 1);
                }
            }
            prep.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error al crear arreglo de datos de reportes videoproyector getReporte: " + e);
        }
        return miArr;
    }

    public void updReporte(int id, String[] datos) {
        try {
            PreparedStatement prep;
            String sql = "UPDATE E_REP_VIDEOPROYECTORES "
                    + "SET TITULO = ?, "
                    + "NOMBRE_ENCARGADO = ?, "
                    + "AREA = ?, "
                    + "DEPTO_REPARADOR = ?, "
                    + "IMPREVISTO = ?, "
                    + "DETALLES = ? "
                    + "WHERE ID_REPORTE_VIDEOPROYECTOR = ?";
            prep = conn.prepareStatement(sql);
            for (int i = 0; i < datos.length; i++) {
                prep.setString(i + 1, datos[i]);
            }
            prep.setInt(7, id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos de reportes videoproyector updReporte: " + e);
        }
    }
}
