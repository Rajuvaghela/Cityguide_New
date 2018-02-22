package misterro.bitnet.com.jaymetals.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Writetous extends Fragment implements View.OnClickListener {
    private EditText edt_mailid, edt_suggestion;
    private Button btn_send;
    private ProgressDialog dialog;
    private TextView txt_write_to_us;
    private ImageView img_write_goback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linear_writetous, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);

        edt_mailid = (EditText) view.findViewById(R.id.edt_mailid);
        edt_suggestion = (EditText) view.findViewById(R.id.edt_suggestion);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        txt_write_to_us=(TextView)view.findViewById(R.id.txt_write_to_us);
        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_write_to_us.setTypeface(face);

        img_write_goback=(ImageView)view.findViewById(R.id.img_write_goback);
        img_write_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        btn_send.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (edt_mailid.getText().toString().equals("")) {
            edt_mailid.setError("Please enter MailId");
        } else if (!isValidEmaillId(edt_mailid.getText().toString())) {
            edt_mailid.setError("Enter Valid EmailId");
        } else if (edt_suggestion.getText().toString().equals("")) {
            edt_suggestion.setError("Please Enter Suggestion");
        } else {
            write();
        }


    }

    private void write() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(800000);
        RequestParams params = new RequestParams();
        params.add("mobile", edt_mailid.getText().toString());
        params.add("password", edt_suggestion.getText().toString());

        client.post(Urlclass.LOGIN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

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



    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
