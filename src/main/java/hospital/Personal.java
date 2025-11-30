/*
 * Clase simplificada del Personal
 */
package hospital;

public class Personal {

    private String empleadoID;     
    private String nombre;          
    private String apellidoPaterno; 
  
    private String puesto;          
    private String departamento;    
    private double salario;         

    private String telefono;
    private String email;

 
    public Personal(String empleadoID, String nombre, String apellidoPaterno, 
                   String puesto, String departamento, String telefono, String email) {
        this.empleadoID = empleadoID;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.puesto = puesto;
        this.departamento = departamento;
        this.telefono = telefono;
        this.email = email;
        this.salario = 0.0; 
    }


    public String getEmpleadoID() { return empleadoID; }
    public void setEmpleadoID(String empleadoID) { this.empleadoID = empleadoID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno;
    }
    
    @Override
    public String toString() {
        return puesto + ": " + nombre + " " + apellidoPaterno;
    }
    
  //Juntar con citas
    public boolean esMedico() {
        if (puesto == null) return false;
        String p = puesto.toLowerCase();
        return p.contains("médico") || p.contains("doctor") || p.contains("cirujano");
    }
    
    //Metodo para realizar vacante a puestos de Jefes que tengan empleados
    public void hacerVacante() {
        this.empleadoID = "VACANTE-" + Math.random(); // ID temporal único
        this.nombre = "[ PLAZA DISPONIBLE ]";
        this.apellidoPaterno = "";
        this.telefono = "";
        this.email = "";
        this.salario = 0.0;
    }
        public boolean esVacante() {
        return this.nombre.contains("[ PLAZA DISPONIBLE ]");
    }
}