package com.jdawidowska.equipmentrentalservice.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.jdawidowska.equipmentrentalservice.R;

import java.util.ArrayList;
import java.util.List;

public class MenuUserActivity extends AppCompatActivity {

    private List<String> menuList = List.of("Rent Equipment", "Your rentals", "History of your rentals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        ListView listView = findViewById(R.id.listViewMenuUser);
        List<String> list = new ArrayList<>();
        list.add();
        list.add();
        list.add();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, list);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        System.out.println("Rent Equipment");
                        Intent intent = new Intent(getApplicationContext(), RentEquipmentActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        System.out.println("Your rentals");
                        Intent intent = new Intent(MenuUserActivity.this, UserRentalsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        System.out.println("History of your rentals");
                        Intent intent = new Intent(getApplicationContext(), HistoryUserRentalsActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }
}