/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author Paul_
 */
public class Lista {//LISTA DE CITAS
    Nodo cab;

    public Lista(){
        cab=null;
    }
    public void insertFirst(int dato){
        Nodo nvo=new Nodo(dato);
        if (cab!=null) 
            nvo.setLiga(cab);
        cab=nvo;
    }
    public void insertLast(int dato){
        Nodo nvo=new Nodo(dato);
        Nodo aux;

        for (aux = cab; aux.getLiga() != null; aux=aux.getLiga());
            
        if (cab==null) 
            cab=nvo;
        else
            aux.setLiga(nvo);
    }
    public void insertAfter(int b, int dato){
        Nodo aux;
        for (aux = cab; aux != null && aux.getDato()!=b; aux=aux.getLiga());
        if (aux==null) {
            System.out.println("No existe ese dato");
            return;
        }
        Nodo nvo=new Nodo(dato);
        nvo.setLiga(aux.getLiga());
        aux.setLiga(nvo);
    }
    public void insertBefore(int b, int dato){
        Nodo aux,auxG=null;
        for (aux = cab; aux != null && aux.getDato()!=b; aux=aux.getLiga())
        {auxG=aux;}
        if (aux==null) {
            System.out.println("No existe ese dato");
            return;
        }
        if (cab==aux) {
            insertFirst(dato);
        }
        else{
            Nodo nvo=new Nodo(dato);
            auxG.setLiga(nvo);
            nvo.setLiga(aux);
        }
    }

    public void removeFirst(){
        if(cab==null)
        {
            System.out.println("Esta vacia la lista");
            return;
        }
        cab=cab.getLiga();
    }
    public void removeLast(){
        
    }

    public void removeAfter(int b){
        if (cab==null) {//Si esta vacia la lista
            System.out.println("Esta lista esta vacia");
            return;
        }
        Nodo aux;
        for (aux = cab; aux!=null && aux.getDato() != b; aux=aux.getLiga());
        if (aux==null) {//Si el elemento que se busca no existe
            System.out.println("No esxiste");
            return;
        }
        if(aux.getLiga()==null) //Ni no existe elemento despues del que se busca
          System.out.println("No esxiste siguiente");
        else  //eliminacion normal
          aux.setLiga(aux.getLiga().getLiga());
    }
    public void removeBefore(int b){
        if (cab==null) { //Si la lista esta vacia
            System.out.println("Esta lista esta vacia");
            return;
        }
        Nodo aux,auxG=null,auxG2=null;
        for (aux = cab; aux!=null && aux.getDato() != b; aux=aux.getLiga()){
            if(auxG!=null)
                auxG2=auxG;
            auxG=aux;
        }   
        if (aux==cab) { //Si no existe un anterior
            System.out.println("No hay valor anterior");
            return;
        }
        if (cab==auxG) { //Si el anterior dato es el primero en la lista
            removeFirst();
        }
        else
            auxG2.setLiga(aux);

    }

    public void eliminarX(int d){
        Nodo aux;
        Nodo auxG=null;
        for (aux = cab; aux!=null && aux.getDato() != d; aux=aux.getLiga()) {
            auxG=aux;
        }

        if (aux.getLiga()==null) {
            //removeLast();
        }
        if (cab==aux) {
            removeFirst();
        }
        else{
            auxG.setLiga(aux.getLiga());
            aux.setLiga(null);
        }

    }

    public void impresion(){
        for (Nodo i = cab; i !=null; i=i.getLiga()) {
            System.out.print("Nodo:"+i);
            System.out.print("[Dato:"+i.getDato());
            System.out.println(" ,liga:"+i.getLiga()+"]");
        }
    }
}
