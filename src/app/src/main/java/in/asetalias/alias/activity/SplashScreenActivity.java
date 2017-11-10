package in.asetalias.alias.activity;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.asetalias.alias.R;

public class SplashScreenActivity extends Activity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //for showing splash screen for certain duration
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //close this activity
                finish();
                loadMainView();
            }
        }, SPLASH_TIME_OUT);
    }

    private void loadMainView() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}