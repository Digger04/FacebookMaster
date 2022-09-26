package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_donhang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class edit_donhang extends AppCompatActivity {

    private TextView edit_hientai,edit_hoanthanh,edit_baohanh,edit_ghichu, madon;
    private Toolbar toolbar;
    private ArrayList<model_donhang> arrayList;
    private int mHientai;
    private int mHoanthanh;
    private String mGhichu;
    private String mBaohanh;
    private int mMadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_donhang);

        anhxa();
        get_data();
        setToolbar();
    }

    private void setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void click_xong(View view) {
        if (madon.getText().toString().length() > 0 ) {
            mMadon = Integer.parseInt(madon.getText().toString()) - 1;

            if (mMadon + 1 > arrayList.size()) {
                Toast.makeText(getApplicationContext(), "Mã đơn hàng không tồn tại", Toast.LENGTH_SHORT).show();
            }else {
                // hien tai
                if (edit_hientai.getText().toString().length() > 0) {
                    mHientai = Integer.parseInt(edit_hientai.getText().toString());
                }else {
                    mHientai = arrayList.get(mMadon).getHientai();
                }
                // hoan thanh
                if (edit_hoanthanh.getText().toString().length() > 0) {
                    mHoanthanh = Integer.parseInt(edit_hoanthanh.getText().toString());
                }else {
                    mHoanthanh = arrayList.get(mMadon).getHoanthanh();
                }
                // ghi chu
                if (edit_ghichu.getText().toString().length() > 0) {
                    mGhichu = edit_ghichu.getText().toString();
                }else {
                    mGhichu = arrayList.get(mMadon).getGhichu();
                }
                // bảo hành
                if (edit_baohanh.getText().toString().length() > 0) {
                    mBaohanh = edit_baohanh.getText().toString();
                }else {
                    mBaohanh = arrayList.get(mMadon).getBaohanh();
                }

                final model_donhang model_donhang = new model_donhang();

                model_donhang.setBaohanh(mBaohanh);
                model_donhang.setSdt(arrayList.get(mMadon).getSdt());
                model_donhang.setGhichu(mGhichu);
                model_donhang.setLink(arrayList.get(mMadon).getLink());
                model_donhang.setSotien(arrayList.get(mMadon).getSotien());
                model_donhang.setStatus(arrayList.get(mMadon).getStatus());
                model_donhang.setTime(arrayList.get(mMadon).getTime());
                model_donhang.setTaikhoan(arrayList.get(mMadon).getTaikhoan());
                model_donhang.setStype(arrayList.get(mMadon).getStype());
                model_donhang.setSoluong(arrayList.get(mMadon).getSoluong());
                model_donhang.setCurren_time(arrayList.get(mMadon).getCurren_time());
                model_donhang.setHientai(mHientai);
                model_donhang.setHoanthanh(mHoanthanh);
                model_donhang.setIsfinisnh(arrayList.get(mMadon).getIsfinisnh());

                FirebaseDatabase.getInstance().getReference().child("don hang admin").child(arrayList.get(mMadon).getCurren_time()).
                        setValue(model_donhang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        FirebaseDatabase.getInstance().getReference().child("don hang user").child(arrayList.get(mMadon).getSdt())
                                .child(arrayList.get(mMadon).getCurren_time()).setValue(model_donhang).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                finish();
                                Toast.makeText(getApplicationContext(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mã đơn hàng trước", Toast.LENGTH_SHORT).show();
        }
    }

    private void get_data() {
        FirebaseDatabase.getInstance().getReference().child("don hang admin").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    arrayList.add(dataSnapshot.getValue(model_donhang.class));
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

    private void anhxa() {
        edit_hientai = findViewById(R.id.edit_hientai);
        edit_baohanh = findViewById(R.id.edit_baohanh);
        edit_ghichu = findViewById(R.id.edit_ghichu);
        edit_hoanthanh = findViewById(R.id.edit_hoanthanh);
        madon = findViewById(R.id.madon);
        toolbar = findViewById(R.id.toolbar);
        arrayList = new ArrayList<>();
    }
}
