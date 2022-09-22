package com.jdawidowska.equipmentrentalservice.adminpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.InventoryOnClickInterface;
import com.jdawidowska.equipmentrentalservice.MainActivity;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.InventoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Inventory> inventoryList;
    private static String url = "http://192.168.1.04:8089/api/inventory";
    InventoryAdapter adapter;
    public Button btn ;



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
        blockRemove();
    }

    private void blockRemove() {
        for (Inventory i: inventoryList) {
            if(i.getTotalAmount()!=i.getTotalAmount()){

            }
        }
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
                adapter.setOnItemClickListener(InventoryActivity.this);

                //need to set method from inferface to adapter

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    //the override method from interface
    @Override
    public void onItemClick(int position) {
        Inventory inventoryClicked = inventoryList.get(position);
        System.out.println("ddddddddddd");
        Toast.makeText(getApplicationContext(), "Single Click on Image :" + position,
                Toast.LENGTH_SHORT).show();


    }

//    private void lendItem(Integer id){
//        //wykonanie zapytania do backendu
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = ApiEndpoints.LEND_EQUIPMENT.toString() + id;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> Toast.makeText(this, response, Toast.LENGTH_SHORT).show(),
//                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
//        );

    public void remove(int position){
        Inventory inventory = inventoryList.get(position);
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println((inventory.getId()).toString());
        String url =  "http://192.168.1.04:8089/api/inventory/remove/"+  (inventory.getId()).toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, response, Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        );
        queue.add(stringRequest);
    }

    //https://www.youtube.com/watch?v=HMjI7cLsyfw
    @Override
    public void onRemoveBtnClicked(int position) {
        Inventory inventoryClicked = inventoryList.get(position);
        if(inventoryClicked.getTotalAmount() != inventoryClicked.getAvailableAmount()){
            Toast.makeText(getApplicationContext(), "CAN'T REMOVE WHILE EQUIPMENT IS RENTED" + position,
                    Toast.LENGTH_SHORT).show();
        }if(inventoryClicked.getTotalAmount() == inventoryClicked.getAvailableAmount()){
            System.out.println("jest rowne");
            remove(position);
            //removing item from list
            inventoryList.remove(position);
            //removing row in recycleview
            adapter.notifyItemRemoved(position);
        }

    }
}