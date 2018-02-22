package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import misterro.bitnet.com.jaymetals.app.AppController;

public class OTPVerify extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_otp;
    private Button btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);
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
        btn_verify = (Button)findViewById(R.id.btn_verify);
        edt_otp = (EditText)findViewById(R.id.edt_otp);

        edt_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_otp.getText().toString().length() != 6)
                {
                    edt_otp.setError("6 Digit OTP");
                }
                if (edt_otp.getText().toString().length() == 6 || edt_otp.getText().toString().length()==0)
                {
                    edt_otp.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_verify.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OTPVerify.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        OTPVerify.this.finish();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_verify)
        {
            if (edt_otp.getText().toString().equals("") || edt_otp.getText().toString().length()!=6)
            {
                edt_otp.requestFocus();
            }
            else
            {
                verifyOTP();
            }
        }
    }

    private void verifyOTP() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("mobile", OTPActivity.phone);
        params.add("otp", edt_otp.getText().toString());
        final ProgressDialog dialog = new ProgressDialog(OTPVerify.this);
        dialog.show();
        dialog.setMessage("Verifying OTP");
        client.post(Urlclass.VERIFY_OTP, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(OTPVerify.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                try {
                    JSONArray jsonArray = responseString.getJSONArray("responce");

                    Log.e("onSuccess: ", String.valueOf(responseString));
                    if (jsonArray.length() == 0) {
                        Toast.makeText(OTPVerify.this, "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = null,id=null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String mobile = object.getString("mobile");
                            message = object.getString("message");
                            id = object.getString("id");

                        }

                        if (message.equals("Verification Success"))
                        {
                            dialog.dismiss();
                            Toast.makeText(OTPVerify.this, "Verified", Toast.LENGTH_SHORT).show();
                            AppController.preferences.savePreference("user_id",id);
                            Intent intent = new Intent(OTPVerify.this,NewPassword.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            OTPVerify.this.finish();
                        }
                        else
                        {
                            dialog.dismiss();
                            edt_otp.requestFocus();
                            Toast.makeText(OTPVerify.this, "OTP not verified", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
