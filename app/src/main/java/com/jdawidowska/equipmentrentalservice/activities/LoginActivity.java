package com.jdawidowska.equipmentrentalservice.activities;

import static com.jdawidowska.equipmentrentalservice.activities.MainActivity.MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdawidowska.equipmentrentalservice.R;
import com.jdawidowska.equipmentrentalservice.activities.admin.AdminMenuActivity;
import com.jdawidowska.equipmentrentalservice.activities.user.UserMenuActivity;
import com.jdawidowska.equipmentrentalservice.api.ApiEndpoints;
import com.jdawidowska.equipmentrentalservice.model.Role;
import com.jdawidowska.equipmentrentalservice.util.AuthTokenHolder;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    private final String LOGIN_URL = ApiEndpoints.LOGIN.getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.eTxtLogin);
        etPassword = findViewById(R.id.eTxtPassword);

        Button btnReturn = findViewById(R.id.btnReturnLogin);
        Button btnLogin = findViewById(R.id.LoginBTN);

        btnReturn.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
        btnLogin.setOnClickListener(this::login);
    }

    private void login(View view) {
        if (invalidUsername() || invalidPassword()) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(
                Request.Method.GET,
                LOGIN_URL,
                this::handleApiSuccess,
                this::handleApiError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return Map.of(
                        "username", etUsername.getText().toString(),
                        "password", etPassword.getText().toString()
                );
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.statusCode == 200 && response.headers != null) {
                    String accessToken = response.headers.get("accessToken");
                    AuthTokenHolder.setAuthToken(accessToken);
                    return Response.success(null, null);
                } else {
                    return Response.error(new AuthFailureError(response));
                }
            }
        };
        requestQueue.add(request);
    }

    private void handleApiSuccess(String s) {
        Intent intent;
        Role role = AuthTokenHolder.getRole();

        if (role.equals(Role.USER)) {
            intent = new Intent(this, UserMenuActivity.class);
        } else {
            intent = new Intent(this, AdminMenuActivity.class);
        }

        intent.putExtra(MESSAGE, "Welcome " + AuthTokenHolder.getName());
        startActivity(intent);
    }

    private void handleApiError(VolleyError error) {
        if (error.networkResponse.statusCode == 401) {
            Toast.makeText(this, "Invalid username or password ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error fetching data from API", Toast.LENGTH_LONG).show();
        }
    }

    private boolean invalidUsername() {
        String username = etUsername.getText().toString();
        return username.isEmpty();
    }

    private boolean invalidPassword() {
        String password = etPassword.getText().toString();
        return password.isEmpty();
    }
}