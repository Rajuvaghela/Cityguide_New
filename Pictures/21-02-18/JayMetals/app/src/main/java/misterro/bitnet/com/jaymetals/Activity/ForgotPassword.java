package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private Button btn_forgot_pass;
    private ProgressDialog dialog;
    private EditText forgot_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
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

        btn_forgot_pass = (Button)findViewById(R.id.btn_forgot_pass);
        btn_forgot_pass.setOnClickListener(this);
        forgot_phone = (EditText)findViewById(R.id.forgot_phone);
        forgot_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (forgot_phone.getText().toString().length()!=10)
                {
                    forgot_phone.setError("10 Digit Number");
                }
                if (forgot_phone.getText().toString().length()==0 || forgot_phone.getText().toString().length()==10)
                {
                    forgot_phone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_forgot_pass)
        {
            if (forgot_phone.getText().toString().length() != 10) {
                forgot_phone.setError("10 Digit number");
                forgot_phone.requestFocus();
            }
            else
            {
                dialog = new ProgressDialog(ForgotPassword.this);
                dialog.show();
                dialog.setMessage("Signing In");
                btn_forgot();
            }
        }
    }

    private void btn_forgot() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(800000);
        RequestParams params = new RequestParams();
        params.add("mobile", forgot_phone.getText().toString());

        client.post(Urlclass.FORGOT_PASS, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                try {
                    String message = null;

                    JSONArray jsonArray = responseString.getJSONArray("responce");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        message = object.getString("message");

                    }

                    if (message.equals("Password Sent"))
                    {
                        android.app.AlertDialog.Builder reorder = new android.app.AlertDialog.Builder(ForgotPassword.this);
                        reorder.setTitle("Login");
                        reorder.setMessage("New password sent to your mobile number. You can login with new password.");
                        reorder.setCancelable(false);
                        reorder.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        ForgotPassword.this.finish();
                                    }
                                });

                        android.app.AlertDialog orderError = reorder.create();
                        orderError.show();
                    }
                    else
                    {
                        Toast.makeText(ForgotPassword.this, "Failed.. Try again.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        ForgotPassword.this.finish();
    }
}
