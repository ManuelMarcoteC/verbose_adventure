package model.javabeans;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 123L;

    private int id;
    private int idCustomer;
    private String product;
    private int quantity;

    public Order(int id) {
        this.id = id;
    }

    public Order(int id, int idCustomer, String product, int quantity) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%s,%d", id, idCustomer, product, quantity);
    }
}
