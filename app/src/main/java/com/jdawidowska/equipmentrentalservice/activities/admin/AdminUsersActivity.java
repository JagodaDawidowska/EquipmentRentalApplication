package com.jdawidowska.equipmentrentalservice.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminUsersAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.UserResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Admin to:
 * - view Users and navigate to their rent history
 */
public class AdminUsersActivity extends AppCompatActivity implements AdminUsersAdapter.OnItemClickListener, AdminUsersAdapter.OnItemTouchListener{

    private RecyclerView recyclerView;
    private AdminUsersAdapter adminUsersAdapter;
    private final List<UserResponse> userResponseList = new ArrayList<>();
    private final String USERS_URL = ApiEndpoints.USERS.getPath();
    private String getIDUser;

    //https://www.youtube.com/watch?v=e3MDW87mbR8 baza
    //https://www.youtube.com/watch?v=__OMnFR-wZU //do obejrzenia
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        recyclerView = findViewById(R.id.recycleViewUsers);

        EditText etUserID = findViewById(R.id.eTxtAdminUsersSearch);

        Button btnReturn = findViewById(R.id.btnReturnUsers);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AdminMenuActivity.class);
            startActivity(intent);
        });

        Button btnSearch = findViewById(R.id.btnAdminUsersSearch);
        btnSearch.setOnClickListener(view -> {
            getIDUser = etUserID.getText().toString();
            Intent intent = new Intent(this, AdminUserHistoryActivity.class);
            intent.putExtra("EXTRA_USER_ID", getIDUser);
            startActivity(intent);
        });

        fetchUsers();
    }

    private void fetchUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                USERS_URL,
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

                UserResponse userResponse = new UserResponse();
                userResponse.setId(jsonObject.getLong("id"));
                userResponse.setName(jsonObject.getString("name"));
                userResponse.setSurname(jsonObject.getString("surname"));

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

    @Override
    public void onIdClicked(int position) {
        //tworze obiekt o danym id z ktorego  pozniej wyciagam dane
        UserResponse userClicked = userResponseList.get(position);
        getIDUser = userClicked.getId().toString();
        Intent intent = new Intent(this, AdminUserHistoryActivity.class);
        intent.putExtra("EXTRA_USER_ID", getIDUser);
        startActivity(intent);
    }


    @Override
    public void onIdTouch(int position) {
        System.out.println("touch"); //todo ???
    }
}