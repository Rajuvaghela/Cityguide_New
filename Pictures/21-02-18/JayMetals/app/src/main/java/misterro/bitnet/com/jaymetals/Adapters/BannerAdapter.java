package misterro.bitnet.com.jaymetals.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import misterro.bitnet.com.jaymetals.POJO.BannerPOJO;
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/21/2018.
 */

public class BannerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private ImageView img_banner;
    private List<BannerPOJO> bannerlist = new ArrayList<>();

    public BannerAdapter(Context context, List<BannerPOJO> banner) {
        this.context = context;
        this.bannerlist = banner;
    }

    @Override
    public int getCount() {
        return bannerlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_banner, container,
                false);
        BannerPOJO list = bannerlist.get(position);
        img_banner = (ImageView)itemView.findViewById(R.id.img_banner);

        Picasso.with(context).load(list.getImage()).into(img_banner, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

                Picasso.with(context).load(R.drawable.no_photo).into(img_banner);
            }
        });

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
