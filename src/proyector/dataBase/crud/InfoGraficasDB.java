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
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGS
 */
public class InfoGraficasDB {

    private final Connection conn;

    public InfoGraficasDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }

    public String[][] getDeptoSolicitudesPorProfesor(int year) {
        String sqlCount = "SELECT COUNT(1) FROM "
                + "(SELECT (SELECT ID_DEPARTAMENTO FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR ) AS DEPTO FROM E_PRESTAMOS WHERE YEAR(CREADO)= ? GROUP BY DEPTO);";
        String sql = "SELECT (SELECT (SELECT ABBREV FROM E_DEPARTAMENTOS WHERE E_DEPARTAMENTOS.ID_DEPARTAMENTO = E_PROFESORES.ID_DEPARTAMENTO) FROM E_PROFESORES WHERE E_PROFESORES.ID_PROFESOR = E_PRESTAMOS.ID_PROFESOR ) AS DEPTO, "
                + "COUNT(*), SUM( DATEDIFF('MI', CREADO, ACTUALIZADO)) "
                + "FROM E_PRESTAMOS WHERE YEAR(CREADO)= ? GROUP BY DEPTO ORDER BY DEPTO;";

        PreparedStatement prep;
        ResultSet rs;
        int deptos = 0;
        try {
            prep = conn.prepareStatement(sqlCount);
            prep.setInt(1, year);
            rs = prep.executeQuery();
            while (rs.next()) {
                deptos = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("> Error al recuperar prestamos por depto: " + e);
        }

        String[][] prestDepto = new String[deptos][3];
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, year);
            rs = prep.executeQuery();
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < 3; j++) {
                    prestDepto[i][j] = rs.getString(j + 1);
                }
                i++;
            }
        } catch (SQLException e) {
            System.out.println("> Error al recuperar prestamos por depto: " + e);
        }
        return prestDepto;
    }

    public String[][] getProySolicitudes(int year) {
        String[][] proyData = null;
        PreparedStatement prep;
        ResultSet rs;
        int i = 0;
        try {
            int cantidadPry = new VideoproyectorDB().getCantProy();
            proyData = new String[cantidadPry][13];

            String sql = "SELECT PROYECTOR, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 01  THEN 1 ELSE 0 END)  AS ENE, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 02  THEN 1 ELSE 0 END)  AS FEB, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 03  THEN 1 ELSE 0 END)  AS MAR, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 04  THEN 1 ELSE 0 END)  AS ABR, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 05  THEN 1 ELSE 0 END)  AS MAY, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 06  THEN 1 ELSE 0 END)  AS JUN, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 07  THEN 1 ELSE 0 END)  AS JUL, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 08  THEN 1 ELSE 0 END)  AS AGO, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 09  THEN 1 ELSE 0 END)  AS SEP, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 10  THEN 1 ELSE 0 END)  AS OCT, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 11  THEN 1 ELSE 0 END)  AS NOV, "
                    + "  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 12  THEN 1 ELSE 0 END)  AS DIC "
                    + "FROM"
                    + "  (SELECT"
                    + "     (SELECT NOMBRE"
                    + "      FROM E_VIDEOPROYECTORES"
                    + "      WHERE E_VIDEOPROYECTORES.ID_VIDEOPROYECTOR = E_PRESTAMOS.ID_VIDEOPROYECTOR) AS PROYECTOR,"
                    + "          CREADO "
                    + "   FROM E_PRESTAMOS"
                    + "   WHERE ESTATUS = FALSE"
                    + "     AND YEAR(CREADO) = ?"
                    + "     AND ID_VIDEOPROYECTOR IN"
                    + "       (SELECT ID_VIDEOPROYECTOR"
                    + "        FROM E_VIDEOPROYECTORES)) "
                    + "GROUP BY PROYECTOR "
                    + "ORDER BY PROYECTOR";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, year);
            rs = prep.executeQuery();
            while (rs.next()) {
                for (int j = 0; j < 13; j++) {
                    proyData[i][j] = rs.getString(j + 1);
                }
                i++;
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error recuperando solicitudes de proyectores:" + e);
        }
        return proyData;
    }

    public int[] leerMeses(int year) {
        int[] reg = new int[12];
        try {
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT  SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 01  THEN 1 ELSE 0 END)  AS ENE, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 02  THEN 1 ELSE 0 END)  AS FEB, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 03  THEN 1 ELSE 0 END)  AS MAR, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 04  THEN 1 ELSE 0 END)  AS ABR, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 05  THEN 1 ELSE 0 END)  AS MAY, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 06  THEN 1 ELSE 0 END)  AS JUN, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 07  THEN 1 ELSE 0 END)  AS JUL, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 08  THEN 1 ELSE 0 END)  AS AGO, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 09  THEN 1 ELSE 0 END)  AS SEP, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 10  THEN 1 ELSE 0 END)  AS OCT, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 11  THEN 1 ELSE 0 END)  AS NOV, "
                    + "SUM( CASE WHEN EXTRACT(MONTH FROM CREADO) = 12  THEN 1 ELSE 0 END)  AS DIC "
                    + "FROM (SELECT *  FROM E_PRESTAMOS  WHERE ESTATUS = FALSE AND YEAR(CREADO) = ?)");
            prep.setInt(1, year);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                for (int i = 0; i < 12; i++) {
                    reg[i] = rs.getInt(i + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("error al recuperar registros del anio leerMeses LeerInicio: " + e);
        }
        return reg;
    }

    public String[][] getProyServicioGrafica() {
        String[][] proyData = null;
        try {
            int cantidadPry = new VideoproyectorDB().getCantProy();
            proyData = new String[cantidadPry][4]; //nombrePry, total, semestre, mes
            int i = 0;
            PreparedStatement prep;
            ResultSet rs;
            String sql = "SELECT " + 
                    "(SELECT NOMBRE FROM E_VIDEOPROYECTORES WHERE E_VIDEOPROYECTORES.ID_VIDEOPROYECTOR = EV_HORASSERVICIO.ID_VIDEOPROYECTOR), " + 
                    "TOTAL, MES, SEMESTRE FROM EV_HORASSERVICIO " + 
                    "WHERE EV_HORASSERVICIO.ID_VIDEOPROYECTOR IN (SELECT ID_VIDEOPROYECTOR FROM E_VIDEOPROYECTORES);";
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            while (rs.next()) {
                proyData[i][0] = rs.getString(1);
                proyData[i][1] = rs.getString(2);
                proyData[i][2] = rs.getString(3);
                proyData[i][3] = rs.getString(4);
                i++;
            }
            rs.close();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error recuperando cantidad de proyectores:" + e);
        }
        return proyData;
    }
}
