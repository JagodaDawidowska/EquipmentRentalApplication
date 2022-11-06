package com.jdawidowska.equipmentrentalservice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.jdawidowska.equipmentrentalservice.R;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
        btnRegister.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));

        handleMessages(getIntent());
    }

    private void handleMessages(Intent intent) {
        String message = intent.getStringExtra(MESSAGE);
        if (message != null) {
            Toast.makeText(this, intent.getStringExtra(MESSAGE), Toast.LENGTH_LONG).show();
        }
    }
}