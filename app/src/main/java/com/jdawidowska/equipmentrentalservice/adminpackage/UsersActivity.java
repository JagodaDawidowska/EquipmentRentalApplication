package com.jdawidowska.equipmentrentalservice.adminpackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.UserResponseAdapter;
import com.jdawidowska.equipmentrentalservice.model.UserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private final String url = "http://192.168.1.04:8089/api/users";
    RecyclerView recyclerView;
    List<UserResponse> userResponseList;
    UserResponseAdapter userResponseAdapter;
    String getIDUser;


    //https://www.youtube.com/watch?v=e3MDW87mbR8 baza
    //https://www.youtube.com/watch?v=__OMnFR-wZU //do obejrzenia
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        EditText etUserID = findViewById(R.id.eTxtAdminUsersSearch);
        Button button = findViewById(R.id.btnReturnUsers);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);
        });

        Button btnSearch = findViewById(R.id.btnAdminUsersSearch);
        btnSearch.setOnClickListener(view -> {

            getIDUser = etUserID.getText().toString();
            Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
            intent.putExtra("EXTRA_USER_ID", getIDUser);
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.recycleViewUsers);
        userResponseList = new ArrayList<>();
        extractUsers();
    }

    private void extractUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject;
                        jsonObject = response.getJSONObject(i);
                        UserResponse userResponse = new UserResponse();
                        userResponse.setId(jsonObject.getLong("id"));
                        userResponse.setName(jsonObject.getString("name"));
                        userResponse.setSurname(jsonObject.getString("surname"));
                        userResponseList.add(userResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                userResponseAdapter = new UserResponseAdapter(getApplicationContext(), userResponseList);
                recyclerView.setAdapter(userResponseAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
