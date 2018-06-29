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
import java.util.HashMap;
import javax.swing.JOptionPane;
import proyector.comboItemProf;
import proyector.dataBase.Conexion;

/**
 *
 * @author JuanGSot
 */
public class ProfesorDB {
    private final Connection conn;
    private static String OS = System.getProperty("os.name").toLowerCase();
    public ProfesorDB() throws SQLException {
        Conexion connect = new Conexion();
        conn = connect.getConexion();
    }
    
    /**
     * Regresa la cantidad de registros en la tabla profesor
     * @return 
     */
    public int getRegistros(){
        int cuantosExisten = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PROFESORES");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) cuantosExisten = rs.getInt(1);
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al recuperar dato getRegistros: " + ex);
        }
        return cuantosExisten;
    }
    
    /**
     * Regresa un array de los profesores existentes
     * Id * Nombre * A_Paterno * A_Materno * Estatus_Escolar * Id_Departamento
     * @return 
     */
    public String[][] getProfesores(){
        String[][] array = new String[getRegistros()][6];
        int i = 0;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_PROFESOR, ID_DEPARTAMENTO, NOMBRE, A_PATERNO, A_MATERNO, SIN_ADEUDO FROM E_PROFESORES ORDER BY ID_PROFESOR");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                array[i][0] = rs.getString("ID_PROFESOR");
                array[i][1] = rs.getString("NOMBRE");
                array[i][2] = rs.getString("A_PATERNO");
                array[i][3] = rs.getString("A_MATERNO");
                array[i][4] = rs.getString("ID_DEPARTAMENTO");
                array[i][5] = rs.getString("SIN_ADEUDO");
                i++;
            }
            rs.close();
            prep.close();
            return array;
        }catch(SQLException ex){
            System.out.println("error al recibir datos: " + ex);
        }
        return array;
    }
    
    /**
     * Regresa la informacion de un profesor en especifico
     * @param id
     * @return 
     */
    public String[] getProfesor(String id){
        String profe[] = new String[6];
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT ID_PROFESOR, ID_DEPARTAMENTO, NOMBRE, A_PATERNO, A_MATERNO, SIN_ADEUDO FROM E_PROFESORES WHERE ID_PROFESOR = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                profe[0] = rs.getString("ID_PROFESOR");
                profe[1] = rs.getString("ID_DEPARTAMENTO");
                profe[2] = rs.getString("NOMBRE");
                profe[3] = rs.getString("A_PATERNO");
                profe[4] = rs.getString("A_MATERNO");
                profe[5] = rs.getString("SIN_ADEUDO");
            }
            rs.close();
            prep.close();
        }catch(SQLException ex){
            System.out.println("error al recibir datos: " + ex);
        }
        return profe;
    }
    
    /**
     * Regresa true si existe el profesor indicado por su id
     * @param id
     * @return 
     */
    public boolean getExisteProfesor(String id){
        boolean existe = false;
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("SELECT COUNT(*) FROM E_PROFESORES WHERE ID_PROFESOR = ?");
            prep.setString(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) existe =  rs.getInt(1) > 0;
        }catch(SQLException ex){
            System.out.println("Error al recuperar la cantidad de datos profesores: " + ex);
        }
        return existe;   
    }
    
    /**
     * Combo Box With Hidden Data, crea un key, value para el comboBox
     * que se encargara de mostrar los nombre de departamentos
     * @return 
     */
    public HashMap<String, Integer> segundoCombo(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        PreparedStatement prep;
        ResultSet rs;
        try{
        prep = conn.prepareStatement("SELECT ID_DEPARTAMENTO, NOMBRE FROM E_DEPARTAMENTOS ORDER BY ID_DEPARTAMENTO");
        rs = prep.executeQuery();
        comboItemProf cmiP;
        while (rs.next()){
            cmiP = new comboItemProf(rs.getInt(1), rs.getString(2));
            map.put(cmiP.getDepartamentoNombre(), cmiP.getIdDepartamento());
        }
        rs.close();
        prep.close();
        }catch(SQLException ex){
            System.out.println("Error al generar comboBox Departamentos: " + ex);
        }
        return map;
    }
    
    /**
     * Ingresar nuevo registro de profesor
     * @param datos 
     */
    public void setProfesor(String[] datos){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("INSERT INTO E_PROFESORES(ID_PROFESOR, ID_DEPARTAMENTO, NOMBRE, A_PATERNO, A_MATERNO) " +
                                         "VALUES (?,?,?,?,?)");
            prep.setString(1, datos[0]);
            prep.setInt(2, Integer.parseInt(datos[1]));
            prep.setString(3, datos[2]);
            prep.setString(4, datos[3]);
            prep.setString(5, datos[4]);
            prep.execute();
            System.out.println("Se genero profesor");
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al ingresar profesor, revise los datos ingresados: " + ex);
        }
    }
    
    /**
     * Metodo encargado de eliminar el registro de un profesor
     * @param id 
     */
    public void destroyProf(String id){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("DELETE FROM E_PROFESORES WHERE ID_PROFESOR = ?");
            prep.setString(1, id);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al borrar PROFESOR: " + ex);
        }
    }
    
    /**
     * Actualiza la informacion de un profesor y su id
     * a partir de su id anterior, el nuevo id y los siguientes datos
     * Id_Departamento * Nombre * A_Paterno * A_Materno * Estatus_Escolar
     * @param datos 
     * @param oldId 
     * @param newId 
     */
    public void updProfesor(String[] datos, String oldId, String newId ){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_PROFESORES "+
            "SET ID_PROFESOR = ?, ID_DEPARTAMENTO = ?, NOMBRE = ?, A_PATERNO = ?, A_MATERNO = ?"+
            "WHERE ID_PROFESOR= ?");
            prep.setString(1, newId);                      //id nuevo
            prep.setInt(2, Integer.parseInt(datos[0])); //id depart
            prep.setString(3, datos[1]);                //nom
            prep.setString(4, datos[2]);                //apellido
            prep.setString(5, datos[3]);
            prep.setString(6, oldId);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al actualizar profesor y su id : " + ex);
        }
    }
    
    /**
     * Actualiza la informacion de un profesor de acuerdo a su id esperando
     * Id_Departamento * Nombre * A_Paterno * A_Materno * Estatus_Escolar
     * @param datos
     * @param id 
     */
    public void updProfesorID(String[] datos, String id){
        try{
            PreparedStatement prep;
            prep = conn.prepareStatement("UPDATE E_PROFESORES "+
            "SET ID_DEPARTAMENTO = ?, NOMBRE = ?, A_PATERNO = ?, A_MATERNO = ?"+
            "WHERE ID_PROFESOR= ?");
            prep.setInt(1, Integer.parseInt(datos[0]));  //id depart
            prep.setString(2, datos[1]);                 //nom
            prep.setString(3, datos[2]);                 //apellido
            prep.setString(4, datos[3]);
            prep.setString(5, id);
            prep.execute();
            prep.close();
        }catch(SQLException ex){
            System.out.println("Error al actualizar profesor: " + ex);
        }
    }
    
    public void bulkLoadProfe(String ruta){
        try {
            PreparedStatement prep;
            System.out.println("ruta: " + ruta.replace('\\', '/'));
            String sql = "INSERT INTO E_PROFESORES(ID_PROFESOR, ID_DEPARTAMENTO, NOMBRE, A_PATERNO, A_MATERNO, SIN_ADEUDO) "
                       + "SELECT * FROM CSVREAD('" + ruta.replace('\\', '/') + "', null, ";
            //sql = sql.concat(OS.contains("win")?"STRINGDECODE('charset=windows-1252'))":"STRINGDECODE('charset=UTF-8'))"); //si es windows decode char con windows 1252 sino usa UTF-8 -se debe usar windows1252 en linux error charsetUTF8
            sql = sql.concat("STRINGDECODE('charset=windows-1252'))");
            System.out.println("SQL: "+ sql);
            prep = conn.prepareStatement(sql);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar multiples registros a la db Profesores: " + e);
            JOptionPane.showMessageDialog(null,"Error al insertar multiples registros a la db Profesores\n\nEs posible que uno o varios registros ya existan, causando que no sea posible una carga uniforme de los datos\n\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void bulkLoadUsuario(String ruta){
        try {
            PreparedStatement prep;
            System.out.println("ruta: " + ruta.replace('\\', '/'));
            String sql = "INSERT INTO E_USUARIOS(ID_USUARIO, NOMBRE, A_PATERNO, A_MATERNO, PASSWORD, ADMINDR) "
                       + "SELECT * FROM CSVREAD('" + ruta.replace('\\', '/') + "', null, ";
            //sql = sql.concat(OS.contains("win")?"STRINGDECODE('charset=windows-1252'))":"STRINGDECODE('charset=UTF-8'))"); //si es windows decode char con windows 1252 sino usa UTF-8 -se debe usar windows1252 en linux error charsetUTF8
            sql = sql.concat("STRINGDECODE('charset=windows-1252'))");
            System.out.println("SQL: "+ sql);
            prep = conn.prepareStatement(sql);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            System.out.println("Error al insertar multiples registros a la db Usuarios: " + e);
            JOptionPane.showMessageDialog(null,"Error al insertar multiples registros a la db Usuarios\n\nEs posible que uno o varios registros ya existan, causando que no sea posible una carga uniforme de los datos\n\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
