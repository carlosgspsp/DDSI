package s2;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.sql.*;

public class Modelo {
    
    private Connection con;
    private boolean terminar;
    private boolean finalizar_pedido;
    private Statement st;
    private Savepoint punto;
    
    public Modelo(Connection conex){
        con = conex;
        terminar = false;
        finalizar_pedido = false;
    }
    
    public Modelo (){
        terminar = false;
        finalizar_pedido = false;
    }
        
    public void setConexion(Connection conexion){
        con = conexion;
    }
    
    public void MostrarTablas(){
        MostrarTablaStock();
        MostrarTablaPedido();
        //MostrarTablaDetallePedido();
    }
    
    public void MostrarTablaStock(){
        String sql = "SELECT * FROM STOCK";
        ResultSet rs; 
        try {
            rs = st.executeQuery(sql);
            System.out.println("cproducto\tcantidad");
            rs.toString();
            while (rs.next()) {
            System.out.println (rs.getString ("cproducto") + "\t\t" + rs.getString("cantidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Problema al mostrar la tabla STOCK");
        }   
    } 
    public void MostrarTablaPedido(){
        String sql = "SELECT * FROM PEDIDO";
        ResultSet rs; 
        try {
            rs = st.executeQuery(sql);
            System.out.println("cpedido\t\tccliente\tfecha");
            //rs.toString();
            while (rs.next()) {
                System.out.println (rs.getString("cpedido") + "\t\t" + rs.getString("ccliente") + "\t\t" + rs.getString("fecha"));
                MostrarTablaDetallePedido(rs.getInt("cpedido"));     
            }
        } catch (SQLException ex) {
            System.out.println("Problema al mostrar la tabla PEDIDO");
        }   
    } 
    
    public void MostrarTablaPedido(int c_pedido){
        String sql = "SELECT * FROM PEDIDO WHERE cpedido = " + c_pedido;
        ResultSet rs; 
        try {
            rs = st.executeQuery(sql);
            System.out.println("cpedido\t\tccliente\tfecha");
            //rs.toString();
            while (rs.next()) {
                System.out.println (c_pedido + "\t\t" + rs.getString("ccliente") + "\t\t" + rs.getString("fecha"));
                MostrarTablaDetallePedido(c_pedido);   
            }
        } catch (SQLException ex) {
            System.out.println("Problema al mostrar la tabla PEDIDO de: " + c_pedido);
        }   
    } 
    public void MostrarTablaDetallePedido(int pedido){
        String sql = "SELECT * FROM DETALLE_PEDIDO WHERE cpedido = " + pedido;
        ResultSet rs; 
        Statement st2;        
        try {
            st2 = con.createStatement();
            rs = st2.executeQuery(sql);
            System.out.println("Detalles\t\t\t\tcproducto\tcantidad");
            rs.toString();
            while (rs.next()) {
            System.out.println ("\t\t\t\t\t\t" + rs.getString("cproducto") + "\t\t" + rs.getString("cantidad"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Problema al mostrar la tabla DETALLE_PEDIDO del pedido: " + pedido);
        }   
    } 
    
    public void MostrarTablaDetallePedido(){
        String sql = "SELECT * FROM DETALLE_PEDIDO ";
        ResultSet rs; 
        try {
            rs = st.executeQuery(sql);
            System.out.println("cpedido\t\tcproducto\tcantidad");
            rs.toString();
            while (rs.next()) {
            System.out.println (rs.getString ("cpedido") + "\t\t" + rs.getString("cproducto") + "\t\t" + rs.getString("cantidad"));
            }
        } catch (SQLException ex) {
            System.out.println("Problema al mostrar la tabla DETALLE_PEDIDO");
        }   
    } 
    
    
    public void setStatement(Statement st){
        this.st = st;
    }
    
    public boolean Terminar (){
        finalizar_pedido = false;
        return terminar;
    }
    
    public boolean Rehacer_Tablas(){
        boolean ok;
        
        
        
        try {
            st.executeUpdate("DROP TABLE detalle_pedido");
            System.out.println("Tabla detalle-pedido borrada con exito.");
        } catch (SQLException e) {
            System.out.println("Tabla detalle-pedido ya borrada o no existe");
        }
        try {
            st.executeUpdate("DROP TABLE stock");
            System.out.println("Tabla stock borrada con exito.");
        } catch (SQLException e) {
            System.out.println("Tabla stock ya borrada o no existe");
        }
        try {
            st.executeUpdate("DROP TABLE pedido");
            System.out.println("Tabla pedido borrada con exito.");
        } catch (SQLException e) {
            System.out.println("Tabla pedido ya borrada o no existe");
        }
        try{ 
            
            /**********************************************/
            st.executeUpdate("CREATE TABLE stock ( cproducto INT PRIMARY KEY, cantidad INT CHECK (cantidad>=0))");
            st.executeUpdate("INSERT INTO stock VALUES (1, 10)");
            st.executeUpdate("INSERT INTO stock VALUES (2, 20)");
            st.executeUpdate("INSERT INTO stock VALUES (3, 15)");
            st.executeUpdate("INSERT INTO stock VALUES (4, 5)");
            st.executeUpdate("INSERT INTO stock VALUES (5, 30)");
            st.executeUpdate("INSERT INTO stock VALUES (6, 27)");
            st.executeUpdate("INSERT INTO stock VALUES (7, 42)");
            st.executeUpdate("INSERT INTO stock VALUES (8, 8)");
            st.executeUpdate("INSERT INTO stock VALUES (9, 13)");
            st.executeUpdate("INSERT INTO stock VALUES (10, 50)");
            /**********************************************/
            st.executeUpdate("CREATE TABLE pedido(cpedido INT PRIMARY KEY,ccliente INT NOT NULL, fecha DATE NOT NULL)");
            st.executeUpdate("CREATE TABLE detalle_pedido (cpedido INT REFERENCES pedido(cpedido), cproducto INT REFERENCES stock(cproducto), cantidad INT NOT NULL, PRIMARY KEY (cpedido, cproducto))");   
            System.out.println("Tablas generadas con éxito");
            ok = true;
            con.commit();
        } catch(SQLException e){
            System.out.println("Problema al generar las tablas. Intentelo de nuevo");
            ok = false;
        }

        return ok;
    }
    
    public boolean Dar_Alta_Pedido(int cpedido, int ccliente, String fecha, String formato){
        boolean ok;
        try{
            punto = con.setSavepoint();
            String sql = "INSERT INTO PEDIDO VALUES (" + String.valueOf(cpedido) + "," + String.valueOf(ccliente) + ",'" + fecha + "')";
            st.executeUpdate(sql);
            System.out.println("Pedido insertado con éxito");
            ok = true;
        } catch(SQLException e){
            System.out.println("Problema al insertar el pedido. Intentelo de nuevo");
            ok = false;
        }
        return ok;
    }
    
    public boolean Borrar_Pedido(int id){
        boolean ok;
        try{
            String sql = "DELETE FROM PEDIDO WHERE cpedido = " + id;
            String sql2 = "DELETE FROM DETALLE_PEDIDO WHERE cpedido = " + id;
            st.executeUpdate(sql2);
            st.executeUpdate(sql);
            con.commit();
            System.out.println("Pedido eliminado con éxito");
            ok = true;
        } catch(SQLException e){
            System.out.println("Problema al eliminar el pedido. Intentelo de nuevo");
            ok = false;
        }
        return ok;
    }
    
    public void Salir(){
        terminar = true;
    }
    
    public boolean Aniadir_Detalle (int cpedido, int cproducto, int cantidad){
        boolean ok;
        String select = "SELECT * FROM STOCK WHERE cproducto = " + cproducto;
        ResultSet rs;
        int stock = -1;
        try {
            rs = st.executeQuery(select);
            rs.next();
            //stock = Integer.parseInt(rs.getString("cantidad"));
            stock = rs.getInt("cantidad");
            //System.out.println("Stock: " + stock);
        } catch (SQLException ex) {
            System.out.println("Problema al consultar la tabla Stock");
        }
        
        if (stock == -1){
            return false;
        }
        else {
            if (cantidad > stock){
                System.out.println("No hay existencias suficientes. No se puede añadir el detalle. Intentelo de nuevo");
                return false;
            }
        }
        try{
            int n_cantidad = stock - cantidad;
            String update = "UPDATE STOCK SET cantidad = " + n_cantidad + "WHERE cproducto = " + cproducto;
            st.executeUpdate(update);
            String sql = "INSERT INTO DETALLE_PEDIDO VALUES (" + cpedido + "," + cproducto + "," + cantidad + ")";
            st.executeUpdate(sql);
            System.out.println("Detalle añadido con éxito");
            ok = true;
        } catch(SQLException e){
            System.out.println("No se puede añadir el detalle. Intentelo de nuevo");
            ok = false;
        }

        return ok;
    }
    
    public boolean Eliminar_Detalles_Producto(int id){
        boolean ok;
        try{
            String sql = "DELETE FROM DETALLE_PEDIDO WHERE cpedido = " + id;
            st.executeUpdate(sql);
            System.out.println("Detalles del Pedido eliminados con éxito");
            ok = true;
        } catch(SQLException e){
            System.out.println("Problema eliminar los detalles. Intentelo de nuevo");
            ok = false;
        }

        return ok;
    }
    
    public boolean Cancelar_Pedido(){
        boolean ok;
        try{ 
            con.rollback(punto);
            punto = null;
            System.out.println("Pedido cancelado");
            ok = true;
            finalizar_pedido = true;
        } catch(SQLException e){
            System.out.println("No se pudo cancelar el pedido. Intentelo de nuevo");
            ok = false;
        }

        return ok;
    }
    
    public void Finalizar_Pedido (){
        try{
            con.commit();
            System.out.println("Finalizadas las acciones sobre el pedido actual");
        }
        catch(SQLException e){
            
        }
        finalizar_pedido = true;
    }
    
    public boolean Fin_Pedido(){
        return finalizar_pedido;
    }
}
