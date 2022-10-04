package com.jdawidowska.equipmentrentalservice.userpackage;

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
import com.jdawidowska.equipmentrentalservice.RentEquipmentAdapter;
import com.jdawidowska.equipmentrentalservice.adminpackage.InventoryActivity;
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.InventoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RentEquipmentActivity extends AppCompatActivity implements RentEquipmentAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Inventory> inventoryList;
    private final String url = "http://192.168.1.04:8089/api/inventory";
    RentEquipmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_equipment);

        Button button = findViewById(R.id.btnReturnRentEquipment);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycleViewRentEquipment);
        inventoryList = new ArrayList<>();
        extractEquipment();
    }

    private void extractEquipment() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject;
                        jsonObject = response.getJSONObject(i);
                        Inventory inventory1 = new Inventory();
                        inventory1.setId(jsonObject.getLong("id"));
                        inventory1.setItemName(jsonObject.getString("itemName"));
                        inventory1.setAvailableAmount(jsonObject.getInt("availableAmount"));
                        inventory1.setTotalAmount(jsonObject.getInt("totalAmount"));
                        inventoryList.add(inventory1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new RentEquipmentAdapter(getApplicationContext(), inventoryList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(RentEquipmentActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {

    }

    //delete this later //in adapter also
    @Override
    public void onRentBtnClicked(int position) {

    }

}