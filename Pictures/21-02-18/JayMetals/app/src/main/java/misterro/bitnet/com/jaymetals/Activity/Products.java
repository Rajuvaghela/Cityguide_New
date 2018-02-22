package misterro.bitnet.com.jaymetals.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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
import misterro.bitnet.com.jaymetals.Adapters.CustomGrid;
import misterro.bitnet.com.jaymetals.Adapters.FeaturedProductAdapter;
import misterro.bitnet.com.jaymetals.Adapters.ProductCategoryAdapter;
import misterro.bitnet.com.jaymetals.POJO.FeaturedProductPOJO;
import misterro.bitnet.com.jaymetals.POJO.ProductCategoryPojo;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;

public class Products extends AppCompatActivity {

    private List<FeaturedProductPOJO> products = new ArrayList<>();
    private ProgressDialog dialog;
    private GridView gridView_subproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        String id = getIntent().getStringExtra("id");
        products.clear();
        getProducts(id);
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

    }

    private void getProducts(String id) {
        dialog = new ProgressDialog(Products.this);
        dialog.show();
        dialog.setMessage("Getting products");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("cat_id", id);

        client.post(Urlclass.GETSUBPRODUCTS_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.cancel();
                Toast.makeText(Products.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
                Log.e("onSuccess: ", String.valueOf(responseString));
                try {

                    JSONArray jsonArray = responseString.getJSONArray("product_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        FeaturedProductPOJO pojo = new FeaturedProductPOJO();
                        String id = object.getString("product_id");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String discount = object.getString("discount");
                        String rating = object.getString("rating");
                        String image_id = object.getString("image_id");
                        String image_name = object.getString("image_name");
                        String image_full_path = object.getString("image_full_path");
                        String discount_price = object.getString("discount_price");

                        pojo.setProduct_id(id);
                        pojo.setName(name);
                        pojo.setPrice(price);
                        pojo.setImage_id(image_id);
                        pojo.setImage_full_path(image_full_path);
                        pojo.setDiscount(discount);
                        pojo.setDiscount_price(discount_price);
                        pojo.setRating(rating);
                        pojo.setImage_name(image_name);

                        products.add(pojo);
                    }

                    dialog.cancel();
                    CustomGrid adapter = new CustomGrid(Products.this, products);
                    gridView_subproducts=(GridView)findViewById(R.id.gridView_subproducts);
                    gridView_subproducts.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Products.this,MainActivity.class);
        startActivity(intent);
        Products.this.finish();
    }
}
