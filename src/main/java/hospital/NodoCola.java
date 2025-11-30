
package hospital;

/**
 *
 * @author marco
 */
public class NodoCola {
       public NodoPersonal nodo;
    public int nivel;
    public NodoCola siguiente;
    
    public NodoCola(NodoPersonal nodo, int nivel) {
        this.nodo = nodo;
        this.nivel = nivel;
        this.siguiente = null;
    }
}
