package model.javabeans;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private static final long SerialVersionUID = 1234L;

    private int idNumber;
    private String name;
    private String surname;
    private String email;
    private String adress;
    private String phoneNumber;
    private String city;
    private String country;

    public Customer() {}

    public Customer(int idNumber, String name, String surname, String email, String adress,
                    String phoneNumber, String city, String country) {
        this.idNumber = idNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return idNumber == customer.idNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idNumber);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s", idNumber, name, surname, email);
    }
}
