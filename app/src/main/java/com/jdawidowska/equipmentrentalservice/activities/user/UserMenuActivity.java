package com.jdawidowska.equipmentrentalservice.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.jdawidowska.equipmentrentalservice.R;

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
}