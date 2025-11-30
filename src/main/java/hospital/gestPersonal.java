/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package hospital;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Paul_
 */
public class gestPersonal extends javax.swing.JDialog {

    private ArbolPersonal gestionPersonal;
    private final String ARCHIVO_DB = "hospital_db.txt";

    // LISTA DE PUESTOS
    final String[] PUESTOS = {
        "Director General", "Director Médico", "Director Administrativo",
        "Jefe de Cirugía", "Jefe de Urgencias", "Jefe de Enfermería",
        "Jefe de Recursos Humanos", "Médico Especialista", "Cirujano",
        "Médico General", "Residente", "Enfermero/a Jefe",
        "Enfermero/a General", "Auxiliar", "Recepcionista",
        "Camillero", "Intendencia"
    };
    
    public gestPersonal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // Configurar Combo
        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(PUESTOS));
        // Inicializar
        gestionPersonal = new ArbolPersonal();
        //LeerArchivo
        gestionPersonal.cargarDesdeArchivo(ARCHIVO_DB);
        // Si está vacío (primera vez), cargamos los datos de prueba
        if (gestionPersonal.estaVacio()) {
            System.out.println("No hay datos guardados. Cargando Demo...");
            cargarDatosDePrueba(); 
            gestionPersonal.guardarEnArchivo(ARCHIVO_DB); 
        }
        // Visualización
        actualizarJTree();
        // Estado Inicial
        configurarEstado("INICIO");
    }
    
    private void configurarEstado(String estado) {
        boolean editable = false;
        
        // Configuración de botones según el caso
        switch (estado) {
            case "INICIO": 
                editable = false;
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(false);
                BtnEliminar.setEnabled(false);
                BtnLimpiar.setEnabled(true);
                jCheckBox1.setEnabled(false);
                jCheckBox1.setSelected(false);
                
                // Limpiar campos
                txtID.setText(""); txtNombre.setText(""); txtApellidos.setText("");
                txtDepartamento.setText(""); txtTelefono.setText(""); 
                txtEmail.setText(""); txtSalario.setText("");
                break;

            case "LECTURA": // Al seleccionar un nodo
                editable = false;
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(true); // Se habilita si marcas check
                BtnEliminar.setEnabled(true);
                BtnLimpiar.setEnabled(true);
                jCheckBox1.setEnabled(true);
                jCheckBox1.setSelected(false);
                break;

            case "EDICION": // Al marcar el checkbox
                editable = true;
                btnAgregar.setEnabled(false);
                btnActualizar.setEnabled(true);
                BtnEliminar.setEnabled(false);
                txtID.setEditable(false); // ID protegido
                break;

            case "AGREGAR": // Al dar clic en Limpiar
                editable = true;
                btnAgregar.setEnabled(true);
                btnActualizar.setEnabled(false);
                BtnEliminar.setEnabled(false);
                jCheckBox1.setEnabled(false);
                jCheckBox1.setSelected(false);
                txtID.setEditable(true);
                txtID.setBackground(java.awt.Color.WHITE);
                txtID.requestFocus();
                break;
        }

        // Aplicar estado a las cajas de textome nos ID
        txtNombre.setEditable(editable);
        txtApellidos.setEditable(editable);
        cmbPuesto.setEnabled(editable);
        txtDepartamento.setEditable(editable);
        txtTelefono.setEditable(editable);
        txtEmail.setEditable(editable);
        txtSalario.setEditable(editable);

        java.awt.Color colorFondo = editable ? java.awt.Color.WHITE : new java.awt.Color(240, 240, 240);
        if (!estado.equals("AGREGAR")) txtID.setBackground(new java.awt.Color(240, 240, 240)); 
        txtNombre.setBackground(colorFondo);  
    }
    
    // --- CARGA DE DATOS DEMO ---
    private void cargarDatosDePrueba() {
        // Nivel 0
        Personal director = new Personal("DIR-001", "Dr. House", "Gregory", "Director General", "Dirección", "555-0100", "house@fox.com");
        director.setSalario(150000);
        gestionPersonal.establecerDirector(director);
        // Nivel 1
        gestionPersonal.insertarEmpleado("DIR-001", new Personal("DIR-MED", "Dra. Cuddy", "Lisa", "Director Médico", "Dirección Médica", "555-0101", "cuddy@fox.com"));
        gestionPersonal.insertarEmpleado("DIR-001", new Personal("DIR-ADM", "Dr. Kelso", "Bob", "Director Administrativo", "Administración", "555-0102", "kelso@scrubs.com"));
        // Nivel 2
        gestionPersonal.insertarEmpleado("DIR-MED", new Personal("JEF-URG", "Dr. Ross", "Doug", "Jefe de Urgencias", "Urgencias", "555-200", "ross@er.com"));
        gestionPersonal.insertarEmpleado("DIR-MED", new Personal("JEF-CIR", "Dr. Shepherd", "Derek", "Jefe de Cirugía", "Quirófano", "555-300", "mcdreamy@greys.com"));
        // Nivel 3
        gestionPersonal.insertarEmpleado("JEF-URG", new Personal("ENF-001", "Enf. Espinosa", "Carla", "Enfermero/a Jefe", "Urgencias", "555-400", "carla@scrubs.com"));
        gestionPersonal.insertarEmpleado("ENF-001", new Personal("ENF-002", "Enf. Focker", "Greg", "Enfermero/a General", "Urgencias", "555-401", "greg@focker.com"));
    }
    
    // Metodos del Arbol
    private void actualizarJTree() {
        if (gestionPersonal.estaVacio()) {
            treeOrganigrama.setModel(null);
            return;
        }
        DefaultMutableTreeNode raizVisual = crearNodoVisual(gestionPersonal.getRaiz());
        DefaultTreeModel modelo = new DefaultTreeModel(raizVisual);
        treeOrganigrama.setModel(modelo);

        for (int i = 0; i < treeOrganigrama.getRowCount(); i++) {
            treeOrganigrama.expandRow(i);
        }
    }
    
    private DefaultMutableTreeNode crearNodoVisual(NodoPersonal nodoPropio) {
        DefaultMutableTreeNode nodoVis = new DefaultMutableTreeNode(nodoPropio.getEmpleado());
        ListaEmpleados hijos = nodoPropio.getEmpleados(); 
        if (hijos != null) {
            NodoListaPersonal actual = hijos.getCabeza();
            while (actual != null) {
                DefaultMutableTreeNode hijoVis = crearNodoVisual(actual.empleado);
                nodoVis.add(hijoVis);
                actual = actual.siguiente;
            }
        }
        return nodoVis;
    }
    
    private DefaultMutableTreeNode buscarNodoVisual(DefaultMutableTreeNode nodoActual, String idBuscado) {
        if (nodoActual == null) return null;
        Object info = nodoActual.getUserObject();
        if (info instanceof Personal) {
            if (((Personal) info).getEmpleadoID().equalsIgnoreCase(idBuscado)) return nodoActual;
        }
        for (int i = 0; i < nodoActual.getChildCount(); i++) {
            DefaultMutableTreeNode result = buscarNodoVisual((DefaultMutableTreeNode) nodoActual.getChildAt(i), idBuscado);
            if (result != null) return result;
        }
        return null;
    }
    
    private void prepararNuevaCaptura() {
        txtID.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDepartamento.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtSalario.setText("");

        // Cambiamos el estado de los botones
        configurarEstado("AGREGAR");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtDepartamento = new javax.swing.JTextField();
        BtnLimpiar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        cmbPuesto = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtBuscarID = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeOrganigrama = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ficha Técnica"));

        jLabel1.setFont(new java.awt.Font("Arial Black", 3, 14)); // NOI18N
        jLabel1.setText("Datos del Empleado");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Apellidos");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Puesto");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Departamento");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        BtnLimpiar.setText("Nuevo empleado");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        btnAgregar.setText("Contratar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        BtnEliminar.setText("Despedir");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Director General ", "Director Médico", " Director Administrativo", " Jefe de Cirugía ", "Jefe de Urgencias", " Jefe de Enfermería", " Médico Especialista", " Cirujano", " Médico General", " Residente", " Enfermero/a Jefe", " Enfermero/a General ", " Auxiliar", " Recepcionista", "Camillero", "Intendencia" }));

        jLabel8.setFont(new java.awt.Font("Arial Black", 3, 14)); // NOI18N
        jLabel8.setText("Datos de Contacto");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Teléfono");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Email");

        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar Datos");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Habilitar Edición ");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Salario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID)
                            .addComponent(txtNombre)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidos)
                            .addComponent(cmbPuesto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(BtnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEliminar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDepartamento)
                            .addComponent(txtSalario))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel8)
                        .addContainerGap(81, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEmail)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLimpiar)
                    .addComponent(btnAgregar)
                    .addComponent(BtnEliminar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(jCheckBox1))
                .addGap(0, 158, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información", jPanel1);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Buscar");

        txtBuscarID.setColumns(12);
        txtBuscarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarIDActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addComponent(txtBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBuscarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        treeOrganigrama.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeOrganigramaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeOrganigrama);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        if (treeOrganigrama.getLastSelectedPathComponent() == null) {
            JOptionPane.showMessageDialog(this, "Para agregar, primero selecciona al JEFE en el árbol.");
            return;
        }
        prepararNuevaCaptura();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        DefaultMutableTreeNode nodoJefe = (DefaultMutableTreeNode) treeOrganigrama.getLastSelectedPathComponent();
        if (nodoJefe == null) {
            return;
        }

        String id = txtID.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (id.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El ID y el Nombre son obligatorios.");
            return;
        }

        if (gestionPersonal.buscarEmpleado(id) != null) {
            JOptionPane.showMessageDialog(this, "Error: El ID '" + id + "' ya existe en el sistema.");
            return;
        }

        double salario = 0.0;
        try {
            if (!txtSalario.getText().isEmpty()) {
                salario = Double.parseDouble(txtSalario.getText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El salario debe ser numérico.");
            return;
        }

        Personal nuevo = new Personal(id, nombre, txtApellidos.getText(),
            cmbPuesto.getSelectedItem().toString(), txtDepartamento.getText(),
            txtTelefono.getText(), txtEmail.getText());
        nuevo.setSalario(salario);

        Personal jefeObj = (Personal) nodoJefe.getUserObject();
        boolean exito = gestionPersonal.insertarEmpleado(jefeObj.getEmpleadoID(), nuevo);

        if (exito) {
            actualizarJTree();
            gestionPersonal.guardarEnArchivo(ARCHIVO_DB);
            JOptionPane.showMessageDialog(this, "¡Empleado Contratado con éxito!");
            prepararNuevaCaptura();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo insertar.");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) treeOrganigrama.getLastSelectedPathComponent();
        if (nodo == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado.");
            return;
        }

        Personal p = (Personal) nodo.getUserObject();
        if (p.esVacante()) {
            int r = JOptionPane.showConfirmDialog(this, "¿Eliminar esta plaza definitivamente?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                 if (gestionPersonal.eliminarEmpleado(p.getEmpleadoID())) {
                    actualizarJTree();
                    gestionPersonal.guardarEnArchivo(ARCHIVO_DB);
                    configurarEstado("INICIO");
                }
            }
            return;
        }
        // Empleado
        Object[] opciones = {"Liberar Plaza (Dejar Vacante)", "Eliminar del Sistema"};
        
        int eleccion = JOptionPane.showOptionDialog(this, "¿Qué deseas hacer con " + p.getNombre() + "?",
                "Gestión de Baja", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (eleccion == 0) {
            p.hacerVacante();
            DefaultTreeModel modelo = (DefaultTreeModel) treeOrganigrama.getModel();
            modelo.nodeChanged(nodo);
            gestionPersonal.guardarEnArchivo(ARCHIVO_DB);
            
            JOptionPane.showMessageDialog(this, "La plaza ha quedado disponible.");
            configurarEstado("INICIO");
        } 
        else if (eleccion == 1) {
            
            if (gestionPersonal.eliminarEmpleado(p.getEmpleadoID())) {
                actualizarJTree();
                gestionPersonal.guardarEnArchivo(ARCHIVO_DB);
                configurarEstado("INICIO");
                JOptionPane.showMessageDialog(this, "Empleado y plaza eliminados.");
            }
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed

    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) treeOrganigrama.getLastSelectedPathComponent();
        if (nodo == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado.");
            return;
        }
        Personal p = (Personal) nodo.getUserObject();
        if (p.esVacante()) {
            String nuevoID = txtID.getText().trim();
            if (nuevoID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Para ocupar la vacante, ingresa el ID del nuevo empleado.");
                return;
            }
          
            if (!nuevoID.equals(p.getEmpleadoID()) && gestionPersonal.buscarEmpleado(nuevoID) != null) {
                JOptionPane.showMessageDialog(this, "El ID '" + nuevoID + "' ya está ocupado por otra persona.");
                return;
            }
            p.setEmpleadoID(nuevoID); 
        }

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApellidos.getText());
        p.setPuesto(cmbPuesto.getSelectedItem().toString());
        p.setDepartamento(txtDepartamento.getText());
        p.setTelefono(txtTelefono.getText());
        p.setEmail(txtEmail.getText());

        // Guardar Salario 
        try {
            if (!txtSalario.getText().isEmpty()) {
                p.setSalario(Double.parseDouble(txtSalario.getText()));
            }
        } catch (NumberFormatException e) {
            
        }

        DefaultTreeModel modelo = (DefaultTreeModel) treeOrganigrama.getModel();
        modelo.nodeChanged(nodo); // Actualiza el texto en el árbol visualmente
        
        gestionPersonal.guardarEnArchivo(ARCHIVO_DB); // Guardar en txt

        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
        
        jCheckBox1.setSelected(false); // Desmarcar casilla
        configurarEstado("LECTURA");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        boolean habilitar = jCheckBox1.isSelected();
    DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) treeOrganigrama.getLastSelectedPathComponent();
        if (nodo != null) {
            Personal p = (Personal) nodo.getUserObject();
            
            if (habilitar) {
                
                boolean esVacante = p.esVacante();
                txtID.setEditable(esVacante);
                txtID.setBackground(esVacante ? java.awt.Color.WHITE : new java.awt.Color(230, 230, 230));
         
                if (esVacante) txtID.requestFocus();
                else txtNombre.requestFocus();
            }
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void txtBuscarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarIDActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String id = txtBuscarID.getText().trim();
        if (id.isEmpty()) {
            return;
        }

        //  Buscar en base de datos
        Personal p = gestionPersonal.buscarEmpleado(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "ID no encontrado.");
            return;
        }

        //  Buscar en la estrutura visual
        DefaultTreeModel model = (DefaultTreeModel) treeOrganigrama.getModel();
        DefaultMutableTreeNode nodoVisual = buscarNodoVisual((DefaultMutableTreeNode) model.getRoot(), id);

        if (nodoVisual != null) {
            TreePath path = new TreePath(nodoVisual.getPath());
            treeOrganigrama.setSelectionPath(path);
            treeOrganigrama.scrollPathToVisible(path);
            treeOrganigrama.requestFocus();
            txtBuscarID.setText("");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void treeOrganigramaValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeOrganigramaValueChanged
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) treeOrganigrama.getLastSelectedPathComponent();
        if (nodo == null) {
            return;
        }

        Object info = nodo.getUserObject();
        if (info instanceof Personal) {
            Personal p = (Personal) info;
            txtID.setText(p.getEmpleadoID());
            txtNombre.setText(p.getNombre());
            txtApellidos.setText(p.getApellidoPaterno());
            cmbPuesto.setSelectedItem(p.getPuesto());
            txtDepartamento.setText(p.getDepartamento());
            txtTelefono.setText(p.getTelefono() != null ? p.getTelefono() : "");
            txtEmail.setText(p.getEmail() != null ? p.getEmail() : "");
            txtSalario.setText(String.valueOf(p.getSalario()));

            // Modo Lectura
            configurarEstado("LECTURA");
        }
    }//GEN-LAST:event_treeOrganigramaValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gestPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestPersonal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gestPersonal dialog = new gestPersonal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox cmbPuesto;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree treeOrganigrama;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarID;
    private javax.swing.JTextField txtDepartamento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
