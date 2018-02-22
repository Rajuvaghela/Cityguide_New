package misterro.bitnet.com.jaymetals.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.SplashScreen;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;
import misterro.bitnet.com.jaymetals.app.AppController;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_number, login_password;
    private Button btn_signin, btn_signup;
    private TextView forgot_password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if (AppController.preferences.getPreference("isLogin","").equals("true"))
        {
            btnsignin();
        }
        else
        {
            login_number = (EditText) findViewById(R.id.login_number);
            login_password = (EditText) findViewById(R.id.login_password);
            login_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (login_number.getText().toString().length() != 10) {
                        login_number.setError("10 Digit number");
                    }
                    if (login_number.getText().toString().length() == 10 || login_number.getText().toString().length() == 0) {
                        login_number.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            btn_signin = (Button) findViewById(R.id.btn_signin);
            btn_signup = (Button) findViewById(R.id.btn_signup);

            forgot_password = (TextView) findViewById(R.id.forgot_password);

            btn_signin.setOnClickListener(this);
            btn_signup.setOnClickListener(this);
            forgot_password.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_signin) {
            if (login_number.getText().toString().length() != 10) {
                login_number.setError("10 Digit number");
                login_number.requestFocus();
            } else if (login_password.getText().toString().equals("")) {
                login_password.setError("Enter password");
                login_password.requestFocus();
            } else {

                AppController.preferences.savePreference("mobile",login_number.getText().toString());
                AppController.preferences.savePreference("password",login_password.getText().toString());
                btnsignin();
            }
        }
        if (v == btn_signup) {
            Intent intent = new Intent(LoginActivity.this, SelectLocation.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        if (v == forgot_password) {
            Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void btnsignin() {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.show();
        dialog.setMessage("Signing In");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("mobile", AppController.preferences.getPreference("mobile",""));
        params.add("password", AppController.preferences.getPreference("password",""));

        client.post(Urlclass.LOGIN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                dialog.cancel();
                Toast.makeText(LoginActivity.this, "Failed. Please try again.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                try {
                   String message = null,full_name=null,balance=null,city=null,id=null,refercode=null,status = null,email=null;
                    JSONArray jsonArray = responseString.getJSONArray("responce");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        message = object.getString("message");

                        JSONObject object1 = object.getJSONObject("user_info");
                        status = object1.getString("status");
                        id = object1.getString("id");
                        String mobile = object1.getString("mobile");
                        full_name = object1.getString("full_name");
                        email = object1.getString("email");
                        balance = object1.getString("balance");
                        String banned = object1.getString("banned");
                        String activated = object1.getString("activated");
                        city = object1.getString("city");
                        refercode = object1.getString("refer_code");
                    }
                        if (status.equals("TRUE"))
                        {
                            if (message.equals("Login Success"))
                            {
                                dialog.cancel();
                                AppController.preferences.savePreference("refercode",refercode);
                                AppController.preferences.savePreference("user_id",id);
                                AppController.preferences.savePreference("balance",balance);
                                AppController.preferences.savePreference("city",city);
                                AppController.preferences.savePreference("isLogin","true");
                                AppController.preferences.savePreference("full_name",full_name);
                                AppController.preferences.savePreference("email",email);

                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            else
                            {
                                dialog.cancel();
                                login_password.setError("Username and password not matched.");
                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "You are deactivated", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                   finish();
                                }
                            }, 3000);
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
