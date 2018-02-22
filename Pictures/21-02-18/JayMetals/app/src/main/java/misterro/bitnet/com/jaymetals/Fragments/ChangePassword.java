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

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;
import misterro.bitnet.com.jaymetals.app.AppController;

public class ChangePassword extends Fragment implements View.OnClickListener {
    private EditText edt_old_pass,edt_new_pass,edt_c_pass;
    private Button btn_changepass;
    private ProgressDialog dialog;
    private TextView txt_account_password;
    private ImageView img_btn_pass_go_back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);

        edt_old_pass=(EditText)view.findViewById(R.id.edt_old_pass);
        edt_new_pass=(EditText)view.findViewById(R.id.edt_new_pass);
        edt_c_pass=(EditText)view.findViewById(R.id.edt_c_pass);
        btn_changepass=(Button) view.findViewById(R.id.btn_changepass);

        txt_account_password=(TextView)view.findViewById(R.id.txt_account_password);
        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_account_password.setTypeface(face);

        img_btn_pass_go_back=(ImageView)view.findViewById(R.id.img_btn_pass_go_back);
        img_btn_pass_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        btn_changepass.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (btn_changepass==view)
        {
            if (edt_old_pass.getText().toString().equals(""))
            {
                edt_old_pass.setError("Please Enter Old Password");
            }
            else if (edt_new_pass.getText().toString().equals(""))
            {
                edt_new_pass.setError("Please Enter New Password");
            }
            else if (edt_c_pass.getText().toString().equals(""))
            {
                edt_c_pass.setError("Please  Confrim Password");
            }
            else if (!edt_new_pass.getText().toString().equals(edt_c_pass.getText().toString()))
            {
                edt_c_pass.setError("New Password and Confrim Password Miss Match!");
            }
            else
            {
                changepass();
            }


        }


    }

    private void changepass() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(800000);
        RequestParams params = new RequestParams();
        params.add("mobile", edt_old_pass.getText().toString());
        params.add("password", edt_new_pass.getText().toString());

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

