package misterro.bitnet.com.jaymetals.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import misterro.bitnet.com.jaymetals.POJO.FeaturedProductPOJO;
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/20/2018.
 */

public class CustomGrid extends BaseAdapter {
    private Context mContext;
   private List<FeaturedProductPOJO> pojos = new ArrayList<>();
    private ImageView img_featured_product;
    private TextView details,product_rate,product_discount,saving;

    public CustomGrid(Context c,List<FeaturedProductPOJO> Imageid ) {
        mContext = c;
        this.pojos = Imageid;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return pojos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final FeaturedProductPOJO list = pojos.get(position);
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.list_featured_product, null);
            img_featured_product = (ImageView)grid.findViewById(R.id.img_featured_product);
            details = (TextView)grid.findViewById(R.id.details);
            product_rate = (TextView)grid.findViewById(R.id.product_rate);
            product_discount = (TextView)grid.findViewById(R.id.product_discount);
            saving = (TextView)grid.findViewById(R.id.saving);


            details.setText(""+list.getName());
            product_rate.setText(""+list.getPrice());
            product_rate.setPaintFlags(product_rate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            product_discount.setText(""+list.getDiscount_price());
            double rate = Double.parseDouble(list.getPrice());
            double dis = Double.parseDouble(list.getDiscount_price());
            double save = rate-dis;
            saving.setText(""+save+" ("+list.getDiscount()+"%)");
            Picasso.with(mContext).load(list.getImage_full_path()).resize(160,200).into(img_featured_product, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(mContext).load(R.drawable.no_photo).resize(160,280).into(img_featured_product);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
