package com.example.khalid.minaret.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.minaret.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.khalid.minaret.utils.Utils.base_url;

public class PasswordCode extends AppCompatActivity {

    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_code);
        editText = findViewById(R.id.importtext);
        button = findViewById(R.id.sendemail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getIntent().getStringExtra("email");

                checkOtp(editText.getText().toString(), email);
            }
        });


    }

    public void checkOtp(String otp, final String email) {
        String url = base_url + "/checkotp.php?otp=" + otp;

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.get("result").equals("correct_number")) {
                                Intent intent = new Intent(PasswordCode.this, UpdatePassword.class);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "الرمز خاطئ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2);

    }
}
