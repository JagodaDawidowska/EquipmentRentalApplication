package com.jdawidowska.equipmentrentalservice.adminpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<UserResponse> userResponseList;
    private static String url = "http://192.168.1.4:8089/api/users";
    Adapter adapter;

    //https://www.youtube.com/watch?v=e3MDW87mbR8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Button button = findViewById(R.id.btnReturnUsers);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycleViewUsers);
        userResponseList = new ArrayList<>();
        extractUsers();
        
    }

    private void extractUsers() {
       //
    }


}
