import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelListener;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    private Connection conexion;
    private String codigoPedido = "";
    private ResultSet rs;
    private ResultSet productos;
    private ResultSet clientes;
    private DefaultTableModel modelTablaProds;
    private DefaultTableModel modeloProductos;
     
     //Metodo para saber el id de un cliente por su nombre
     public int idCliente(String nombre){
        try {
            while(clientes.next()){
                if(clientes.getString(2).equals(nombre)){
                    int id = clientes.getInt(1);
                    return id;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return 0;   
     }
    
     //Metodo para cargar la lista de Clientes
     public void cargarClientesCategorias(){
        try {
            Statement sClientes = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            clientes = sClientes.executeQuery("SELECT codigo_cliente, nombre_cliente FROM cliente");
            while(clientes.next()){
                jComboClientes.addItem(clientes.getString(2));
            }
            clientes.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Statement sCategorias = conexion.createStatement();
            ResultSet categorias = sCategorias.executeQuery("SELECT gama FROM gama_producto");
            while(categorias.next()){
                jComboCategorias.addItem(categorias.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
     //EVENTO PARA LEER QUE SE MODIFIQUE EL TABLE MODEL
     public void tableModelListener(){
        modelTablaProds = (DefaultTableModel)jTableArticulos.getModel();
        
        modelTablaProds.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.INSERT) {
                    jButtonCrearPedido.setEnabled(true);
                }else if(modelTablaProds.getRowCount() < 1){
                     jButtonCrearPedido.setEnabled(false);
                }
            }
        });
     }
      
     //EVENTO PARA LEER EL DOBLECLICK
     public void mouseListener(){
         String[] producto = new String[6];
         jTableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            
             @Override
             public void mouseClicked(java.awt.event.MouseEvent e){
                 if(e.getClickCount() == 2){
                     int cant = 0;
                     if(Integer.parseInt(String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 3))) > 0){
                     try{
                         cant = Integer.parseInt(JOptionPane.showInputDialog("Inserta la cantidad deseada"));
                     }catch(Exception ex){
                         JOptionPane.showMessageDialog(null, "Inserta un numero");
                     }
                     if(cant > 0){
                        producto[0] = String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 0));
                        producto[1] = String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 1));
                        producto[2] = String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 2));
                        producto[3] = String.valueOf(cant);
                        producto[4] = String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 4));
                        producto[5] = String.valueOf(jTableProductos.getValueAt(jTableProductos.getSelectedRow(), 5));
                        modelTablaProds.addRow(producto);
                        jTableArticulos.setModel(modelTablaProds);
                     }
                     
                    }
                 }                 
             }
         }
       );}
     
     //Metodo para insertar los datos del pedio nuevo
     public void insertarDatos(){
        try {
            rs.last();
            jTextID.setText(rs.getString(1));
            jTextFechaCreacion.setText(rs.getString(3));
            jTextFechaAprox.setText(rs.getString(4));
            rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
     //Metodo para actualizar la tabla
     public void actualizarTabla(){
        try{
            Statement st = conexion.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("SELECT codigo_pedido, estado, fecha_pedido, fecha_esperada, fecha_entrega, comentarios FROM pedido ORDER BY codigo_pedido");
             
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
        modelTablaProds = new DefaultTableModel();
        modelTablaProds.addColumn("ID");
        modelTablaProds.addColumn("Nombre");
        modelTablaProds.addColumn("Dimensiones");
        modelTablaProds.addColumn("Cantidad");
        modelTablaProds.addColumn("Precio");
        modelTablaProds.addColumn("Descripcion");
        conexion = ConexionBD.getConexion();
        actualizarTabla();
        cargarClientesCategorias();
        mouseListener();
        tableModelListener();
        jTableProductos.setDefaultEditor(Object.class, null);
        
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
        jPanel1 = new javax.swing.JPanel();
        jComboClientes = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFechaCreacion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFechaAprox = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProductos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jComboCategorias = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableArticulos = new javax.swing.JTable();
        jButtonMenos = new javax.swing.JButton();
        jButtonBorrarArt = new javax.swing.JButton();
        jButtonCrearPedido = new javax.swing.JButton();
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jComboClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONA CLIENTE" }));

        jLabel3.setText("NOMBRE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Pedido"));

        jTextID.setEnabled(false);
        jTextID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIDActionPerformed(evt);
            }
        });

        jLabel2.setText("ID");

        jLabel4.setText("FECHA CREACION");

        jTextFechaCreacion.setEnabled(false);
        jTextFechaCreacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFechaCreacionActionPerformed(evt);
            }
        });

        jLabel5.setText("FECHA APROXIMADA");

        jTextFechaAprox.setEnabled(false);
        jTextFechaAprox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFechaAproxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFechaAprox, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jTextFechaCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFechaAprox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

        jTableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableProductos);

        jLabel6.setText("CATEGORIA");

        jComboCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONA UNA CATEGORIA" }));
        jComboCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboCategoriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Articulos del pedido"));

        jTableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane3.setViewportView(jTableArticulos);

        jButtonMenos.setText("-1");
        jButtonMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMenosActionPerformed(evt);
            }
        });

        jButtonBorrarArt.setText("BORRAR");
        jButtonBorrarArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorrarArtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBorrarArt)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMenos)
                    .addComponent(jButtonBorrarArt))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonCrearPedido.setText("CREAR PEDIDO");
        jButtonCrearPedido.setEnabled(false);
        jButtonCrearPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelNuevoPedidoLayout = new javax.swing.GroupLayout(jPanelNuevoPedido);
        jPanelNuevoPedido.setLayout(jPanelNuevoPedidoLayout);
        jPanelNuevoPedidoLayout.setHorizontalGroup(
            jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNuevoPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelNuevoPedidoLayout.createSequentialGroup()
                        .addGroup(jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jButtonCrearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelNuevoPedidoLayout.setVerticalGroup(
            jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNuevoPedidoLayout.createSequentialGroup()
                .addGroup(jPanelNuevoPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelNuevoPedidoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCrearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(jPanelNuevoPedidoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jButtonEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanelNuevoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jPanelNuevoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
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
        mouseListener();
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

    private void jTextIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIDActionPerformed

    private void jTextFechaCreacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFechaCreacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFechaCreacionActionPerformed

    private void jTextFechaAproxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFechaAproxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFechaAproxActionPerformed

    private void jButtonMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMenosActionPerformed
        int cant = Integer.parseInt(String.valueOf(jTableArticulos.getValueAt(jTableArticulos.getSelectedRow(), 3)))-1;
        DefaultTableModel modelo = modelTablaProds;
        jTableArticulos.setValueAt(cant, jTableArticulos.getSelectedRow(), 3);
    }//GEN-LAST:event_jButtonMenosActionPerformed

    private void jButtonBorrarArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorrarArtActionPerformed
        DefaultTableModel modelo = modelTablaProds;
        modelo.removeRow(jTableArticulos.getSelectedRow());
    }//GEN-LAST:event_jButtonBorrarArtActionPerformed

    private void jComboCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboCategoriasActionPerformed
        try {
            Statement sProductos = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            productos = sProductos.executeQuery("SELECT * FROM producto");
            modeloProductos = new DefaultTableModel();
            String[] result = new String[6];
            modeloProductos.addColumn("ID");
            modeloProductos.addColumn("Nombre");
            modeloProductos.addColumn("Dimensiones");
            modeloProductos.addColumn("Stock");
            modeloProductos.addColumn("Precio");
            modeloProductos.addColumn("Descripcion");
            
            while(productos.next()){
                result[0] = productos.getString(1);
                result[1] = productos.getString(2);
                result[2] = productos.getString(4);
                result[3] = productos.getString(7);
                result[4] = productos.getString(8);
                result[5] = productos.getString(6);
                modeloProductos.addRow(result);
            }
            jTableProductos.setModel(modeloProductos);
            productos.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboCategoriasActionPerformed

    private void jTableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableProductosMouseClicked

    private void jButtonCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPedidoActionPerformed
        String sPedido = "INSERT INTO pedido VALUES(?,curdate(),curdate()+10,null,'Pendiente',null,?)";
        String sDetalle = "INSERT INTO detalle_pedido VALUES(?,?,?,?,?)";
        String sProducto = "UPDATE producto SET cantidad_en_stock=? WHERE codigo_producto =?";
        
        int id;
        int idClient;
        int cont=0;
        
        try {
            PreparedStatement pedido = conexion.prepareStatement(sPedido);
            PreparedStatement detalle = conexion.prepareStatement(sDetalle);
            PreparedStatement producto = conexion.prepareStatement(sProducto);
            
            rs.last();
            id = Integer.parseInt(rs.getString(1))+1;
            rs.beforeFirst();
            idClient = idCliente(String.valueOf(jComboClientes.getSelectedItem()));
            pedido.setInt(1, id);
            //pedido.setString(2, comentario);
            pedido.setInt(2, idClient);
            
            if(pedido.executeUpdate() > 0){cont++;}
            
            for(int i=0; i<modelTablaProds.getRowCount(); i++){
                detalle.setInt(1, id);
                String idProd = String.valueOf(modelTablaProds.getValueAt(i, 1));
                detalle.setString(2, idProd);
                int cant = Integer.parseInt(String.valueOf(modelTablaProds.getValueAt(i, 3)));
                detalle.setInt(3, cant);
                float precio = Float.parseFloat(String.valueOf(modelTablaProds.getValueAt(i, 4)));
                detalle.setFloat(4, precio);
                detalle.setInt(5, i);
                
                
//                if(detalle.executeUpdate()>0){cont++;}
                
                int cantProd = Integer.parseInt(String.valueOf(modeloProductos.getValueAt(i, 3)));
                producto.setInt(1, cantProd);
                producto.setString(2, idProd);
                
                if(producto.executeUpdate()>0){cont++;}
                
            }
                if(cont==3){JOptionPane.showMessageDialog(this, "Pedido creado correctamente");}
            
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCrearPedidoActionPerformed

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
    private javax.swing.JButton jButtonBorrarArt;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCrearPedido;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonEliminar1;
    private javax.swing.JButton jButtonEliminar2;
    private javax.swing.JButton jButtonMenos;
    private javax.swing.JComboBox<String> jComboCategorias;
    private javax.swing.JComboBox<String> jComboClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelNuevoPedido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableArticulos;
    private javax.swing.JTable jTablePedidos;
    private javax.swing.JTable jTableProductos;
    private javax.swing.JTextField jTextFechaAprox;
    private javax.swing.JTextField jTextFechaCreacion;
    private javax.swing.JTextField jTextID;
    // End of variables declaration//GEN-END:variables
}
