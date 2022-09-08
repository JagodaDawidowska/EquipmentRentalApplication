package com.jdawidowska.equipmentrentalservice.adminpackage;

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

        ListView listView = findViewById(R.id.listViewMenuUser);
        List<String> list =new ArrayList<>();
        list.add("Inventory");
        list.add("Rentals");
        list.add("Users");
        list.add("Feedbacks");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1 ,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
                    startActivity(intent);
                }if(i==1) {
                    Intent intent1 = new Intent(getApplicationContext(), RentalsActivity.class);
                    startActivity(intent1);
                }if(i==2) {
                    Intent intent2 = new Intent(getApplicationContext(), UsersActivity.class);
                          startActivity(intent2);
                }if(i==3) {
                    Intent intent3 = new Intent(getApplicationContext(), FeedbacksActivity.class);
                    startActivity(intent3);
                }

//                switch(i){
//                    case 0: {
//                        Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);
//                        startActivity(intent);
//                        break;
//                    } case 1: {
//                        Intent intent1 = new Intent(getApplicationContext(), RentalsActivity.class);
//                        startActivity(intent1);
//                        System.out.println("rentals");
//                        break;
//                    } case 2: {
//                        Intent intent2 = new Intent(getApplicationContext(), UsersActivity.class);
//                        startActivity(intent2);
//                        break;
//                    }  case 3: {
//                        Intent intent3 = new Intent(getApplicationContext(), FeedbakcsActivity.class);
//                        startActivity(intent3);
//                        System.out.println("feedbacks");
//                    }
//                 }
            }
        });
    }
}