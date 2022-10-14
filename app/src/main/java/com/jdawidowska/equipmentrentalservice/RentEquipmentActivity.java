package com.jdawidowska.equipmentrentalservice;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.model.Inventory;
import com.jdawidowska.equipmentrentalservice.userpackage.MenuUserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RentEquipmentActivity extends AppCompatActivity implements RentEquipmentAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Inventory> inventoryList;
    String maxIp="192.168.0.35";
    String JaIp="192.168.1.04";

    private final String url = "http://"+maxIp+":8089/api/inventory";
    RentEquipmentAdapter adapter;
    Long idUser1 = Long.valueOf(1);
    RentingRequest rentingRequest;
    private String rentUrl="http://"+maxIp+":8089/api/renting/rent";


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



    private void rentEquipment(int position) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        Inventory inventoryClicked = inventoryList.get(position);


        if (inventoryClicked.getAvailableAmount()==0) {
            Toast.makeText(getApplicationContext(), "This equipment is out" , Toast.LENGTH_SHORT).show();
            inventoryList.remove(position);
            adapter.notifyItemRemoved(position);
        }
       else{
            JSONObject body = new JSONObject();

            try {

               body.put("idUser",idUser1);
               body.put("idItem",inventoryClicked.getId());

                // Put user JSONObject inside of another JSONObject which will be the body of the request
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    rentUrl,
                    body,
                    response -> {   //oddaje response w json:(
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }, error -> {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
            );
            queue.add(jsonObjectRequest);

        }
    }


    @Override
    public void onRentBtnClicked(int position) {
        Inventory inventoryClicked = inventoryList.get(position);
        inventoryClicked.getId();
        rentEquipment(position);
    }

}