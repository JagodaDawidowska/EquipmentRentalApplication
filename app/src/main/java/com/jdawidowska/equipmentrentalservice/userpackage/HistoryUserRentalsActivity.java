package com.jdawidowska.equipmentrentalservice.userpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.HistoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.UserHistoryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HistoryUserRentalsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String maxIp="192.168.0.35";
    String JaIp="192.168.1.04";
    String UserId;
    private String url = "http://"+JaIp+":8089/api/history/user/DTO/1";

    HistoryAdapter historyAdapter;
    ArrayList<UserHistoryResponse> userHistoryResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user_rentals);


        Button button = findViewById(R.id.btnReturnUserHistoryRentals);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycleViewUserHistoryRentals);
        userHistoryResponseList = new ArrayList<>();
        extractUsers();
    }

    private void extractUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        UserHistoryResponse userHistoryResponse = new UserHistoryResponse();
                        userHistoryResponse.setEmail(jsonObject.getString("email"));
                        userHistoryResponse.setItemName(jsonObject.getString("itemName"));

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String rentDate = jsonObject.getString("rentDate");
                        String returnDate = jsonObject.getString("returnDate");
                        if (returnDate.equals("null")) {
                            userHistoryResponse.setReturnDate(null);
                        } else {
                            userHistoryResponse.setReturnDate(format.parse(returnDate));
                        }
                        Date dateRent2 = format.parse(rentDate);
                        userHistoryResponse.setRentDate(dateRent2);

                        userHistoryResponseList.add(userHistoryResponse);
                    } catch (JSONException | ParseException e) {
                        //Toast.makeText(HistoryActivity.this, "No ID is passed, Return to set User Id", Toast.LENGTH_LONG).show();
                    }
                }
                if(userHistoryResponseList.isEmpty()){
                    Toast.makeText(HistoryUserRentalsActivity.this, "User with this Id has not rent anything yet", Toast.LENGTH_LONG).show();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                historyAdapter = new HistoryAdapter(getApplicationContext(), userHistoryResponseList);
                recyclerView.setAdapter(historyAdapter);
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