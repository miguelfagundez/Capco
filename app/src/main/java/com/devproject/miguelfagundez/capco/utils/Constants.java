package com.devproject.miguelfagundez.capco.utils;

/********************************************
 * Class- Constants
 * Util class to handle my app constants
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class Constants {

    //******************************************
    // Retrofit variables
    //******************************************
    //public static final String BASE_URL = "http://dummy.restapiexample.com/";
    public static final String BASE_URL = "http://ec2-174-129-102-71.compute-1.amazonaws.com/coding-challenge/";

    //******************************************
    // Testing variables
    //******************************************
    public static final String TAG = "Testing";

    //******************************************
    // Fragments keys
    //******************************************
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_FIRST_NAME = "employee_first_name";
    public static final String EMPLOYEE_LAST_NAME = "employee_last_name";
    public static final String EMPLOYEE_ROLE = "employee_role";
    public static final String EMPLOYEE_CREATION = "create_new_employee";

    //******************************************
    // Splash
    //******************************************
    public static final int SPLASH_TIME_OUT = 5000;

    //******************************************
    // Shared Preferences
    //******************************************
    public static final String SHARED_PREFERENCES_NAME = "Employee Shared Preferences";
    public static final String HEADER_TOKEN = "Bearer Token";

    //******************************************
    // Control variables
    //******************************************
    public static final String HAS_ADMIN_PERMISSION = "ROLE_ADMIN";
}

