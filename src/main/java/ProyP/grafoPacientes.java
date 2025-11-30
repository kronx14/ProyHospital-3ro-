/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author JESUS
 */
public class grafoPacientes {
    Paciente cab;
    
    public Paciente buscar(String exp) {
        if (cab == null) return null;

        Paciente aux = cab;

        // Recorrer hasta encontrar el expediente o llegar al final
        while (aux != null && !aux.getExpediente().equals(exp)) {
            aux = aux.getLigaD();
        }

        return aux;
    }
    
    public void insertFirst(String expediente, String name, String lastN) {
    Paciente nvo=new Paciente(expediente, name,lastN);
    if(cab!=null){
        nvo.setLigaD(cab);
        cab.setLigaI(nvo);
        cab = nvo;
    }
    else 
        cab = nvo;
    }
    public void insertLast(String expediente,String name, String lastN){
        Paciente nvo=new Paciente(expediente,name,lastN);
        Paciente aux;
        if (cab == null) {
            cab=nvo;
        }
        else{
            for (aux = cab; aux.getLigaD() != null; aux=aux.getLigaD());
            aux.setLigaD(nvo);
            nvo.setLigaI(aux);
        }
    }
    public void removeFirst(){
        if (cab==null) {//Cuando la col aesta vacia
            return;
        }
        cab=cab.getLigaD();
        cab.getLigaI().setLigaD(null);
        cab.setLigaI(null);

    }
    public void removeX(String exp){
        Paciente aux=buscar(exp);
        if(aux==null){//Cuando el dato no existe
            return;
        }
        if(cab==aux)
            removeFirst();
        else
            if (aux.getLigaD()==null)
                removeLast();
            else{
                aux.getLigaI().setLigaD(aux.getLigaD());
                aux.getLigaD().setLigaI(aux.getLigaI());
                aux.setLigaD(null);
                aux.setLigaI(null);
            }
    }
    
    public void removeLast(){
        if (cab==null) {//Cuando la cola está vacia
            return;
        }
        Paciente aux;
        for (aux = cab; aux.getLigaD() != null; aux=aux.getLigaD());
        aux=aux.getLigaI();
        aux.getLigaD().setLigaI(null);
        aux.setLigaD(null);
    }
}