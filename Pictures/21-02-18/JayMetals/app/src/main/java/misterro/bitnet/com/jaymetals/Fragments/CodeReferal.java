package misterro.bitnet.com.jaymetals.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import misterro.bitnet.com.jaymetals.Activity.MainActivity;
import misterro.bitnet.com.jaymetals.R;
import misterro.bitnet.com.jaymetals.app.AppController;

public class CodeReferal extends Fragment {

    private TextView txt_referal_code,txt_code;
    private ImageView btn_referalcode_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_referal, container, false);
        btn_referalcode_back=(ImageView)view.findViewById(R.id.btn_referalcode_back);
        MainActivity.toolbar.setVisibility(View.GONE);

        btn_referalcode_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getActivity(),MainActivity.class);
              startActivity(intent);
              getActivity().finish();
            }
        });

        Typeface face;
        txt_code=(TextView)view.findViewById(R.id.txt_code);
        face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nexa.otf");
        txt_code.setTypeface(face);

        txt_referal_code=(TextView)view.findViewById(R.id.txt_referal_code);
        //IN LOGIN SCREEN SAVED PREFERENCE
        txt_referal_code.setText(""+ AppController.preferences.getPreference("refercode",""));
        return view;
    }
}
