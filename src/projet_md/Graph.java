/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author fy_ba
 */

public class Graph {

    private final Map<Sommet, Set<Sommet>> adjencList = new HashMap<>();

    public Graph(List<FXMLCanvasController.NodeFX> l1, List<FXMLCanvasController.EdgeFX> l2) {
        for(FXMLCanvasController.NodeFX n: l1){
            Sommet s = new Sommet(n.id.getText());
            n.sommet = s;
            addNode(s);
        }
        
        for(FXMLCanvasController.EdgeFX e: l2){
            edge(e.node1.sommet, e.node2.sommet);
        }
    }
    
    public void addNode(Sommet node) {
        if (!adjencList.containsKey(node)) {
            adjencList.put(node, new HashSet<>());
        }
    }

    public void edge(Sommet node1, Sommet node2) {
        addNode(node1);
        addNode(node2);
        adjencList.get(node1).add(node2);
        adjencList.get(node2).add(node1);
    }

    
    public Set<Sommet> adjencNodes() {
        return Collections.<Sommet>unmodifiableSet(adjencList.keySet());
    }

    //Fonction utilisée par GraphCompletFinder
    //en cas de changement de structure du Graph, il faut d'assurer
    //que cet fonction retourne toujours le bon résultat.
    //Signature modifiée, on utilisera la classe Sommet au lieu
    //des entiers
    public boolean edgeExists(Sommet node1, Sommet node2) {
        if (!adjencList.containsKey(node1)) {
            return false;
        }

        if (!adjencList.containsKey(node2)) {
            return false;
        }

        return adjencList.get(node1).contains(node2);
    }

    public int size() {
        return adjencList.size();
    }
    
    public Sommet[] nodeList(){
        return adjencList.keySet().toArray(new Sommet[0]);
    }
}

