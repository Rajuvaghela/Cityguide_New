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
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/7/2018.
 */

public class ROServiesAdapter extends RecyclerView.Adapter<ROServiesAdapter.VideoInfoHolder> {

    Context ctx;
    List<FeaturedProductPOJO> data = new ArrayList<>();
    private ImageView img_ro_services;
    private TextView ro_service_name,ro_service_amount,ro_service_rating;
    private LinearLayout rating_indication_ro_service;



    public ROServiesAdapter(Context activity, List<FeaturedProductPOJO> data) {
        this.ctx = activity;
        this.data = data;

    }





    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ro_services, parent, false);

        return new ROServiesAdapter.VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final FeaturedProductPOJO list = data.get(position);

        ro_service_name.setText(""+list.getName());
        ro_service_amount.setText("Rs "+list.getPrice());
        Picasso.with(ctx).load(list.getImage_full_path()).resize(160,200).into(img_ro_services, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

                Picasso.with(ctx).load(R.drawable.no_photo).resize(160,280).into(img_ro_services);
            }
        });
        ro_service_rating.setText(""+list.getRating().toString());
        getRatings(list.getRating().toString());

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
                rating_indication_ro_service.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            imageView.setBackgroundResource(R.drawable.rating_half);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            rating_indication_ro_service.addView(imageView);
        }
        else if (rating.equals("3.5"))
        {
            for (int i=0;i<3;i++)
            {
                ImageView imageView = new ImageView(ctx);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication_ro_service.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setBackgroundResource(R.drawable.rating_half);
            imageView.setLayoutParams(layoutParams);
            rating_indication_ro_service.addView(imageView);

            ImageView imageView1 = new ImageView(ctx);
            imageView1.setBackgroundResource(R.drawable.rating_no);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView1.setLayoutParams(layoutParams);
            rating_indication_ro_service.addView(imageView1);
        }
        else if (rating.equals("2.5"))
        {
            for (int i=0;i<2;i++)
            {

                ImageView imageView = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView.setBackgroundResource(R.drawable.rating_one);
                imageView.setLayoutParams(layoutParams);
                rating_indication_ro_service.addView(imageView);
            }

            ImageView imageView = new ImageView(ctx);
            imageView.setBackgroundResource(R.drawable.rating_half);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            rating_indication_ro_service.addView(imageView);

            for (int i=0;i<2;i++)
            {

                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
            }
            ImageView imageView = new ImageView(ctx);
            layoutParams.setMargins(2, 0, 0, 0);
            imageView.setBackgroundResource(R.drawable.rating_half);
            imageView.setLayoutParams(layoutParams);
            rating_indication_ro_service.addView(imageView);

            for (int i=0;i<3;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
            }
            for (int i=0;i<4;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
            }
            for (int i=0;i<3;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
            }
            for (int i=0;i<2;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                layoutParams.setMargins(2, 0, 0, 0);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
            }
            for (int i=0;i<1;i++)
            {
                ImageView imageView1 = new ImageView(ctx);
                imageView1.setBackgroundResource(R.drawable.rating_no);
                imageView1.setLayoutParams(layoutParams);
                layoutParams.setMargins(2, 0, 0, 0);
                rating_indication_ro_service.addView(imageView1);
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
                rating_indication_ro_service.addView(imageView);
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
                rating_indication_ro_service.addView(imageView);
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
            img_ro_services = (ImageView)itemView.findViewById(R.id.img_ro_services);
            ro_service_name = (TextView)itemView.findViewById(R.id.ro_service_name);
            ro_service_amount = (TextView)itemView.findViewById(R.id.ro_service_amount);
            ro_service_rating = (TextView)itemView.findViewById(R.id.ro_service_rating);
            rating_indication_ro_service = (LinearLayout)itemView.findViewById(R.id.rating_indication_ro_service);

        }

    }
}
