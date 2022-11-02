package com.jdawidowska.equipmentrentalservice.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.jdawidowska.equipmentrentalservice.R;
import java.util.List;

public class AdminMenuActivity extends AppCompatActivity {

    private final List<String> menuItems = List.of(
            "Inventory",
            "Rented inventory",
            "Users",
            "Feedbacks"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menuItems
        );

        ListView listView = findViewById(R.id.lvUserMenu);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this::menuAction);
    }

    private void menuAction(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0: {
                Intent intent = new Intent(this, AdminInventoryActivity.class);
                startActivity(intent);
                break;
            }
            case 1: {
                Intent intent = new Intent(this, AdminRentedInventoryActivity.class);
                startActivity(intent);
                break;
            }
            case 2: {
                Intent intent = new Intent(this, AdminUsersActivity.class);
                startActivity(intent);
                break;
            }
            case 3: {
                Intent intent = new Intent(this, AdminFeedbacksActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}