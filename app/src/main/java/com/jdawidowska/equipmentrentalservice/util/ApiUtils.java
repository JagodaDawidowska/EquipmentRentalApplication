package com.jdawidowska.equipmentrentalservice.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class ApiUtils {

    public static final String API_ERROR = "Error parsing response from API ";

    public static void handleApiError(VolleyError error, Context context) {
        Toast.makeText(context, "Error fetching data from API " + error.getMessage(), Toast.LENGTH_LONG).show();
    }


}
