package com.devproject.miguelfagundez.capco.model.pojo;


/********************************************
 * Class- Employee
 * Pojo class to handle employee information.
 * Parcelable should be implemented.
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String role;

    public Employee(int id, String firstName, String lastName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

