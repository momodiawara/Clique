/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import java.util.*;

public class Combinaisons {

    //Trouver les combinaisons possibles de taille k dans la liste "val"
    public static List<List<Sommet>> FindCombinations(Sommet[] val, int k){
        return FindCombinations(val, 0, k, new ArrayList<>());
    }
    
    //Surcharge, passage d'une liste au lieu d'un tableau
    public static List<List<Sommet>> FindCombinations(List<Sommet> val, int k){
        return FindCombinations(val.toArray(new Sommet[0]), 0, k, new ArrayList<>());
    }
    
    //Fonction récursive de calcul des combinaisons
    private static List<List<Sommet>> FindCombinations(Sommet[] val, int start, int k, List<Sommet> ret){
        int n = val.length - start;
        if(k == 1){
            List<List<Sommet>> result = new ArrayList<>();
            for(int i = start;i<=start + n - k;i ++){
                List<Sommet> r2 = new ArrayList<>(ret);
                r2.add(val[i]);
                result.add(r2);
            }
            return result;
        }
        else
        {
            List<List<Sommet>> result = new ArrayList<>();
            for(int i = start; i<=start + n - k;i ++){
                List<Sommet> ret2 = new ArrayList<>(ret);
                ret2.add(val[i]);
                result.addAll(FindCombinations(val, i + 1, k - 1, ret2));
            }
            return result;
        }
    }
    
}
