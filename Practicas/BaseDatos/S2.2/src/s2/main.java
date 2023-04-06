package s2;

import java.sql.*;

public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        VistaTextual vista;
        vista = new VistaTextual();
        
        Modelo modelo = new Modelo();
        
        Controlador controlador;
        controlador = new Controlador (modelo, vista);
        
        System.out.println("Bienvenido al programa de gestión de almacén");
        vista.pausa();
        
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conexion = DriverManager.getConnection(
            "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es",
            "x5170604",
            "x5170604");
            
            String cadena = "Conexion establecida con exito";
            System.out.println(cadena);
            
            modelo.setConexion(conexion);
            Statement st = conexion.createStatement();
            modelo.setStatement(st);
            conexion.setAutoCommit(false);
            controlador.ejecutar();
            /************************************************************/
            modelo.MostrarTablas();
            conexion.commit();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Sin conexion");
        }
        
        System.out.println("Hasta pronto");
    }  
}
