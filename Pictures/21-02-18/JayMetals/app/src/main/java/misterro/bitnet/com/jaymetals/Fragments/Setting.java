package misterro.bitnet.com.jaymetals.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import misterro.bitnet.com.jaymetals.Activity.LoginActivity;
import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.app.AppController;


public class Setting extends Fragment implements View.OnClickListener {
    private TextView txt_wallet_amount,txt_setting;
    private ImageView btn_go_back,btn_logout;
    private LinearLayout linear_wallet, linear_account_setting, linear_add_address, linear_change_password, linear_invite, linear_writetous, linear_rateus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        MainActivity.toolbar.setVisibility(View.GONE);

        txt_wallet_amount = (TextView) view.findViewById(R.id.txt_wallet_amount);
        float f = Float.parseFloat(AppController.preferences.getPreference("balance", ""));
        txt_wallet_amount.setText("Rs. " + Math.round(f));

        linear_wallet = (LinearLayout) view.findViewById(R.id.linear_wallet);
        linear_account_setting = (LinearLayout) view.findViewById(R.id.linear_account_setting);
        linear_add_address = (LinearLayout) view.findViewById(R.id.linear_add_address);
        linear_change_password = (LinearLayout) view.findViewById(R.id.linear_change_password);
        linear_invite = (LinearLayout) view.findViewById(R.id.linear_invite);
        linear_writetous = (LinearLayout) view.findViewById(R.id.linear_writetous);
        linear_rateus = (LinearLayout) view.findViewById(R.id.linear_rateus);

        btn_go_back=(ImageView)view.findViewById(R.id.btn_go_back);
        btn_logout=(ImageView)view.findViewById(R.id.btn_logout);


        linear_wallet.setOnClickListener(this);
        linear_account_setting.setOnClickListener(this);
        linear_add_address.setOnClickListener(this);
        linear_change_password.setOnClickListener(this);
        linear_invite.setOnClickListener(this);
        linear_writetous.setOnClickListener(this);
        linear_rateus.setOnClickListener(this);
        btn_go_back.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        txt_setting=(TextView)view.findViewById(R.id.txt_account_setting);

        Typeface face;

        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_setting.setTypeface(face);

        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == linear_wallet) {
            Wallet nextFrag = new Wallet();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
        if (v == linear_account_setting) {
            AccountSetting nextFrag = new AccountSetting();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
        if (v == linear_add_address) {
            AddressFragment nextFrag = new AddressFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }

        if (v == linear_change_password) {
            ChangePassword nextFrag = new ChangePassword();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
        if (v == linear_invite) {
            Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
        }
        if (v == linear_writetous) {
            Writetous nextFrag = new Writetous();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }

        if (v == linear_rateus) {
            Toast.makeText(getActivity(), "Hello Dear", Toast.LENGTH_SHORT).show();
        }
        if (v==btn_go_back)
        {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        if (v==btn_logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirm");
            builder.setMessage("Are you sure do you want to logout?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }
}
