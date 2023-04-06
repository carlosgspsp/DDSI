/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Santiago Carbó García
 */
public class VistaTextual {
    
    private Scanner in;
    private int iOpcion;
    private int iFuncionalidad;
    private Modelo modelo;
    
    VistaTextual () {
        in = new Scanner (System.in);
        iOpcion = -1;
        iFuncionalidad = -1;
        modelo = null;
    }
    
    void elegir_opcion() {
      iOpcion = menu("Seleccione una Funcionalidad: ", new ArrayList<>(Arrays.asList("Funcionalidad de Proveedores", "Funcionalidad de Logistica", "Funcionalidad de Recursos Humanos", "Funcionalidad Financiera","Terminar")));
    }

    void elegir_funcionalidad(String input){
        iFuncionalidad = menu("Seleccione la accion a realizar: ", new ArrayList<>(Arrays.asList("Registrar " + input, "Modificar " + input, "Consultar " + input, "Eliminar " + input, "Volver al menu anterior")));
    }
    

    public int getOpcion(){
        return iOpcion;
    }

    public int getFuncionalidad(){
      return iFuncionalidad;
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
  
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }
  
  public void setModelo(Modelo modelo){ 
        this.modelo=modelo;
  } 
  
}