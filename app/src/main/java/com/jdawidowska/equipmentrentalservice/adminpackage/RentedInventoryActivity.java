package com.jdawidowska.equipmentrentalservice.adminpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.RentedInventoryResponse;
import com.jdawidowska.equipmentrentalservice.model.UserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RentedInventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RentedInventoryResponse> rentedInventoryResponseList;
    private static String url = "http://192.168.1.4:8089/api/rentedInventory/rentedInventoryResponse";
    RentedInventoryResponseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_inventory);

        Button button = findViewById(R.id.btnReturnRentals);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);

        });
        recyclerView = findViewById(R.id.recycleViewRentedInventory);
        rentedInventoryResponseList = new ArrayList<>();
        extractData();
    }

    private void extractData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject;
                        jsonObject = response.getJSONObject(i);
                        RentedInventoryResponse riResponse = new RentedInventoryResponse();
                        riResponse.setName(jsonObject.getString("name"));
                        riResponse.setSurname(jsonObject.getString("surname"));
                        riResponse.setEquipment(jsonObject.getString("equipment"));
                        riResponse.setAmount(jsonObject.getInt("amount"));
                        rentedInventoryResponseList.add(riResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new  RentedInventoryResponseAdapter(getApplicationContext(), rentedInventoryResponseList);
                recyclerView.setAdapter(adapter);
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