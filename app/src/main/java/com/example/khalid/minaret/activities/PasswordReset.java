package com.example.khalid.minaret.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class PasswordReset extends AppCompatActivity {
    Typeface typeface;
    TextView textView;
    Button button;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        textView = findViewById(R.id.importemail);
        editText = findViewById(R.id.importtext);
        button = findViewById(R.id.sendemail);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(editText.getText().toString());

            }
        });
    }

    public void sendEmail(final String email) {
        String url = base_url + "/generateotp.php?submit_email=" + email;

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.get("result").equals("mobile")) {
                                Intent intent = new Intent(PasswordReset.this, PasswordCode.class);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            } else if (jsonObject.get("result").equals("email")) {
                                Toast.makeText(getApplicationContext(), "تم ارسال الرمز الى البريد الالكتروني", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(PasswordReset.this, PasswordCode.class);
                                intent.putExtra("email", email);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "البريد الالكتروني خاطئ", Toast.LENGTH_LONG).show();

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
