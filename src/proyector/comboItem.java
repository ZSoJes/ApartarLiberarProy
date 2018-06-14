/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

/**
 *ComboItem para construir un comboBox con datos ocultos para Aulas 
 * @author JuanGSot
 */
public class comboItem {
    private int IdAula;
    private String AulaNombre;
    
    public comboItem(int idAula, String aulaNombre){
        this.IdAula = idAula;
        this.AulaNombre = aulaNombre;
    }
    
    public int getIdAula(){
        return IdAula;
    }
    
    public void setIdAula(int id){
        this.IdAula = id;
    }
    
    public String getAulaNombre(){
        return AulaNombre;
    }
    
    public void setAulaNombre(String aulaNombre){
        this.AulaNombre = aulaNombre;
    }
}
