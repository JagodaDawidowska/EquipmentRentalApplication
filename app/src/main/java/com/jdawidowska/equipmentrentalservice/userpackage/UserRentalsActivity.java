package com.jdawidowska.equipmentrentalservice.userpackage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jdawidowska.equipmentrentalservice.R;

public class UserRentalsActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText eTxtFeedbackPopUp;
    private Button btnSubmitPopUp;
    private Button btnReturnPopUp ;

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