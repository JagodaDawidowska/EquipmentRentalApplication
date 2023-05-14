package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.LoginActivity;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminInventoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.model.Inventory;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;
import com.jdawidowska.equipmentrentalservice.util.AuthTokenHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Activity for Admin to:
 * - view all inventory
 * - add new items to inventory
 * - remove items from inventory
 */
public class AdminInventoryActivity extends AppCompatActivity implements AdminInventoryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private AdminInventoryAdapter adapter;
    private final ArrayList<Inventory> inventoryList = new ArrayList<>();
    private final String INVENTORY_URL = ApiEndpoints.INVENTORY.getPath();
    private final String ADD_INVENTORY_URL = ApiEndpoints.ADD_INVENTORY.getPath();
    private String REMOVE_INVENTORY_URL = ApiEndpoints.REMOVE_INVENTORY.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_inventory);

        recyclerView = findViewById(R.id.recycleViewInventory);

        Button btnReturn = findViewById(R.id.btnReturnInventory);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminMenuActivity.class);
            startActivity(intent);
        });

        Button btnAddEquipment = findViewById(R.id.btnInventoryAddItem);
        btnAddEquipment.setOnClickListener(view -> createAddEquipmentDialog());

        fetchInventory();
    }

    private void handleApiError(VolleyError error) {
        if (error instanceof AuthFailureError) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "api error", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchInventory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                INVENTORY_URL,
                null,
                this::handleApiSuccess,
                this::handleApiError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return Map.of("Authorization", "Bearer " + AuthTokenHolder.getAuthToken());
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 403) {
                    return Response.error(new AuthFailureError());
                }

                if (response.statusCode == 200) {
                    try {
                        String jsonString = new String(
                                response.data,
                                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET)
                        );
                        return Response.success(
                                new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException | JSONException e) {
                        return Response.error(new ParseError(e));
                    }
                }
                return Response.error(new ServerError(response));
            }
        };
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
                response -> Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show(),
                this::handleApiError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return Map.of("Authorization", "Bearer " + AuthTokenHolder.getAuthToken());
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                switch(response.statusCode) {
                    case 403:
                        return Response.error(new AuthFailureError());
                    case 404:
                        return Response.error(new ServerError(response));
                    default:
                        return Response.success(null, null);
                }
            }
        };
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

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void createAddEquipmentDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View addEquipmentPopUpView = getLayoutInflater().inflate(R.layout.admin_add_equipment_popup, null);
        //editTexts
        EditText etItemName = addEquipmentPopUpView.findViewById(R.id.add_equipment_popup_item_name);
        EditText etItemAmount = addEquipmentPopUpView.findViewById(R.id.add_equipment_popup_item_amount);
        //buttons
        Button btnAddItem = addEquipmentPopUpView.findViewById(R.id.add_equipment_popup_add_item_button);
        Button btnReturn = addEquipmentPopUpView.findViewById(R.id.btnReturnRentPopup);

        dialogBuilder.setView(addEquipmentPopUpView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etItemName.getText().toString();
                String itemAmount = etItemAmount.getText().toString();

                if (itemName.isEmpty() || itemAmount.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both values", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isInteger(itemAmount)) {
                    Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                } else {
                    addNewEquipmentItem(itemName, itemAmount, dialog);
                }
            }
        });

        btnReturn.setOnClickListener(view -> dialog.dismiss());
    }

    private void addNewEquipmentItem(String itemName, String itemAmount, Dialog addItemDialog) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JSONObject body = new JSONObject();
        try {
            body.put("itemName", itemName);
            body.put("totalAmount", itemAmount);
            body.put("availableAmount", itemAmount);
        } catch (JSONException e) {
            e.printStackTrace(); //TODO
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                ADD_INVENTORY_URL,
                body,
                response -> {
                    adapter.notifyDataSetChanged();
                    inventoryList.clear();
                    fetchInventory();

                    addItemDialog.dismiss();
                    Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                },
                this::handleApiError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return Map.of("Authorization", "Bearer " + AuthTokenHolder.getAuthToken());
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 403) {
                    return Response.error(new AuthFailureError());
                }
                return Response.success(null, null);
            }
        };
        queue.add(jsonObjectRequest);
    }
}