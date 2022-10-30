package com.jdawidowska.equipmentrentalservice.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jdawidowska.equipmentrentalservice.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        ListView menuItemsListView = findViewById(R.id.listViewMenuUser);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1 , initMenuList());
        menuItemsListView.setAdapter(arrayAdapter);

        menuItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0: {
                        Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                        startActivity(intent);
                        break;
                    } case 1: {
                        Intent intent = new Intent(getApplicationContext(), RentedInventoryActivity.class);
                        startActivity(intent);
                        break;
                    } case 2: {
                        Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                        startActivity(intent);
                        break;
                    }  case 3: {
                        Intent intent = new Intent(getApplicationContext(), FeedbacksActivity.class);
                        startActivity(intent);
                        break;
                    }
                 }
            }
        });
    }

    private List<String> initMenuList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Inventory");
        list.add("Rented Inventory");
        list.add("Users");
        list.add("Feedbacks");
        return list;
    }
}