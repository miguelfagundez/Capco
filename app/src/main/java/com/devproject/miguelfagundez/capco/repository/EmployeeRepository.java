package com.devproject.miguelfagundez.capco.repository;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.devproject.miguelfagundez.capco.model.network.EmployeeApiClient;
import com.devproject.miguelfagundez.capco.model.pojo.Employee;
import com.devproject.miguelfagundez.capco.model.pojo.Error;

import java.util.List;

import androidx.lifecycle.LiveData;

/********************************************
 * Class- EmployeeRepository
 * Middle class that connect my ViewModel layer
 * with my model - Business logic, validations,
 * and more can be added here
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeRepository extends ContextWrapper {

    //*********************************
    // Singleton pattern
    //*********************************
    private static EmployeeRepository repository = null;

    //*********************************
    // Client member
    // Connecting with my model layer
    //*********************************
    private EmployeeApiClient client;

    public static EmployeeRepository getInstance(Context context){
        if (repository == null){
            synchronized(EmployeeRepository.class){
                if (repository == null){
                    repository = new EmployeeRepository(context);
                }
            }
        }
        return repository;
    }

    private EmployeeRepository(Context context){
        super(context);
        client = EmployeeApiClient.getInstance(context);
    }

    //************************************************
    //  Networking methods
    //************************************************
    public LiveData<List<Employee>> getListOfEmployees() {
        return client.getListOfEmployees();
    }

    public void searchAllEmployees(){
        client.searchAllEmployees();
    }

    public LiveData<Error> getError() {
        return client.getError();
    }

    public LiveData<Employee> getEmployee() {
        return client.getEmployee();
    }

    public LiveData<Employee> getEmployeeById(int id) {
        return client.getEmployeeById(id);
    }

    public void createEmployee(int id, String fName, String lName, String role) {
        Employee employee = new Employee(id, fName, lName, role);
        client.createEmployee(employee);
    }

    public void updateEmployee(int id, String fName, String lName, String role) {

        Employee employee = new Employee(id, fName, lName, role);
        client.updateEmployee(employee);
    }

    public void deleteEmployee(int id, String fName, String lName, String role) {

        Log.d("Test", "onClick: delete employee - Repository");
        Employee employee = new Employee(id, fName, lName, role);
        client.deleteEmployee(employee);
    }

    //*****************************************
    //  SignIn methods
    //*****************************************

    public void signIn(String username, String password) {
        client.signIn(username, password);
    }

    public boolean hasAdminPermissions() {
        return client.hasAuthority();
    }

    public void closeAccessGranted() {
        client.closeAccessGranted();
    }

    //*****************************************
    //      Shared Preferences methods
    //*****************************************

    //********************************************
    // Saving phone locally in Shared Preferences
    //********************************************
    public void saveEmployeePhoneInPreferences(int id, String phone) {
        client.savePhoneInPreferences(id, phone);
    }

    public String searchEmployeePhone(int id) {
        return client.searchEmployeePhoneInPreferences(id);
    }

    public LiveData<Boolean> getAccessGranted() {
        return client.getAccessGranted();
    }
}

