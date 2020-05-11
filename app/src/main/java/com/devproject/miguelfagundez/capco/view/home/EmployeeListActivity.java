package com.devproject.miguelfagundez.capco.view.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devproject.miguelfagundez.capco.R;
import com.devproject.miguelfagundez.capco.adapters.EmployeeAdapter;
import com.devproject.miguelfagundez.capco.listeners.EmployeeListener;
import com.devproject.miguelfagundez.capco.model.pojo.Employee;
import com.devproject.miguelfagundez.capco.model.pojo.Error;
import com.devproject.miguelfagundez.capco.utils.Constants;
import com.devproject.miguelfagundez.capco.view.BaseActivity;
import com.devproject.miguelfagundez.capco.view.details.EmployeeDetailsFragment;
import com.devproject.miguelfagundez.capco.view.splash.SplashActivity;
import com.devproject.miguelfagundez.capco.viewmodel.EmployeeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/********************************************
 * Class- EmployeeListActivity
 * This activity will display a list of employees
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeListActivity extends BaseActivity implements EmployeeListener {

    private String TAG = "Test";

    // View members
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;
    private FloatingActionButton fabCreateEmployee;
    private View rootView;

    // ViewModel Data
    private EmployeeViewModel viewModel;

    // Fragments
    private EmployeeDetailsFragment employeeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        setupViews();
        setupFragments();
        setupViewModel();
        setupViewModelObservers();
        setupListeners();
        setupRecyclerView();

    }

    //*********************************************
    //              Setup components
    //*********************************************
    private void setupViews() {

        recyclerView = findViewById(R.id.rvEmployeeList);
        fabCreateEmployee = findViewById(R.id.fabCreateEmployee);
        rootView = findViewById(R.id.rootViewEmployeeList);
    }

    private void setupFragments() {
        employeeFragment = new EmployeeDetailsFragment();
    }


    private void setupViewModel() {
        // Setup ViewModel
        viewModel = ViewModelProviders.of(EmployeeListActivity.this).get(EmployeeViewModel.class);
    }

    private void setupViewModelObservers(){

        //******************************
        // Observe my list of employees
        //******************************
        viewModel.getListOfEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                if (employees != null){

                    adapter.setEmployeeInfo(employees);
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(EmployeeListActivity.this, R.string.no_employees_found, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //******************************
        // Observe my Error data
        //******************************
        viewModel.getError().observe(this, new Observer<Error>() {
            @Override
            public void onChanged(Error error) {
                String message = "Status: " + error.getStatus() + " | Message: " + error.getMessage();
                Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void setupListeners() {

        //****************************
        // Floating Action Button:
        // Create a new employee
        //****************************
        fabCreateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.hasAdminPermissions()){
                    Toast.makeText(EmployeeListActivity.this, R.string.create_employee, Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.EMPLOYEE_CREATION, true);

                    callingEmployeeDetailsFragment(bundle);
                }else{
                    Toast.makeText(EmployeeListActivity.this, R.string.access_denied_create, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setupRecyclerView() {
        adapter = new EmployeeAdapter( this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ******************** CHECKING HERE **************************

        viewModel.searchListOfEmployees();

        // ******************** CHECKING HERE **************************
    }

    //*********************************************
    //              Menu options
    //*********************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_logout).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Item clicks from action bar menu
        int id = item.getItemId();

        switch (id){
            case R.id.menu_logout:
                viewModel.closeAccessGranted();
                startActivity(new Intent(EmployeeListActivity.this, SplashActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void employeeClick(int id, String fName, String lName, String role) {

        // Here I will be calling a fragment

        Bundle bundle = new Bundle();

        bundle.putBoolean(Constants.EMPLOYEE_CREATION, false);
        bundle.putInt(Constants.EMPLOYEE_ID, id);
        bundle.putString(Constants.EMPLOYEE_FIRST_NAME, fName);
        bundle.putString(Constants.EMPLOYEE_LAST_NAME, lName);
        bundle.putString(Constants.EMPLOYEE_ROLE, role);

        callingEmployeeDetailsFragment(bundle);

    }

    //***************************************************
    // Util method - Calling this fragment several times
    //***************************************************
    private void callingEmployeeDetailsFragment(Bundle bundle) {

        employeeFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_in,
                        R.anim.anim_out,
                        R.anim.anim_in,
                        R.anim.anim_out)
                .replace(R.id.fragmentContainer, employeeFragment)
                .addToBackStack(employeeFragment.getTag())
                .commit();
    }
}

