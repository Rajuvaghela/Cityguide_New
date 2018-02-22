package misterro.bitnet.com.jaymetals.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
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
import misterro.bitnet.com.jaymetals.Adapters.LocationAdapter;
import misterro.bitnet.com.jaymetals.POJO.LocationPOJO;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;

public class SelectLocation extends AppCompatActivity {

    public static List<LocationPOJO> locPOJOS = new ArrayList<>();
    private ProgressBar selectlocation_progress;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.location);
        toolbar.setTitle("  Select your Location");
        setSupportActionBar(toolbar);
        getLocation();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        selectlocation_progress = (ProgressBar)findViewById(R.id.seleclocation_progress);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
    }

    private void getLocation() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("name", "all_city");

        client.post(Urlclass.GETCITY_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SelectLocation.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                locPOJOS.clear();
                try {
                    JSONArray jsonArray = responseString.getJSONArray("home_city_data");

                    if (jsonArray.length() == 0) {
                        Toast.makeText(SelectLocation.this, "No data", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            LocationPOJO pojo = new LocationPOJO();
                            String id = object.getString("id");
                            String city_name = object.getString("city_name");

                            pojo.setId(id);
                            pojo.setCity_name(city_name);

                            locPOJOS.add(pojo);

                        }
                        selectlocation_progress.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);

                        recyclerview.setHasFixedSize(true);
                        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectLocation.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerview.setLayoutManager(linearLayoutManager);
                        LocationAdapter adapter = new LocationAdapter(SelectLocation.this, locPOJOS);
                        recyclerview.setAdapter(adapter);
                        final LayoutAnimationController controller =
                                AnimationUtils.loadLayoutAnimation(SelectLocation.this, R.anim.layout_animation_slide_right);
                        recyclerview.setLayoutAnimation(controller);
                        recyclerview.getAdapter().notifyDataSetChanged();
                        recyclerview.scheduleLayoutAnimation();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(SelectLocation.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        SelectLocation.this.finish();
    }
}
