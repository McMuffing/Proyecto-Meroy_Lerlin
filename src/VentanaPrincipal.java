import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    private Connection conexion;
    private String codigoPedido = "";
    private ResultSet rs;
     
     public void actualizarTabla(){
        try{
            Statement st = conexion.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT codigo_pedido, estado, fecha_pedido, fecha_esperada, fecha_entrega, comentarios FROM pedido");
             
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("CODIGO PEDIDO");
            modelo.addColumn("ESTADO");
            modelo.addColumn("FECHA PEDIDO");
            modelo.addColumn("FECHA ESPERADA");
            modelo.addColumn("FECHA ENTREGA");
            modelo.addColumn("COMENTARIO");
             
            String[] result = new String[6];
            while(rs.next()){
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
                result[2] = rs.getString(3);
                result[3] = rs.getString(4);
                result[4] = rs.getString(5);
                result[5] = rs.getString(6);
                modelo.addRow(result);
            }
            jTablePedidos.setModel(modelo);
            rs.beforeFirst();
        } 
        catch (SQLException ex){
            System.out.println(ex);
        }
    }
    
     //Metodo para pasar el estado de un pedido a RECIBIDO
     public void aRecibido(int idPedido){
         try {
            String ssql = "UPDATE pedido SET estado='Recibido', fecha_entrega=curdate() WHERE codigo_pedido =?";
            PreparedStatement update = conexion.prepareStatement(ssql);
            update.setInt(1, idPedido);
            if(update.executeUpdate() > 0){
                JOptionPane.showMessageDialog(this, "Ha sido actualizado correctamente");
                actualizarTabla();
            }
                   
         } catch (SQLException ex) {
             Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     //Metodo para pasar el estado de un pedido a CANCELADO
     public void aCancelado(int idPedido)   {
         try {
            String ssql = "UPDATE pedido SET estado='Cancelado' WHERE codigo_pedido = ?";
            String select = "SELECT codigo_producto, cantidad FROM detalle_pedido WHERE codigo_pedido = ?";
            String selectProd = "SELECT cantidad_en_stock FROM producto WHERE codigo_producto = ?";
            String restablecer = "UPDATE producto SET cantidad_en_stock = ? WHERE codigo_producto = ?";
           
            PreparedStatement selectDetalle = conexion.prepareStatement(select);
            selectDetalle.setInt(1, idPedido);
            ResultSet detalles = selectDetalle.executeQuery();
            
            PreparedStatement restab = conexion.prepareStatement(restablecer);
            PreparedStatement selectP = conexion.prepareStatement(selectProd);
            
            while(detalles.next()){
                String codigoP = detalles.getString(1);
                selectP.setString(1, codigoP);
                ResultSet productos = selectP.executeQuery();
                productos.next();
                int cant = detalles.getInt(2)+productos.getInt(1);
                restab.setInt(1, cant);
                restab.setString(2, codigoP);
                restab.executeUpdate();
            }
             
            PreparedStatement update = conexion.prepareStatement(ssql);
            update.setInt(1, idPedido);
            
            if(update.executeUpdate() > 0){
                JOptionPane.showMessageDialog(this, "Ha sido cancelado correctamente");
                actualizarTabla();
            }    
         } catch (SQLException ex) {
             Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
         }
     } 
     
    public VentanaPrincipal() {
        initComponents();
       
        conexion = ConexionBD.getConexion();
        actualizarTabla();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePedidos = new javax.swing.JTable();
        jButtonEliminar = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jPanelNuevoPedido = new javax.swing.JPanel();
        jButtonBuscar = new javax.swing.JButton();
        jButtonEliminar1 = new javax.swing.JButton();
        jButtonActualizar = new javax.swing.JButton();
        jButtonEliminar2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 37)); // NOI18N
        jLabel1.setText("GESTION DE PEDIDOS:");

        jTablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ESTADO", "FECHA CREACION", "FECHA APROXIMADA", "FECHA LLEGADA", "COMENTARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePedidos);

        jButtonEliminar.setText("ELIMINAR PEDIDO");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonEditar.setLabel("EDITAR");

        jPanelNuevoPedido.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NUEVO PEDIDO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 20))); // NOI18N
        jPanelNuevoPedido.setName("Pedido Nuevo"); // NOI18N

        javax.swing.GroupLayout jPanelNuevoPedidoLayout = new javax.swing.GroupLayout(jPanelNuevoPedido);
        jPanelNuevoPedido.setLayout(jPanelNuevoPedidoLayout);
        jPanelNuevoPedidoLayout.setHorizontalGroup(
            jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelNuevoPedidoLayout.setVerticalGroup(
            jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );

        jButtonBuscar.setText("BUSCAR PEDIDO");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jButtonEliminar1.setText("GENERAR INFORME DE CLIENTES ");
        jButtonEliminar1.setMaximumSize(new java.awt.Dimension(300, 24));
        jButtonEliminar1.setMinimumSize(new java.awt.Dimension(300, 24));
        jButtonEliminar1.setPreferredSize(new java.awt.Dimension(300, 24));
        jButtonEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminar1ActionPerformed(evt);
            }
        });

        jButtonActualizar.setText("ACTUALIZAR");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jButtonEliminar2.setText("GENERAR INFORME DE RECHAZADOS");
        jButtonEliminar2.setMinimumSize(new java.awt.Dimension(300, 24));
        jButtonEliminar2.setPreferredSize(new java.awt.Dimension(300, 24));
        jButtonEliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanelNuevoPedido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jButtonEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jPanelNuevoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanelNuevoPedido.getAccessibleContext().setAccessibleName("");
        jPanelNuevoPedido.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        // TODO add your handling code here:
        VentanaBuscarPedido newFrame = new VentanaBuscarPedido();
        
        newFrame.setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminar1ActionPerformed
        ResultSet resultSet = null;
        try{
            Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = st.executeQuery("SELECT c.nombre_cliente, c.linea_direccion1, c.codigo_postal, c.ciudad, c.telefono, p.codigo_pedido, p.fecha_pedido FROM pedido p JOIN cliente c USING(codigo_cliente) ORDER BY codigo_pedido");
             
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        JFrameInformeClientes jFrameInforme = new JFrameInformeClientes(resultSet);
        jFrameInforme.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonEliminar1ActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        String[] opciones = {"Recibido", "Cancelado"}; 
        int cont = 0;
        try {
             int idPedido = Integer.parseInt(JOptionPane.showInputDialog("Inserta el id del pedido a actualizar:"));
             while(rs.next()){
                if(Integer.parseInt(rs.getString(1)) == idPedido){
                    int x = JOptionPane.showOptionDialog(null, "¿A que estado quieres cambiar el pedido?","Selecciona una opcion",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                    switch(rs.getString(2)){
                        case "Pendiente":
                            if(x == 0){
                                aRecibido(idPedido);
                            }else if(x == 1){
                                aCancelado(idPedido);
                            }
                            break;
                        
                        case "Recibido" :
                            JOptionPane.showMessageDialog(this, "El pedido seleccionado ya ha sido entregado");
                            break;
                        
                        case "Cancelado" : 
                            JOptionPane.showMessageDialog(this, "El pedido seleccionado ha sido cancelado, no puede acutalizarse");
                            break;
                                    
                    }
                    break;
                }else{
                    cont++;
                }
                
             }
         } catch (SQLException ex) {
             Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jButtonEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminar2ActionPerformed

        jFrameInformeRechazados frame = null;
        
        try {
            Statement cRechazados = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultRechazados = cRechazados.executeQuery("SELECT * FROM pedido WHERE estado LIKE 'Rechazado'");
            frame = new jFrameInformeRechazados(resultRechazados);
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonEliminar2ActionPerformed

    private void jTablePedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePedidosMouseClicked
        // TODO add your handling code here: 
        if(jTablePedidos.getSelectedRowCount() > 0){
            codigoPedido = (String) (jTablePedidos.getValueAt(jTablePedidos.getSelectedRow(), 0));
        }
    }//GEN-LAST:event_jTablePedidosMouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
        Connection conexion = ConexionBD.getConexion();
        
        PreparedStatement sentenciaDetallesPedido = null;
        PreparedStatement sentenciaStock = null;
        PreparedStatement sentenciaEliminarDetalles = null;
        
        try{
            String sentenciaSQL = "SELECT * FROM detalle_pedido WHERE codigo_pedido LIKE ?";
            String sentenciaSQL1 = "UPDATE producto SET cantidad_en_stock = cantidad_en_stock + ? WHERE codigo_producto = ?";
            String sentenciaSQL2 = "DELETE FROM detalle_pedido WHERE codigo_pedido LIKE ?";
            
            sentenciaDetallesPedido = conexion.prepareStatement(sentenciaSQL);
            sentenciaStock = conexion.prepareStatement(sentenciaSQL1);
            sentenciaEliminarDetalles = conexion.prepareStatement(sentenciaSQL2);
            
            sentenciaDetallesPedido.setString(1, codigoPedido);
            
            ResultSet rs = sentenciaDetallesPedido.executeQuery();

            while(rs.next()){
                String codigoProducto = rs.getString("codigo_producto");
                int cantidadProducto = rs.getInt("cantidad");
                
                String codigoProductoActual = codigoProducto;
                int cantidadActual = cantidadProducto;
                
                try{
                    sentenciaStock.setInt(1, cantidadActual);
                    sentenciaStock.setString(2, codigoProductoActual);
                    sentenciaStock.executeUpdate();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
                
                sentenciaEliminarDetalles.setString(1, codigoPedido);
                sentenciaEliminarDetalles.executeUpdate();
            }
            rs.close();
            sentenciaDetallesPedido.close();
            sentenciaStock.close();
            sentenciaEliminarDetalles.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
        catch(Exception e){
            e.printStackTrace();
        }
        
        PreparedStatement sentenciaEliminarPedido = null;
                
        try{
            String sentenciaSQL3 = "DELETE FROM pedido WHERE codigo_pedido LIKE ?";
            sentenciaEliminarPedido = conexion.prepareStatement(sentenciaSQL3);
            sentenciaEliminarPedido.setString(1, codigoPedido);
            sentenciaEliminarPedido.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        } 
        catch(Exception e){
            e.printStackTrace();
        }
        
        actualizarTabla();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonEliminar1;
    private javax.swing.JButton jButtonEliminar2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelNuevoPedido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePedidos;
    // End of variables declaration//GEN-END:variables
}
