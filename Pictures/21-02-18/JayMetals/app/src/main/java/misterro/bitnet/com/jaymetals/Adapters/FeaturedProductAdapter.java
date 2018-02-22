package misterro.bitnet.com.jaymetals.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import misterro.bitnet.com.jaymetals.POJO.FeaturedProductPOJO;
import misterro.bitnet.com.jaymetals.POJO.ProductCategoryPojo;
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/7/2018.
 */

public class FeaturedProductAdapter extends RecyclerView.Adapter<FeaturedProductAdapter.VideoInfoHolder> {

    Context ctx;
    List<FeaturedProductPOJO> data = new ArrayList<>();
    private ImageView img_featured_product;
    private TextView details,product_rate,product_discount,saving,rating;
    private LinearLayout rating_indication;


    public FeaturedProductAdapter(Context activity, List<FeaturedProductPOJO> data) {
        this.ctx = activity;
        this.data = data;

    }





    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_featured_product, parent, false);

        return new FeaturedProductAdapter.VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final FeaturedProductPOJO list = data.get(position);

        details.setText(""+list.getName());
        product_rate.setText(""+list.getPrice());
        product_rate.setPaintFlags(product_rate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        product_discount.setText(""+list.getDiscount_price());
        double rate = Double.parseDouble(list.getPrice());
        double dis = Double.parseDouble(list.getDiscount_price());
        double save = rate-dis;
        saving.setText(""+save+" ("+list.getDiscount()+"%)");
        rating.setText(""+list.getRating());

        Picasso.with(ctx).load(list.getImage_full_path()).resize(160,200).into(img_featured_product, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

                Picasso.with(ctx).load(R.drawable.no_photo).resize(160,280).into(img_featured_product);
            }
        });
        getRatings(list.getRating());


    }

    private void getRatings(String rating) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
        if (rating.equals("4.5"))
        {
            for (int i=0;i<4;i++)
            {
                ImageView imageView = new ImageView(ctx);
                imageView.setBackgroundResource(R.drawable.rating_one);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            imageView.setBackgroundResource(R.drawable.rating_half);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            rating_indication.addView(imageView);
        }
        else if (rating.equals("3.5"))
        {
            for (int i=0;i<3;i++)
            {
                ImageView imageView = new ImageView(ctx);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setBackgroundResource(R.drawable.rating_half);
            imageView.setLayoutParams(layoutParams);
            rating_indication.addView(imageView);

            ImageView imageView1 = new ImageView(ctx);
            imageView1.setBackgroundResource(R.drawable.rating_no);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView1.setLayoutParams(layoutParams);
            rating_indication.addView(imageView1);
        }
        else if (rating.equals("2.5"))
        {
            for (int i=0;i<2;i++)
            {

                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            imageView.setBackgroundResource(R.drawable.rating_half);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            rating_indication.addView(imageView);

            for (int i=0;i<2;i++)
            {

                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("1.5"))
        {
            for (int i=0;i<1;i++)
            {
                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
            ImageView imageView = new ImageView(ctx);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setBackgroundResource(R.drawable.rating_half);
            imageView.setLayoutParams(layoutParams);
            rating_indication.addView(imageView);

            for (int i=0;i<3;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("1.0"))
        {
            for (int i=0;i<1;i++)
            {
                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
            for (int i=0;i<4;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("2.0"))
        {
            for (int i=0;i<2;i++)
            {
                ImageView imageView = new ImageView(ctx);
                imageView.setBackgroundResource(R.drawable.rating_one);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
            for (int i=0;i<3;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("3.0"))
        {
            for (int i=0;i<3;i++)
            {
                ImageView imageView = new ImageView(ctx);
                imageView.setBackgroundResource(R.drawable.rating_one);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
            for (int i=0;i<2;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("4.0"))
        {
            for (int i=0;i<4;i++)
            {
                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
            for (int i=0;i<1;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication.addView(imageView1);
            }
        }
        else if (rating.equals("5.0"))
        {
            for (int i=0;i<5;i++)
            {
                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
        }
        else if (rating.equals("0"))
        {
            for (int i=0;i<5;i++)
            {
                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_no);
                imageView.setLayoutParams(layoutParams);
                rating_indication.addView(imageView);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder{


        public VideoInfoHolder(View itemView) {
            super(itemView);
            img_featured_product = (ImageView)itemView.findViewById(R.id.img_featured_product);
            details = (TextView)itemView.findViewById(R.id.details);
            product_rate = (TextView)itemView.findViewById(R.id.product_rate);
            product_discount = (TextView)itemView.findViewById(R.id.product_discount);
            saving = (TextView)itemView.findViewById(R.id.saving);
            rating = (TextView)itemView.findViewById(R.id.rating);
            rating_indication = (LinearLayout)itemView.findViewById(R.id.rating_indication);
        }

    }
}
