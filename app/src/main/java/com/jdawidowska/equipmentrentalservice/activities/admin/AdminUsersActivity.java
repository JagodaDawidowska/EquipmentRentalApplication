package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.LoginActivity;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminUsersAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;
import com.jdawidowska.equipmentrentalservice.util.AuthTokenHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Activity for Admin to:
 * - view Users and navigate to their rent history
 */
public class AdminUsersActivity extends AppCompatActivity implements AdminUsersAdapter.OnItemClickListener, AdminUsersAdapter.OnItemTouchListener{

    private RecyclerView recyclerView;
    private AdminUsersAdapter adminUsersAdapter;
    private final List<UserResponse> userResponseList = new ArrayList<>();
    private final String USERS_URL = ApiEndpoints.USERS.getPath();
    private String searchUserId;

    //https://www.youtube.com/watch?v=e3MDW87mbR8 baza
    //https://www.youtube.com/watch?v=__OMnFR-wZU //do obejrzenia
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        recyclerView = findViewById(R.id.recycleViewUsers);

        EditText etSearchUser = findViewById(R.id.etSearchUser);

        Button btnReturn = findViewById(R.id.btnReturnUsers);
        btnReturn.setOnClickListener(view -> startActivity(new Intent(this, AdminMenuActivity.class)));

        Button btnSearch = findViewById(R.id.btnAdminUsersSearch);
        btnSearch.setOnClickListener(view -> {
            if(isInteger(etSearchUser.getText().toString())) {
                searchUserId = etSearchUser.getText().toString();
                Intent intent = new Intent(this, AdminUserHistoryActivity.class);
                intent.putExtra("EXTRA_USER_ID", searchUserId);
                startActivity(intent);
            }
        });

        fetchUsers();
    }

    private boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void fetchUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                USERS_URL,
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

                UserResponse userResponse = new UserResponse();
                userResponse.setId(jsonObject.getLong("id"));
                userResponse.setName(jsonObject.getString("name"));
                userResponse.setSurname(jsonObject.getString("surname"));
                userResponse.setAddress(jsonObject.getString("address"));

                userResponseList.add(userResponse);
            } catch (JSONException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        adminUsersAdapter = new AdminUsersAdapter(this, userResponseList);
        adminUsersAdapter.setOnItemClickListener(this::onIdClicked);
        adminUsersAdapter.setOnItemTouchListener(this::onIdTouch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adminUsersAdapter);
    }

    private void handleApiError(VolleyError error) {
        if (error instanceof AuthFailureError) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "api error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIdClicked(int position) {
        //tworze obiekt o danym id z ktorego  pozniej wyciagam dane
        UserResponse userClicked = userResponseList.get(position);
        searchUserId = userClicked.getId().toString();
        Intent intent = new Intent(this, AdminUserHistoryActivity.class);
        intent.putExtra("EXTRA_USER_ID", searchUserId);
        startActivity(intent);
    }


    @Override
    public void onIdTouch(int position) {
        System.out.println("touch"); //todo ???
    }
}