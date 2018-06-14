/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyector;

/**
 *
 * @author Juan
 */
public class comboItemProy {
    private String NoSerie;
    private String VidNombre;
    
    public comboItemProy(String noSerie, String vidNombre){
        this.NoSerie = noSerie;
        this.VidNombre = vidNombre;
    }
    
    public String getNoSerie(){
        return NoSerie;
    }
    
    public void setNoSerie(String noSerie){
        this.NoSerie = noSerie;
    }
    
    public String getVidNombre(){
        return VidNombre;
    }
    
    public void setVidNombre(String vidNombre){
        this.VidNombre = vidNombre;
    }
}
