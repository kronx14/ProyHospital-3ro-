package hospital;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author marco
 */
public class ArbolPersonal {
    private NodoPersonal raiz;
    private int totalEmpleados;

    public ArbolPersonal() {
        this.raiz = null;
        this.totalEmpleados = 0;
    }

    public void establecerDirector(Personal director) {
        if (raiz == null) {
            raiz = new NodoPersonal(director);
            totalEmpleados = 1;
            System.out.println("Director establecido: " + director.getNombreCompleto());
        } else {
            System.out.println("Ya existe un director. Elimínalo primero si deseas cambiar.");
        }
    }
    
    // Insertar un empleado bajo un supervisor específico
    public boolean insertarEmpleado(String supervisorID, Personal nuevoEmpleado) {
        if (raiz == null) {
            System.out.println("Error: Debe establecer un director primero.");
            return false;
        }
        
        NodoPersonal supervisor = buscarNodo(raiz, supervisorID);
        if (supervisor != null) {
            supervisor.agregarEmpleado(new NodoPersonal(nuevoEmpleado));
            totalEmpleados++;
            System.out.println("Empleado " + nuevoEmpleado.getNombreCompleto() + 
                             " agregado bajo " + supervisor.getEmpleado().getNombreCompleto());
            return true;
        } else {
            System.out.println("Error: No se encontró al supervisor con ID: " + supervisorID);
            return false;
        }
    }
    
    // Buscar un empleado por ID y retornar sus datos (Público)
    public Personal buscarEmpleado(String empleadoID) {
        NodoPersonal nodo = buscarNodo(raiz, empleadoID);
        return (nodo != null) ? nodo.getEmpleado() : null;
    }
    
    // Eliminar un empleado (sus subordinados pasan al supervisor de este)
    public boolean eliminarEmpleado(String empleadoID) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return false;
        }
        
        if (raiz.getEmpleado().getEmpleadoID().equals(empleadoID)) {
            System.out.println("No se puede eliminar al director directamente.");
            return false;
        }
        
        return eliminarNodoRecursivo(raiz, empleadoID);
    }
    
    // Actualizar información de un empleado
    public boolean actualizarEmpleado(String empleadoID, Personal datosActualizados) {
        NodoPersonal nodo = buscarNodo(raiz, empleadoID);
        if (nodo != null) {
            nodo.setEmpleado(datosActualizados);
            System.out.println("Empleado actualizado exitosamente.");
            return true;
        } else {
            System.out.println("Error: No se encontró al empleado con ID: " + empleadoID);
            return false;
        }
    }

    // Buscar un nodo por ID de empleado (DFS)
    private NodoPersonal buscarNodo(NodoPersonal nodo, String empleadoID) {
        if (nodo == null) {
            return null;
        }
        
        if (nodo.getEmpleado().getEmpleadoID().equals(empleadoID)) {
            return nodo;
        }
        
        // Buscar en los subordinados recursivamente
        ListaEmpleados subordinados = nodo.getEmpleados();
        if (subordinados != null) {
            NodoListaPersonal actual = subordinados.getCabeza();
            while (actual != null) {
                NodoPersonal resultado = buscarNodo(actual.empleado, empleadoID);
                if (resultado != null) {
                    return resultado;
                }
                actual = actual.siguiente;
            }
        }
        return null;
    }

    private boolean eliminarNodoRecursivo(NodoPersonal padre, String empleadoID) {
        ListaEmpleados subordinados = padre.getEmpleados();
        if (subordinados == null) return false;

        NodoListaPersonal actual = subordinados.getCabeza();
        
        while (actual != null) {
          
            if (actual.empleado.getEmpleado().getEmpleadoID().equals(empleadoID)) {
                
                //  Rescatar a los subordinados del eliminado (Adopción por el abuelo)
                ListaEmpleados subsDelEliminado = actual.empleado.getEmpleados();
                if (subsDelEliminado != null) {
                    NodoListaPersonal subActual = subsDelEliminado.getCabeza();
                    while (subActual != null) {
                        padre.agregarEmpleado(subActual.empleado);
                        subActual = subActual.siguiente;
                    }
                }
                
                // Eliminar el nodo de la lista del padre
                subordinados.eliminar(empleadoID);
                totalEmpleados--;
                System.out.println("Empleado eliminado. Sus subordinados fueron reasignados.");
                return true;
            }
            
            //Buscar en los hijos
            if (eliminarNodoRecursivo(actual.empleado, empleadoID)) {
                return true;
            }
            
            actual = actual.siguiente;
        }
        
        return false;
    }

    public void guardarEnArchivo(String nombreArchivo) {
        if (raiz == null) {
            System.out.println("No hay datos para guardar.");
            return;
        }

        PrintWriter writer = null; 

        try {
            writer = new PrintWriter(nombreArchivo);
          
            guardarNodoRecursivo(raiz, "NULL", writer);
            System.out.println("Datos guardados correctamente en " + nombreArchivo);
            
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        } finally { 
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void guardarNodoRecursivo(NodoPersonal nodo, String idJefe, PrintWriter writer) {
        if (nodo == null) return;

        Personal p = nodo.getEmpleado();
        
        
        String linea = String.format("%s;%s;%s;%s;%s;%s;%.2f;%s;%s",
                idJefe,
                p.getEmpleadoID(),
                p.getNombre(),
                p.getApellidoPaterno(), 
                p.getPuesto(),
                p.getDepartamento(),
                p.getSalario(),
                (p.getTelefono() == null ? "" : p.getTelefono()),
                (p.getEmail() == null ? "" : p.getEmail())
        );
                
        writer.println(linea);

        // Recorrer hijos
        ListaEmpleados hijos = nodo.getEmpleados();
        if (hijos != null) {
            NodoListaPersonal hijoActual = hijos.getCabeza();
            while (hijoActual != null) {
                guardarNodoRecursivo(hijoActual.empleado, p.getEmpleadoID(), writer);
                hijoActual = hijoActual.siguiente;
            }
        }
    }

    public void cargarDesdeArchivo(String nombreArchivo) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(nombreArchivo)); 
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length < 7) continue; 

                String idSupervisor = datos[0];
                String id = datos[1];
                String nombre = datos[2];
                String apellido = datos[3];
                String puesto = datos[4];
                String depto = datos[5];
                
                double salario = 0.0;
                try {
                    salario = Double.parseDouble(datos[6].replace(",", "."));
                } catch (Exception e) {}

                // Recuperar Tel y Email (si existen)
                String tel = (datos.length > 7) ? datos[7] : "";
                String email = (datos.length > 8) ? datos[8] : "";

                // Crear objeto con el CONSTRUCTOR NUEVO
                Personal nuevo = new Personal(id, nombre, apellido, puesto, depto, tel, email);
                nuevo.setSalario(salario);

                if (idSupervisor.equalsIgnoreCase("NULL") || idSupervisor.equals("null")) {
                    this.establecerDirector(nuevo);
                } else {
                    this.insertarEmpleado(idSupervisor, nuevo);
                }
            }
            System.out.println("Datos cargados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) { }
        }
    }

    public NodoPersonal getRaiz() {
        return raiz;
    }
    
    public boolean estaVacio() {
        return raiz == null;
    }
    
    public int getTotalEmpleados() {
        return totalEmpleados;
    }
    
    public Personal obtenerDirector() {
        return (raiz != null) ? raiz.getEmpleado() : null;
    }
}