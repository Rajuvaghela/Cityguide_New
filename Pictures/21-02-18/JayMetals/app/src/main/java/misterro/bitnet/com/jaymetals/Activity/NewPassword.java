package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class NewPassword extends AppCompatActivity {
    private EditText edt_newpass, edt_cpass;
    private Button btn_next_pass;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
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

        edt_newpass = (EditText) findViewById(R.id.edt_newpass);
        edt_cpass = (EditText) findViewById(R.id.edt_cpass);
        btn_next_pass = (Button) findViewById(R.id.btn_next_pass);
        btn_next_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_newpass.getText().toString().equals("")) {
                    edt_newpass.setError("Password can't be blank");
                } else if (edt_cpass.getText().toString().equals("")) {
                    edt_cpass.setError("Confrim Password can't be blank");
                } else if (edt_newpass.getText().toString().equals(edt_cpass.getText().toString())) {
                    dialog = new ProgressDialog(NewPassword.this);
                    dialog.show();
                    dialog.setMessage("Sending");
                    newpass();
                } else {
                    Toast.makeText(NewPassword.this, "password and confirm password miss match!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void newpass() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(800000);
        RequestParams params = new RequestParams();
        params.add("user_id", AppController.preferences.getPreference("user_id", ""));
        Toast.makeText(this, "" + AppController.preferences.getPreference("user_id", ""), Toast.LENGTH_SHORT).show();
        params.add("password", edt_newpass.getText().toString());

        client.post(Urlclass.SETPASSWORD_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.cancel();
                Toast.makeText(NewPassword.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                Log.e("onSuccess: ", String.valueOf(responseString));
                try {
                    String message = null;

                    JSONArray jsonArray = responseString.getJSONArray("responce");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        message = object.getString("message");
                    }

                    if (message.equals("Password Changed")) {

                        Toast.makeText(NewPassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewPassword.this, ReferalCode.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        NewPassword.this.finish();

                    } else {
                        Toast.makeText(NewPassword.this, "Failed.. Try again.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
