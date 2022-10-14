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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.userpackage.adapters.ReturnUserAdapter;
import com.jdawidowska.equipmentrentalservice.model.ReturnUserResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserRentalsActivity extends AppCompatActivity implements ReturnUserAdapter.OnItemClickListener{

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText eTxtFeedbackPopUp;
    private Button btnSubmitPopUp;
    private Button btnReturnPopUp ;
    ReturnUserAdapter adapter;
    RecyclerView recyclerView;
    String maxIp="192.168.0.35";
    String JaIp="192.168.1.04";

    private final String extractUrl="http://"+maxIp+":8089/api/rentedInventory/user/1";

    private final String returnUrl="http://"+maxIp+":8089/api/renting/return";
    ArrayList <ReturnUserResponse> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rent5);

        Button btnAddFeedBack = findViewById(R.id.btnAddFeedback);
        //btnAddFeedBack.setOnClickListener(view -> createFeedbackDialog());

        Button btnReturn = findViewById(R.id.btnReturnUserRentals);
        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerViewUserRentals);
        list = new ArrayList<>();
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
                        ReturnUserResponse returnUserResponse =new ReturnUserResponse();
                        returnUserResponse.setId(jsonObject.getLong("id"));
                        returnUserResponse.setName(jsonObject.getString("name"));
                        returnUserResponse.setAmount(jsonObject.getInt("amount"));
                        list.add(returnUserResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new ReturnUserAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(UserRentalsActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    public void createFeedbackDialog(int position){
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

                returnEquipment(position, message);
            }
        });

        btnReturnPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onReturnButton(int position) {
       //? Toast.makeText(getApplicationContext(),"SD", Toast.LENGTH_SHORT);
        System.out.println("sdsdsdsdsdsdsdsdsd");
        createFeedbackDialog(position);
    }

    private void returnEquipment(int position,String message) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        //do wyciagniecia idrentedinventory
        ReturnUserResponse returnUserResponseClicked = list.get(position);
        Long rentedInventoryIdValue = returnUserResponseClicked.getId();

            JSONObject body = new JSONObject();

            try {
                body.put("idRentedInventory",rentedInventoryIdValue);
                body.put("feedback",message);
                // Put user JSONObject inside of another JSONObject which will be the body of the request
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    returnUrl,
                    body,
                    response -> {   //oddaje response w json:(
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }, error -> {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
            );
            queue.add(jsonObjectRequest);
       // } 192.168.0.35
    }
}