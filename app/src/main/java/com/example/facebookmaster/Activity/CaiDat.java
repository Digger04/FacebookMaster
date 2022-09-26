package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaiDat extends AppCompatActivity {

    private CircleImageView img_avata;
    private EditText edit_mk_hientai, edit_mk_moi1, edit_mk_moi2;
    private Button bt_doimk;
    private Toolbar toolbar;

    private String msdt = "";
    private ArrayList<model_taikhoan> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        Initview();
        Get_data_intent();
        Get_data_account();
        Click_change_passwork();
    }

    private void Click_change_passwork() {
        bt_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_mk_hientai.getText().toString().equals(arrayList.get(0).getMatkhau())) {

                    if (edit_mk_moi1.getText().toString().equals(edit_mk_moi2.getText().toString())) {

                        model_taikhoan model_taikhoan = new model_taikhoan();
                        model_taikhoan.setTaikhoan(arrayList.get(0).getTaikhoan());
                        model_taikhoan.setMatkhau(edit_mk_moi1.getText().toString());
                        model_taikhoan.setAnhbia(arrayList.get(0).getAnhbia());
                        model_taikhoan.setDate(arrayList.get(0).getDate());
                        model_taikhoan.setAvata(arrayList.get(0).getAvata());
                        model_taikhoan.setDiachi(arrayList.get(0).getAvata());
                        model_taikhoan.setQuyentk(arrayList.get(0).getQuyentk());
                        model_taikhoan.setSdt(msdt);
                        model_taikhoan.setSodu(arrayList.get(0).getSodu());

                        FirebaseDatabase.getInstance().getReference().child("user").child(msdt).child("information")
                                .setValue(model_taikhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(CaiDat.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), trangchu.class);
                                intent.putExtra(console.SAVE_SDT, msdt);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(CaiDat.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Toast.makeText(CaiDat.this, "Mật khẩu mới phải trùng nhau", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(CaiDat.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Initview() {
        edit_mk_hientai = findViewById(R.id.edit_mk_hientai);
        edit_mk_moi1 = findViewById(R.id.edit_mk_moi1);
        edit_mk_moi2 = findViewById(R.id.edit_mk_moi2);
        bt_doimk = findViewById(R.id.bt_doi_mat_khau);
        toolbar = findViewById(R.id.toolbar);

        img_avata = findViewById(R.id.img_avata);
        arrayList = new ArrayList<>();
    }

    private void Setdata() {
        Glide.with(getApplicationContext()).load(arrayList.get(0).getAvata()).into(img_avata);
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