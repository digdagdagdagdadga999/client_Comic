package datdvph44632.fpoly.assignment_networking.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import datdvph44632.fpoly.assignment_networking.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity after 3 seconds
                Intent intent = new Intent(SplashActivity.this, DangNhap.class);
                startActivity(intent);
                finish(); // Close the splash activity to prevent user from going back to it
            }
        }, SPLASH_TIME_OUT);
    }
}
