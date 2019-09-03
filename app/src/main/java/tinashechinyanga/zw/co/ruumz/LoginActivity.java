package tinashechinyanga.zw.co.ruumz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.facebook.ParseFacebookUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, OnClickListener, CreateParseUser {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int RC_GOOGLE_SIGN_IN = 1;
    private static final int RC_FACEBOOK_SIGN_IN = 2;
    private static final String TAG = "Login Activity";

    private GoogleSignInClient mGoogleSignInClient;

    // UI references.
    private AutoCompleteTextView mUsername;
    private EditText mPasswordView;
    private TextView forgotPasswordText;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mSignUpTextView;
    private Button facebookLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = findViewById(R.id.username);
        populateAutoComplete();

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        forgotPasswordText = findViewById(R.id.forgot_password);
        forgotPasswordText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        SignInButton googleSignInButton = findViewById(R.id.sign_in_button);
        googleSignInButton.setOnClickListener(this);

        //google sign im
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //facebook login
        facebookLoginBtn = findViewById(R.id.facebook_login_btn);
        facebookLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Collection<String> permissions = Arrays.asList("public_profile", "email");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e != null){
                            Log.e("Facebook login: ", e.getMessage());
                        }
                        if (user == null){
                            Log.i("Facebook login: ", "Login cancelled.");
                        }else if(user.isNew()){
                            Log.i("New Facebook login: ", "New user." + " " + user.getUsername());
                            getUserDetailsFromFacebook();
                        }else {
                            getUserDetailsFromParse();
                        }
                    }
                });
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //User sign up
        mSignUpTextView = findViewById(R.id.sign_up_textView);
        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void getUserDetailsFromFacebook() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                ParseUser user = ParseUser.getCurrentUser();
                try{
                    Log.i("Current User: ", "Current username " + ParseUser.getCurrentUser().getUsername());
                    Log.i("Returned name: ", "Facebook name: " + object.getString("name"));
                    user.setUsername(object.getString("name"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                try {
                    Log.i("Returned email: ", "Facebook email: " + object.getString("email"));
                    user.setEmail(object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        //Add welcome alert
                        if(e == null){
                            Log.i("Success", "Successful Parse user update");
                            finish();
                        }else {
                            Log.e("Parse update failure", "Parse error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getUserDetailsFromParse() {
        Toast.makeText(this, "Welcome, " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check for existing Google Sign In account, if user already signed in GoogleSignIn account will be non-null
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        /* TO DO: implement updateUI to handle googleSignInAccount object
            if null, account hasn't signed in show sign in button

                else, already signed in, update UI and show signed in interface */
//        if(googleSignInAccount != null){
//            updateUI(googleSignInAccount);
//        }else{
//            //initiate Google sign-in: go back to sign-in page
//            finish();
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        Log.i("Result code: ", "" + resultCode);

        //result returned from launching the Intent from GoogleSignInClient
        if(requestCode == RC_GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult();
            Log.i("Returned account: ", account.getEmail() + " " + account.getDisplayName() + " " + account.getIdToken());
            //handle sign in task
            handleSignInTask(task);
        }


    }

    private void handleSignInTask(Task<GoogleSignInAccount> completedTask) {
        showProgress(true);
        try{
           GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.i(TAG, "Handle sign in: " + account.getEmail() + " " + account.getIdToken());
            //signed in successfully,
            String idToken = account.getIdToken();

            //send token to backend server
            /*
            * postIDTokenToParseServer(idToken);
            * */

            /*
            * if there is no user in the back-end
            *   create a new user
            * else
            *   validate token
            *   get sessionId from Parse
            * steps:
            *   - send token to backend
            *   - validate token in backend
            *   - check if user is already registered in backend
            *   - send response
            * */

            //sending the token to the backend
            String backendApiUrlToGenerateSessionToken = getResources().getString(R.string.google_signIn_get_sessionToken_url);
            Log.i("URL: ", "Back-end URL: " + backendApiUrlToGenerateSessionToken);

            RequestQueue newRequestQueue = Volley.newRequestQueue(this);

            JSONObject getSessionTokenJsonRequestBody = new JSONObject();
            //back-end requires the token and Google client ID to be verified
            try {
                getSessionTokenJsonRequestBody.put("idToken", idToken);
                getSessionTokenJsonRequestBody.put("GClientId", getResources().getString(R.string.server_client_id));
                Log.i("Google Token: ", "Google token: " + idToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = getSessionTokenJsonRequestBody.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, backendApiUrlToGenerateSessionToken, getSessionTokenJsonRequestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //success callback
                            //set current user and continue
                            try {
                                ParseUser.becomeInBackground(response.getString("result"), new LogInCallback() {
                                    @Override
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null){
                                            //successfully logged in, take the user to the last page they were on
                                            Log.i("Login Success", "Successfully logged in as: " + user.getUsername());
                                            finish();
                                        }else{
                                            //error
                                            Log.e("Login error: ", "Google login error: " + e.getMessage());
                                            //show error dialog, prompt user to login again

                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                Log.e("Error", "Google login Error: " + e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //error callback
                            int  statusCode = error.networkResponse.statusCode;
                            NetworkResponse response = error.networkResponse;
                            JSONObject jsonResponse = null;
                            String actualResponse = new String(response.data);
                            try {
                                jsonResponse = new JSONObject(actualResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            showProgress(false);

                            try {
                                Log.d("Error Response req: ","Error response " + statusCode + " " + jsonResponse.getJSONObject("error").get("message")
                                        + " " + error.getMessage() + " " + response.data);
                                Toast.makeText(getApplicationContext(), "Error, " + jsonResponse.getJSONObject("error").get("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String> headers = new HashMap<>();
                    //post parameters
                    headers.put("X-Parse-Application-Id", getResources().getString(R.string.parse_app_id));
                    headers.put("X-Parse-REST-API-Key", getResources().getString(R.string.parse_rest_api_key));
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                public byte[] getBody(){
                    try {
                        String body;
                        if (requestBody == null) body = null;
                        else body = String.valueOf(requestBody.getBytes("utf-8"));
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
            newRequestQueue.add(jsonObjectRequest);

        }catch (ApiException e){
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + GoogleSignInStatusCodes.getStatusCodeString(e.getStatusCode()));
            updateUI(null);
        }

    }

    private void postIDTokenToParseServer(String idToken) {

    }

    private void updateUI(Object o) {
        finish();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        return checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password = mPasswordView.getText().toString();

        username = username.trim();
        password = password.trim();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(username)){
            mUsername.setError(getResources().getString(R.string.username_blank_error));
            focusView = mUsername;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            //attempt log in
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e != null) {
                        //error
                        Log.e("Login Error", e.getMessage());
                        showProgress(false);
                        mPasswordView.setError("Invalid username or password");
                        View error = mPasswordView;
                        error.requestFocus();
                    } else {
                        //success
                        //go back to previous activity
                        //navToMainActivity();
                        finish();
                    }
                }
            });


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void handleSuccessfullyCreateNewParseUser() {
        finish();
    }

    @Override
    public void handleErrorOnSignUp(ParseException e) {
        Toast.makeText(this, "Error, " + e.getMessage(), Toast.LENGTH_LONG).show();
        showProgress(false);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    void navToMainActivity() {
        Intent navMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        navMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        navMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(navMainActivity);
    }
}

