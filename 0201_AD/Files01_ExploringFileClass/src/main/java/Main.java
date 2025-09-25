import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

       try (Scanner leer = new Scanner(System.in)) { //mediante el try permitimos que se abra y cierre nuestro scanner
           //con seguridad al final del try-with-resources

           System.out.print("Indique la ruta que desea analizar: ");
           String path = leer.nextLine().trim(); //con trim() eliminamos espacios iniciales y finales

           File directorio = new File(path);

           if (!directorio.exists() || !directorio.isDirectory()) {
               System.out.println("La ruta indicada no existe");
               return;
           }

           System.out.printf("\n-- Ruta del directorio -- %s%n \n",
                   directorio.getAbsolutePath());

           // Si la ruta especificada es un directorio podemos listar los archivos contenidos en él
           // mediante el métod .listFiles(). Dado que
           File[] listaArchivos = directorio.listFiles();


           if (listaArchivos != null) {
               for (File archivo: listaArchivos) {

                   SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                   String fechaModificacion = formatoFecha.format(archivo.lastModified());

                   if (archivo.isFile()) {

                       System.out.printf("- [ARCHIVO] %s (%d bytes) - Última modificación: %s%n",
                               archivo.getName(),
                               archivo.getTotalSpace(),
                               fechaModificacion);

                   } else if (archivo.isDirectory()) {

                       System.out.printf("- [DIRECTORIO] %s - Última modificación: %s%n",
                               archivo.getName(),
                               fechaModificacion);

                   }

               }
           }

       } catch(Exception e) {

           System.out.println(e.getMessage());

       }
    }
}
