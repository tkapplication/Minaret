package com.example.khalid.minaret;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.khalid.minaret.utils.Utils.base_url;
import static com.example.khalid.minaret.utils.Utils.encodeString;
import static com.example.khalid.minaret.utils.Utils.isInternetAvailable;

public class Register extends AppCompatActivity {
    EditText username, email, password;
    Button register;
    ProgressBar progressBar;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            username.setText(bundle.getString("username"));
            email.setText(bundle.getString("email"));
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetAvailable(getApplicationContext())) {
                    if (username.getText().toString().equals("") || password.getText().toString().equals("") || email.getText().toString().equals("")) {
                        Toast.makeText(Register.this, "يرجى ملئ جميع البيانات", Toast.LENGTH_SHORT).show();
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        getNonce();
                    }
                } else {
                    Toast.makeText(Register.this, "لا يوجد اتصال بالشبكة", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getNonce() {
        String url = base_url + "api/get_nonce/?controller=user&method=register";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            register(jsonObject.getString("nonce"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void register(String nonce) {

        String url = base_url + "api/user/register" +
                "?insecure=cool&username=" + encodeString(username.getText().toString()) +
                "&email=" + encodeString(email.getText().toString()) +
                "&nonce=" + nonce +
                "&notify=no" +
                "&user_pass=" + encodeString(password.getText().toString()) +
                "&display_name=" + encodeString(username.getText().toString());

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                startActivity(new Intent(Register.this, Login.class));
                                insertToken(email.getText().toString(), jsonObject.getString("user_id"));
                                insertPassword(jsonObject.getString("user_id"), password.getText().toString());
                            }
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.GONE);

                NetworkResponse errorRes = error.networkResponse;
                String stringData = "";
                if (errorRes != null && errorRes.data != null) {
                    try {
                        stringData = new String(errorRes.data, "UTF-8");
                        JSONObject jsonObject = new JSONObject(stringData);
                        if (jsonObject.getString("status").equals("error")) {
                            Toast.makeText(Register.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();


                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


        }
        );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void insertToken(String email, String id) {
        String url = base_url + "insert_token.php?user_id=" + id +
                "&token=" + FirebaseInstanceId.getInstance().getToken() +
                "&email=" + email;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    private void insertPassword(String id, String password) {
        String url = base_url + "insert_password.php?user_id=" + id +
                "&password=" + password;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
