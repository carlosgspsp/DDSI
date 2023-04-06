package practica3;

import java.sql.*;

public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
     
        
        System.out.println("Bienvenido al programa de Gestion de Base de Datos de Mercadona");
        
        
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection(
            "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es",
            "x7390389",
            "x7390389");
            
            String cadena = "Conexion establecida con exito";
            System.out.println(cadena);
            
            Statement st = conexion.createStatement();
            Modelo modelo = new Modelo(conexion, st);
            //Modelo
            VistaTextual cv = new VistaTextual();
            Controlador control = new Controlador(modelo, cv);
            modelo.GetConexion().setAutoCommit(false);
            cv.setModelo(modelo);
            control.iniciar();
            conexion.commit();
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexion de la base de datos");
        }
        
        System.out.println("Hasta pronto");
    }  
}
