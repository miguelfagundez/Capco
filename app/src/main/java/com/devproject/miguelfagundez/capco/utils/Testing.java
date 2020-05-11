package com.devproject.miguelfagundez.capco.utils;

import android.util.Log;

import com.devproject.miguelfagundez.capco.model.pojo.Employee;

import java.util.List;

/********************************************
 * Class- Testing
 * Util class to test my code
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class Testing {

    //**************************************
    // Printing just employee name
    //**************************************
    public static void printEmployeesName(List<Employee> employees){
        Log.d(Constants.TAG, "-------------------------------------------------------------------");
        for(Employee employee : employees){
            Log.d(Constants.TAG, "printEmployees: " + employee.getFirstName());
        }
    }

    //**************************************
    // Printing complete employee data
    //**************************************
    public static void printEmployees(List<Employee> employees){
        Log.d(Constants.TAG, "-------------------------------------------------------------------");
        for(Employee employee : employees){
            Log.d(Constants.TAG, "printEmployees: " + employee.toString());
        }
    }
}
