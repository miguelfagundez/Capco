package com.devproject.miguelfagundez.capco.model.network;

import com.devproject.miguelfagundez.capco.model.pojo.Employee;
import com.devproject.miguelfagundez.capco.model.pojo.Jwt;
import com.devproject.miguelfagundez.capco.model.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/********************************************
 * Interface- EmployeeService
 * Defining HTTP annotations for this service
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public interface EmployeeInterface {

    //**************************
    // GET Annotations
    //**************************

    // Getting All Employees
    @GET("api/employees")
    //Call<AllEmployeeResponse> getListEmployees();
    Call<List<Employee>> getListOfEmployees();

    // Getting an employee by Id
    @GET("api/employees/{id}")
    Call<Employee> getEmployeeById(
        @Path("id") int id
    );

    //**************************
    // POST Annotations
    //**************************

    // Create a new employee
    @POST("api/employees")
    Call<Employee> createNewEmployee(
            @Body Employee employee
    );

    //**************************
    // PUT Annotations
    //**************************

    //Update employee
    @PUT("api/employees/{id}")
    Call<Employee> updateEmployee(
            @Path("id") int id,
            @Body Employee employee
    );

    //**************************
    // DELETE Annotation
    //**************************

    //Delete an employee
    @DELETE("api/employees/{id}")
    Call<Void> deleteEmployee(
            @Path("id") int id
    );

    //*******************************
    // Sign In user with credentials
    //*******************************
    @POST("users/signin")
    Call<Jwt> signInUsername(
            @Body User user
    );

}
