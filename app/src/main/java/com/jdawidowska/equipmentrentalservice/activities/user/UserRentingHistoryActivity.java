package com.jdawidowska.equipmentrentalservice.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminUserHistoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentingHistoryResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;
import com.jdawidowska.equipmentrentalservice.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Activity for User to:
 * - view history of his rented items
 */
public class UserRentingHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final ArrayList<UserRentingHistoryResponse> userRentingHistoryResponseList = new ArrayList<>();
    private final String USER_RENTING_HISTORY_URL = ApiEndpoints.USER_RENTING_HISTORY.getPath() + "/1"; //TODO passing user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_renting_history);

        recyclerView = findViewById(R.id.rvUserRentalsHistory);

        Button button = findViewById(R.id.btnReturnUserHistoryRentals);
        button.setOnClickListener(this::returnButtonAction);

        fetchUserHistory();
    }

    private void returnButtonAction(View view) {
        Intent intent = new Intent(this, UserMenuActivity.class);
        startActivity(intent);
    }

    private void fetchUserHistory() {
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

        if(userRentingHistoryResponseList.isEmpty()){
            Toast.makeText(UserRentingHistoryActivity.this, "User has not rented anything yet", Toast.LENGTH_LONG).show();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdminUserHistoryAdapter(this, userRentingHistoryResponseList));
    }
}