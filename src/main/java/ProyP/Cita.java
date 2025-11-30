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
    private String folio;
    private String apellidos;

    public Cita(String nombre,String apellido , String hora, String fecha,String asunto) {
        this.estadoCita = true;
        this.hora = hora;
        this.fecha = fecha;
        this.nombre = nombre;
        this.asunto = asunto;
        this.folio = null;
        this.apellidos = apellido;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
        
}
