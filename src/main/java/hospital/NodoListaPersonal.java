
package hospital;

/**
 *
 * @author marco
 */
public class NodoListaPersonal {
    public NodoPersonal empleado;
    public NodoListaPersonal siguiente;
    
    public NodoListaPersonal(NodoPersonal empleado) {
        this.empleado = empleado;
        this.siguiente = null;
    }
    
}
