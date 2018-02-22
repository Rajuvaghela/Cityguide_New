package misterro.bitnet.com.jaymetals.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.app.AppController;

public class Wallet extends Fragment {

    private TextView txt_wallet_money,txt_welcomebonus,txt_account_wallet;
    private ImageView wallet_go_back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);

        wallet_go_back=(ImageView)view.findViewById(R.id.wallet_go_back);
        txt_wallet_money=(TextView)view.findViewById(R.id.txt_wallet_money);
        txt_welcomebonus=(TextView)view.findViewById(R.id.txt_welcomebonus);
        txt_account_wallet=(TextView)view.findViewById(R.id.txt_account_wallet);


        wallet_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_account_wallet.setTypeface(face);

        float f = Float.parseFloat(AppController.preferences.getPreference("balance", ""));
        txt_wallet_money.setText(" " + Math.round(f));


        return view;
    }

}

