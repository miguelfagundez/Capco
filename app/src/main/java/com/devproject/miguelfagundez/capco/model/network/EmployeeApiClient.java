package com.devproject.miguelfagundez.capco.model.network;

import android.content.Context;
import android.content.ContextWrapper;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.devproject.miguelfagundez.capco.model.pojo.Authority;
import com.devproject.miguelfagundez.capco.model.pojo.Employee;
import com.devproject.miguelfagundez.capco.model.pojo.Error;
import com.devproject.miguelfagundez.capco.model.pojo.Jwt;
import com.devproject.miguelfagundez.capco.model.pojo.User;
import com.devproject.miguelfagundez.capco.model.preferences.EmployeePreferences;
import com.devproject.miguelfagundez.capco.utils.Constants;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/********************************************
 * Class- EmployeeApiClient
 * Model layer where I connect and make a call.
 * This layer connect with repository
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeApiClient extends ContextWrapper {

    private static final String TAG = "Test";
    //******************************************
    // Singleton pattern
    //******************************************
    private static EmployeeApiClient client = null;

    // Data Member LiveData
    private MutableLiveData<List<Employee>> listOfEmployees;
    private MutableLiveData<Employee> singleEmployee;
    private MutableLiveData<Error> errorMessage;

    // Service
    private EmployeeInterface service;

    // Shared preferences
    private EmployeePreferences preferences;

    // Primitives
    private MutableLiveData<Boolean> accessGranted;

    // JWT third part library to control access [USER_ROLE, ADMIN_ROLE]
    private JWT jwtLibrary;
    private Claim claim;


    public static EmployeeApiClient getInstance(Context context){
        if (client == null){
            synchronized(EmployeeApiClient.class){
                if (client == null){
                    client = new EmployeeApiClient(context);
                }
            }
        }
        return client;
    }

    private EmployeeApiClient(Context context){
        super(context);
        listOfEmployees = new MutableLiveData<>();
        preferences = new EmployeePreferences(context);
        initLiveData();
        initEmployeeInterface();

    }

    private void initLiveData() {
        listOfEmployees = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        singleEmployee = new MutableLiveData<>();

        accessGranted = new MutableLiveData<>();
        accessGranted.setValue(false);

        jwtLibrary = null;
        claim = null;
    }

    //*******************
    //  Interface init
    //*******************
    private void initEmployeeInterface() {
        final String authentication = preferences.read(Constants.HEADER_TOKEN);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder addedHeaderRequest = request.newBuilder().header("Authorization", authentication);
                return chain.proceed(addedHeaderRequest.build());
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //Adding in the client for authentication header
                .client(httpClientBuilder.build())
                .build();

        service = retrofit.create(EmployeeInterface.class);
    }

    //***************************************************
    // GETTING METHODS **********************************
    // Connecting with client
    public LiveData<Error> getError() {
        if (errorMessage == null){
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }

    // Returning current employee
    public LiveData<Employee> getEmployee() {
        if (singleEmployee == null){
            singleEmployee = new MutableLiveData<>();
        }
        return singleEmployee;
    }

    // Returning current list of employees
    public LiveData<List<Employee>> getListOfEmployees(){
        if (listOfEmployees == null){
            listOfEmployees = new MutableLiveData<>();
        }
        searchAllEmployees();
        return listOfEmployees;
    }


    //******************************************
    // Making the network connection - Call
    //******************************************

    // SEARCHING METHODS **********************************
    public void searchAllEmployees() {

        if (listOfEmployees == null){
            listOfEmployees = new MutableLiveData<>();
        }

        service.getListOfEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<Employee> employees = response.body();
                    listOfEmployees.postValue(employees);
                }else{
                    updateErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                //***********************************
                // CHECKING LATER
                //***********************************
                updateErrorMessage(null);
            }
        });

    }

    // Searching for employee by id
    public LiveData<Employee> getEmployeeById(int id) {
        if (singleEmployee == null) {
            singleEmployee = new MutableLiveData<>();
        }

        // Connecting with Retrofit
        service.getEmployeeById(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    singleEmployee.postValue(response.body());
                }else{
                    updateErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                    updateErrorMessage(null);
            }
        });

        return singleEmployee;
    }


    //*****************************************************
    // CRUD OPERATIONS ************************************
    //*****************************************************
    public void createEmployee(Employee employee) {
        service.createNewEmployee(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employees = listOfEmployees.getValue();
                    employees.add(response.body());
                    listOfEmployees.postValue(employees);
                }else{
                    updateErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                updateErrorMessage(null);
            }
        });
    }

    public void updateEmployee(final Employee employee) {
        service.updateEmployee(employee.getId(), employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Employee> employees = listOfEmployees.getValue();
                    employees.remove(employee);
                    employees.add(response.body());
                    listOfEmployees.postValue(employees);
                }else{
                    updateErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                updateErrorMessage(null);
            }
        });
    }


    public void deleteEmployee(final Employee employee) {
        service.deleteEmployee(employee.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    List<Employee> employees = listOfEmployees.getValue();
                    employees.remove(employee);
                    listOfEmployees.postValue(employees);
                }else{
                    updateErrorMessage(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                updateErrorMessage(null);
            }
        });
    }


    //*****************************************************
    // CREDENTIALS ****************************************
    //*****************************************************
public void signIn(String username, String password) {

        User user = new User(username, password);
        service.signInUsername(user).enqueue(new Callback<Jwt>() {
            @Override
            public void onResponse(Call<Jwt> call, Response<Jwt> response) {
                if (response.isSuccessful() && response.body() != null){

                    // Using third library to handle jwt credentials
                    // Manage internal permissions
                    jwtLibrary = new JWT(response.body().getJwt());
                    claim = jwtLibrary.getClaim("auth");

                    preferences.write(Constants.HEADER_TOKEN, "Bearer " + response.body().getJwt());

                    changeAccess(true);
                }else{
                    updateErrorMessage(response.errorBody());
                    changeAccess(false);
                }
            }

            @Override
            public void onFailure(Call<Jwt> call, Throwable t) {
                updateErrorMessage(null);
                changeAccess(false);
            }
        });
    }

    //*********************************
    //     JWT and Claim methods
    // One user can be basic or admin
    //*********************************
    public boolean hasAuthority(){
        boolean result = false;

        if (jwtLibrary == null || claim == null){
            return result;
        }
        Authority[] authClaims = claim.asArray(Authority.class);
        for (Authority authClaim : authClaims) {

            if (authClaim.getAuthority().equals(Constants.HAS_ADMIN_PERMISSION)){
                result = true;
            }
        }
        return result;
    }

    //***************************
    // Update LiveData<Error>
    //***************************
    private void updateErrorMessage(ResponseBody errorBody) {
        if(errorBody!=null){
            Gson gson = new Gson();
            Error error =gson.fromJson(errorBody.charStream(), Error.class);
            errorMessage.postValue(error);
            return;
        }
        Error error = new Error(0, "Unknown Error");
        errorMessage.postValue(error);
    }

    //******************************
    // Controling access to the app
    //******************************
    private void changeAccess(boolean granted) {
        accessGranted.setValue(granted);
    }

    public void closeAccessGranted() {
        accessGranted.setValue(false);
    }

    public LiveData<Boolean> getAccessGranted() {
        return accessGranted;
    }

    //********************************************
    // Saving phone locally in Shared Preferences
    //********************************************
    public void savePhoneInPreferences(int id, String phone) {
        preferences.write(String.valueOf(id),phone);
    }

    public String searchEmployeePhoneInPreferences(int id) {
        return preferences.read(String.valueOf(id));
    }

}

