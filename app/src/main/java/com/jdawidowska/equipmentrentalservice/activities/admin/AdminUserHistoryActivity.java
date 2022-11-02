package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminUserHistoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentingHistoryResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;
import com.jdawidowska.equipmentrentalservice.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Admin to:
 * - view user's renting history
 */
public class AdminUserHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminUserHistoryAdapter adminUserHistoryAdapter;

    private String USER_RENTING_HISTORY_URL = ApiEndpoints.USER_RENTING_HISTORY.getPath();
    private final List<UserRentingHistoryResponse> userRentingHistoryResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_history);

        String userId = getIntent().getStringExtra("EXTRA_USER_ID");
        if (userId == null || userId.isEmpty()) {
            Toast.makeText(this, "Id User not chosen \nReturn and pass user Id", Toast.LENGTH_LONG).show();
        } else {
            USER_RENTING_HISTORY_URL += userId;
        }

        recyclerView = findViewById(R.id.recycleViewUserHistoryID);

        Button btnReturn = findViewById(R.id.btnReturnUserHistoryRentals2);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminUsersActivity.class);
            startActivity(intent);
        });

        extractUsers();
    }

    private void extractUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                USER_RENTING_HISTORY_URL,
                null,
                this::handleApiSuccess,
                error -> ApiUtils.handleApiError(error, this)
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void handleApiSuccess(JSONArray response) {
        for (int i=0; i<response.length(); i++) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                JSONObject jsonObject = response.getJSONObject(i);

                UserRentingHistoryResponse userRentingHistoryResponse = new UserRentingHistoryResponse();
                userRentingHistoryResponse.setEmail(jsonObject.getString("email"));
                userRentingHistoryResponse.setItemName(jsonObject.getString("itemName"));
                userRentingHistoryResponse.setRentDate(format.parse(jsonObject.getString("rentDate")));

                String returnDate = jsonObject.getString("returnDate");
                if (DateUtils.isValidReturnDate(returnDate)) {
                    userRentingHistoryResponse.setReturnDate(format.parse(returnDate));
                } else {
                    userRentingHistoryResponse.setReturnDate(null);
                }

                userRentingHistoryResponseList.add(userRentingHistoryResponse);
            } catch (JSONException | ParseException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (userRentingHistoryResponseList.isEmpty()) {
            Toast.makeText(AdminUserHistoryActivity.this, "User with this Id has not rent anything yet", Toast.LENGTH_LONG).show();
        }
        adminUserHistoryAdapter = new AdminUserHistoryAdapter(this, userRentingHistoryResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adminUserHistoryAdapter);
    }
}