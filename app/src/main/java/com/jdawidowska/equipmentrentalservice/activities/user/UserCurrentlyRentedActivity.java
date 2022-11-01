package com.jdawidowska.equipmentrentalservice.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.user.adapters.UserCurrentlyRentedAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentedInventoryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserCurrentlyRentedActivity extends AppCompatActivity implements UserCurrentlyRentedAdapter.OnItemClickListener {

    private UserCurrentlyRentedAdapter adapter;
    private RecyclerView recyclerView;
    private final List<UserRentedInventoryResponse> userRentedInventoryList = new ArrayList<>();
    private final String USER_RENTED_INVENTORY_URL = ApiEndpoints.USER_CURRENTLY_RENTED_INVENTORY.getPath() + "/1"; //TODO pass user ID
    private final String RETURN_ITEM_URL = ApiEndpoints.RETURN_ITEM.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_currently_rented);

        recyclerView = findViewById(R.id.recyclerViewUserRentals);

        Button btnReturn = findViewById(R.id.btnReturnUserRentals);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserMenuActivity.class);
            startActivity(intent);
        });

        fetchCurrentlyRentedInventory();
    }

    private void fetchCurrentlyRentedInventory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                USER_RENTED_INVENTORY_URL,
                null,
                this::handleApiSuccess,
                this::handleApiError);
        requestQueue.add(jsonArrayRequest);
    }

    private void handleApiSuccess(JSONArray response) {
        for (int i=0; i<response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);

                UserRentedInventoryResponse userRentedInventoryResponse = new UserRentedInventoryResponse();
                userRentedInventoryResponse.setId(jsonObject.getLong("id"));
                userRentedInventoryResponse.setName(jsonObject.getString("name"));
                userRentedInventoryResponse.setAmount(jsonObject.getInt("amount"));

                userRentedInventoryList.add(userRentedInventoryResponse);
            } catch (JSONException error) {
                Toast.makeText(this, "Error fetching data from API " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        adapter = new UserCurrentlyRentedAdapter(this, userRentedInventoryList);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void handleApiError(VolleyError error) {
        Toast.makeText(this, "Error fetching data from API " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void createFeedbackDialog(int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View feedbackPopUPView = getLayoutInflater().inflate(R.layout.popupfeedback, null);
        EditText eTxtFeedbackPopUp = feedbackPopUPView.findViewById(R.id.editTextAmountPopUp);
        Button btnSubmitPopUp = feedbackPopUPView.findViewById(R.id.btnSubmitFeedbackPopUp);
        Button btnReturnPopUp = feedbackPopUPView.findViewById(R.id.btnReturnEquipmentkPopUp);

        dialogBuilder.setView(feedbackPopUPView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        btnSubmitPopUp.setOnClickListener(view -> {
            String message = String.valueOf(eTxtFeedbackPopUp.getText());

            returnEquipment(position, message);
            UserRentedInventoryResponse responseClicked = userRentedInventoryList.get(position);
            if (responseClicked.getAmount() == 1) {
                userRentedInventoryList.remove(position);
                adapter.notifyItemRemoved(position);
            }
            adapter.notifyDataSetChanged();
            userRentedInventoryList.clear();
            fetchCurrentlyRentedInventory();
        });

        btnReturnPopUp.setOnClickListener(view -> dialog.dismiss());
    }

    @Override
    public void onReturnButton(int position) {
        createFeedbackDialog(position);
    }

    private void returnEquipment(int position, String message) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        //do wyciagniecia idrentedinventory
        UserRentedInventoryResponse userRentedInventoryResponseClicked = userRentedInventoryList.get(position);
        Long rentedInventoryIdValue = userRentedInventoryResponseClicked.getId();

        JSONObject body = new JSONObject();

        try {
            body.put("idRentedInventory", rentedInventoryIdValue);
            body.put("feedback", message);
            // Put user JSONObject inside of another JSONObject which will be the body of the request
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                RETURN_ITEM_URL,
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