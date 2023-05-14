package com.jdawidowska.equipmentrentalservice.activities.user;

import static com.jdawidowska.equipmentrentalservice.activities.MainActivity.MESSAGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.MainActivity;

import java.util.List;

public class UserMenuActivity extends AppCompatActivity {

    private final List<String> menuItems = List.of(
            "Rent equipment",
            "Your rentals",
            "History of your rentals"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                menuItems
        );

        ListView listView = findViewById(R.id.lvUserMenu);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this::menuAction);

        Button btnReturn = findViewById(R.id.btnReturnUserMenu);
        btnReturn.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        handleMessages(getIntent());
    }

    private void menuAction(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0: {
                Intent intent = new Intent(this, UserRentingActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                Intent intent = new Intent(this, UserCurrentlyRentedActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(this, UserRentingHistoryActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void handleMessages(Intent intent) {
        String message = intent.getStringExtra(MESSAGE);
        if (message != null) {
            Toast.makeText(this, intent.getStringExtra(MESSAGE), Toast.LENGTH_LONG).show();
        }
    }
}