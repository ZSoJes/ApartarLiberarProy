/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

/**
 * ComboItem para construir un comboBox con datos ocultos para Departamentos
 * @author JuanGSot
 */
public class comboItemProf {
    private int IdDepartamento;
    private String DepartNombre;
    
    public comboItemProf(int idDepart, String departNombre){
        this.IdDepartamento = idDepart;
        this.DepartNombre = departNombre;
    }
    
    public int getIdDepartamento(){
        return IdDepartamento;
    }
    
    public void setIdDepartamento(int id){
        this.IdDepartamento = id;
    }
    
    public String getDepartamentoNombre(){
        return DepartNombre;
    }
    
    public void setDepartamentoNombre(String departNombre){
        this.DepartNombre = departNombre;
    }    
}
