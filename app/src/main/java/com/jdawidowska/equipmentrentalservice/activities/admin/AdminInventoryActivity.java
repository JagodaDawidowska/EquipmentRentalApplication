package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminInventoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.model.Inventory;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity for Admin to:
 * - view all inventory
 * - add new items to inventory
 * - remove items from inventory
 */
public class AdminInventoryActivity extends AppCompatActivity implements AdminInventoryAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    AdminInventoryAdapter adapter;

    private final ArrayList<Inventory> inventoryList = new ArrayList<>();
    private final String INVENTORY_URL = ApiEndpoints.INVENTORY.getPath();
    private final String ADD_INVENTORY_URL = ApiEndpoints.ADD_INVENTORY.getPath();
    private String REMOVE_INVENTORY_URL = ApiEndpoints.REMOVE_INVENTORY.getPath();

    private EditText addEquipmentPopUp, addAmountPopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        recyclerView = findViewById(R.id.reycleViewInventory);

        Button btnReturn = findViewById(R.id.btnReturnInventory);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminMenuActivity.class);
            startActivity(intent);
        });

        Button btnAddEquipment = findViewById(R.id.btnInventoryAddItem);
        btnAddEquipment.setOnClickListener(view -> createAddEquipmentDialog());

        fetchInventory();
    }

    private void fetchInventory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                INVENTORY_URL,
                null,
                this::handleApiSuccess,
                error -> ApiUtils.handleApiError(error, this)
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void handleApiSuccess(JSONArray response) {
        for (int i=0; i<response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);

                Inventory inventory = new Inventory();
                inventory.setId(jsonObject.getLong("id"));
                inventory.setItemName(jsonObject.getString("itemName"));
                inventory.setAvailableAmount(jsonObject.getInt("availableAmount"));
                inventory.setTotalAmount(jsonObject.getInt("totalAmount"));

                inventoryList.add(inventory);
            } catch (JSONException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        adapter = new AdminInventoryAdapter(this, inventoryList);
        adapter.setOnItemClickListener(AdminInventoryActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    //the override method from interface
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Single Click on Image :" + position, Toast.LENGTH_SHORT).show();
    }

    public void removeInventory(int position) {
        Inventory inventory = inventoryList.get(position);
        RequestQueue queue = Volley.newRequestQueue(this);
        REMOVE_INVENTORY_URL += inventory.getId();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                REMOVE_INVENTORY_URL,
                response -> Toast.makeText(this, response, Toast.LENGTH_SHORT).show(), //TODO
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        );
        queue.add(stringRequest);
    }

    //https://www.youtube.com/watch?v=HMjI7cLsyfw
    @Override
    public void onRemoveBtnClicked(int position) {
        Inventory inventoryClicked = inventoryList.get(position);
        if (!inventoryClicked.getTotalAmount().equals(inventoryClicked.getAvailableAmount())) {
            Toast.makeText(this, "CAN'T REMOVE WHILE EQUIPMENT IS RENTED", Toast.LENGTH_SHORT).show();
        } else {
            removeInventory(position);
            //removing item from list
            inventoryList.remove(position);
            //removing row in recycleview
            adapter.notifyItemRemoved(position);
        }
    }

    public void createAddEquipmentDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View addEquipmentPopUpView = getLayoutInflater().inflate(R.layout.admin_add_equipment_popup, null);
        //editTexts
        addEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.editTextEqupmentPopUp);
        addAmountPopUp = addEquipmentPopUpView.findViewById(R.id.editTextAmountPopUp);
        //buttons
        Button btnSaveEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.btnPopup_addequipment_add);
        Button returnEquipmentPopUp = addEquipmentPopUpView.findViewById(R.id.btnReturnEquipmentkPopUp);

        dialogBuilder.setView(addEquipmentPopUpView);
        AlertDialog dialog = dialogBuilder.create();
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
                fetchInventory();
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
            e.printStackTrace(); //TODO
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                ADD_INVENTORY_URL,
                body,
                response -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show(); //TODO
                }, error -> {
                    Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                }
        );
        queue.add(jsonObjectRequest);
    }
}