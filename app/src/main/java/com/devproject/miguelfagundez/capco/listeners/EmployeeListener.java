package com.devproject.miguelfagundez.capco.listeners;

/********************************************
 * Listener- EmployeeListener
 * Including methods to handle communication
 * between components (adapter, activities,
 * fragments, etc)
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public interface EmployeeListener {

    //******************************************
    // Connect Adapter with EmployeeListActivity
    //******************************************
    void employeeClick(int id, String fName, String lName, String role);
}
