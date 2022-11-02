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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.adapters.AdminFeedbacksAdapter;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.api.dto.response.FeedbackResponse;
import com.jdawidowska.equipmentrentalservice.util.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Admin to:
 * - view feedbacks from Users
 */
public class AdminFeedbacksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminFeedbacksAdapter adminFeedbacksAdapter;

    private final List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
    private final String FEEDBACK_URL = ApiEndpoints.FEEDBACK.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedbacks);

        recyclerView = findViewById(R.id.recycleView);

        Button btnReturn = findViewById(R.id.btnReturnFeedbacks);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
            startActivity(intent);
        });

        fetchFeedbacks();
    }

    private void fetchFeedbacks() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                FEEDBACK_URL,
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

                FeedbackResponse feedbackResponse = new FeedbackResponse();
                feedbackResponse.setEmail(jsonObject.getString("email"));
                feedbackResponse.setContent(jsonObject.getString("content"));

                feedbackResponseList.add(feedbackResponse);
            } catch (JSONException error) {
                Toast.makeText(this, ApiUtils.API_ERROR + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        adminFeedbacksAdapter = new AdminFeedbacksAdapter(this, feedbackResponseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adminFeedbacksAdapter);
    }
}