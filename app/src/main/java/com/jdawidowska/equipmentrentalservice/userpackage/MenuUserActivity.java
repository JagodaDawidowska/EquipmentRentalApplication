package com.jdawidowska.equipmentrentalservice.userpackage;

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

public class MenuUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);

        ListView listView = findViewById(R.id.listViewMenuUser);
        List<String> list =new ArrayList<>();
        list.add("Rent Equipment");
        list.add("Your rentals");
        list.add("History of your rentals");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1 ,list);
        listView.setAdapter(arrayAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               switch(i){
                   case 0: {
                       System.out.println("Rent Equipment");
                       Intent intent = new Intent(getApplicationContext(),RentEquipmentActivity.class);
                       startActivity(intent);
                       break;
                   } case 1: {
                       System.out.println("Your rentals");
                       Intent intent = new Intent( MenuUserActivity.this , UserRent5Activity.class);
                       startActivity(intent);
                       break;
                   } case 2: {
                       System.out.println("History of your rentals");
                       Intent intent = new Intent(getApplicationContext(),HistoryUserRentalsActivity.class);
                       startActivity(intent);
                       break;
                   }
               }
           }
       });

    }
}