package com.mobproto.keenan.kuky.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.keenan.kuky.R;
import com.mobproto.keenan.kuky.api.ApiClient;
import com.mobproto.keenan.kuky.models.UserApiKeyResponse;
import com.mobproto.keenan.kuky.models.UserRequest;
import com.mobproto.keenan.kuky.validators.PasswordValidator;
import com.mobproto.keenan.kuky.validators.UsernameValidator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Login Activity is the first view when the app is opened. It has two text fields, a username and
 * a password. It handles the authentication of the existing user, or the creation of a new user. Once
 * a valid user exits, then it goes to the Ku View activity to enter the app
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PREFS_NAME = "UserData";

    private UsernameValidator usernameValidator = new UsernameValidator();
    private PasswordValidator passwordValidator = new PasswordValidator();

    @Bind(R.id.login_username) EditText username;
    @Bind(R.id.login_password) EditText password;
    @Bind(R.id.login_action) Button loginAction;
    @Bind(R.id.login_error) TextView loginError;

    /**
     * Button handle when the Login button is clicked, makes and API call to check authentication
     * @param view the current screen view
     */
    @OnClick(R.id.login_action)
    public void onLoginClicked(View view) {
        String uname = username.getText().toString();
        String pw = password.getText().toString();
        // Makes call to attempt to log in with info provided
        try {
            usernameValidator.validate(uname);
            passwordValidator.validate(pw);
            ApiClient.getKukyApiClient().login(new UserRequest(uname, pw))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserApiKeyResponse>() {
                        @Override
                        public void onCompleted() {
                        }
                        
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG + ": Retrofit Error - ", e.toString());
                    }

                        @Override
                        public void onNext(UserApiKeyResponse apiKeyResponse) {
                            String errorMessage = apiKeyResponse.getErrorMessage();
                            if (errorMessage != null) {
                                // If error exists, displays it
                                handleApiError(errorMessage);
                            } else {
                                changeActivity(username.getText().toString(), apiKeyResponse.getNewKey(), apiKeyResponse.getUserId());
                            }
                        }
                    });
        } catch (UsernameValidator.UsernameException e) {
            loginError.setText(e.getMessage());
        } catch (PasswordValidator.PasswordException e) {
            loginError.setText(e.getMessage());
        }
    }

    /**
     * Button handle when the Sign Up button is clicked, makes and API call to check authentication
     * and create a new user
     * @param view the current screen view
     */
    @OnClick(R.id.register_action)
    public void onRegisterClicked(View view) {
        String uname = username.getText().toString();
        String pw = password.getText().toString();
        try {
            usernameValidator.validate(uname);
            passwordValidator.validate(pw);
            ApiClient.getKukyApiClient().register(new UserRequest(uname, pw))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserApiKeyResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG + ": Retrofit Error - ", e.toString());
                        }

                        @Override
                        public void onNext(UserApiKeyResponse apiKeyResponse) {
                            String errorMessage = apiKeyResponse.getErrorMessage();
                            if (errorMessage != null) {
                                handleApiError(errorMessage);
                            } else {
                                changeActivity(username.getText().toString(), apiKeyResponse.getNewKey(), apiKeyResponse.getUserId());
                            }
                        }
                    });
        } catch (UsernameValidator.UsernameException e) {
            loginError.setText(e.getMessage());
        } catch (PasswordValidator.PasswordException e) {
            loginError.setText(e.getMessage());
        }
    }

    /**
     * Starts the Ku View activity when the user is verified into the app
     **/
    public void changeActivity(String username, String apiKey, int userId) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("username", username);
        editor.putInt("userId", userId);
        editor.putString("apiKey", apiKey);
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, KuViewActivity.class);
        startActivity(intent);
    }

    public void handleApiError(String message) {
        if (message.equals("SequelizeUniqueConstraintError")) {
            loginError.setText("This username is already taken.");
        } else {
            loginError.setText(message);
        }
    }

    /**
     * Sets up the view of the activity
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu Menu object
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     * @param item MenuItem object
     * @return boolean true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
