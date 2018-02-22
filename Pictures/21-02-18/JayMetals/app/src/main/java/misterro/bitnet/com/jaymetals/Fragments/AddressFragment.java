package misterro.bitnet.com.jaymetals.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.Activity.LoginActivity;
import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.POJO.LocationPOJO;
import misterro.bitnet.com.jaymetals.POJO.StatePOJO;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;


public class AddressFragment extends Fragment implements View.OnClickListener {

    private EditText edt_address1, edt_address2, edt_zipcode, edt_contactno;
    private Spinner spinner_city, spinner_state;
    private Button btn_save;
    private TextView txt_address;
    private ProgressDialog dialog;
    public static List<LocationPOJO> cityPOJO = new ArrayList<>();
    private static List<StatePOJO> statePOJO = new ArrayList<>();
    private ImageView img_btn_address_go_back;

    private List<String> cityname = new ArrayList<>();
    private List<String> statename = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);

        getCity();
        edt_address1 = (EditText) view.findViewById(R.id.edt_address1);
        edt_address2 = (EditText) view.findViewById(R.id.edt_address2);
        spinner_city = (Spinner) view.findViewById(R.id.spinner_city);
        spinner_state = (Spinner) view.findViewById(R.id.spinner_state);
        edt_zipcode = (EditText) view.findViewById(R.id.edt_zipcode);
        edt_contactno = (EditText) view.findViewById(R.id.edt_contactno);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        txt_address=(TextView)view.findViewById(R.id.txt_address);
        img_btn_address_go_back = (ImageView) view.findViewById(R.id.img_btn_address_go_back);
        btn_save.setOnClickListener(this);

        img_btn_address_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_address.setTypeface(face);


        return view;

    }

    @Override
    public void onClick(View view) {
        if (view == btn_save) {
            if (edt_address1.getText().toString().equals("")) {
                edt_address1.setError("Please Enter Address!");
            } else if (spinner_state.getSelectedItem().equals("<<Select>>")) {
                Toast.makeText(getActivity(), "Please select state", Toast.LENGTH_SHORT).show();
            } else if (spinner_city.getSelectedItem().equals("<<Select>>")) {
                Toast.makeText(getActivity(), "Please select city", Toast.LENGTH_SHORT).show();
            } else if (edt_zipcode.getText().toString().equals("")) {
                edt_zipcode.setError("Please Enter zipcode!");
            } else if (edt_contactno.getText().toString().equals("")) {
                edt_contactno.setError("Please Enter contact no!");
            } else if (edt_contactno.getText().toString().length() != 10) {
                edt_contactno.setError("Please Enter Valid contact no!");
            } else {
                AddAddress();
            }


        }
    }


    private void AddAddress() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(80000);
        RequestParams params = new RequestParams();
        params.add("mobile", edt_address1.getText().toString());
        params.add("mobile", edt_address2.getText().toString());
        params.add("mobile", spinner_city.getSelectedItem().toString());
        params.add("mobile", spinner_state.getSelectedItem().toString());
        params.add("mobile", edt_zipcode.getText().toString());
        params.add("mobile", edt_contactno.getText().toString());

        client.post(Urlclass.LOGIN_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.cancel();
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

                    if (message.equals("Success")) {
                        android.app.AlertDialog.Builder reorder = new android.app.AlertDialog.Builder(getActivity());
                        reorder.setTitle("Login");
                        reorder.setMessage("New password is successfully set.");
                        reorder.setCancelable(false);
                        reorder.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                        android.app.AlertDialog orderError = reorder.create();
                        orderError.show();
                    } else {
                        Toast.makeText(getActivity(), "Failed.. Try again.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void getCity() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("name", "all_city");

        client.post(Urlclass.GETCITY_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                cityPOJO.clear();
                try {
                    JSONArray jsonArray = responseString.getJSONArray("home_city_data");
                    Log.e("onSuccess: ", String.valueOf(responseString));
                    cityname.clear();
                    cityname.add("<<Select>>");
                    if (jsonArray.length() == 0) {
                        Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            LocationPOJO pojo = new LocationPOJO();
                            String id = object.getString("id");
                            String city_name = object.getString("city_name");

                            pojo.setId(id);
                            pojo.setCity_name(city_name);

                            cityPOJO.add(pojo);
                            cityname.add(city_name);

                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, cityname);
                        spinner_city.setAdapter(adapter);

                    }

                    //call state method
                    getState();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getState() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("name", "state");

        client.post(Urlclass.GETSTATE_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                statePOJO.clear();
                try {
                    JSONArray jsonArray = responseString.getJSONArray("state_data");
                    Log.e("onSuccess: ", String.valueOf(responseString));
                    statename.clear();
                    statename.add("<<Select>>");
                    if (jsonArray.length() == 0) {
                        Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            StatePOJO statePOJO1 = new StatePOJO();
                            String id = object.getString("id");
                            String state_name = object.getString("state_name");

                            statePOJO1.setId(id);
                            statePOJO1.setState_name(state_name);

                            statePOJO.add(statePOJO1);
                            statename.add(state_name);

                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_checked, statename);
                        spinner_state.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
