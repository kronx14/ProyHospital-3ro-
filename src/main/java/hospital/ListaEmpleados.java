
package hospital;


/**
 *
 * @author marco
 */
public class ListaEmpleados {
    private NodoListaPersonal cabeza;
    private int tamaño;
     public ListaEmpleados() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    public void agregar(NodoPersonal empleado) {
        NodoListaPersonal nuevo = new NodoListaPersonal(empleado);
        
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoListaPersonal actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }
    
    public boolean eliminar(String empleadoID) {
        if (cabeza == null) {
            return false;
        } 
        
        // Si es el primero
        if (cabeza.empleado.getEmpleado().getEmpleadoID().equals(empleadoID)) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }
        
        // Buscar en el resto
        NodoListaPersonal actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.empleado.getEmpleado().getEmpleadoID().equals(empleadoID)) {
                actual.siguiente = actual.siguiente.siguiente;
                tamaño--;
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
        public NodoPersonal buscar(String empleadoID) {
        NodoListaPersonal actual = cabeza;
        while (actual != null) {
            if (actual.empleado.getEmpleado().getEmpleadoID().equals(empleadoID)) {
                return actual.empleado;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public NodoListaPersonal getCabeza() {
        return cabeza;
    }
}
