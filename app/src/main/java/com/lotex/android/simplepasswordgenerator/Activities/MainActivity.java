package com.lotex.android.simplepasswordgenerator.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lotex.android.simplepasswordgenerator.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button mPasswordGenerator, mPasswordLocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set views to variables
        viewAttach();
    }

    public void viewAttach() {
        mPasswordGenerator = findViewById(R.id.button_password_generator);
        mPasswordGenerator.setOnClickListener(this);
        mPasswordLocker = findViewById(R.id.button_password_locker);
        mPasswordLocker.setOnClickListener(this);
    }

    public void passwordGeneratorActivity() {
        Intent intent = new Intent(MainActivity.this, PasswordGenerator.class);
        startActivity(intent);
    }

    public void passwordLockerActivity() {
        Intent intent = new Intent(MainActivity.this, PasswordLocker.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_password_generator:
                passwordGeneratorActivity();
                break;
            case R.id.button_password_locker:
                passwordLockerActivity();
                break;
            default:
                break;
        }
    }

} // class end