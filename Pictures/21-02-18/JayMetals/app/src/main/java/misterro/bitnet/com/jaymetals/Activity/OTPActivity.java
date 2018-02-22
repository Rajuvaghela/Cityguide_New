package misterro.bitnet.com.jaymetals.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import misterro.bitnet.com.jaymetals.R;

public class OTPActivity extends AppCompatActivity {

    private EditText edt_phone;
    private Button btn_next;
    private String id;
    public static String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        edt_phone=(EditText)findViewById(R.id.edt_phone);
        btn_next =(Button)findViewById(R.id.btn_next);
        id = getIntent().getStringExtra("id");
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = edt_phone.getText().toString();

                if (edt_phone.getText().toString().length() != 10)
                {
                    edt_phone.setError("Enter 10 digit mobile number");
                }
                else
                {
                    Intent intent = new Intent(OTPActivity.this,OTPConfirm.class);
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OTPActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        OTPActivity.this.finish();
    }
}
