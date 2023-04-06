/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.sql.SQLException;

/**
 *
 * @author Santiago Carbó García
 */
public class Controlador {
    private Modelo modelo;
    private VistaTextual vista;

    Controlador(Modelo modelo, VistaTextual vista){
        this.modelo = modelo;
        this.vista = vista;
    }
    
    void iniciar() throws SQLException{
        vista.setModelo(modelo);
        boolean terminar = false;
        while(!terminar){
            
            vista.elegir_opcion();

            if(vista.getOpcion() != 4)
            {
                switch(vista.getOpcion()){
                    case 0:
                        vista.elegir_funcionalidad("Proveedor");
                        break;

                    case 1:
                        vista.elegir_funcionalidad("Pedido");
                        break;

                    case 2:
                        vista.elegir_funcionalidad("Empleado");
                        break;
                    
                    case 3:
                        vista.elegir_funcionalidad("Partida");
                        break;
                }
            }

            //vista.pausa();
            switch(vista.getOpcion())
            {
                case 0:
                    switch(vista.getFuncionalidad()){
                        case 0:
                            modelo.RegistrarProveedor();
                            break;
                        case 1:
                            //Modificar Proveedor  
                            modelo.ModificarProveedor();   
                            break;
                        case 2:
                            //Consultar Proveedor
                            modelo.ConsultarProveedor();
                            break;
                        case 3:
                            //Eliminar Proveedor   
                            modelo.EliminarProveedor(); 
                            break;
                        default:
                    }
                    break;
                case 1:
                    switch(vista.getFuncionalidad()){
                        case 0:
                            //Registrar pedido   
                            modelo.RegistrarPedido();      
                            break;
                        case 1:
                            //Modificar pedido        
                            modelo.ModificarPedido();         
                            break;
                        case 2:
                            //Consultar Pedido
                            modelo.ConsultarPedido();
                            break;
                        case 3:
                            //Cancelar Pedido          
                            modelo.CancelarPedido();           
                            break;
                        default:
                    }
                    break;
                case 2:
                    switch(vista.getFuncionalidad()){
                        case 0:
                            //Registrar empleado
                            modelo.AgregarEmpleado();
                            break;
                        case 1:
                            //Modificación Empleado         
                            modelo.ModificarEmpleado();      
                            break;
                        case 2:
                            modelo.ConsultarEmpleado();
                            break;
                        case 3:
                            //Borrado Empleado             
                            modelo.EliminarEmpleado();                         
                            break;
                        default:
                    }
                    break;
                case 3:
                    switch(vista.getFuncionalidad()){
                        case 0:
                            modelo.RegistrarPartida();
                            break;
                        case 1:
                            //Modificación Partida
                            modelo.ModificarPartida();
                            break;
                        case 2:
                            //Consultar Partida
                            modelo.ConsultarPartida();
                            break;
                        case 3:
                            //Borrado Partida
                            modelo.ELiminarPartida();
                            break;
                        default:
                            
                    }
                    
                    break;
                default:
                    terminar = true;
                  
                    
            }

           vista.pausa();
        }
    }
}