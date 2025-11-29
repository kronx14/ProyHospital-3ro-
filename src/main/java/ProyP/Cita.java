/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProyP;

/**
 *
 * @author Paul_
 */
public class Cita {
    private Cita liga;
    private String asunto;
    private boolean estadoCita;
    private String hora;
    private String fecha;
    private String nombre;

    public Cita(String nombre,String hora, String fecha) {
        this.estadoCita = true;
        this.hora = hora;
        this.fecha = fecha;
        this.nombre=nombre;
    }

    public Cita getLiga() {
        return liga;
    }

    public void setLiga(Cita liga) {
        this.liga = liga;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public boolean isEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(boolean estadoCita) {
        this.estadoCita = estadoCita;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
