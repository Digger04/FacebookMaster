package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView sdt, matkhau;
    private CheckBox checkbox;
    private Button dangnhap;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button dangki;
    private String mSdt, mMK;
    private String mPathavta;
    private ArrayList<model_taikhoan> arrayList;

    private ProgressDialog progress_dangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        get_save();
        set_pass_signin();
        progress_dangnhap();
    }
    private void progress_dangnhap(){

        progress_dangnhap = new ProgressDialog(this);
        progress_dangnhap.setTitle("Đang đăng nhập");
        progress_dangnhap.setCancelable(false);
    }
    private void set_pass_signin() {
        if (sharedPreferences.getBoolean("checkbox", false) == true &&
        sharedPreferences.getString(console.SAVE_SDT, "").length() > 0 &&
        sharedPreferences.getString(console.SAVE_PASS, "").length() > 0 &&
        sharedPreferences.getString(console.SAVE_AVATA, "").length() > 0) {
            mPathavta = sharedPreferences.getString(console.SAVE_AVATA, "");
            mSdt = sharedPreferences.getString(console.SAVE_SDT, "");
            Intent intent = new Intent(getApplicationContext(), trangchu.class);
            intent.putExtra(console.SAVE_SDT, mSdt);
            startActivity(intent);
        }
    }

    private void get_save() {
        sdt.setText(sharedPreferences.getString(console.SAVE_SDT, ""));
        matkhau.setText(sharedPreferences.getString(console.SAVE_PASS, ""));
        checkbox.setChecked(sharedPreferences.getBoolean("checkbox", false));
    }

    public void dangnhap(View view) {

        if (sdt.getText().toString().length() > 0 && matkhau.getText().toString().length() > 0) {
            mMK = matkhau.getText().toString();
            mSdt = sdt.getText().toString();
            progress_dangnhap.show();

            arrayList.clear();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("user").hasChild(mSdt)) {

                        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                if (dataSnapshot.exists()) {

                                    progress_dangnhap.cancel();

                                    arrayList.add(dataSnapshot.getValue(model_taikhoan.class));

                                    if (mMK.equals(arrayList.get(0).getMatkhau())&& mSdt.equals(arrayList.get(0).getSdt())) {

                                        if (checkbox.isChecked()) {
                                            editor = sharedPreferences.edit();
                                            editor.putString(console.SAVE_PASS, mMK);
                                            editor.putString(console.SAVE_SDT, mSdt);
                                            editor.putString(console.SAVE_AVATA, arrayList.get(0).getAvata());
                                            editor.putBoolean("checkbox", true);
                                            editor.commit();
                                        }else {
                                            editor = sharedPreferences.edit();
                                            editor.remove(console.SAVE_PASS);
                                            editor.remove(console.SAVE_SDT);
                                            editor.remove(console.SAVE_AVATA);
                                            editor.remove("checkbox");
                                            editor.commit();
                                        }

                                        mPathavta = arrayList.get(0).getAvata();
                                        Intent intent = new Intent(getApplicationContext(), trangchu.class);
                                        intent.putExtra(console.SAVE_SDT, mSdt);
                                        startActivity(intent);
                                        progress_dangnhap.cancel();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                        progress_dangnhap.cancel();
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else {
                        progress_dangnhap.cancel();
                        Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "Nhập số điện thoại và mật khẩu để đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
    public void dangki(View view) {
        startActivity(new Intent(this, com.example.facebookmaster.Activity.dangki.class));
    }

    private void anhxa() {
        sdt = findViewById(R.id.sdt);
        matkhau = findViewById(R.id.matkhau);
        dangnhap = findViewById(R.id.dangnhap);
        dangki = findViewById(R.id.dangki);
        checkbox = findViewById(R.id.checkbox);

        arrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("SAVE", MODE_PRIVATE);

    }
}
