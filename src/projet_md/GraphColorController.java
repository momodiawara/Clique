/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

public class GraphColorController {
    
    //rafraichir la couleur du graphe en coloriant tous les sommets en gris
    public static void InitGraphColor(Sommet[] sommets){
        for (Sommet sommet : sommets) {
            sommet.ColorierGris();
        }
    }
    
    //se focaliser sur un sous-graphe en coloriant ses sommets en jaune
    public static void ChoisirSousGraphe(Sommet[] sommets){
        for (Sommet sommet : sommets) {
            sommet.ColorierJaune();
        }
        FXML_ProjetMDController.cref.SetUIText(1, sousGraphString(sommets));
        FXML_ProjetMDController.cref.SetUIText(2, "");
    }
    
    public static void CliqueTrouvee(Sommet[] sommets){
        for (Sommet sommet : sommets) {
            sommet.ColorierVert();
        }
        FXML_ProjetMDController.cref.SetUIText(0, "Clique maximale trouvée de taille : " + sommets.length);
        FXML_ProjetMDController.cref.SetUIText(1, sousGraphString(sommets));
        FXML_ProjetMDController.cref.SetUIText(2, "");
    }
    
    //montrer l'existance d'une arete en coloriant ses 2 sommets en vert
    public static void ChoisirAreteExistante(Sommet s1, Sommet s2){
        s1.ColorierVert();
        s2.ColorierVert();
        FXML_ProjetMDController.cref.SetUIText(2, "Les sommets " + s1.name + " & " + s2.name 
                + " sont voisins");
    }
    
    //montrer la non-existance d'une arete entre 2 sommet en les coloriant en rouge
    public static void ChoisirAreteManquante(Sommet s1, Sommet s2){
        s1.ColorierRouge();
        s2.ColorierRouge();
        FXML_ProjetMDController.cref.SetUIText(2, "Les sommets " + s1.name + " & " + s2.name 
                + " ne sont pas voisins");
    }
    
    private static String sousGraphString(Sommet[] s){
        if(s == null || s.length == 0){
            return "{ }";
        }
        else{
            String ret = "{ " + s[0].name;
            for(Sommet ss : s){
                if(ss != s[0])
                ret += ", " + ss.name;
            }
            
            return ret + " }";
        }
    }
}
