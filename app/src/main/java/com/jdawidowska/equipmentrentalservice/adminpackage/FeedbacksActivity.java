package com.jdawidowska.equipmentrentalservice.adminpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.model.Feedback;

import java.util.List;

public class FeedbacksActivity extends AppCompatActivity {

    private TableLayout table;
    String url = "http://192.168.1.4:8089/api/feedback";
    private final int COLUMN_WIDTH = 170;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbakcs);

        Button button = findViewById(R.id.btnReturnFeedbacks);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuAdminActivity.class);
            startActivity(intent);
        });

        table = findViewById(R.id.tableLayoutFeedbacks);
        initTable();
    }


    public void initTable() {
        //czyszczenie tabeli przed jej wypelnieniem


        //wykonanie zapytania do backendu
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = ApiEndpoints.FIND_ALL_EQUIPMENT.toString();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    //parsowanie odpowiedz na liste sprzetu
                    List<Feedback> feedbackList = new Gson().fromJson(response, new TypeToken<List<Feedback>>(){}.getType());

                    //przygotowanie naglowka tabeli
                  //  createTableHeader();

                    //dla kazdego sprzetu w liscie tworzymy jego rzad w tabeli
                    for(Feedback feedback : feedbackList){
                        feedbackTableRow(feedback);
                    }
                },
                error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        );

        queue.add(request);
    }

    private void feedbackTableRow(Feedback feedback) {
        TableRow row = new TableRow(this);

        TextView id = new TextView(this);
        id.setText(feedback.getId().toString());
        id.setGravity(Gravity.LEFT);
        id.setMinWidth(COLUMN_WIDTH);
        id.setWidth(COLUMN_WIDTH);
        id.setMaxWidth(COLUMN_WIDTH);
        row.addView(id);

        TextView idUser = new TextView(this);
        idUser.setText(feedback.getIdUser().toString());
        idUser.setGravity(Gravity.CENTER);
        idUser.setMinWidth(COLUMN_WIDTH);
        idUser.setWidth(COLUMN_WIDTH);
        idUser.setMaxWidth(COLUMN_WIDTH);
        row.addView(idUser);

        TextView content = new TextView(this);
        content.setText(feedback.getContent());
        content.setGravity(Gravity.CENTER);
        content.setMinWidth(COLUMN_WIDTH);
        content.setWidth(COLUMN_WIDTH);
        content.setMaxWidth(COLUMN_WIDTH);
        row.addView(content);

        table.addView(row);
    }

    private void createTableHeader() {
        TableRow headerRow = new TableRow(this);

        TextView id = new TextView(this);
        id.setText("Id");
        id.setGravity(Gravity.LEFT);
        id.setMinWidth(50);
        id.setWidth(50);
        id.setMaxWidth(50);
        headerRow.addView(id);

        TextView idUser = new TextView(this);
        idUser.setText("User id");
        idUser.setGravity(Gravity.CENTER);
        idUser.setMinWidth(COLUMN_WIDTH);
        idUser.setWidth(COLUMN_WIDTH);
        idUser.setMaxWidth(COLUMN_WIDTH);
        headerRow.addView(idUser);

        TextView feedback = new TextView(this);
        feedback.setText("Feedbacks");
        feedback.setGravity(Gravity.CENTER);
        feedback.setMinWidth(COLUMN_WIDTH);
        feedback.setWidth(COLUMN_WIDTH);
        feedback.setMaxWidth(COLUMN_WIDTH);
        headerRow.addView(feedback);

        table.addView(headerRow);
    }
}