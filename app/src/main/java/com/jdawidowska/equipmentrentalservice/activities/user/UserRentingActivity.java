package com.jdawidowska.equipmentrentalservice.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.model.Inventory;
import com.jdawidowska.equipmentrentalservice.activities.user.adapters.UserRentingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRentingActivity extends AppCompatActivity implements UserRentingAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserRentingAdapter adapter;
    private final List<Inventory> inventoryList = new ArrayList<>();

    private final String INVENTORY_URL = ApiEndpoints.INVENTORY.getPath();
    private final String RENT_ITEM_URL = ApiEndpoints.RENT_ITEM.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_renting);

        recyclerView = findViewById(R.id.recycleViewRentEquipment);

        Button btnReturn = findViewById(R.id.btnReturnRentEquipment);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserMenuActivity.class);
            startActivity(intent);
        });

        fetchInventory();
    }

    private void fetchInventory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                INVENTORY_URL,
                null,
                this::handleApiSuccess,
                this::handleApiError
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void handleApiSuccess(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);

                Inventory inventory = new Inventory();
                inventory.setId(jsonObject.getLong("id"));
                inventory.setItemName(jsonObject.getString("itemName"));
                inventory.setAvailableAmount(jsonObject.getInt("availableAmount"));
                inventory.setTotalAmount(jsonObject.getInt("totalAmount"));
                if (inventory.getAvailableAmount() > 0) {
                    inventoryList.add(inventory);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new UserRentingAdapter(this, inventoryList);
        adapter.setOnItemClickListener(UserRentingActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void handleApiError(VolleyError error) {
        Toast.makeText(this, "Error fetching data from API " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void rentEquipment(int position) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Inventory inventoryClicked = inventoryList.get(position);

        if (inventoryClicked.getAvailableAmount() == 0) {
            Toast.makeText(this, "This equipment is unavailable", Toast.LENGTH_SHORT).show();
            inventoryList.remove(position);
            adapter.notifyItemRemoved(position);
        } else {
            JSONObject body = new JSONObject();
            try {
                body.put("idUser", 1); //TODO id user value must be passed form somewhere
                body.put("idItem", inventoryClicked.getId());
                // Put user JSONObject inside of another JSONObject which will be the body of the request
            } catch (JSONException e) {
                e.printStackTrace(); //TODO
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    RENT_ITEM_URL,
                    body,
                    response -> {   //oddaje response w json:(
                        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                    }, error -> {
                        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
            );
            queue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onRentBtnClicked(int position) {
        rentEquipment(position);
    }
}