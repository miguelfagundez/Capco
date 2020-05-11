package com.devproject.miguelfagundez.capco.view.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.devproject.miguelfagundez.capco.R;
import com.devproject.miguelfagundez.capco.utils.Constants;
import com.devproject.miguelfagundez.capco.view.BaseActivity;
import com.devproject.miguelfagundez.capco.view.authentication.AuthenticationFragment;

/********************************************
 * Activity- SplashActivity
 * This activity implements a simple splash screen
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class SplashActivity extends BaseActivity {

    // Animnation
    private Animation topAnimation;
    private Animation bottomAnimation;

    // TextView
    private TextView companyName;
    private TextView departmentName;

    // Fragments
    private AuthenticationFragment authenticationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        setupAnimations();
        setupViews();
        connectAnimationWithViews();

        // I hold 3.5 sec before calling the login activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callNextActivity();
            }
        }, Constants.SPLASH_TIME_OUT);
    }

    //***************************************************
    //              Setup components
    //***************************************************
    private void setupAnimations() {
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);
    }

    private void setupViews() {
        companyName = findViewById(R.id.companyName);
        departmentName = findViewById(R.id.departmentName);

        authenticationFragment = new AuthenticationFragment();
    }

    //***************************************************
    //              Utils methods
    //***************************************************
    private void connectAnimationWithViews() {
        companyName.setAnimation(topAnimation);
        departmentName.setAnimation(bottomAnimation);
    }

    private void callNextActivity() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.anim_in,
                        R.anim.anim_out)
                .replace(R.id.splashContainer, authenticationFragment)
                .commit();
    }
}
