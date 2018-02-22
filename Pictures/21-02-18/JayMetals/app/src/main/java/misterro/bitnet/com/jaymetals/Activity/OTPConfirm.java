package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class OTPConfirm extends AppCompatActivity implements View.OnClickListener {

    private Button ib_close;
    private TextView tv_number;
    private String id;
    private Button btn_ok;
    private LinearLayout ll_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirm);
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

        final String ph = OTPActivity.phone;

        ll_confirm = (LinearLayout) findViewById(R.id.ll_confirm);

        ib_close = (Button) findViewById(R.id.ib_close);
        tv_number = (TextView) findViewById(R.id.tv_number);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        id = getIntent().getStringExtra("id");
        tv_number.setText("" + OTPActivity.phone + "  - India");

        btn_ok.setOnClickListener(this);
        ib_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_ok) {
            btn_sendLogin();
        }
        if (v == ib_close)
        {
            Intent intent = new Intent(OTPConfirm.this, OTPActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            startActivity(intent);
            finish();
        }
    }

    private void btn_sendLogin() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("mobile", OTPActivity.phone);
        params.add("home_city", id);
        final ProgressDialog dialog = new ProgressDialog(OTPConfirm.this);
        dialog.show();
        dialog.setMessage("Sending OTP");
        client.post(Urlclass.SENDNUMBER_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                dialog.dismiss();
                Toast.makeText(OTPConfirm.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                try {
                    JSONArray jsonArray = responseString.getJSONArray("responce");
                    if (jsonArray.length() == 0) {
                        Toast.makeText(OTPConfirm.this, "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        String message = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String mobile = object.getString("mobile");
                            message = object.getString("message");

                        }

                        if (message.equals("Mobile Number Already Registered"))
                        {
                            dialog.dismiss();
                            Toast.makeText(OTPConfirm.this, "Number already registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OTPConfirm.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            OTPConfirm.this.finish();
                        }
                        else if (message.equals("OTP Sent Successfully"))
                        {
                            dialog.dismiss();
                            Toast.makeText(OTPConfirm.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OTPConfirm.this,OTPVerify.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            OTPConfirm.this.finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
