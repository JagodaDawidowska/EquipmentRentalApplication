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
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.InventoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Inventory> inventoryList;
    private static String url = "http://192.168.1.04:8089/api/inventory";
    InventoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Button button = findViewById(R.id.btnReturnInventory);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);
        });
        recyclerView = findViewById(R.id.reycleViewInventory);
        inventoryList = new ArrayList<>();
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
                        Inventory inventory = new Inventory();
                        inventory.setId(jsonObject.getLong("id"));
                        inventory.setItemName(jsonObject.getString("itemName"));
                        inventory.setAvailableAmount(jsonObject.getInt("availableAmount"));
                        inventory.setTotalAmount(jsonObject.getInt("totalAmount"));
                        inventoryList.add(inventory);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new InventoryAdapter(getApplicationContext(), inventoryList);
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