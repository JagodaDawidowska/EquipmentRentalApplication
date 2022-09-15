package com.jdawidowska.equipmentrentalservice.adminpackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.HistoryAdapter;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.UserHistoryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    String UserId;
    private String url = "http://192.168.0.35:8089/api/history/user/DTO/";

    HistoryAdapter historyAdapter;
    ArrayList<UserHistoryResponse> userHistoryResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        String userId = intent.getStringExtra("EXTRA_USER_ID");
        if(userId == null || userId.isEmpty()){
            Toast.makeText(this,"Id User not chosen \n Return and pass user Id",Toast.LENGTH_LONG).show();
        } else {
            url = url + userId;
            extractUsers();
        }

        Button btnReturn = findViewById(R.id.btnReturnUserHistoryRentals2);
        btnReturn.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent2);
        });

        recyclerView = findViewById(R.id.recycleViewUserHistoryID);
        userHistoryResponseList = new ArrayList<>();
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
                    Toast.makeText(HistoryActivity.this, "User with this Id has not rent anything yet", Toast.LENGTH_LONG).show();
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