package s2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class VistaTextual {

  private Modelo modelo;
  private static String separador = "=====================";
  private int cpedido;
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
    cpedido = -1;
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }
  
  int leeEntero (String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

    Opciones_Principal SiguienteAccionPrincipal() {
        int opcion = menu ("Menu Principal",
        new ArrayList<> (Arrays.asList("Borrado y nueva creacion de las tablas"
        ,"Dar de alta nuevo pedido","Borrar un pedido","Salir del pograma")));
        return Opciones_Principal.values()[opcion];
    }
     Opciones_Secundario SiguienteAccionSecundaria() {
        int opcion = menu ("Menu Pedido",
        new ArrayList<> (Arrays.asList("Añadir detalle de producto"
        ,"Eliminar todos los detalles del producto","Cancelar pedido","Finalizar pedido")));
        return Opciones_Secundario.values()[opcion];
    }

  public void setModelo(Modelo mod){ 
        modelo=mod;
  }
  
  public int LeeCPedido(){    
      cpedido = leeEntero("Introduce el codigo del pedido: ","Valor erróneo");      
      return cpedido;
  }
  
  public int LeerCCliente(){
      int ccliente = leeEntero("Introduce el codigo del cliente: ","Valor erróneo");   
      return ccliente;
  }
  
  public int LeerCProducto(){
      int ccliente = leeEntero("Introduce el codigo del producto: ","Valor erróneo");   
      return ccliente;
  }
  
  public int LeerCantidad(){
      int ccliente = leeEntero("Introduce la cantidad de producto: ","Valor erróneo");   
      return ccliente;
  }
  
  public String LeerFecha(){
      System.out.print("Introduce la fecha: ");
      String fecha = in.nextLine();  
      return fecha;
  }
  
  public String LeerFormato(){   
      System.out.print("Introduce el formato: ");
      String formato = in.nextLine();
      return formato;
  }
  
 
}
