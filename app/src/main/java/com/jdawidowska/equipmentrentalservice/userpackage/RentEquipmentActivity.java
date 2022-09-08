package com.jdawidowska.equipmentrentalservice.userpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.jdawidowska.equipmentrentalservice.R;


public class RentEquipmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_equipment);

        Button button = findViewById(R.id.btnReturnRentEquipment);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });
    }
}