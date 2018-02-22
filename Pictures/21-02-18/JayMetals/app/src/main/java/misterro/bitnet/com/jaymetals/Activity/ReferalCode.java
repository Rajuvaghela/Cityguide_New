package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ReferalCode extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_referalcode;
    private Button btn_referal_code, btn_skip;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referal_code);
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

        edt_referalcode = (EditText) findViewById(R.id.edt_referalcode);
        btn_referal_code = (Button) findViewById(R.id.btn_referal_code);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        btn_referal_code.setOnClickListener(this);
        btn_skip.setOnClickListener(this);
    }


    private void referalcode() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(800000);
        RequestParams params = new RequestParams();
        params.add("user_id", AppController.preferences.getPreference("user_id", ""));
        params.add("refer_by", edt_referalcode.getText().toString());

        client.post(Urlclass.REFERBY_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.cancel();
                Toast.makeText(ReferalCode.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                try {
                    String message = null;

                    JSONArray jsonArray = responseString.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        message = object.getString("message");

                    }

                    if (message.equals("Your Refferal Code Has Been Added Successfully ")) {
                        Toast.makeText(ReferalCode.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ReferalCode.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ReferalCode.this.finish();

                    } else {
                        edt_referalcode.setError("No referal code");
                        edt_referalcode.requestFocus();
                        Toast.makeText(ReferalCode.this, "Failed.. Try again.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btn_referal_code) {
            if (edt_referalcode.getText().toString().equals("")) {
                edt_referalcode.setError("Please Enter Referal Code");
            } else {
                dialog = new ProgressDialog(ReferalCode.this);
                dialog.show();
                dialog.setMessage("Please wait");
                referalcode();
            }
        }
        if (view == btn_skip) {
            Toast.makeText(this, "Login with your password", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ReferalCode.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            ReferalCode.this.finish();
        }
    }
}
