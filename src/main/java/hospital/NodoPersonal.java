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
public class NodoPersonal {
    private Personal empleado;
    private ListaEmpleados empleados;
    
    // Constructor y métodos
    public NodoPersonal(Personal empleado) {
        this.empleado = empleado;
        this.empleados = new ListaEmpleados();
    }
    
    public Personal getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Personal empleado) {
        this.empleado = empleado;
    }
    
    public ListaEmpleados getEmpleados() {
        return empleados;
    }
    
    public void agregarEmpleado(NodoPersonal empleado) {
        this.empleados.agregar(empleado);
    }
    
    public boolean eliminarEmpleado(String empleadoID) {
        return this.empleados.eliminar(empleadoID);
    }
    
    public int cantidadEmpleados() {
        return empleados.getTamaño();
    }
    
    public boolean tieneEmpleados() {
        return !empleados.estaVacia();
    }
}

