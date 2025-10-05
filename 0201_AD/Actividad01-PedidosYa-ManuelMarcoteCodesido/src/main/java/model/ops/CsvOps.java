package model.ops;

import model.javabeans.Customer;
import model.javabeans.Order;

import java.io.*;
import java.util.ArrayList;

public class CsvOps {

    public static void saveCustomersCsv(ArrayList<Customer> customers, String path) {
        
        File file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de datos");
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, true))) {
            printWriter.println("idNumber,name,surname,email,adress,phoneNumber,city,country");
            for (Customer customer : customers) {
                printWriter.println(customer);
            }
        } catch (IOException e) {
            System.err.println("Error en la escritura del archivo");
        }
    }

    public static void saveOrdersCsv(ArrayList<Order> orders, String path) {

        File file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de datos");
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, true))) {
            printWriter.println("\nid,idCustomer,product,quantity");
            for (Order order : orders) {
                printWriter.println(order);
            }
        } catch (IOException e) {
            System.err.println("Error en la escritura del archivo");
        }

    }

    public static void getRecords(String path) {

        File file = new File(path);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error no se encuentra el archivo");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de datos");
        }

    }

}
