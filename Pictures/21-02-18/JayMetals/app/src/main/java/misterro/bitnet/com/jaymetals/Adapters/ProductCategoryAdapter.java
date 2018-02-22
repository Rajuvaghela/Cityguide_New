package misterro.bitnet.com.jaymetals.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import misterro.bitnet.com.jaymetals.Activity.OTPActivity;
import misterro.bitnet.com.jaymetals.Activity.Products;
import misterro.bitnet.com.jaymetals.POJO.LocationPOJO;
import misterro.bitnet.com.jaymetals.POJO.ProductCategoryPojo;
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/7/2018.
 */

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.VideoInfoHolder> {

    Context ctx;
    List<ProductCategoryPojo> data = new ArrayList<>();
    private ImageView img_product;
    private TextView product_name;
    private RelativeLayout rl_product;
    private TypedArray img;
    private int[] resource;

    public ProductCategoryAdapter(Context activity, List<ProductCategoryPojo> data) {
        this.ctx = activity;
        this.data = data;

    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_category, parent, false);
/*
        img = ctx.getResources().obtainTypedArray(R.array.product_cat_imgs);

        resource = new int[img.length()];
        for (int i = 0; i < img.length(); i++) {
            resource[i] = img.getResourceId(i, 0);
        }
        img.recycle();*/

        return new ProductCategoryAdapter.VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final ProductCategoryPojo list = data.get(position);
        if (position > 0) {
            if (position % 3 == 0) {
                rl_product.setBackgroundColor(Color.parseColor("#6EA9A5"));
            } else if (position % 3 == 1) {
                rl_product.setBackgroundColor(Color.parseColor("#68B68C"));
            } else if (position % 3 == 2) {
                rl_product.setBackgroundColor(Color.parseColor("#82CE6A"));
            }
        }
       // rl_product.setBackgroundResource(R.color.pproduct_cat1);
//        Picasso.with(ctx).load(list.getImage()).into(img_product, new Callback() {
//            @Override
//            public void onSuccess() {
//                progressbar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//                Picasso.with(ctx).load(R.drawable.no_photo).resize(160,200).into(img_product);
//                progressbar.setVisibility(View.GONE);
//            }
//        });
        product_name.bringToFront();
        product_name.setText("" + list.getName());
        rl_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, Products.class);

                intent.putExtra("id", list.getId());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder {


        public VideoInfoHolder(View itemView) {
            super(itemView);
            img_product = (ImageView) itemView.findViewById(R.id.img_product);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            rl_product = (RelativeLayout) itemView.findViewById(R.id.rl_product);
        }

    }
}
