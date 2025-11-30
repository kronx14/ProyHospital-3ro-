/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ProyP;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JESUS
 */
public class VtaInfoPaciente extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VtaInfoPaciente.class.getName());

    /**
     * Creates new form VtaInfoPaciente
     */
    private String expediente;
    private grafoPacientes grafoF;
    public VtaInfoPaciente(java.awt.Dialog parent, boolean modal, String expediente,grafoPacientes grafo) {
        super(parent, modal);
        this.grafoF=grafo;
        this.expediente=expediente;
        initComponents();
        print();
    }
    
    public void print(){
        Paciente pac=grafoF.buscar(this.expediente);
        nameTxtBox.setText(pac.getNombre()+" "+pac.getApellidos());
        mostrarCitas(pac);
    }
    
    public void mostrarCitas(Paciente pac){
        DefaultTableModel modelo = (DefaultTableModel) tablita.getModel();
        modelo.setRowCount(0);
        
        Cita[] citas=pac.getListaCitas();       
        // Recorrer el arreglo y llenar el modelo
        if (citas != null) {
            for (Cita c : citas) {
                if (c != null) {
                    modelo.addRow(new Object[]{
                        c.getFolio(),
                        c.getFecha()
                    });
                }   
            }            
        }
        tablita.setModel(modelo);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablita = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        nameTxtBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        LnameTxtBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Folio", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(tablita);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 400, 160));

        jLabel1.setText("Nombre:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 20));

        nameTxtBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTxtBoxActionPerformed(evt);
            }
        });
        getContentPane().add(nameTxtBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 130, -1));

        jLabel2.setText("Apellidos:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        LnameTxtBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LnameTxtBoxActionPerformed(evt);
            }
        });
        getContentPane().add(LnameTxtBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameTxtBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTxtBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTxtBoxActionPerformed

    private void LnameTxtBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LnameTxtBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LnameTxtBoxActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                grafoPacientes grafoPrueba=new grafoPacientes();
                VtaInfoPaciente dialog = new VtaInfoPaciente(new javax.swing.JDialog(), true,"",grafoPrueba);
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
    private javax.swing.JTextField LnameTxtBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameTxtBox;
    private javax.swing.JTable tablita;
    // End of variables declaration//GEN-END:variables
}
