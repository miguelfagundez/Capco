package com.devproject.miguelfagundez.capco.view.details;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devproject.miguelfagundez.capco.R;
import com.devproject.miguelfagundez.capco.utils.Constants;
import com.devproject.miguelfagundez.capco.viewmodel.EmployeeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


/********************************************
 * Class- EmployeeDetailsFragment
 * This fragment will display employee details
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeDetailsFragment extends Fragment {

    // View Members
    private TextInputEditText tvFirstName;
    private TextInputEditText tvLastName;
    private TextInputEditText tvRole;
    private TextInputEditText tvPhone;

    private FloatingActionButton fabDeleteEmployee;
    private FloatingActionButton fabUpdateEmployee;

    // ViewModel
    private EmployeeViewModel viewModel;

    // Primitives
    int id;
    String fName;
    String lName;
    String role;
    String phone;
    boolean isNewEmployee;


    public EmployeeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_details, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        setupViews();
        setupViewModel();
        setupListeners();

        isNewEmployee = getArguments().getBoolean(Constants.EMPLOYEE_CREATION);
        if (isNewEmployee){
            // New Employee
            setViewEmployeeValues("", "", "", "");

            // If new employee, I am not able to delete yet
            fabDeleteEmployee.setEnabled(false);
            phone = "";

        }else {
            fabDeleteEmployee.setEnabled(true);
            // This employee come from my recycler view
            id = getArguments().getInt(Constants.EMPLOYEE_ID);
            fName = getArguments().getString(Constants.EMPLOYEE_FIRST_NAME);
            lName = getArguments().getString(Constants.EMPLOYEE_LAST_NAME);
            role = getArguments().getString(Constants.EMPLOYEE_ROLE);
            //***************************************************
            // Searching phone locally using Shared Preferences
            //***************************************************
            phone = viewModel.searchEmployeePhone(id);

            if (fName != null && lName != null && role != null) {

                setViewEmployeeValues(fName, lName, role, phone);
                if (viewModel.hasAdminPermissions()) {
                    setViewEnabled(true);
                } else {
                    setViewEnabled(false);
                }
            }
        }
    }

    //***************************************************
    //              Setup components
    //***************************************************

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(EmployeeDetailsFragment.this).get(EmployeeViewModel.class);
    }


    private void setupViews() {
        tvFirstName = getActivity().findViewById(R.id.etEmployeeFirstName);
        tvLastName = getActivity().findViewById(R.id.etEmployeeLastName);
        tvRole = getActivity().findViewById(R.id.etEmployeeRole);
        tvPhone = getActivity().findViewById(R.id.etEmployeePhone);

        fabDeleteEmployee = getActivity().findViewById(R.id.fabDeleteEmployee);
        fabUpdateEmployee = getActivity().findViewById(R.id.fabUpdateEmployee);
    }

    private void setupListeners() {
        fabUpdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.hasAdminPermissions()){

                    // Data cannot be empty
                    if (checkingData()){
                        if(isNewEmployee){
                            // Adding new employee
                            viewModel.createEmployee(id, fName, lName, role);
                            Toast.makeText(getContext(), R.string.employee_created, Toast.LENGTH_SHORT).show();
                            if(!phone.isEmpty()){
                                viewModel.saveEmployeePhone(id, phone);
                            }
                        }else{
                            // Updating an employee
                            viewModel.updatedEmployee(id, fName, lName, role);
                            Toast.makeText(getContext(), R.string.employee_updated, Toast.LENGTH_SHORT).show();
                            if(!phone.isEmpty()){
                                viewModel.saveEmployeePhone(id, phone);
                            }
                        }

                        getActivity().onBackPressed();
                    }else{
                        Toast.makeText(getContext(), R.string.denied_update_employee, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), R.string.denied_update_employee, Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.hasAdminPermissions()){
                    viewModel.deleteEmployee(id, fName, lName, role);
                    viewModel.searchListOfEmployees();
                    Toast.makeText(getContext(), R.string.delete_successfully, Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }else{
                    Toast.makeText(getContext(), R.string.denied_delete_employee, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //***************************************************
    //          Checking if data is Empty
    //***************************************************
    private boolean checkingData() {
        boolean validData = true;

        fName = tvFirstName.getText().toString();
        lName = tvLastName.getText().toString();
        role = tvRole.getText().toString();
        phone = tvPhone.getText().toString();

        if (fName.isEmpty() || lName.isEmpty() || role.isEmpty())
            validData = false;

        return validData;
    }

    //***************************************************
    //              Utils methods
    //***************************************************
    private void setViewEnabled(boolean enabled){
        tvFirstName.setEnabled(enabled);
        tvLastName.setEnabled(enabled);
        tvRole.setEnabled(enabled);
        tvPhone.setEnabled(enabled);
    }

    private void setViewEmployeeValues(String fName, String lName, String role, String phone){
        tvFirstName.setText(fName);
        tvLastName.setText(lName);
        tvRole.setText(role);
        tvPhone.setText(phone);
    }
}
