/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital;


/**
 *
 * @author marco
 */
public class ColaPersonal {
    private NodoCola frente;
    private NodoCola fin;
    private int tamaño;
        public ColaPersonal() {
        this.frente = null;
        this.fin = null;
        this.tamaño = 0;
    }
    
    public void encolar(NodoPersonal nodo, int nivel) {
        NodoCola nuevo = new NodoCola(nodo, nivel);
        
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamaño++;
    }
    
    public NodoPersonal desencolar() {
        if (estaVacia()) {
            return null;
        }
        
        NodoPersonal nodo = frente.nodo;
        frente = frente.siguiente;
        
        if (frente == null) {
            fin = null;
        }
        
        tamaño--;
        return nodo;
    }
    
    public int getNivelFrente() {
        if (estaVacia()) {
            return -1;
        }
        return frente.nivel;
    }
    
    public boolean estaVacia() {
        return frente == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }
}
