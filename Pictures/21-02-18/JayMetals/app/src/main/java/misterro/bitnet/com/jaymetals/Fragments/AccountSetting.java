package misterro.bitnet.com.jaymetals.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;
import misterro.bitnet.com.jaymetals.app.AppController;


public class AccountSetting extends Fragment implements View.OnClickListener {

    private Button btn_save_profile, btn_name, btn_address, btn_number, btn_email;
    private Dialog dialog;
    private EditText txt_name, txt_address, txt_number, txt_email;
    private TextView txt_setting;
    private ImageView img_back;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_setting, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);


        btn_save_profile = (Button) view.findViewById(R.id.btn_save_profile);
        btn_name = (Button) view.findViewById(R.id.btn_name);
        btn_address = (Button) view.findViewById(R.id.btn_address);
        btn_number = (Button) view.findViewById(R.id.btn_number);
        btn_email = (Button) view.findViewById(R.id.btn_email);

        txt_name = (EditText) view.findViewById(R.id.txt_name);
        txt_address = (EditText) view.findViewById(R.id.txt_address);
        txt_number = (EditText) view.findViewById(R.id.txt_number);
        txt_email = (EditText) view.findViewById(R.id.txt_email);
        txt_setting=(TextView)view.findViewById(R.id.txt_setting);
        img_back=(ImageView) view.findViewById(R.id.img_back);

        txt_name.setEnabled(false);
        txt_address.setEnabled(false);
        txt_number.setEnabled(false);
        txt_email.setEnabled(false);

        img_back.setOnClickListener(this);
        btn_save_profile.setOnClickListener(this);
        btn_name.setOnClickListener(this);
        btn_address.setOnClickListener(this);
        btn_number.setOnClickListener(this);
        btn_email.setOnClickListener(this);

        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_setting.setTypeface(face);


        return view;


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        if (view == btn_address) {
            btn_address.setBackgroundColor(Color.parseColor("#d80027"));
            btn_address.setText("Done");
            txt_address.setEnabled(true);
            txt_address.setHint("Write your address");
            txt_address.setHintTextColor(R.color.black);
        }
        if (view == btn_name) {
            txt_name.setEnabled(true);
            btn_name.setBackgroundColor(Color.parseColor("#d80027"));
            btn_name.setText("Done");
            txt_name.setHint("Write your Name");
            txt_name.setHintTextColor(R.color.black);
        }
        if (view == btn_number) {
            txt_number.setEnabled(true);
            btn_number.setText("Done");
            btn_number.setBackgroundColor(Color.parseColor("#d80027"));
            txt_number.setEnabled(true);
            txt_number.setHint("Number");
            txt_number.setHintTextColor(R.color.black);
        }
        if (view == btn_email) {
            txt_email.setEnabled(true);
            btn_email.setText("Done");
            btn_email.setBackgroundColor(Color.parseColor("#d80027"));
            txt_email.setEnabled(true);
            txt_email.setEnabled(true);
            btn_email.setHint("Email");
            btn_email.setHintTextColor(R.color.black);
        }
        if (view==img_back)
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        }

        if (view == btn_save_profile) {
            if (txt_address.getText().toString().equals("")) {
                txt_address.setError("Please enter address!");
            } else if (txt_number.getText().toString().length() < 10) {
                txt_number.setError("Please enter valid number!");
            } else if (!isValidEmaillId(txt_email.getText().toString())) {
                txt_email.setError("Please enter valid email!");
            } else if (btn_name.getText().toString().equals("")) {
                btn_name.setError("Please enter name!");
            } else {
                UpdateProfile();
            }


        }
    }


    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void UpdateProfile() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(80000);
        RequestParams params = new RequestParams();
        params.add("mobile", txt_email.getText().toString());
        params.add("password", txt_number.getText().toString());
        params.add("mobile", txt_name.getText().toString());
        params.add("password", txt_address.getText().toString());

        client.post(Urlclass.LOGIN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
                try {
                    String message = null, full_name = null, balance = null;
                    JSONArray jsonArray = responseString.getJSONArray("responce");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        message = object.getString("message");

                        JSONObject object1 = object.getJSONObject("user_info");
                        String status = object1.getString("status");
                        String id = object1.getString("id");
                        String mobile = object1.getString("mobile");
                        full_name = object1.getString("full_name");
                        String email = object1.getString("email");
                        balance = object1.getString("balance");
                        String banned = object1.getString("banned");
                        String activated = object1.getString("activated");
                    }
                    if (message.equals("Login Success")) {
                        AppController.preferences.savePreference("balance", balance);
                        Intent intent = new Intent(getActivity(), Writetous.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
