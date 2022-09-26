package com.example.facebookmaster.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Naptien extends AppCompatActivity {

    private CircleImageView img_avata;
    private TextView txt_tentk, txt_sodu;
    private String msdt = "";
    private ArrayList<model_taikhoan> arrayList;
    private Button bt_dahieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naptien);

        Initview();
        Get_data_intent();
        Get_data_account();
        click_btdahieu();
    }

    private void click_btdahieu() {
        bt_dahieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Initview() {
        img_avata = findViewById(R.id.img_avata);
        bt_dahieu = findViewById(R.id.bt_dahieu);
        txt_sodu = findViewById(R.id.txt_sodu);
        txt_tentk = findViewById(R.id.txt_tentk);
        arrayList = new ArrayList<>();
    }

    private void Setdata() {
        Glide.with(getApplicationContext()).load(arrayList.get(0).getAvata()).into(img_avata);
        txt_tentk.setText(arrayList.get(0).getTaikhoan());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int sodu = (int) arrayList.get(0).getSodu();
        txt_sodu.setText("Số dư: " +decimalFormat.format(sodu) +" Vnd");
    }

    private void Get_data_account() {
        FirebaseDatabase.getInstance().getReference().child("user").child(msdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    arrayList.add(dataSnapshot.getValue(model_taikhoan.class));
                    Setdata();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void Get_data_intent() {
        if (getIntent() != null) {
            msdt = getIntent().getStringExtra(console.SAVE_SDT);
        }
    }
}
