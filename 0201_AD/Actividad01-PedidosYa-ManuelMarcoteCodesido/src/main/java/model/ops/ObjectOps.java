package model.ops;

import model.javabeans.Customer;
import model.javabeans.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjectOps {

    public static void writeObjects(ArrayList<Customer> customers, ArrayList<Order> orders, String path){

        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el fichero");
            }
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(customers);
            oos.writeObject(orders);
        } catch (FileNotFoundException e) {
            System.err.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            System.err.println("Error al escribir los datos en el fichero");
        }

    }

    public static void readObjects(File file) {

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        if (!file.exists() || file.length() == 0) {
            System.err.println("El fichero no existe o está vacío, no se puede leer");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            customers = (ArrayList<Customer>) in.readObject();
            orders = (ArrayList<Order>) in.readObject();

            System.out.println("CUSTOMERS");
            customers.forEach(c -> System.out.println(c.toString()));
            System.out.println("\nORDERS");
            orders.forEach(o -> System.out.println(o.toString()));

            File f = new File("src/main/resources/salida.txt");
            if (!f.exists()){ f.createNewFile(); }

            try(PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("CUSTOMERS");
                customers.forEach(pw::println);
                pw.println("\nORDERS");
                orders.forEach(pw::println);

            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en la lectura del archivo" + e.getMessage());
        }

    }

}

