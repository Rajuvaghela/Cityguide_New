package misterro.bitnet.com.jaymetals.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import misterro.bitnet.com.jaymetals.Activity.LoginActivity;
import misterro.bitnet.com.jaymetals.Activity.OTPActivity;
import misterro.bitnet.com.jaymetals.POJO.LocationPOJO;
import misterro.bitnet.com.jaymetals.R;

/**
 * Created by Dhaval on 2/7/2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.VideoInfoHolder> {

    Context ctx;
    List<LocationPOJO> data = new ArrayList<>();
    private TextView txt_city;
    private int[] colors;

    public LocationAdapter(Context activity, List<LocationPOJO> data) {
        this.ctx = activity;
        this.data = data;

    }





    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_location, parent, false);
        TypedArray ta = ctx.getResources().obtainTypedArray(R.array.color);
        colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        return new LocationAdapter.VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final LocationPOJO list = data.get(position);

        txt_city.setText(""+list.getCity_name());
        txt_city.setBackgroundColor(colors[position % colors.length]);
        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = list.getId();
                Intent intent = new Intent(ctx, OTPActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("id",id);
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

    public class VideoInfoHolder extends RecyclerView.ViewHolder{


        public VideoInfoHolder(View itemView) {
            super(itemView);
            txt_city = (TextView)itemView.findViewById(R.id.txt_city);
        }

    }
}
