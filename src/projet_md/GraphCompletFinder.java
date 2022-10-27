/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import java.util.*;

public class GraphCompletFinder {

    //Dire si le sous graphe formé par "sommets" est complet ou non
    //Graph G : pour verifier l'existance des aretes entre les paires de sommets
    //...en utilisant la fonction G.edgeExists(s1, s2)
    public static boolean SousGraphComplet(Graph G, Sommet[] sommets){
        GraphColorController.ChoisirSousGraphe(sommets);
        WaitDelay();
        for(int i = 0;i < sommets.length;i++){
            for(int j = i + 1;j < sommets.length;j++){
                GraphColorController.ChoisirSousGraphe(sommets);
                if(!G.edgeExists(sommets[i], sommets[j])){
                    GraphColorController.ChoisirAreteManquante(sommets[i], sommets[j]);
                    WaitDelay();
                    return false;
                }
                else{
                    GraphColorController.ChoisirAreteExistante(sommets[i], sommets[j]);
                    WaitDelay();
                }
            }
        }
        return true;
    }
    
    //Surcharge, utilisation d'une liste au lieu d'un tableau
    public static boolean SousGraphComplet(Graph G, List<Sommet> sommets){
        return SousGraphComplet(G, sommets.toArray(new Sommet[0]));
    }
    
    //Retourner la clique maximale d'un graphe G de sommets "sommets"
    //Null si la clique maximale est de taille <= 1
    public static List<Sommet> CliqueMaximale(Graph G, Sommet[] sommets){
        //ensemble vide
        if(sommets == null || sommets.length == 0){
            return null;
        }
        //ensemble non vide de taille au moins 2
        for(int i = sommets.length;i > 1;i--){
            FXML_ProjetMDController.cref.SetUIText(0, "Parcours des sous-graphes de taille : " + i);
            List<List<Sommet>> sousGraphes = Combinaisons.FindCombinations(sommets, i);
            for(List<Sommet> l : sousGraphes){
                GraphColorController.InitGraphColor(sommets);
                if(SousGraphComplet(G, l)){
                    return l;
                }
            }
        }
        return Arrays.asList(new Sommet[]{sommets[0]});
    }
    
    //Surcharge, utilisation d'une liste au lieu d'un tableau
    public static List<Sommet> CliqueMaximale(Graph G, List<Sommet> sommets){
        return CliqueMaximale(G, sommets.toArray(new Sommet[0]));
    }
    
    private static Thread currentThread = null;
    
    public static void TrouverCliqueMaximale(Graph graph){
        currentThread = Thread.currentThread();
        Sommet[] l = CliqueMaximale(graph, graph.nodeList()).toArray(new Sommet[0]);
        GraphColorController.InitGraphColor(graph.nodeList());
        GraphColorController.CliqueTrouvee(l
                );
    }
    
    public static void WaitDelay(){
        try
        {
           Thread.sleep(1000);
        }
        catch(Exception e){}
        finally{
            if(!FXML_ProjetMDController.cref.playing || currentThread != Thread.currentThread()){
                WaitDelay();
            }
        }
    }
}