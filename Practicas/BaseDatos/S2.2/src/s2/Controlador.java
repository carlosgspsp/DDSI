package s2;

public class Controlador {

    private VistaTextual vista;
    private Modelo modelo;
    
    Controlador (Modelo modelo, VistaTextual vista){
        this.vista=vista;
        this.modelo = modelo;
    }
    
    void ejecutar (){
        
        vista.setModelo(modelo);
        //modelo.Rehacer_Tablas();
        
        while (!modelo.Terminar()){
            //vista.pausa();
            modelo.MostrarTablas();
            Opciones_Principal op = vista.SiguienteAccionPrincipal();

            switch (op){
                case REHACER:
                    modelo.Rehacer_Tablas();
                    break;

                case DAR_ALTA:
                    modelo.MostrarTablaPedido();
                    int id_pedido = vista.LeeCPedido();
                    int id_cliente = vista.LeerCCliente();
                    String fecha = vista.LeerFecha();
                    //String formato = vista.LeerFormato();
                    String formato = "";
                    boolean exito = modelo.Dar_Alta_Pedido(id_pedido, id_cliente, fecha, formato);
                    modelo.MostrarTablaPedido();
                    while (!modelo.Fin_Pedido() && exito ){
                        //vista.pausa();
                        modelo.MostrarTablaPedido(id_pedido);
                        Opciones_Secundario os = vista.SiguienteAccionSecundaria();
                        switch (os){
                            case ADD_DETALLE:
                                int id_producto = vista.LeerCProducto();
                                int cantidad = vista.LeerCantidad();
                                modelo.Aniadir_Detalle(id_pedido, id_producto, cantidad);
                                modelo.MostrarTablaStock();
                                break;

                            case BORRAR_DETALLES:
                                modelo.Eliminar_Detalles_Producto(id_pedido);
                                break;

                            case CANCELAR_PEDIDO:
                                modelo.Cancelar_Pedido();
                                
                                break;

                            case FINALIZAR_PEDIDO:
                                modelo.Finalizar_Pedido();
                                break;
                        }        
                    }
                    break;

                case BORRAR:
                    int cpedido = vista.LeeCPedido();
                    modelo.Borrar_Pedido(cpedido);
                    break;

                case SALIR:
                    modelo.Salir();
                    break;
            }        
        }      
    }
}

