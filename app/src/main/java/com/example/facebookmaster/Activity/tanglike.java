package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_banggia;
import com.example.facebookmaster.model.model_donhang;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class tanglike extends AppCompatActivity {

    private ViewFlipper viewfliper;
    private Toolbar toolbar;
    private TextView giahientai, nhaplink, soluong, tongtien;
    private long mTongtien;
    private TextView sodu;
    private ProgressDialog progress_thanhtoan;

    private String mSdt, mPathavata;
    private ArrayList<model_taikhoan> list_taikhoan;
    private ArrayList<model_banggia> list_banggia;
    private ArrayList<String> list_viewfliper;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanglike);

        getintent();
        anhxa();
        set_toolbar();
        getData();
        get_banggia();
        set_progress_thanhtoan();
        set_viewfliper();
    }
    private void set_sodu() {
        sodu.setText("Số dư: " + decimalFormat.format(list_taikhoan.get(0).getSodu()) + " vnd");
    }

    private void set_viewfliper() {
        Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.anim_out);

        list_viewfliper.add("https://blog.puziness.com/wp-content/uploads/2018/04/Facebook-Support-Number.png");
        list_viewfliper.add("https://admarket.vn/wp-content/uploads/2018/02/bang-gia-dich-vutang-like-facebook-gia-re-hieu-qua.png");
        list_viewfliper.add("https://fptshop.com.vn/uploads/originals/2017/8/5//636375331739213910_cach-xem-nguoi-khac-theo-doi-minh-tren-facebook-cover.jpg");
        list_viewfliper.add("https://admarket.vn/wp-content/uploads/2014/11/cach-tang-luot-share-tren-facebook2.jpg");

        viewfliper.setFlipInterval(3000);
        viewfliper.setAutoStart(true);

        for (int i = 0; i < list_viewfliper.size(); i ++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getApplicationContext()).load(list_viewfliper.get(i)).into(imageView);
            viewfliper.addView(imageView);
        }

        Animation in1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
        Animation out1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_out);
        viewfliper.setInAnimation(in1);
        viewfliper.setOutAnimation(out1);
    }

    private void set_progress_thanhtoan() {
        progress_thanhtoan = new ProgressDialog(this);
        progress_thanhtoan.setCancelable(false);
        progress_thanhtoan.setTitle("Đang thực hiện giao dịch");
    }

    private void get_banggia() {

        FirebaseDatabase.getInstance().getReference().child("bang gia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    list_banggia.add(dataSnapshot.getValue(model_banggia.class));
                    giahientai.setText("Giá hiện tại: " + decimalFormat.format(list_banggia.get(0).getGialike()) + " vnd / 1k");
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

    private void getintent() {
        if (getIntent() != null) {
            mSdt = getIntent().getStringExtra(console.SAVE_SDT);
            mPathavata = getIntent().getStringExtra(console.SAVE_AVATA);
        }
    }

    private void getData() {

        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    list_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));
                    set_sodu();
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

    public void thanhtoan(View view) {
        mTongtien = ( list_banggia.get(0).getGialike() / 1000 ) * Integer.parseInt(soluong.getText().toString());
        if (Integer.parseInt(soluong.getText().toString()) > 0 && nhaplink.getText().toString().length() > 0) {

            if (mTongtien < list_taikhoan.get(0).getSodu()) {
                dialog_thanhtoan();
            }else {
                dialog_sodu_khongdu();
            }

        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng và link trước.", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadgia(View view) {
        if (soluong.getText().toString().length() > 0) {
            mTongtien = Integer.parseInt(soluong.getText().toString()) * (list_banggia.get(0).getGialike() / 1000);
            tongtien.setText("Chi phí: " + decimalFormat.format(mTongtien) + " vnd");
        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số lượng like trước", Toast.LENGTH_SHORT).show();
        }
    }

    private void dialog_thanhtoan() {
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_thanhtoan);

        final TextView dialog_ghichu;
        final TextView dialog_matkhau;
        TextView dialog_tongtien;
        TextView dialog_soluong;
        Button dialog_thanhtoan;
        Button dialog_huy;

        dialog_soluong = dialog.findViewById(R.id.dialog_txt_soluong);
        dialog_tongtien = dialog.findViewById(R.id.dialog_txt_sotien);
        dialog_ghichu = dialog.findViewById(R.id.dialog_ghichu);
        dialog_matkhau = dialog.findViewById(R.id.dialog_matkhau_thanhtoan);
        dialog_thanhtoan = dialog.findViewById(R.id.dialog_thanhtoan);
        dialog_huy = dialog.findViewById(R.id.dialog_huy);

        dialog_soluong.setText("Số lượng like: " + soluong.getText().toString() + " like");
        dialog_tongtien.setText("Số tiền phải trả: " + decimalFormat.format(mTongtien) + " vnd");
        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_matkhau.getText().toString().length() > 0) {

                    if (dialog_matkhau.getText().toString().equals(list_taikhoan.get(0).getMatkhau())) {

                            progress_thanhtoan.show();
                            final model_taikhoan model_taikhoan = new model_taikhoan();
                            model_taikhoan.setQuyentk(list_taikhoan.get(0).getQuyentk());
                            model_taikhoan.setDiachi(list_taikhoan.get(0).getDiachi());
                            model_taikhoan.setFacebook(list_taikhoan.get(0).getFacebook());
                            model_taikhoan.setDate(list_taikhoan.get(0).getDate());
                            model_taikhoan.setMatkhau(list_taikhoan.get(0).getMatkhau());
                            model_taikhoan.setSdt(list_taikhoan.get(0).getSdt());
                            model_taikhoan.setSodu(list_taikhoan.get(0).getSodu() - mTongtien);
                            model_taikhoan.setAnhbia(list_taikhoan.get(0).getAnhbia());
                            model_taikhoan.setAvata(list_taikhoan.get(0).getAvata());
                            model_taikhoan.setTaikhoan(list_taikhoan.get(0).getTaikhoan());

                            FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).child("information").setValue(model_taikhoan)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                           FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
                                               @Override
                                               public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                   String currentime = System.currentTimeMillis() + "";

                                                   if (dataSnapshot.exists()) {
                                                       list_taikhoan.clear();
                                                       list_taikhoan.add(dataSnapshot.getValue(com.example.facebookmaster.model.model_taikhoan.class));
                                                       sodu.setText("Số dư: " + list_taikhoan.get(0).getSodu() + " vnd");

                                                       String day = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                                                       String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                                                       model_donhang model_donhang = new model_donhang();

                                                       model_donhang.setBaohanh("Còn");
                                                       model_donhang.setSdt(mSdt);
                                                       model_donhang.setGhichu(dialog_ghichu.getText().toString());
                                                       model_donhang.setLink(nhaplink.getText().toString());
                                                       model_donhang.setSotien(mTongtien);
                                                       model_donhang.setStatus("Đang xử lý");
                                                       model_donhang.setTime("Ngày: " + day + " Giờ: " + time);
                                                       model_donhang.setTaikhoan(list_taikhoan.get(0).getTaikhoan());
                                                       model_donhang.setStype("TĂNG LIKE");
                                                       model_donhang.setSoluong(Integer.parseInt(soluong.getText().toString()));
                                                       model_donhang.setCurren_time(currentime);
                                                       model_donhang.setIsfinisnh(1);

                                                       FirebaseDatabase.getInstance().getReference().child("don hang admin").child(currentime + "")
                                                               .setValue(model_donhang).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void aVoid) {

                                                               nhaplink.setText("");
                                                               tongtien.setText("Chi phí:");
                                                               soluong.setText("");
                                                               progress_thanhtoan.cancel();
                                                               dialog.cancel();
                                                               Toast.makeText(getApplicationContext(), "Thanh toán thành công, like sẽ sớm được gửi tới bạn", Toast.LENGTH_SHORT).show();
                                                               Toast.makeText(getApplicationContext(), "Cảm ơn đã xử dụng dịch vụ", Toast.LENGTH_SHORT).show();

                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception e) {
                                                               Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                                           }
                                                       });

                                                       FirebaseDatabase.getInstance().getReference().child("don hang user").child(mSdt).child(currentime + "")
                                                               .setValue(model_donhang).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                           @Override
                                                           public void onSuccess(Void aVoid) {
                                                               dialog.cancel();
                                                           }
                                                       }).addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception e) {
                                                               Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
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
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                }
                            });

                    }else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }
    private void dialog_sodu_khongdu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Số dư không đủ, vui lòng nạp thêm tiền để thực hiện giao dịch.");
        builder.setPositiveButton("Nạp tiền", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(getApplicationContext(), Naptien.class));

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void set_toolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        viewfliper = findViewById(R.id.viewfliper);
        toolbar = findViewById(R.id.toolbar);
        giahientai = findViewById(R.id.giahientai);
        nhaplink = findViewById(R.id.nhaplink);
        soluong = findViewById(R.id.soluong);
        sodu = findViewById(R.id.sodu);
        tongtien = findViewById(R.id.tongtien);
        list_taikhoan = new ArrayList<>();
        list_banggia = new ArrayList<>();
        list_viewfliper = new ArrayList<>();
        decimalFormat = new DecimalFormat("###,###,###");
    }
}
