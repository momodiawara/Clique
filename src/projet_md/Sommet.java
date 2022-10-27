/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

/**
 *
 * @author Hadjebar
 */
public class Sommet {
    public String name;

    public Sommet(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return " sommet d'id = " + name ;
    }    
    
    //fonctions de coloriage, à implementer apres selon la partie graphique
    public void ColorierRouge(){
        FXML_ProjetMDController.cref.colorierRouge(this);
    }
    
    public void ColorierVert(){
        FXML_ProjetMDController.cref.colorierVert(this);
    }
    
    public void ColorierJaune(){
        FXML_ProjetMDController.cref.colorierJaune(this);
    }
    
    public void ColorierGris(){
        FXML_ProjetMDController.cref.colorierGris(this);
    }
}
