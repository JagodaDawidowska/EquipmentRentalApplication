package com.jdawidowska.equipmentrentalservice.activities;

import static com.jdawidowska.equipmentrentalservice.activities.MainActivity.MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText etPassword;

    private final String REGISTER_URL = ApiEndpoints.REGISTER.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.eTxtname);
        etSurname = findViewById(R.id.eTxtSurname);
        etEmail = findViewById(R.id.eTxtEmail2);
        etPassword = findViewById(R.id.eTxtPassword2);

        Button btnReturn = findViewById(R.id.btnReturnRegister);
        btnReturn.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));

        Button btnRegister = findViewById(R.id.btnRegisterUser);
        btnRegister.setOnClickListener(this::registerUser);
    }

    private void registerUser(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject body = new JSONObject();
        try {
            body.put("name", validateName());
            body.put("surname", validateSurname());
            body.put("email", validateEmail());
            body.put("password", validatePassword());
        } catch (JSONException e) {
            e.printStackTrace(); //TODO
            return;
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.POST,
                REGISTER_URL,
                body,
                this::handleApiSuccess,
                this::handleApiError
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 201) {
                    return Response.success(null, null);
                } else {
                    return Response.error(new ServerError(response));
                }
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void handleApiSuccess(JSONObject response) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MESSAGE, "Registered user successfully");
        startActivity(intent);
    }

    private void handleApiError(VolleyError error) {
        if (error.networkResponse.statusCode == 409) {
            Toast.makeText(this, "User with this email already exists ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error fetching data from API", Toast.LENGTH_LONG).show();
        }
    }

    private String validateName() throws IllegalArgumentException {
        String name = etName.getText().toString();

        if (!name.isEmpty() && name.length() > 2) {
            return name;
        } else {
            throw new IllegalArgumentException("Please input proper name");
        }
    }

    private String validateSurname() throws IllegalArgumentException {
        String surname = etSurname.getText().toString();

        if (!surname.isEmpty() && surname.length() > 2) {
            return surname;
        } else {
            throw new IllegalArgumentException("Please input proper surname");
        }
    }

    private String validateEmail() throws IllegalArgumentException {
        String email = etEmail.getText().toString();

        if (Pattern.matches("^(.+)@(\\S+)$", email)) {
            return email;
        } else {
            throw new IllegalArgumentException("Please input proper email");
        }
    }

    private String validatePassword() throws IllegalArgumentException {
        String password = etPassword.getText().toString();

        if (password.length() > 4) {
            return password;
        } else {
            throw new IllegalArgumentException("Password must be at least 5 characters long");
        }
    }
}