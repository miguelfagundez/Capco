package com.devproject.miguelfagundez.capco.view.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.devproject.miguelfagundez.capco.R;
import com.devproject.miguelfagundez.capco.model.pojo.Error;
import com.devproject.miguelfagundez.capco.view.home.EmployeeListActivity;
import com.devproject.miguelfagundez.capco.viewmodel.EmployeeViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


/********************************************
 * Class- AuthenticationFragment
 * This fragment will display authentication
 * window
 *
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class AuthenticationFragment extends Fragment {

    // View members
    private Button btnStart;
    private TextInputEditText username;
    private TextInputEditText password;
    private View rootView;

    // ViewModel
    private EmployeeViewModel viewModel;
    // Checking
    boolean bugTemp = false;


    public AuthenticationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);

        setupViews();
        setupListeners();
        setupViewModelObservers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    //***************************************************
    //              Setup components
    //***************************************************
    private void setupViews() {

        btnStart = getActivity().findViewById(R.id.btnStart);
        username = getActivity().findViewById(R.id.etUsername);
        password = getActivity().findViewById(R.id.etPassword);
        rootView = getActivity().findViewById(R.id.rootViewSplash);

    }

    private void setupListeners() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUsername = username.getText().toString();
                String tempPassword = password.getText().toString();
                if(tempUsername.isEmpty() || tempPassword.isEmpty()){
                    Toast.makeText(getActivity(), R.string.data_entry_cannot_empty, Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.signIn(tempUsername, tempPassword);
                }

            }
        });
    }

    private void setupViewModelObservers() {
        viewModel.getError().observe(getActivity(), new Observer<Error>() {
            @Override
            public void onChanged(Error error) {
                String message = "Status: " + error.getStatus() + " | Message: " + error.getMessage();
                Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();

            }
        });

        viewModel.getAccessgranted().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(bugTemp){
                    if (aBoolean){
                        startActivity(new Intent(getActivity().getApplicationContext(), EmployeeListActivity.class));
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(), R.string.credentials_not_accepted, Toast.LENGTH_SHORT).show();
                    }
                }
                // Temp variable - Checking
                bugTemp = true;
            }
        });
    }
}
