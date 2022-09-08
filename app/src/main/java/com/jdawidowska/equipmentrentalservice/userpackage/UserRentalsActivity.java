package com.jdawidowska.equipmentrentalservice.userpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jdawidowska.equipmentrentalservice.R;

public class UserRentalsActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText eTxtFeedbackPopUp;
    private Button btnSubmitPopUp;
    private Button btnReturnPopUp;
    public Button btnAddFeedBack;


    Button btnReturn = findViewById(R.id.btnReturnUserRentals);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rentals);



        btnReturn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MenuUserActivity.class);
            startActivity(intent);
        });

        btnAddFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFeedbackDialog();
            }
        });


    }

    // tworzenie popup okna do oddania feedbacku
    //https://www.youtube.com/watch?v=4GYKOzgQDWI
        public void createFeedbackDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View feedbackPopUPView = getLayoutInflater().inflate(R.layout.popupfeedback,null);
        eTxtFeedbackPopUp = (EditText) feedbackPopUPView.findViewById(R.id.editTextFeedbackPopUp);
        btnSubmitPopUp = (Button) feedbackPopUPView.findViewById(R.id.btnSubmitFeedbackPopUp);
        btnReturnPopUp = (Button) feedbackPopUPView.findViewById(R.id.btnReturnFeedbackPopUp);

        dialogBuilder.setView(feedbackPopUPView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSubmitPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define save for editText
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