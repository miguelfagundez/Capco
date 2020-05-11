package com.devproject.miguelfagundez.capco.viewmodel;

import android.app.Application;

import com.devproject.miguelfagundez.capco.model.pojo.Employee;
import com.devproject.miguelfagundez.capco.model.pojo.Error;
import com.devproject.miguelfagundez.capco.repository.EmployeeRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/********************************************
 * Class- EmployeeViewModel
 * This class connect the ViewModel with
 * repository components
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeViewModel extends AndroidViewModel {

    // Members
    // Connecting with Repository layer
    private EmployeeRepository repository;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        repository = EmployeeRepository.getInstance(getApplication());
    }

    public void searchListOfEmployees(){
        repository.searchAllEmployees();
    }

    //*****************
    // Get methods
    //*****************
    // List of Employees
    public LiveData<List<Employee>> getListOfEmployees(){
        return repository.getListOfEmployees();
    }

    // Get the error if exist
    public LiveData<Error> getError(){
        return repository.getError();
    }

    // Getting an employee
    public LiveData<Employee> getEmployee(){
        return repository.getEmployee();
    }

    //*****************
    // Other methods
    //*****************
    // Employee by Id
    public void getEmployeeById(int id){
        repository.getEmployeeById(id);
    }

    // Create a new employee
    public void createEmployee(int id, String fName, String lName, String role){
        repository.createEmployee(id, fName, lName, role);
    }

    // Update an employee
    public void updatedEmployee(int id, String fName, String lName, String role){
        repository.updateEmployee(id, fName, lName, role);
    }

    // Delete an employee
    public void deleteEmployee(int id, String fName, String lName, String role){
        repository.deleteEmployee(id, fName, lName, role);
    }


    //***********************
    // Sign In Methods
    //***********************
    public void signIn(String username, String password) {
        repository.signIn(username, password);
    }

    //********************************************
    // Admin Authorization
    //********************************************
    public boolean hasAdminPermissions(){
        return repository.hasAdminPermissions();
    }

    public void closeAccessGranted() {
        repository.closeAccessGranted();
    }


    //********************************************
    // Saving phone locally in Shared Preferences
    //********************************************
    public void saveEmployeePhone(int id, String phone) {
        repository.saveEmployeePhoneInPreferences(id, phone);
    }

    public String searchEmployeePhone(int id) {
        return repository.searchEmployeePhone(id);
    }

    public LiveData<Boolean> getAccessgranted() {
        return repository.getAccessGranted();
    }
}

