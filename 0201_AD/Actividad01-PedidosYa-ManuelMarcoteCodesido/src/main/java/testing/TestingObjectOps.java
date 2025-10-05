package testing;

import model.javabeans.Customer;
import model.javabeans.Order;
import model.ops.ObjectOps;

import java.io.File;
import java.util.ArrayList;

public class TestingObjectOps {

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static final String datpath = "src\\main\\resources\\datos.dat";

    static {
        customers.add(new Customer(1,"Sara","Valles","saravalles@gmail.com","avda. de la concordia",
                "+34787878787","Vigo","Spain"));
        customers.add(new Customer(2,"Manuel","Marcote","manuelmarcote@gmail.com","avda. de los mares",
                "+34787878784","A Coruña","Spain"));
        customers.add(new Customer(3,"Estela","Cruz","estelacruz@gmail.com","calle Alfonso Molina",
                "+34787878785","A Coruña","Spain"));

        orders.add(new Order(1, 1,"orange net",3));
        orders.add(new Order(2, 2,"textured soy",2));
        orders.add(new Order(3, 3,"grape juice",1));
        orders.add(new Order(4, 1,"bottle of wine",3));
        orders.add(new Order(5, 2,"chips",2));
    }

    public static void main(String[] args) {

        ObjectOps.writeObjects(customers, orders, datpath);

        ObjectOps.readObjects(new File(datpath));

    }

}
