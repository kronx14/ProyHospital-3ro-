/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author Paul_
 */
public class Lista {
    Cita cab;

    public Lista(){
        cab=null;
    }
    public void insertFirst(String nombre, String apellido, String asunto, String hora, String fecha){
        Cita nvo=new Cita(nombre, apellido, hora, fecha, asunto);
        if (cab!=null) 
            nvo.setLiga(cab);
        cab=nvo;
    }
    public void insertLast(String nombre, String apellido, String asunto, String hora, String fecha){
        Cita nvo=new Cita(nombre, apellido, hora, fecha, asunto);
        Cita aux;
        if (cab==null){cab=nvo; return;} 

        for (aux = cab; aux.getLiga() != null; aux=aux.getLiga());

        aux.setLiga(nvo);
    }

    public void removeFirst(){
        if(cab==null){
            System.out.println("Esta vacia la lista");
            return;
        }
        cab=cab.getLiga();
    }
    
    public void removeLast(){     
        if (cab == null) {
            System.out.println("Esta vacia la lista");
            return;
        }
        if (cab.getLiga() == null) {
            cab = null;
            return;
        }
        Cita aux;
        for (aux = cab; aux.getLiga().getLiga() != null; aux = aux.getLiga());
        aux.setLiga(null);
    }

    public void eliminarX(String d){
        if(cab == null){ System.out.println("Lista vacia"); return; }
        
        if(d == null){System.out.println("folio invalido"); return;}
        
        if (cab.getFolio() != null && cab.getFolio().equals(d)) {
            removeFirst();
            return;
        }
        Cita aux;
        Cita auxG=null;
        for (aux = cab; aux!=null ; aux=aux.getLiga()) {
            if (aux.getFolio() != null && aux.getFolio().equals(d)) {
                break;  // Salir cuando la encuentre
            }
            auxG=aux;
        }

        if (aux==null) {
            System.out.println("cita no encontrada");
            return;
        }
        
        auxG.setLiga(aux.getLiga());
        aux.setLiga(null);
    }

}
