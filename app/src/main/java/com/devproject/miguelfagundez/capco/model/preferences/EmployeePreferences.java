package com.devproject.miguelfagundez.capco.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.devproject.miguelfagundez.capco.utils.Constants;

/********************************************
 * Class- EmployeePreferences
 * Basic Shared Preferences for persistent data,
 * for example, employee phone or jwt token
 *
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeePreferences {

    private SharedPreferences preferences;

    public EmployeePreferences(Context context){
        preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, context.MODE_PRIVATE);
    }

    //****************************************
    // Write options available:
    // String, Boolean
    //****************************************
    public void write(String key, String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //****************************************
    // Read options available:
    // String, Boolean
    //****************************************
    public String read(String key) {
        return preferences.getString(key,"");
    }

}
