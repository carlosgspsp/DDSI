/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Santiago Carbó García
 */
public class Modelo {
    private Statement estado;
    private Connection conexion;
    
    
public Modelo (Connection con, Statement state){
       
     estado = state;
     conexion = con;

}

public Connection GetConexion (){
       
     
     return conexion;

}

//EMPLEADOS
public void AgregarEmpleado() 
{
      Scanner in;
    in = new Scanner (System.in);
    System.out.println("\nIntroduzca el DNI del nuevo empleado\n");
    String dni = in.nextLine();
    System.out.println("\nIntroduzca el nombre del empleado (sin apellidos)\n");
    String nombre = in.nextLine();
    System.out.println("\nIntroduzca los apellidos del empleado.\n");
    String apellidos = in.nextLine();
    System.out.println("\nIntroduzca el telefono de contacto del empleado.\n");
    String telefono = in.nextLine();
    System.out.println("\nIntroduzca la fecha de nacimiento del empleado. DD/MM/YYYY\n");
    String fecha_nacimiento = in.nextLine();
    System.out.println("\nIntroduzca el departamento al que perteneze. (1.Logistica 2.Recursos Humanos 3.Financiero 4.Proveedores)\n");
    String departamento = in.nextLine();

    try{
        
    estado.executeUpdate("INSERT INTO EMPLEADO values('" + dni + "', '" + nombre + "', '" + apellidos + "', '" +  telefono + "', '"+ fecha_nacimiento + "', '"+ departamento +"')");
            
    }catch(SQLException e)
    {
        System.out.println("\nError al Registrar el empleado.\n");
    }
    try{
       ResultSet rs = estado.executeQuery ("select * from EMPLEADO WHERE DNI='"+dni+"'");
            while(rs.next()){
            System.out.println("\nDNI: "+rs.getString("DNI"));    
            System.out.println("Nombre: "+rs.getString("nombre"));    
            System.out.println("Apellidos: "+rs.getString("apellidos"));
            System.out.println("Telefono: "+rs.getString("tlf"));
            System.out.println("Fecha de Nacimiento: "+rs.getString("fecha_nacimiento"));
            System.out.println("Departamento: "+rs.getString("cod_departamento"));
    
            }
            rs.close();  
            conexion.commit();
            System.out.println("\nEmpleado Agregado con Exito\n");
    }catch(SQLException e)
    {
        System.out.println("\nError al Mostrar Empleado.\n");
    }
       
}


public void ConsultarEmpleado() 
{
        System.out.println("\nIntroduzca el DNI del Empleado");
        Scanner in;
        in = new Scanner (System.in);
        String dni = in.nextLine();
        

        try{
           ResultSet rs = estado.executeQuery("SELECT * FROM EMPLEADO where DNI='"+dni+"'");
           String sql = null;
           while (rs.next())
           {
               System.out.println("\nDNI: "+rs.getString("DNI")); 
               System.out.println("\nNombre: "+rs.getString("nombre"));
               System.out.println("\nApellidos: "+rs.getString("apellidos"));
               System.out.println("\nContacto: "+rs.getString("tlf"));
               System.out.println("\nFecha de Nacimiento: "+rs.getString("fecha_nacimiento"));
               sql = rs.getString("cod_departamento");
           }
            
           rs = estado.executeQuery("SELECT * FROM DEPARTAMENTOS WHERE cod_departamento='"+sql+"'");
           while (rs.next())
                System.out.println("\nDepartamento: "+rs.getString("nombre")); 
           
           
           System.out.println("\nINFORMACION SOBRE LAS NOMINAS ");
           
           rs = estado.executeQuery("SELECT * FROM NOMINA where DNI='"+dni+"'");
           
           while (rs.next())
           {
               System.out.println("\nCodigo de la nomina: "+rs.getString("cod_nom")); 
               System.out.println("\nPago: "+rs.getString("pago"));
             
           }
           
           System.out.println("\nINFORMACION SOBRE LAS ASISTENCIAS ");
              rs = estado.executeQuery("SELECT * FROM ASISTENCIA where DNI='"+dni+"'");
           while (rs.next())
           {
               System.out.println("\nFecha: "+rs.getString("fecha")); 
               System.out.println("\nMotivo: "+rs.getString("motivo"));
               sql = rs.getString("cod_estado");
               
               Statement estado2 = conexion.createStatement();
               ResultSet rs_aux  =  estado2.executeQuery("SELECT * FROM ESTADO where cod_estado='"+sql+"'");
               
            while(rs_aux.next())  
                System.out.println("\nEstado: "+rs_aux.getString("nombre")); 
           }
           
        }catch(SQLException e)
        {
            System.out.println("\nError al consultar empleado.\n");
        }
        
}

public void ModificarEmpleado()
{  
        System.out.println("\nIntroduzca el DNI del Empleado");
        Scanner in;
        in = new Scanner (System.in);
        String dni = in.nextLine();
        int opcion;
        boolean terminar = false, error = false;
        Savepoint punto = null;
        
      try
        {
            punto = conexion.setSavepoint();
     while(!terminar)  
     {
         
         System.out.println("\nQue valor desea modificar \n1.Nombre.\n2.Apellidos.\n3.Contacto.\n4.Fecha de Nacimiento.\n5.Terminar.");    
         opcion = Integer.parseInt(in.nextLine());
         switch(opcion)
        {
            case 1:
                System.out.println("\nIntroduzca el nuevo Nombre (Introduzca -1 para cancelar la operacion)");
                String nombre = in.nextLine();
                if (!nombre.equals("-1"))
                estado.executeUpdate("UPDATE EMPLEADO SET nombre='"+nombre+"' WHERE DNI= '"+dni+"'");
                break;
            case 2:
                System.out.println("\nIntroduzca los nuevos Apellido (Introduzca -1 para cancelar la operacion)");
                String apellidos = in.nextLine();
                if (!apellidos.equals("-1"))
                estado.executeUpdate("UPDATE EMPLEADO SET apellidos='"+apellidos+"' WHERE DNI= '"+dni+"'");
                break;
            case 3:
                System.out.println("\nIntroduzca el nuevo Contacto (Introduzca -1 para cancelar la operacion)");
                String contacto = in.nextLine();
                if (!contacto.equals("-1"))
                estado.executeUpdate("UPDATE EMPLEADO SET tlf="+contacto+" WHERE DNI= '"+dni+"'");
                 break;
            case 4:
                System.out.println("\nIntroduzca la nueva Fecha de Nacimiento DD/MM/YYYY (Introduzca -1 para cancelar la operacion)");
                String fecha_nacimiento = in.nextLine();
                if (!fecha_nacimiento.equals("-1"))
                estado.executeUpdate("UPDATE EMPLEADO SET fecha_nacimiento='"+fecha_nacimiento+"' WHERE DNI= '"+dni+"'");
                 break;
            case 5:
                terminar = true;
                 break;
         }      
     }
        }catch(SQLException ex)
        {
            error = true;
           System.out.println("\nError al Modificar Empleado.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
      
      try{
          if (!error)
              conexion.commit();
          else
              conexion.rollback(punto);
          
      }catch(SQLException ex)
      {
         System.out.println("\nError al hacer el commit o el rollback.\n"); 
         System.out.println("Error: "+ex.getMessage()); 
      }
    
}


public void EliminarEmpleado()
{
     System.out.println("\nIntroduzca el DNI del Empleado que desea Eliminar");
     Scanner in;
     in = new Scanner (System.in);
     String dni = in.nextLine();
     boolean error = false;
     Savepoint punto = null;
     
     
     try{
         punto = conexion.setSavepoint();
         estado.executeUpdate("DELETE FROM ASISTENCIA WHERE DNI='"+dni+"'");
         estado.executeUpdate("DELETE FROM NOMINA WHERE DNI='"+dni+"'");
         estado.executeUpdate("DELETE FROM CONTABILIZA WHERE DNI='"+dni+"'");
         estado.executeUpdate("DELETE FROM ADMINISTRA WHERE DNI='"+dni+"'");
         estado.executeUpdate("DELETE FROM GESTIONA WHERE DNI1='"+dni+"' OR DNI2='"+dni+"'");
         estado.executeUpdate("DELETE FROM EMPLEADO WHERE DNI='"+dni+"'");
     
     
     }catch(SQLException ex)
     {
           error = true;
           System.out.println("\nError al Eliminar Empleado.\n"); 
           System.out.println("Error: "+ex.getMessage());
     }
      
     try
      {
          if (!error)
        {
            conexion.commit();;
            System.out.println("Empleado Eliminado con exito.");
            
        }else
        {
            conexion.rollback(punto);
        }
      }catch(SQLException ex)
      {
          System.out.println("\nError en commit o en rollback.\n");
          System.out.println("Error: "+ex.getMessage());
      }
     
     
     
}


//PAGOS Y PARTIDAS
public void RegistrarPago(String cod_partida)
{
    Scanner in;
    in = new Scanner (System.in);
    String cod_pago;
    System.out.println("\nIntroduzca la cantidad del pago.\n");
    String cantidad = in.nextLine();
    boolean error = false;
    String numero_cod_pago_st = "";
    int numero_cod_pago, mayor = 0;
    Savepoint punto = null;
    String cod_pago_nuevo = "";
    
    
    try{
            punto = conexion.setSavepoint();
            ResultSet rs = estado.executeQuery("SELECT cod_pago FROM PAGO WHERE cod_partida='"+cod_partida+"'");
             
            while(rs.next())
            {
                cod_pago = rs.getString("cod_pago");
                numero_cod_pago_st =  cod_pago.replace("pg", "");
                numero_cod_pago = Integer.parseInt(numero_cod_pago_st);
                if (numero_cod_pago > mayor)
                {
                    mayor = numero_cod_pago;  
                }
                numero_cod_pago_st="";
            }
            mayor += 1;
            cod_pago_nuevo = "pg"+mayor;
             
            estado.executeUpdate("INSERT INTO PAGO values('" + cod_partida + "', '" + cod_pago_nuevo + "', SYSDATE, '" +  cantidad + "')");

            rs.close();  
            
    }catch(SQLException ex)
    {
           error = true;
           System.out.println("\nError al Registrar Pago.\n"); 
           System.out.println("Error: "+ex.getMessage());
    }
    
    try
      {
          if (!error)
        {
            conexion.commit();;
            System.out.println("\nPago Introducido con exito\n");
            
        }else
        {
            conexion.rollback(punto);
        }
      }catch(SQLException ex)
      {
          System.out.println("\nError en commit o en rollback.\n");
          System.out.println("Error: "+ex.getMessage());
      }
    
}


public void ModificarPago(String cod_partida)
{
    
     
        Scanner in;
        in = new Scanner (System.in);
        System.out.println("\nIntroduzca el codigo del pago que desea modificar:)");
        String cod_pago = in.nextLine();
        boolean error = false;
        Savepoint punto = null;
        
    try
    {
            punto = conexion.setSavepoint();
        
            System.out.println("\n¿Cual es el nuevo importe del pago?)");
            String cantidad = in.nextLine();
            estado.executeUpdate("UPDATE PAGO SET cantidad="+cantidad+" WHERE cod_pago='"+ cod_pago +"' AND cod_partida='"+cod_partida+"'");
                    
    }catch(SQLException ex)
        {
            error = true;
           System.out.println("\nError al Modificar Pago.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
      
      try{
          if (!error)
              conexion.commit();
          else
              conexion.rollback(punto);
          
      }catch(SQLException ex)
      {
         System.out.println("\nError al hacer el commit o el rollback.\n"); 
         System.out.println("Error: "+ex.getMessage()); 
      }
}   


public void RegistrarPartida()
{
    Scanner in;
    boolean error = false;
    in = new Scanner (System.in);
    System.out.println("\nIntroduzca el empleado que contabiliza la partida\n");
    String empleado = in.nextLine();
    System.out.println("\nIntroduzca el importe de la partida.\n");
    String importe = in.nextLine();
    String numero_cod_partida_st = "", cod_partida;
    int numero_cod_partida, mayor = 0;
    
    Savepoint punto = null;
    
    try{
            punto = conexion.setSavepoint();
            ResultSet rs = estado.executeQuery("SELECT cod_partida FROM PARTIDA");
             
            while(rs.next())
            {
                cod_partida = rs.getString("cod_partida");
                numero_cod_partida_st =  cod_partida.replace("pa", "");
                numero_cod_partida = Integer.parseInt(numero_cod_partida_st);
                if (numero_cod_partida > mayor)
                {
                    mayor = numero_cod_partida;  
                }
                numero_cod_partida_st="";
            }
            mayor += 10;
            String cod_partida_nuevo = "pa"+mayor;
            System.out.println("\nAntes insert");
            estado.executeUpdate("INSERT INTO PARTIDA values('" + cod_partida_nuevo + "', '" + importe + "', SYSDATE)");
            System.out.println("\nDespues insert Partida");
            estado.executeUpdate("INSERT INTO CONTABILIZA values('" + empleado + "', '" + cod_partida_nuevo +"')");
            System.out.println("\nDespues insert Contabiliza");
          
           System.out.println("\n¿Desea Agregar Pagos a la partida?.\n1.Si.\n2.No.");
           int opcion  = Integer.parseInt(in.nextLine());
           if (opcion == 1)
           {
            boolean terminar = false;
            while (!terminar)
            {
             RegistrarPago(cod_partida_nuevo);  
             System.out.println("\n¿Desea Agregar Otro Pago a la partida?.\n1.Si.\n2.No.");
             int opcion2  = Integer.parseInt(in.nextLine());
             if (opcion2 == 2)
                {
                    terminar = true;
                }
            }
           
           }
            
            
            
    }catch(SQLException ex)
        {
           error = true;
           System.out.println("\nError al Registrar el Pedido.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
    
    
    try
      {
          if (!error)
        {
            conexion.commit();;
            System.out.println("Partida Registrada con exito.");
            
        }else
        {
            conexion.rollback(punto);
        }
      }catch(SQLException ex)
      {
          System.out.println("\nError en commit o en rollback.\n");
          System.out.println("Error: "+ex.getMessage());
      }
        
}




public void ConsultarPartida(){
        
        System.out.println("\nIntroduzca el codigo de la partida");
        Scanner in;
        in = new Scanner (System.in);
        String cod_partida = in.nextLine();
        
        
        try{
            
           ResultSet rs = estado.executeQuery("SELECT * FROM CONTABILIZA where cod_partida='"+cod_partida+"'");
            while (rs.next()){
                System.out.println("\nEl empleado encargado de contabilizar esta partida es: "+rs.getString("dni"));
            }  
            
            rs = estado.executeQuery("SELECT * FROM PARTIDA where cod_partida='"+cod_partida+"'");
            while (rs.next()){
                System.out.println("\nCodigo de la Partida: "+rs.getString("cod_partida"));
                System.out.println("Importe de la partida: "+rs.getFloat("importe")); 
            }  
            
           rs = estado.executeQuery("SELECT * FROM PAGO where cod_partida='"+cod_partida+"'"); 
            while (rs.next()){
                String cod_pago = rs.getString("cod_pago");
                System.out.println("\nCodigo Del Pago: "+cod_pago);
                System.out.println("Fecha del Pago: "+rs.getString("fecha"));
                System.out.println("Importe del Pago: "+rs.getInt("cantidad"));
                 
            }  
            
            rs.close();
        }catch(SQLException ex)
        {
           System.out.println("\nError al Consultar el Pedido.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }

    }



public void ModificarPartida()
{
     
        System.out.println("\nIntroduzca el codigo de la partida");
        Scanner in;
        in = new Scanner (System.in);
        String cod_partida = in.nextLine();
        boolean terminar = false, error = false;
        Savepoint punto = null;
        
    try
    {
          
            punto = conexion.setSavepoint();
        while(!terminar)  
        {
                    System.out.println("\n¿Desea:\n1.Cambiar Importe.\n2.Modificar un Pago\n3.Añadir un Pago Nuevo\n4.Terminar.)");
                    int opcion = Integer.parseInt(in.nextLine());
                    
                   switch (opcion)
                   {
                       case 1:
                           System.out.println("\nIntroduzca el nuevo importe de la partida.)");
                           String importe = in.nextLine();
                           estado.executeUpdate("UPDATE PARTIDA SET importe="+importe+" WHERE cod_partida ='"+cod_partida+"'");  
                           break;
                       case 2:
                           ModificarPago(cod_partida);
                           break;
                       case 3:
                           RegistrarPago(cod_partida);
                           break;
                       case 4:
                           terminar = true;
                           break;
                   }
                
        }
        
       
         
    }catch(SQLException ex)
        {
            error = true;
           System.out.println("\nError al Modificar Partida.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
      
      try{
          if (!error)
              conexion.commit();
          else
              conexion.rollback(punto);
          
      }catch(SQLException ex)
      {
         System.out.println("\nError al hacer el commit o el rollback.\n"); 
         System.out.println("Error: "+ex.getMessage()); 
      }
}




public void ELiminarPartida()
{
    
        
        System.out.println("\nIntroduzca el codigo de la partida que desea eliminar\n");
        Scanner in;
        in = new Scanner (System.in);
        String cod_partida = in.nextLine();
        boolean error = false;
        Savepoint punto = null;
    
    try{
           punto = conexion.setSavepoint();
           estado.executeUpdate("DELETE FROM PAGO WHERE cod_partida='"+cod_partida+"'");
           estado.executeUpdate("DELETE FROM CONTABILIZA WHERE cod_partida='"+cod_partida+"'"); 
           estado.executeUpdate("DELETE FROM PARTIDA WHERE cod_partida='"+cod_partida+"'"); 
         
        
        }catch(SQLException ex)
                {
                    error = true;
                    System.out.println("Problema al Eliminar la partida");
                    System.out.println("Error: "+ex.getMessage());
                }
 
    try{
     if (!error)
        {
           conexion.commit();
           System.out.println("\nPedido Partida eliminada con exito\n");
        }
        else
        conexion.rollback(punto);
   
    }catch(SQLException ex)
       {
           System.out.println("Error al hacer el commit o el rollback");
           System.out.println("Error: "+ ex.getMessage());
       }

}

//PEDIDOS
public void RegistrarPedido()
{   
        
        Savepoint punto = null;
        boolean error = false;
        Scanner in;
        String  cod_pedido, cod_cliente; 
        ArrayList<String> vcod_pro = new ArrayList<>();
        ArrayList<Integer> vcantidad = new ArrayList<>();
        String aux = "0";
        String numero_cod_pedido_st = "";
        int numero_cod_pedido, mayor = 0;
        
        System.out.println("\nIntroduzca su Usuario (cod_cliente)");
        in = new Scanner (System.in);
        cod_cliente = in.nextLine();
        
        while (!aux.equals("-1"))
        {
            
            System.out.println("\nIntroduzca el codigo del producto (Introduzca -1 para terminar de introducir codigos)");
            in = new Scanner (System.in);
            aux = in.nextLine();
            if (!aux.equals("-1"))
            {
                vcod_pro.add(aux);
                System.out.println("\nIntroduzca la cantidad del producto (Mayor que 0)");
                in = new Scanner (System.in);
                Integer c = in.nextInt();
                while (c <= 0)
                {
                    System.out.println("\nIntroduzca la cantidad del producto (Mayor que 0)");
                    c = in.nextInt();
                }   
                vcantidad.add(c);
                
            }
        }
        try{
            punto = conexion.setSavepoint();
            ResultSet rs = estado.executeQuery("SELECT cod_pedido FROM PEDIDO");
             
            while(rs.next())
            {
                cod_pedido = rs.getString("cod_pedido");
                numero_cod_pedido_st =  cod_pedido.replace("cp", "");
                numero_cod_pedido = Integer.parseInt(numero_cod_pedido_st);
                if (numero_cod_pedido > mayor)
                {
                    mayor = numero_cod_pedido;  
                }
                numero_cod_pedido_st="";
            }
            mayor += 1;
            String cod_pedido_nuevo = "cp"+mayor;
            
            
            
            float precio = 0.0f;
            
            for (int i =0; i < vcod_pro.size(); i++)
            {
                rs = estado.executeQuery("SELECT * FROM PRODUCTO WHERE cod_pro='" + vcod_pro.get(i) +"'");
                while(rs.next())
                {
                   precio+=(rs.getFloat("precio_pro"))* vcantidad.get(i);   
                }
                
            }     
            
            estado.executeUpdate("INSERT INTO PEDIDO values('" + cod_pedido_nuevo + "', " + precio + ")");
            
            
            for (int i =0; i < vcod_pro.size(); i++)
            {
                String sql = "INSERT INTO FORMADO values('" + cod_pedido_nuevo + "', '" + vcod_pro.get(i) + "', " + vcantidad.get(i) + ")";
                estado.executeUpdate(sql);
                
                estado.executeUpdate("UPDATE PRODUCTO SET cantidad_stock=cantidad_stock-"+vcantidad.get(i)+" WHERE cod_pro ='"+ vcod_pro.get(i) +"'");  
            }
          
                estado.executeUpdate("INSERT INTO REALIZA values('" + cod_pedido_nuevo + "', '" + cod_cliente + "', SYSDATE)");
            
            
          }catch(SQLException ex)
        {
           error = true;
           System.out.println("\nError al Registrar el Pedido.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
        
      try
      {
          if (!error)
        {
            conexion.commit();
            System.out.println("Pedido Registrado con exito.");
            
        }else
        {
            conexion.rollback(punto);
        }
      }catch(SQLException ex)
      {
          System.out.println("\nError al commit o al rollback.\n");
      }
}



public void ConsultarPedido(){
        
        System.out.println("\nIntroduzca el codigo del pedido");
        Scanner in;
        in = new Scanner (System.in);
        String cod_pedido = in.nextLine();
        
        
        try{
           ResultSet rs = estado.executeQuery("SELECT * FROM PEDIDO where cod_pedido='"+cod_pedido+"'");
            while (rs.next()){
                System.out.println("\nCodigo del Pedido: "+rs.getString("cod_pedido"));
                System.out.println("Precio Total del pedido: "+rs.getFloat("precio")); 
            }  
            
           rs = estado.executeQuery("SELECT * FROM FORMADO where cod_pedido='"+cod_pedido+"'"); 
            while (rs.next()){
                String cod_pro = rs.getString("cod_pro");
                System.out.println("\nCodigo Producto: "+cod_pro);
                System.out.println("Cantidad del producto: "+rs.getInt("cantidad"));
                
                Statement estado2 = conexion.createStatement();
                ResultSet rs_aux = estado2.executeQuery("SELECT * FROM PRODUCTO where cod_pro='"+cod_pro+"'");
                while (rs_aux.next())
                {
                    System.out.println("Descripcion: "+rs_aux.getString("descripcion"));
                    System.out.println("El PVP del producto es: "+rs_aux.getFloat("precio_pro")+"\n");
                }
                    
            }  

            rs.close();
        }catch(SQLException ex)
        {
           System.out.println("\nError al Consultar el Pedido.\n"); 
        }

    }

public void ModificarPedido()
{
     
        System.out.println("\nIntroduzca el codigo del pedido");
        Scanner in;
        in = new Scanner (System.in);
        String cod_pedido = in.nextLine();
        boolean terminar = false, error = false;
        Savepoint punto = null;
        
      try
        {
          
            punto = conexion.setSavepoint();
     while(!terminar)  
     {
         
                    System.out.println("\nIntroduzca el codigo del producto a añadir o modificar (Introduzca -1 para cancelar la operacion)");
                    ResultSet rs = estado.executeQuery("SELECT * FROM FORMADO where cod_pedido='"+cod_pedido+"'"); 
                    while (rs.next())
                    {
                        String cod_pro = rs.getString("cod_pro");
                        System.out.println("\nCodigo Producto: "+cod_pro);
                        System.out.println("Cantidad del producto: "+rs.getInt("cantidad"));
                
                    }
                    String cod_pro = in.nextLine();
                    float precio;
                    
                    if (!cod_pro.equals("-1"))
                    {
                        
                         
                        String cantidad_inicial;
                        String cantidad_nueva, precio_nuevo;
                        rs = estado.executeQuery("SELECT * FROM FORMADO where cod_pro='"+cod_pro+"' AND cod_pedido='"+cod_pedido+"'");
                         if(rs.next())  
                         cantidad_inicial = rs.getString("cantidad");
                         else 
                             cantidad_inicial = "0";
                        
                        
                        System.out.println("\nIntroduzca la nueva cantidad del producto (La cantidad actual es "+cantidad_inicial+")");
                        String cantidad = in.nextLine();
                  
                         
                    
                         
                         estado.executeUpdate("DELETE FROM FORMADO WHERE cod_pedido= '"+cod_pedido+"' AND cod_pro='"+cod_pro+"'");
                         if (!cantidad.equals("0"))  
                         estado.executeUpdate("INSERT INTO FORMADO values('" + cod_pedido + "', '" + cod_pro + "', " + cantidad + ")");
                         
                         
                         
                         
                         rs = estado.executeQuery("SELECT * FROM PRODUCTO where cod_pro='"+cod_pro+"'");
                         rs.next();
                         precio = rs.getFloat("precio_pro");  
                         cantidad_nueva = "" + (Float.parseFloat(cantidad) - Float.parseFloat(cantidad_inicial));
                         precio_nuevo = "" + precio*Float.parseFloat(cantidad_nueva);
                         String precio_nuevo_st = "" + precio_nuevo; 
                         if (Float.parseFloat(cantidad_nueva) > 0)   
                            estado.executeUpdate("UPDATE PRODUCTO SET cantidad_stock=cantidad_stock-"+cantidad_nueva+" WHERE cod_pro='"+cod_pro+"'");
                         else
                         {
                            cantidad_nueva = "" + Math.abs(Float.parseFloat(cantidad_nueva));
                            estado.executeUpdate("UPDATE PRODUCTO SET cantidad_stock=cantidad_stock+"+cantidad_nueva+" WHERE cod_pro='"+cod_pro+"'");    
                         }    
                         estado.executeUpdate("UPDATE PEDIDO SET precio=precio+"+precio_nuevo_st+" WHERE cod_pedido='"+cod_pedido+"'");
                         rs.close();
                         
                    }
                    else
                        terminar = true;
                
                
     }
        
       
         
        }catch(SQLException ex)
        {
            error = true;
           System.out.println("\nError al Modificar Empleado.\n"); 
           System.out.println("Error: "+ex.getMessage());
        }
      
      try{
          if (!error)
              conexion.commit();
          else
              conexion.rollback(punto);
          
      }catch(SQLException ex)
      {
         System.out.println("\nError al hacer el commit o el rollback.\n"); 
         System.out.println("Error: "+ex.getMessage()); 
      }
}

public void CancelarPedido ()
{
    
        
        System.out.println("\nIntroduzca el codigo del pedido\n");
        Scanner in;
        in = new Scanner (System.in);
        String cod_pedido = in.nextLine();
        boolean error = false;
        Savepoint punto = null;

    
    
    try{
        
       punto = conexion.setSavepoint();
       ResultSet rs = estado.executeQuery ("SELECT cantidad, cod_pro FROM FORMADO WHERE cod_pedido='"+cod_pedido+"'");
       System.out.println("Consulta Realizada");
        
       
       while(rs.next())
        {
            String cantidad = rs.getString("cantidad"); 
            String cod_pro = rs.getString("cod_pro");
            String update = "UPDATE PRODUCTO SET cantidad_stock=cantidad_stock+"+cantidad+" WHERE cod_pro = '"+cod_pro+"'";
            Statement estado2 = conexion.createStatement();
            estado2.executeUpdate(update);   
    
        }
        rs.close();
        
        
        }catch(SQLException ex)
                {
                    error = true;
                    System.out.println("Problema al consultar la tabla Formado y  hacer el Update de la tabla producto");
                    System.out.println("Error: "+ex.getMessage());
                }
    
    try{
        
           estado.executeUpdate("DELETE FROM FORMADO WHERE cod_pedido='"+cod_pedido+"'"); 
           estado.executeUpdate("DELETE FROM REALIZA WHERE cod_pedido='"+cod_pedido+"'"); 
    
        
        }catch(SQLException ex)
                {
                    error = true;
                    System.out.println("Problema al borrar las tuplas de la tabla Formado o Realiza");
                    System.out.println("Error: "+ex.getMessage());
                }
    
    try{
        
            estado.executeUpdate("DELETE FROM PEDIDO WHERE cod_pedido='"+cod_pedido+"'");   
        
        }catch(SQLException ex)
                {
                    error = true;
                    System.out.println("Problema al borrar las tuplas de la tabla Pedido");
                    System.out.println("Error: "+ex.getMessage());
                }
   
   
   if (!error)
   {
       try{
           conexion.commit();
           System.out.println("\nPedido cancelado con exito\n");
       }catch(SQLException ex)
       {
           System.out.println("Error al hacer el commit");
           System.out.println("Error: "+ex.getMessage());
       }
       
   }
   else
       try{
        conexion.rollback(punto);
        
        
       }catch(SQLException ex)
       {
           System.out.println("Error al hacer el rollback");
           System.out.println("Error: "+ex.getMessage());
       }

    
    }



///PROVEEDORES
public void RegistrarProveedor()
{
   
    Scanner in;
    in = new Scanner (System.in);
    String cod_proveedor;
    System.out.println("\nIntroduzca el nombre del proveedor\n");
    String nombre = in.nextLine();
    System.out.println("\nIntroduzca el contacto del proveedor\n");
    String contacto = in.nextLine();
    String numero_cod_proveedor_st = "";
    int numero_cod_proveedor, mayor = 0;

    try{
        
            ResultSet rs = estado.executeQuery("SELECT cod_proveedor FROM PROVEEDORES");
             
            while(rs.next())
            {
                cod_proveedor = rs.getString("cod_proveedor");
                numero_cod_proveedor_st =  cod_proveedor.replace("pr", "");
                numero_cod_proveedor = Integer.parseInt(numero_cod_proveedor_st);
                if (numero_cod_proveedor > mayor)
                {
                    mayor = numero_cod_proveedor;  
                }
                numero_cod_proveedor_st="";
            }
            mayor += 1;
            String cod_proveedor_nuevo = "pr"+mayor;
        
        
        estado.executeUpdate("INSERT INTO PROVEEDORES values('" + cod_proveedor_nuevo + "', '" + nombre + "', '" + contacto + "')");
        conexion.commit();
        rs.close();
    System.out.println("\nProveedor registrdo con exito\n");
    
    }catch(SQLException ex)
            {
               System.out.println("\nError al Registrar un proveedor.\n");
               System.out.println("Error: "+ex.getMessage());
            }
   
}




public void ConsultarProveedor() 
{
    System.out.println("\nIntroduzca el codigo del proveedor\n");
    Scanner in;
    in = new Scanner (System.in);
    String cod_proveedor = in.nextLine(); 
    
    try{
    ResultSet rs = estado.executeQuery("SELECT * FROM PROVEEDORES where cod_proveedor='"+cod_proveedor+"'");
    
    while(rs.next())
    {
        System.out.println("\nCodigo de Proveedor: " + rs.getString("cod_proveedor"));
        System.out.println("Nombre de Proveedor: " + rs.getString("nombre"));
        System.out.println("Contacto: " + rs.getString("contacto"));
        
    }
    
    rs = estado.executeQuery("SELECT * FROM PRODUCTO where cod_proveedor='"+cod_proveedor+"'");
    
    System.out.println("PRODUCTOS QUE PROVEE");
    while(rs.next())
    {
        System.out.println("\nCodigo de Producto: " + rs.getString("cod_pro"));
        System.out.println("Descripcion del Producto: " + rs.getString("descripcion"));
        System.out.println("Cantidad en Stock: " + rs.getString("cantidad_stock"));
        System.out.println("PVP del producto: " + rs.getString("precio_pro"));
    }
    
    }catch(SQLException ex)
            {
                System.out.println("\nError al obtener informacion del proveedor.\n");
            }
  
}

public void ModificarProveedor()
{
   System.out.println("\nIntroduzca el codigo del proveedor que desea modificar\n");
    Scanner in;
    in = new Scanner (System.in);
    String cod_proveedor = in.nextLine();
    boolean terminar = false;
    boolean error = false;
    Savepoint punto = null;
    System.out.println("\nQue apartado desea modiicar\n1.Nombre.\n2.Contacto.\n3.Terminar Operacion");
    try{
    punto = conexion.setSavepoint();
    
    while(!terminar)
    {
     System.out.println("\nQue apartado desea modiicar\n1.Nombre.\n2.Contacto.\n3.Terminar Operacion");
     int opcion = Integer.parseInt(in.nextLine());
     switch (opcion)
             {
                 case  1:
                 System.out.println("\nIntroduzca el nuevo nombre para el proveedor\n");  
                 String nombre = in.nextLine();
                 estado.executeUpdate("UPDATE PROVEEDORES SET nombre='"+nombre+"' WHERE cod_proveedor='"+cod_proveedor+"'");
                     break;
                     
                 case 2:
                 System.out.println("\nIntroduzca el nuevo contacto para el proveedor\n");
                 String contacto = in.nextLine();
                 estado.executeUpdate("UPDATE PROVEEDORES SET contacto='"+contacto+"' WHERE cod_proveedor='"+cod_proveedor+"'");
                     break;   
                 case 3:
                     terminar = true;
                     break;
             }
    
    }
    
      
 
    }catch(SQLException ex)
            {
                error = true;
                System.out.println("\nError al modificar la informacion del proveedor.\n");
                System.out.println("Error: "+ex.getMessage());
            }  
    
    
    try
      {
          if (!error)
        {
            conexion.commit();
            System.out.println("Proveedor Modificado con exito.");
            
        }else
        {
            conexion.rollback(punto);
        }
      }catch(SQLException ex)
      {
          System.out.println("\nError al commit o al rollback.\n");
          System.out.println("Error: "+ex.getMessage());
      }
    
    
    
}


public void EliminarProveedor()
{
    
    System.out.println("\nIntroduzca el codigo del proveedor\n");
    Scanner in;
    in = new Scanner (System.in);
    String cod_proveedor = in.nextLine();
    Savepoint punto = null;
    boolean error = false;
    
    try{
        punto = conexion.setSavepoint();
       
        estado.executeUpdate("UPDATE PRODUCTO SET cod_proveedor=NULL WHERE cod_proveedor='"+cod_proveedor+"'");
        estado.executeUpdate("DELETE FROM PROVEEDORES WHERE cod_proveedor='"+cod_proveedor+"'"); 
        
          
        
       }catch(SQLException ex)
       {
           error = true;
           System.out.println("\nError al borrar un proveedor de la tabla PROVEEDORES.\n");
           System.out.println("Error: "+ex.getMessage());
       }
        
   
    try{
      
        if (!error)
    {
        conexion.commit();
        System.out.println("\nProveedor Eliminado con exito.\n");
    }
    else
    {
        conexion.rollback(punto);
    }
        
    }catch(SQLException ex)
    {
        System.out.println("\nError al hacer commit o rollback en EliminarProveedor.\n");
    }
    
    
}


    
}



