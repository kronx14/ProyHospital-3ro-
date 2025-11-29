package ProyP;
import java.util.Arrays;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JESUS
 */
public class Paciente {
    private String expediente;
    private int dato;
    private Cita liga[];
    private Paciente ligaI;
    private Paciente ligaD;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;     // Formato: dd/MM/yyyy
    private int edad;
    private String sexo;                // "M" o "F"
    private String tipoSangre;          // "O+", "A-", etc.
    private String telefono;
    private String alergias;
    private String ultimaVisita;
    private String estadoPaciente;

    public Paciente(String expediente, String nombre, String apellidos, String telefono) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.dato = 0;
        this.edad = 0;
        this.fechaNacimiento = null;
        this.sexo = null;
        this.tipoSangre = null;
        this.alergias = null;
        this.ultimaVisita = null;
        this.estadoPaciente = null;
        this.liga = new Cita[1];
        this.ligaI = null;
        this.ligaD = null;
    }
    
    public void incrementarCitas(Cita nuevaCita) {
        liga = Arrays.copyOf(liga, liga.length + 1);
        liga[liga.length - 1] = nuevaCita;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }
    
    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }
    public Paciente getLigaI() {
        return ligaI;
    }
    public void setLigaI(Paciente ligaI) {
        this.ligaI = ligaI;
    }
    public Paciente getLigaD() {
        return ligaD;
    }
    public void setLigaD(Paciente ligaD) {
        this.ligaD = ligaD;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(String ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }

    public String getEstadoPaciente() {
        return estadoPaciente;
    }

    public void setEstadoPaciente(String estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
    }
}
