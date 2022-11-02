package com.jdawidowska.equipmentrentalservice.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminRentedInventoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.RentedInventoryResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Admin to:
 * - view all currently rented inventory
 */
public class AdminRentedInventoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminRentedInventoryAdapter adapter;
    private final List<RentedInventoryResponse> rentedInventoryResponseList = new ArrayList<>();
    private final String ALL_CURRENTLY_RENTED_INVENTORY_URL = ApiEndpoints.ALL_CURRENTLY_RENTED_INVENTORY.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rented_inventory);

        recyclerView = findViewById(R.id.recycleViewRentedInventory);

        Button btnReturn = findViewById(R.id.btnReturnRentals);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
            startActivity(intent);
        });

        fetchRentedInventory();
    }

    private void fetchRentedInventory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                ALL_CURRENTLY_RENTED_INVENTORY_URL,
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

                RentedInventoryResponse riResponse = new RentedInventoryResponse();
                riResponse.setName(jsonObject.getString("name"));
                riResponse.setSurname(jsonObject.getString("surname"));
                riResponse.setEquipment(jsonObject.getString("equipment"));
                riResponse.setAmount(jsonObject.getInt("amount"));

                rentedInventoryResponseList.add(riResponse);
            } catch (JSONException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        adapter = new AdminRentedInventoryAdapter(this, rentedInventoryResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}