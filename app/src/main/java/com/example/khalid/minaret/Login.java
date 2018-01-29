package com.example.khalid.minaret;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.khalid.minaret.utils.Utils.base_url;
import static com.example.khalid.minaret.utils.Utils.encodeString;
import static com.example.khalid.minaret.utils.Utils.get;
import static com.example.khalid.minaret.utils.Utils.isInternetAvailable;
import static com.example.khalid.minaret.utils.Utils.save;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    Button register;
    EditText username, password;
    Button login;
    ProgressBar progressBar;
    CallbackManager callbackManager;
    FirebaseAuth mAuth;
    ImageView facebook, twitter, gmail;
    TwitterAuthClient mTwitterAuthClient;
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                "uqJITUIvrkJdnKfHZW2V6OR6y",
                "s3FJUhd1LGqZMPD7GCpz2xVNZb8sUac3J2oWEr3dweRSRCiNCm");

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();
        Twitter.initialize(twitterConfig);
        mTwitterAuthClient = new TwitterAuthClient();
        mAuth = FirebaseAuth.getInstance();

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Login.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        setContentView(R.layout.activity_login);
        gmail = findViewById(R.id.gmail);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        gmail.setOnClickListener(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            }

        }
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("sad", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("sd", "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {

                            checkEmail(acct.getEmail(), acct.getDisplayName());
                        } else {
                            Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            checkEmail(user.getEmail(), user.getDisplayName());


                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void handleTwitterSession(TwitterSession session) {
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Log.w("", "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            checkEmail(task.getResult().getUser().getEmail(), task.getResult().getUser().getDisplayName());

                        }
                    }
                });
    }

    public void signIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void login(String username, String password) {
        String url = base_url + "api/user/generate_auth_cookie/?username=" + username + "&password=" + password;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("ok")) {
                                save(getApplicationContext(), "username", jsonObject.getJSONObject("user").getString("username"));
                                save(getApplicationContext(), "user_email", jsonObject.getJSONObject("user").getString("email"));
                                save(getApplicationContext(), "user_id", jsonObject.getJSONObject("user").getString("id"));
                                save(getApplicationContext(), "login", "yes");
                                save(getApplicationContext(), "cookie", jsonObject.getString("cookie"));

                                startActivity(new Intent(Login.this, MainActivity.class));
                                updateToken();
                            } else {
                                Toast.makeText(Login.this, "اسم المستخدم او رمز المرور خاطئ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "حدثت مشكلة اثناء الدخول", Toast.LENGTH_SHORT).show();

            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void updateToken() {
        String url = base_url + "update_token.php?device_token=" + FirebaseInstanceId.getInstance().getToken()
                + "&user_email_id=" + get(getApplicationContext(), "user_email") +
                "&user_id=" + get(getApplicationContext(), "user_id");
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(Login.this, Register.class));
                break;
            case R.id.login:
                if (isInternetAvailable(getApplicationContext())) {
                    if (username.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "يرجى ادخال اسم المستخدم", Toast.LENGTH_LONG).show();
                    } else if (password.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "يرجى ادخال رمز المرور ", Toast.LENGTH_LONG).show();
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        login(encodeString(username.getText().toString()), encodeString(password.getText().toString()));

                    }
                } else
                    Toast.makeText(this, "لا يوجد اتصال بالشبكة", Toast.LENGTH_SHORT).show();
                break;
            case R.id.facebook:

                if (!isInternetAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "لا يوجد اتصال بالأنترنت", Toast.LENGTH_SHORT).show();

                } else {
                    LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "user_friends", "email"));
                }
                break;
            case R.id.gmail:

                if (!isInternetAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "لا يوجد اتصال بالأنترنت", Toast.LENGTH_SHORT).show();

                } else {
                    signIn();
                }
                break;
            case R.id.twitter:

                if (!isInternetAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "لا يوجد اتصال بالأنترنت", Toast.LENGTH_SHORT).show();

                } else {
                    mTwitterAuthClient.authorize(Login.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                        @Override
                        public void success(Result<TwitterSession> twitterSessionResult) {
                            handleTwitterSession(twitterSessionResult.data);
                            Toast.makeText(Login.this, twitterSessionResult.data.getUserName(), Toast.LENGTH_SHORT).show();
                            save(getApplicationContext(), "profile_username", twitterSessionResult.data.getUserName());
                            save(getApplicationContext(), "profile_email", twitterSessionResult.data.getUserName() + "@twitter.com");
                        }

                        @Override
                        public void failure(TwitterException e) {
                            e.printStackTrace();
                        }
                    });
                }
                break;
        }

    }

    @Override
    protected void onStart() {
        if (get(getApplicationContext(), "login").equals("yes")) {
            startActivity(new Intent(Login.this, MainActivity.class));
        }
        super.onStart();
    }

    private void checkEmail(final String email, final String username) {
        String url = base_url + "check_email.php?user_email=" + email;
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("yes")) {

                                getPassword(jsonObject.getString("id"), email);
                            } else {
                                Intent intent = new Intent(Login.this, Register.class);
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                startActivity(intent);

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
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void getPassword(final String id, final String email) {
        String url = base_url + "get_password.php?user_id=" + id;
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            login(email, jsonObject.getString("password"));
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
}
