package misterro.bitnet.com.jaymetals.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import misterro.bitnet.com.jaymetals.R;

public class AddAddress extends Fragment implements View.OnClickListener {
    private Button btn_add_address;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        btn_add_address=(Button)view.findViewById(R.id.btn_add_address);
        btn_add_address.setOnClickListener(this);
        return view;


    }

    @Override
    public void onClick(View v) {
        if (btn_add_address==v)
        {
            AddressFragment nextFrag= new AddressFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, nextFrag,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}
