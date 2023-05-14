package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.LoginActivity;
import com.jdawidowska.equipmentrentalservice.activities.common.adapters.HistoryAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserRentingHistoryResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;
import com.jdawidowska.equipmentrentalservice.util.AuthTokenHolder;
import com.jdawidowska.equipmentrentalservice.util.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity for Admin to:
 * - view user's renting history
 */
public class AdminUserHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

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

        fetchUserHistory();
    }

    private void fetchUserHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                USER_RENTING_HISTORY_URL,
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

                UserRentingHistoryResponse userRentingHistoryResponse = new UserRentingHistoryResponse();
                userRentingHistoryResponse.setEmail(jsonObject.getString("email"));
                userRentingHistoryResponse.setItemName(jsonObject.getString("itemName"));
                userRentingHistoryResponse.setRentDate(Date.valueOf(jsonObject.getString("rentDate")));

                String returnDate = jsonObject.getString("returnDate");
                if (DateUtils.isValidReturnDate(returnDate)) {
                    userRentingHistoryResponse.setReturnDate(Date.valueOf(returnDate));
                } else {
                    userRentingHistoryResponse.setReturnDate(null);
                }

                userRentingHistoryResponseList.add(userRentingHistoryResponse);
            } catch (JSONException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        if (userRentingHistoryResponseList.isEmpty()) {
            Toast.makeText(AdminUserHistoryActivity.this, "User with this Id has not rent anything yet", Toast.LENGTH_LONG).show();
        }
        historyAdapter = new HistoryAdapter(this, userRentingHistoryResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);
    }

    private void handleApiError(VolleyError error) {
        if (error instanceof AuthFailureError) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "api error", Toast.LENGTH_SHORT).show();
        }
    }
}