/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

import java.util.Arrays;

/**
 *
 * @author Paul_
 */
public class NodoM {
    Integer dato;
    NodoM[] liga;
    int grado=0;

    public NodoM(Integer dato) {
        this.dato=dato;
        grado=0;
        this.liga = new NodoM[2];
    }

    public void incrementar(){
        NodoM[] aux=new NodoM[grado];
        aux=Arrays.copyOf(liga, liga.length + 1);
        liga=aux;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public NodoM[] getLiga() {
        return liga;
    }

    public void setLiga(NodoM[] liga) {
        this.liga = liga;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }
}
