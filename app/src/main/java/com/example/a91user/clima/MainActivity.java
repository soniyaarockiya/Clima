package com.example.a91user.clima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView temp, city, date, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        city = findViewById(R.id.city);
        date = findViewById(R.id.date);
        description = findViewById(R.id.description);

        weather();
    }

    public void weather() {


        String url = "http://api.openweathermap.org/data/2.5/weather?q=Mumbai&appid=b4a34514f7c95cb3ff065c26125d27c6&units=metric";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj1 = response.getJSONObject("main"); // get main object since it has temp in it
                    String temp1 = String.valueOf(obj1.getDouble("temp"));// assign temp value from main to a string

                    JSONArray weatherArray = response.getJSONArray("weather");// Now weather has two values/ objects within it so its a array
                    JSONObject obj2 = weatherArray.getJSONObject(0);// assign array of objects starting from first object to obj2

                    String description1 = obj2.getString("description");
                    String city1 = response.getString("name");


                    temp.setText(temp1); // assign values to text views
                    city.setText(city1);
                    description.setText(description1);

                    Calendar calender = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = sdf.format(calender.getTime());

                    date.setText(formattedDate);


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);
    }
}
