import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adrig
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/jardineria";
    private static Connection conexion;

    public static Connection getConexion(){
        if (conexion == null){
            try {
                String nombreUsuario = JOptionPane.showInputDialog(null, "Inserta el usuario:");
                String contra = JOptionPane.showInputDialog(null, "Inserta la contraseña:");
                
                conexion = DriverManager.getConnection(URL, nombreUsuario, contra);
            }
            catch (SQLException ex){
                JOptionPane.showMessageDialog(null,"No se pudo realizar la conexión con la Base de Datos.", "ERROR CONEXION CON BASE DE DATOS",JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        }
        return conexion;
    }
}
