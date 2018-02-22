package misterro.bitnet.com.jaymetals.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.Activity.OTPConfirm;
import misterro.bitnet.com.jaymetals.Adapters.BannerAdapter;
import misterro.bitnet.com.jaymetals.Adapters.FeaturedProductAdapter;
import misterro.bitnet.com.jaymetals.Adapters.ProductCategoryAdapter;
import misterro.bitnet.com.jaymetals.Adapters.ROServiesAdapter;
import misterro.bitnet.com.jaymetals.POJO.BannerPOJO;
import misterro.bitnet.com.jaymetals.POJO.FeaturedProductPOJO;
import misterro.bitnet.com.jaymetals.POJO.ProductCategoryPojo;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.UrlClass.Urlclass;


public class Home extends Fragment {

    private ProgressDialog dialog;
    private List<ProductCategoryPojo> category = new ArrayList<>();
    private List<FeaturedProductPOJO> featured = new ArrayList<>();
    private List<FeaturedProductPOJO> ro_services = new ArrayList<>();
    private List<BannerPOJO> banner = new ArrayList<>();
    private ViewPager viewPager;
    private RecyclerView product_category_recyclerview,featured_product_recyclerview,ro_services_recyclerview;

    int currentPage = 0,NUM_PAGES =0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        MainActivity.toolbar.setVisibility(View.VISIBLE);

        category.clear();
        featured.clear();
        ro_services.clear();
        banner.clear();
        getBanners();


        product_category_recyclerview = (RecyclerView)view.findViewById(R.id.product_category_recyclerview);
        featured_product_recyclerview= (RecyclerView)view.findViewById(R.id.featured_product_recyclerview);
        ro_services_recyclerview = (RecyclerView)view.findViewById(R.id.ro_services_recyclerview);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        return view;
    }

    private void getBanners() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("name", "get_banner");

        client.post(Urlclass.FETCHBANNER_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
                getProductCategory();
                Log.e("onSuccess: ", String.valueOf(responseString));
                try {

                    JSONArray jsonArray = responseString.getJSONArray("banner_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        BannerPOJO pojo = new BannerPOJO();
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String type_name = object.getString("type_name");
                        String type_id = object.getString("type_id");
                        String image = object.getString("image");

                        pojo.setId(id);
                        pojo.setName(name);
                        pojo.setType_name(type_name);
                        pojo.setType_id(type_id);
                        pojo.setImage(image);

                        banner.add(pojo);
                    }

                    NUM_PAGES = banner.size();
                    // Locate the ViewPager in viewpager_main.xml

                    // Pass results to ViewPagerAdapter Class
                    BannerAdapter adapter = new BannerAdapter(getActivity(),banner );
                    // Binds the Adapter to the ViewPager
                    viewPager.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getProductCategory() {
        dialog = new ProgressDialog(getActivity());
        dialog.show();
        dialog.setMessage("Getting products");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();
        params.add("name", "product");

        client.post(Urlclass.PRODUCTCATEGORY_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
                getProductFeatured();
                Log.e("onSuccess: ", String.valueOf(responseString));
                try {

                    JSONArray jsonArray = responseString.getJSONArray("category_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ProductCategoryPojo pojo = new ProductCategoryPojo();
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String image = object.getString("image");

                        pojo.setId(id);
                        pojo.setName(name);
                        pojo.setImage(image);

                        category.add(pojo);
                    }

                    dialog.cancel();
                    product_category_recyclerview.setHasFixedSize(true);
                    //to use RecycleView, you need a layout manager. default is LinearLayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    product_category_recyclerview.setLayoutManager(linearLayoutManager);
                    ProductCategoryAdapter adapter = new ProductCategoryAdapter(getActivity(), category);
                    product_category_recyclerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getROServices()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();

        client.post(Urlclass.ROSERVICE_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {

                Log.e("onSuccess: ", String.valueOf(responseString));
                try {

                    JSONArray jsonArray = responseString.getJSONArray("trending_service_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        FeaturedProductPOJO pojo = new FeaturedProductPOJO();
                        String id = object.getString("service_id");
                        String name = object.getString("name");
                        String amount = object.getString("price");
                        String rating = object.getString("rating");
                        String discount = object.getString("discount");
                        String image_id = object.getString("image_id");
                        String image_name = object.getString("image_name");
                        String discount_price = object.getString("discount_price");
                        String image = object.getString("image_full_path");


                        pojo.setProduct_id(id);
                        pojo.setRating(rating);
                        pojo.setDiscount(discount);
                        pojo.setDiscount_price(discount_price);
                        pojo.setImage_id(image_id);
                        pojo.setImage_name(image_name);
                        pojo.setName(name);
                        pojo.setImage_full_path(image);
                        pojo.setPrice(amount);
                        ro_services.add(pojo);
                    }

                    ro_services_recyclerview.setHasFixedSize(true);
                    //to use RecycleView, you need a layout manager. default is LinearLayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    ro_services_recyclerview.setLayoutManager(linearLayoutManager);
                    ROServiesAdapter adapter = new ROServiesAdapter(getActivity(), ro_services);
                    ro_services_recyclerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getProductFeatured() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        RequestParams params = new RequestParams();

        client.post(Urlclass.FEATUREDPRODUCT_URL, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseString) {
                getROServices();
                Log.e("onSuccess: ", String.valueOf(responseString));
                try {

                    JSONArray jsonArray = responseString.getJSONArray("featured_product_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        FeaturedProductPOJO pojo = new FeaturedProductPOJO();
                        String product_id = object.getString("product_id");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String rating = object.getString("rating");
                        String discount = object.getString("discount");
                        String image_id = object.getString("image_id");
                        String image_name = object.getString("image_name");
                        String image_full_path = object.getString("image_full_path");
                        String discount_price = object.getString("discount_price");

                        pojo.setProduct_id(product_id);
                        pojo.setName(name);
                        pojo.setPrice(price);
                        pojo.setRating(rating);
                        pojo.setDiscount(discount);
                        pojo.setImage_id(image_id);
                        pojo.setImage_name(image_name);
                        pojo.setImage_full_path(image_full_path);
                        pojo.setDiscount_price(discount_price);

                        featured.add(pojo);
                    }

                    featured_product_recyclerview.setHasFixedSize(true);
                    //to use RecycleView, you need a layout manager. default is LinearLayoutManager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    featured_product_recyclerview.setLayoutManager(linearLayoutManager);
                    FeaturedProductAdapter adapter = new FeaturedProductAdapter(getActivity(), featured);
                    featured_product_recyclerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
