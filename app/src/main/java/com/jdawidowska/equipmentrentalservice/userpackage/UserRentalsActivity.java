package com.jdawidowska.equipmentrentalservice.userpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.RentEquipmentActivity;
import com.jdawidowska.equipmentrentalservice.RentEquipmentAdapter;
import com.jdawidowska.equipmentrentalservice.ReturnUserAdapter;
import com.jdawidowska.equipmentrentalservice.model.Inventory;
import com.jdawidowska.equipmentrentalservice.model.RentedInventoryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserRentalsActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText eTxtFeedbackPopUp;
    private Button btnSubmitPopUp;
    private Button btnReturnPopUp ;
    ReturnUserAdapter adapter;
    RecyclerView recyclerView;

    private final String extractUrl="http://192.168.1.04:8089/api/rentedInventory/rentedInventoryResponse";
    private final String returnUrl="";
    ArrayList <RentedInventoryResponse> rentedInventoryResponseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rent5);

        Button btnAddFeedBack = findViewById(R.id.btnAddFeedback);
        btnAddFeedBack.setOnClickListener(view -> createFeedbackDialog());

        Button btnReturn = findViewById(R.id.btnReturnUserRentals);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerViewUserRentals);
        rentedInventoryResponseList = new ArrayList<>();
        extractEquipment();
    }

    private void extractEquipment() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, extractUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject;
                        jsonObject = response.getJSONObject(i);
                        RentedInventoryResponse rentedInventoryResponse =new RentedInventoryResponse();
                        rentedInventoryResponse.setName(jsonObject.getString("name"));
                        rentedInventoryResponse.setSurname(jsonObject.getString("surname"));
                        rentedInventoryResponse.setEquipment(jsonObject.getString("equipment"));
                        rentedInventoryResponse.setAmount(jsonObject.getInt("amount"));
                        rentedInventoryResponseList.add(rentedInventoryResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new ReturnUserAdapter(getApplicationContext(), rentedInventoryResponseList);
                recyclerView.setAdapter(adapter);
              // adapter.setOnItemClickListener(RentEquipmentActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    public void createFeedbackDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View feedbackPopUPView = getLayoutInflater().inflate(R.layout.popupfeedback, null);
        eTxtFeedbackPopUp = (EditText) feedbackPopUPView.findViewById(R.id.editTextAmountPopUp);
        btnSubmitPopUp = (Button) feedbackPopUPView.findViewById(R.id.btnSubmitFeedbackPopUp);
        btnReturnPopUp = (Button) feedbackPopUPView.findViewById(R.id.btnReturnEquipmentkPopUp);

        dialogBuilder.setView(feedbackPopUPView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSubmitPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = String.valueOf(eTxtFeedbackPopUp.getText());
                Toast.makeText(UserRentalsActivity.this, message , Toast.LENGTH_SHORT).show();
            }
        });

        btnReturnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}