package com.jdawidowska.equipmentrentalservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnReturn = findViewById(R.id.btnReturnLogin);

        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class );
            startActivity(intent);
        });
    }
}