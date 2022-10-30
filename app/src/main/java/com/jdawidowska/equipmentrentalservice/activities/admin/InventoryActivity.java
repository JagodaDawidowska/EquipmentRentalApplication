package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.InventoryAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity implements InventoryAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Inventory> inventoryList;
    private final String url = "http://192.168.1.04:8089/api/inventory";
    InventoryAdapter adapter;
    public Button btn;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText addEquipmentPopUp, addAmountPopUp;
    private Button btnSaveEquipmentPopUp, returnEquipmentPopUp;
    private final String addUrl = "http://192.168.1.04:8089/api/inventory/add";

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

        Button btnAddEquipment = findViewById(R.id.btnInventoryAddItem);
        btnAddEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddEquipmentDialog();
            }
        });
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
            }
        }, new ErrorListener() {
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
        Toast.makeText(getApplicationContext(), "Single Click on Image :" + position, Toast.LENGTH_SHORT).show();
    }

    public void remove(int position) {
        Inventory inventory = inventoryList.get(position);
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println((inventory.getId()).toString());
        String url = "http://192.168.1.04:8089/api/inventory/remove/" + (inventory.getId()).toString();
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
        if (!inventoryClicked.getTotalAmount().equals(inventoryClicked.getAvailableAmount())) {
            Toast.makeText(getApplicationContext(), "CAN'T REMOVE WHILE EQUIPMENT IS RENTED" + position, Toast.LENGTH_SHORT).show();
        }
        if (inventoryClicked.getTotalAmount().equals(inventoryClicked.getAvailableAmount())) {
            remove(position);
            //removing item from list
            inventoryList.remove(position);
            //removing row in recycleview
            adapter.notifyItemRemoved(position);
        }
    }

    public void createAddEquipmentDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View addEquipmentPopUpView = getLayoutInflater().inflate(R.layout.popup_addequipment, null);
        //editTexts
        addEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.editTextEqupmentPopUp);
        addAmountPopUp = addEquipmentPopUpView.findViewById(R.id.editTextAmountPopUp);
        //buttons
        btnSaveEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.btnPopup_addequipment_add);
        returnEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.btnReturnEquipmentkPopUp);

        dialogBuilder.setView(addEquipmentPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSaveEquipmentPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addEquipmentPopUp.getText().toString().isEmpty() || addAmountPopUp.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both values", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isInteger(addAmountPopUp.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                } else {
                    volleyAddRequest();
                }
            }
        });

        returnEquipmentPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                inventoryList.clear();
                adapter.notifyDataSetChanged();
                extractUsers();
            }
        });
    }

    boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void volleyAddRequest() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // user object that we need to send
        // body of the request
        JSONObject body = new JSONObject();

        String equipmentName = addEquipmentPopUp.getText().toString();
        String equipmentAmount = addAmountPopUp.getText().toString();

        try {
            // Put user attributes in a JSONObject
            body.put("itemName", equipmentName);
            body.put("totalAmount", equipmentAmount);
            body.put("availableAmount", equipmentAmount);

            // Put user JSONObject inside of another JSONObject which will be the body of the request
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                addUrl,
                body,
                response -> {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }, error -> {
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
        );
        queue.add(jsonObjectRequest);
    }
}