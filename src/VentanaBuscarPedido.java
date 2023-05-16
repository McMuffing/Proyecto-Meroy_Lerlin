import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author adrig
 */
public class VentanaBuscarPedido extends javax.swing.JFrame {

    /**
     * Creates new form VentanaBuscarPedido
     */
    public VentanaBuscarPedido() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombreVentana = new javax.swing.JLabel();
        jPanelDetallesPedido = new javax.swing.JPanel();
        jLabelCodigoPedido = new javax.swing.JLabel();
        jLabelFechaPedido = new javax.swing.JLabel();
        jTextFieldFechaPedido = new javax.swing.JTextField();
        jLabelFechaEsperada = new javax.swing.JLabel();
        jTextFieldFechaEsperada = new javax.swing.JTextField();
        jLabelFechaEntrega = new javax.swing.JLabel();
        jTextFieldFechaEntrega = new javax.swing.JTextField();
        jLabelComentarios = new javax.swing.JLabel();
        jTextFieldComentarios = new javax.swing.JTextField();
        jLabelEstado = new javax.swing.JLabel();
        jTextFieldEstado = new javax.swing.JTextField();
        jLabelCodigoCliente = new javax.swing.JLabel();
        jTextFieldCodigoCliente = new javax.swing.JTextField();
        jTextFieldCodigoProducto = new javax.swing.JTextField();
        jButtonMostrarResultadosPedido = new javax.swing.JButton();
        jButtonLimpiarCampos = new javax.swing.JButton();
        jTextFieldPrecioTotal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDetallesPedido = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabelNombreVentana.setFont(new java.awt.Font("Segoe UI", 1, 37)); // NOI18N
        jLabelNombreVentana.setText("DETALLES DEL PEDIDO:");

        jPanelDetallesPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PEDIDO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 20))); // NOI18N

        jLabelCodigoPedido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCodigoPedido.setText("CODIGO PEDIDO:");

        jLabelFechaPedido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelFechaPedido.setText("FECHA PEDIDO:");

        jTextFieldFechaPedido.setEditable(false);

        jLabelFechaEsperada.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelFechaEsperada.setText("FECHA ESPERADA:");

        jTextFieldFechaEsperada.setEditable(false);

        jLabelFechaEntrega.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelFechaEntrega.setText("FECHA ENTREGA:");

        jTextFieldFechaEntrega.setEditable(false);

        jLabelComentarios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelComentarios.setText("COMENTARIOS:");

        jTextFieldComentarios.setEditable(false);

        jLabelEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEstado.setText("ESTADO:");

        jTextFieldEstado.setEditable(false);

        jLabelCodigoCliente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCodigoCliente.setText("CODIGO CLIENTE:");

        jTextFieldCodigoCliente.setEditable(false);

        jButtonMostrarResultadosPedido.setText("MOSTRAR RESULTADOS");
        jButtonMostrarResultadosPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMostrarResultadosPedidoActionPerformed(evt);
            }
        });

        jButtonLimpiarCampos.setText("LIMPIAR CAMPOS");
        jButtonLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarCamposActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("PRECIO TOTAL DEL PEDIDO:");

        javax.swing.GroupLayout jPanelDetallesPedidoLayout = new javax.swing.GroupLayout(jPanelDetallesPedido);
        jPanelDetallesPedido.setLayout(jPanelDetallesPedidoLayout);
        jPanelDetallesPedidoLayout.setHorizontalGroup(
            jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPedidoLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPedidoLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetallesPedidoLayout.createSequentialGroup()
                                .addComponent(jLabelFechaPedido)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldFechaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabelFechaEsperada)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldFechaEsperada, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                                .addComponent(jLabelFechaEntrega)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetallesPedidoLayout.createSequentialGroup()
                                .addComponent(jLabelComentarios)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldComentarios))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelDetallesPedidoLayout.createSequentialGroup()
                                .addComponent(jLabelCodigoPedido)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(5, 5, 5))
                    .addGroup(jPanelDetallesPedidoLayout.createSequentialGroup()
                        .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDetallesPedidoLayout.createSequentialGroup()
                                .addComponent(jLabelEstado)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabelCodigoCliente)
                                .addGap(15, 15, 15)
                                .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDetallesPedidoLayout.createSequentialGroup()
                                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jButtonLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonMostrarResultadosPedido)
                                    .addComponent(jTextFieldPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelDetallesPedidoLayout.setVerticalGroup(
            jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDetallesPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCodigoPedido)
                    .addComponent(jTextFieldCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldFechaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFechaPedido)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelFechaEsperada)
                        .addComponent(jTextFieldFechaEsperada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelFechaEntrega)
                        .addComponent(jTextFieldFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelComentarios)
                    .addComponent(jTextFieldComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEstado)
                    .addComponent(jTextFieldEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCodigoCliente)
                    .addComponent(jTextFieldCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanelDetallesPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLimpiarCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonMostrarResultadosPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DETALLES PEDIDO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 18))); // NOI18N

        jTableDetallesPedido.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jTableDetallesPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "CANTIDAD", "CODIGO PEDIDO", "CODIGO PRODUCTO", "NUMERO LINEA", "PRECIO UNIDAD"
            }
        ));
        jTableDetallesPedido.setEnabled(false);
        jScrollPane1.setViewportView(jTableDetallesPedido);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelNombreVentana)
                    .addComponent(jPanelDetallesPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabelNombreVentana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelDetallesPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonMostrarResultadosPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMostrarResultadosPedidoActionPerformed
        // TODO add your handling code here:
        double sumaTotal = 0;
        
        Connection conexion = ConexionBD.getConexion();
        
        String codigoProducto = jTextFieldCodigoProducto.getText();
                
        PreparedStatement sentencia = null;
        
        try{
            Statement st = conexion.createStatement();
            // Ejecutamos una consulta SELECT para obtener la tabla vendedores
            
            String sentenciaSQL = "SELECT * FROM pedido WHERE codigo_pedido LIKE ?";
            
            sentencia = conexion.prepareStatement(sentenciaSQL);
            
            sentencia.setString(1, codigoProducto);
            
            ResultSet rs = sentencia.executeQuery();
            // Recorremos todo el ResultSet y mostramos sus datos    
            
            if(rs.next()){
                jTextFieldFechaPedido.setText(rs.getString("fecha_pedido"));
                jTextFieldFechaEsperada.setText(rs.getString("fecha_esperada"));
                jTextFieldFechaEntrega.setText(rs.getString("fecha_entrega"));
                jTextFieldComentarios.setText(rs.getString("comentarios"));
                jTextFieldEstado.setText(rs.getString("estado"));
                jTextFieldCodigoCliente.setText(rs.getString("codigo_cliente"));
            }
            else{
                JOptionPane.showMessageDialog(this,"El codigo de producto " + codigoProducto + " no existe en la base de datos.","ERROR CARGA DE DATOS",JOptionPane.WARNING_MESSAGE);
            }
            // Cerramos el statement y la conexión
            st.close();
        }

        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        //INTRODUCIMOS LOS DETALLES DEL PEDIDO ESPECIFICADO EN UN JTABLE
        try{
            String sql = "SELECT * FROM detalle_pedido WHERE codigo_pedido LIKE ? ORDER BY numero_linea ASC";
            
            sentencia = conexion.prepareStatement(sql);
            
            sentencia.setString(1, codigoProducto);
            
            ResultSet rs = sentencia.executeQuery();
            
            DefaultTableModel tabla = new DefaultTableModel();
            tabla.addColumn("CANTIDAD:");
            tabla.addColumn("CODIGO PEDIDO:");
            tabla.addColumn("CODIGO PRODUCTO:");
            tabla.addColumn("NUMERO LINEA:");
            tabla.addColumn("PRECIO UNIDAD:");
            
            String datosUsuarios[] = new String[5];

            while(rs.next()){
                datosUsuarios[0] = rs.getString("cantidad");
                datosUsuarios[1] = rs.getString("codigo_pedido");
                datosUsuarios[2] = rs.getString("codigo_producto");
                datosUsuarios[3] = rs.getString("numero_linea");
                datosUsuarios[4] = rs.getString("precio_unidad");
                tabla.addRow(datosUsuarios);
                
                double cantidad = Double.parseDouble(datosUsuarios[0]);
                double precioUnidad = Double.parseDouble(datosUsuarios[4]);
                double precioTotalFila = cantidad * precioUnidad;
       
                sumaTotal += precioTotalFila;
            }
            
            jTableDetallesPedido.setModel(tabla);
            
            jTextFieldPrecioTotal.setText(String.valueOf(sumaTotal));
        }
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonMostrarResultadosPedidoActionPerformed

    private void jButtonLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarCamposActionPerformed
        // TODO add your handling code here:
        jTextFieldCodigoProducto.setText("");
        jTextFieldFechaPedido.setText("");
        jTextFieldFechaEsperada.setText("");
        jTextFieldFechaEntrega.setText("");
        jTextFieldComentarios.setText("");
        jTextFieldEstado.setText("");
        jTextFieldCodigoCliente.setText("");
    }//GEN-LAST:event_jButtonLimpiarCamposActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaBuscarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaBuscarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaBuscarPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLimpiarCampos;
    private javax.swing.JButton jButtonMostrarResultadosPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCodigoCliente;
    private javax.swing.JLabel jLabelCodigoPedido;
    private javax.swing.JLabel jLabelComentarios;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelFechaEntrega;
    private javax.swing.JLabel jLabelFechaEsperada;
    private javax.swing.JLabel jLabelFechaPedido;
    private javax.swing.JLabel jLabelNombreVentana;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDetallesPedido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDetallesPedido;
    private javax.swing.JTextField jTextFieldCodigoCliente;
    private javax.swing.JTextField jTextFieldCodigoProducto;
    private javax.swing.JTextField jTextFieldComentarios;
    private javax.swing.JTextField jTextFieldEstado;
    private javax.swing.JTextField jTextFieldFechaEntrega;
    private javax.swing.JTextField jTextFieldFechaEsperada;
    private javax.swing.JTextField jTextFieldFechaPedido;
    private javax.swing.JTextField jTextFieldPrecioTotal;
    // End of variables declaration//GEN-END:variables
}
