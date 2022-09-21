package com.jdawidowska.equipmentrentalservice.adminpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.adminpackage.adapters.FeedbackResponseAdapter;
import com.jdawidowska.equipmentrentalservice.model.FeedbackResponseDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbacksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FeedbackResponseDTO> feedbackResponseDTOList;
    String url = "http://192.168.1.04:8089/api/feedback/findFeedbackResponseDTO";
    FeedbackResponseAdapter feedbackResponseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbakcs);

        Button button = findViewById(R.id.btnReturnFeedbacks);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recycleViewF);
        feedbackResponseDTOList = new ArrayList<>();
        extractData();
    }

    private void extractData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject;
                        jsonObject = response.getJSONObject(i);
                        FeedbackResponseDTO feedbackResponse = new FeedbackResponseDTO();
                        feedbackResponse.setEmail(jsonObject.getString("email"));
                        feedbackResponse.setContent(jsonObject.getString("content"));
                        feedbackResponseDTOList.add(feedbackResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                feedbackResponseAdapter = new FeedbackResponseAdapter(getApplicationContext(), feedbackResponseDTOList);
                recyclerView.setAdapter(feedbackResponseAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}