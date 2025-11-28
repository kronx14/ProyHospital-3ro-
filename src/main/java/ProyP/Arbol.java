/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author Paul_
 */
public class Arbol {
    private NodoM raiz;
    private NodoM bp;
    private boolean ban;

    public Arbol() {
        raiz = null;
    }

    public void insert(Integer dato, Integer padre){
        if (raiz==null && padre==null) {
            raiz = new NodoM(dato);
        }
        else{
            if (raiz == null)
                System.out.println("Raiz vacia");
            else{
                bp=null;
                ban=false;
                buscar(raiz, padre);
                if(bp==null)
                    System.out.println("Padre inexistente");
                else{
                    if(bp.grado==bp.liga.length)
                        bp.incrementar();
                    bp.liga[bp.grado]=new NodoM(dato);
                    bp.grado++;
                }
            }
        }
    }

    public void buscar(NodoM act, Integer b){
        if (!ban) {
            if (act.dato == b) {
                bp=act;
                ban=true;
            }
            for (int i = 0; i < act.grado; i++) 
                buscar(act.liga[i], b);
        }
    }

    public void removeDato(Integer b){
        if(raiz==null){
            System.out.println("Arbol vacio");
        }
        buscar(raiz, b);
        if (bp==null) {
            System.out.println("No existe el dato "+b);
        }
        else{

            //for (int i = act; i < act.grado && act.liga[i]!=null; i++) 
                //act=act.liga[i];
        }
    }
    
    public void impresion(NodoM act){
        if(act == null){
            System.out.println("");
            return;
        }
        System.out.println(act.dato);
        for (int i = 0; i < act.grado && act.liga[i]!=null; i++) 
            impresion(act.liga[i]);
    }

    public void impresion(){
        if (raiz==null) 
            System.out.println("Arbol vacio");
        else
            impresion(raiz);
    }
}
