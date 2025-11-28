/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author Paul_
 */
public class Nodo {
    private int dato;
    private Nodo liga;
    public Nodo(int dato) {
        this.dato = dato;
        this.liga=null;
    }
    public int getDato() {
        return dato;
    }
    public void setDato(int dato) {
        this.dato = dato;
    }
    public Nodo getLiga() {
        return liga;
    }
    public void setLiga(Nodo liga) {
        this.liga = liga;
    }
}
