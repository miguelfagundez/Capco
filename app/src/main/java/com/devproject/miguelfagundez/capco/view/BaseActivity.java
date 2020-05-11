package com.devproject.miguelfagundez.capco.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.devproject.miguelfagundez.capco.R;

/********************************************
 * Abstract Class- BaseActivity
 * This class handle only common activity
 * components
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public abstract class BaseActivity extends AppCompatActivity {

    // View members
    public ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);

        //*****************************************
        //   Setup Views
        //*****************************************
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activityContanier);
        progressBar = constraintLayout.findViewById(R.id.baseProgressBar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(layoutResID);
    }

    //*****************************************
    //  Method to show or hide progress bar
    //*****************************************
    public void showProgressBar(boolean visibility){
        progressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
