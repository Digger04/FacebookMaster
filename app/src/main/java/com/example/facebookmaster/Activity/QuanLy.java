package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebookmaster.Adapter.Adaper_list_donhang;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.egl.EGLDisplay;

public class QuanLy extends AppCompatActivity {

    private RecyclerView list_don;
    private ImageView history;
    private Toolbar toolbar;
    private TextView txt_nguoiquanly, danhsachdon;
    private TextView gialike, giashare, giafollow;
    private TextView sdt_naptien, sotien, txt_naptien;
    private Button naptien;
    private Button bt_chinhsua;
    private TextView edit_sdtcapquen;
    private TextView txt_capquyen;
    private Button bt_admin, bt_daily;
    private TextView sodon;
    private String mSdt;
    private int mGialike = 0;
    private int mGiashare = 0;
    private int mGiafollow = 0;

    private ProgressDialog progress_setgia;

    private ArrayList<model_banggia> list_banggia;
    private ArrayList<model_taikhoan> list_taikhoan;
    private ArrayList<model_taikhoan> my_account;

    private String day, time;
    private String mSdt_naptien, mSdt_capquyen;
    private int mSotien = 0;

    private Adaper_list_donhang adaper_list_donhang;
    private ArrayList<model_donhang> list_donhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        get_inten();
        anhxa();
        set_toolbar();
        progress_setgia();
        get_banggia();
        get_taikhoan();
        get_donhang();
    }

    private void get_donhang() {

        FirebaseDatabase.getInstance().getReference().child("don hang admin").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    list_donhang.add(dataSnapshot.getValue(model_donhang.class));
                    adaper_list_donhang.notifyDataSetChanged();
                    sodon.setText("Số đơn: " + list_donhang.size());
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

    private void dialog_xacminh_capquyen_admin() {

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_passwork);

        final EditText dialog_matkhau = dialog.findViewById(R.id.dialog_matkhau);
        Button dialog_ok = dialog.findViewById(R.id.dialog_btok);
        Button dialog_huy = dialog.findViewById(R.id.dialog_bthuy);

        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_matkhau.getText().toString().length() > 0) {

                    if (dialog_matkhau.getText().toString().equals(my_account.get(0).getMatkhau())) {

                        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_capquyen).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.exists()) {

                                    list_taikhoan.clear();
                                    list_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));

                                    model_taikhoan model_taikhoan = new model_taikhoan();
                                    model_taikhoan.setQuyentk("admin");
                                    model_taikhoan.setDiachi(list_taikhoan.get(0).getDiachi());
                                    model_taikhoan.setFacebook(list_taikhoan.get(0).getFacebook());
                                    model_taikhoan.setDate(list_taikhoan.get(0).getDate());
                                    model_taikhoan.setMatkhau(list_taikhoan.get(0).getMatkhau());
                                    model_taikhoan.setSdt(list_taikhoan.get(0).getSdt());
                                    model_taikhoan.setSodu(list_taikhoan.get(0).getSodu());
                                    model_taikhoan.setAnhbia(list_taikhoan.get(0).getAnhbia());
                                    model_taikhoan.setAvata(list_taikhoan.get(0).getAvata());
                                    model_taikhoan.setTaikhoan(list_taikhoan.get(0).getTaikhoan());

                                    FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_capquyen).child("information").setValue(model_taikhoan)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(getApplicationContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                                    edit_sdtcapquen.setText("");
                                                    dialog.cancel();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();

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

    private void dialog_xacminh_capquyen_daily() {

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_passwork);

        final EditText dialog_matkhau = dialog.findViewById(R.id.dialog_matkhau);
        Button dialog_ok = dialog.findViewById(R.id.dialog_btok);
        Button dialog_huy = dialog.findViewById(R.id.dialog_bthuy);

        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_matkhau.getText().toString().length() > 0) {

                    if (dialog_matkhau.getText().toString().equals(my_account.get(0).getMatkhau())) {

                        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_capquyen).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.exists()) {

                                    list_taikhoan.clear();
                                    list_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));

                                    model_taikhoan model_taikhoan = new model_taikhoan();
                                    model_taikhoan.setQuyentk("daily");
                                    model_taikhoan.setDiachi(list_taikhoan.get(0).getDiachi());
                                    model_taikhoan.setFacebook(list_taikhoan.get(0).getFacebook());
                                    model_taikhoan.setDate(list_taikhoan.get(0).getDate());
                                    model_taikhoan.setMatkhau(list_taikhoan.get(0).getMatkhau());
                                    model_taikhoan.setSdt(list_taikhoan.get(0).getSdt());
                                    model_taikhoan.setSodu(list_taikhoan.get(0).getSodu());
                                    model_taikhoan.setAnhbia(list_taikhoan.get(0).getAnhbia());
                                    model_taikhoan.setAvata(list_taikhoan.get(0).getAvata());
                                    model_taikhoan.setTaikhoan(list_taikhoan.get(0).getTaikhoan());

                                    FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_capquyen).child("information").setValue(model_taikhoan)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(getApplicationContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                                    edit_sdtcapquen.setText("");
                                                    dialog.cancel();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();

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

    private void dialog_xacminh_naptien() {

        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_passwork);

        final EditText dialog_matkhau = dialog.findViewById(R.id.dialog_matkhau);
        Button dialog_ok = dialog.findViewById(R.id.dialog_btok);
        Button dialog_huy = dialog.findViewById(R.id.dialog_bthuy);

        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_matkhau.getText().toString().length() > 0) {

                    if (dialog_matkhau.getText().toString().equals(my_account.get(0).getMatkhau())) {

                        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_naptien).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (dataSnapshot.exists()) {

                                    list_taikhoan.clear();
                                    list_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));

                                    model_taikhoan model_taikhoan = new model_taikhoan();
                                    model_taikhoan.setQuyentk(list_taikhoan.get(0).getQuyentk());
                                    model_taikhoan.setDiachi(list_taikhoan.get(0).getDiachi());
                                    model_taikhoan.setFacebook(list_taikhoan.get(0).getFacebook());
                                    model_taikhoan.setDate(list_taikhoan.get(0).getDate());
                                    model_taikhoan.setMatkhau(list_taikhoan.get(0).getMatkhau());
                                    model_taikhoan.setSdt(list_taikhoan.get(0).getSdt());
                                    model_taikhoan.setSodu(list_taikhoan.get(0).getSodu() + mSotien);
                                    model_taikhoan.setAnhbia(list_taikhoan.get(0).getAnhbia());
                                    model_taikhoan.setAvata(list_taikhoan.get(0).getAvata());
                                    model_taikhoan.setTaikhoan(list_taikhoan.get(0).getTaikhoan());

                                    FirebaseDatabase.getInstance().getReference().child("user").child(mSdt_naptien).child("information").setValue(model_taikhoan)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(getApplicationContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                                                    sotien.setText("");
                                                    sdt_naptien.setText("");
                                                    dialog.cancel();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();

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

    public void click_naptien(View view) {

        if (sdt_naptien.getText().toString().length() > 0 && sotien.getText().toString().length() > 0) {

            mSdt_naptien = sdt_naptien.getText().toString().trim();
            mSotien = Integer.parseInt(sotien.getText().toString());

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("user").hasChild(mSdt_naptien)){

                        dialog_xacminh_naptien();

                    }else {
                        Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại và số tiền nạp trước", Toast.LENGTH_SHORT).show();
        }

    }

    public void click_admin(View view) {

        if (edit_sdtcapquen.getText().toString().length() > 0) {

            mSdt_capquyen = edit_sdtcapquen.getText().toString().trim();


            if (edit_sdtcapquen.getText().toString().length() > 0) {

                mSdt_capquyen = edit_sdtcapquen.getText().toString().trim();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("user").hasChild(mSdt_capquyen)) {

                            dialog_xacminh_capquyen_admin();
                        }else {
                            Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại cấp quyền trước", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại cấp quyền trước", Toast.LENGTH_SHORT).show();
        }

    }

    public void click_daily(View view) {

        if (edit_sdtcapquen.getText().toString().length() > 0) {

            mSdt_capquyen = edit_sdtcapquen.getText().toString().trim();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("user").hasChild(mSdt_capquyen)) {

                        dialog_xacminh_capquyen_daily();
                    }else {
                        Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại cấp quyền trước", Toast.LENGTH_SHORT).show();
        }

    }

    private void get_taikhoan() {

        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()){
                    my_account.add(dataSnapshot.getValue(model_taikhoan.class));
                    txt_nguoiquanly.setText("Người quản lý: " + my_account.get(0).getTaikhoan());
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

    private void get_banggia() {

        FirebaseDatabase.getInstance().getReference().child("bang gia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    list_banggia.add(dataSnapshot.getValue(model_banggia.class));

                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    gialike.setText("Giá like: " + decimalFormat.format(list_banggia.get(0).getGialike()) + " vnd / 1k");
                    giafollow.setText("Giá follow: " + decimalFormat.format(list_banggia.get(0).getGiaflollow()) + " vnd / 1k");
                    giashare.setText("Giá share: " + decimalFormat.format(list_banggia.get(0).getGiashare()) + " vnd / 1k");

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

    private void progress_setgia() {
        progress_setgia = new ProgressDialog(this);
        progress_setgia.setTitle("Đang load bảng giá...");
        progress_setgia.setCancelable(false);
    }

    public void set_banggia(View view) {
        dialog_banggia();
    }

    private void dialog_banggia() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_banggia);
        dialog.setCanceledOnTouchOutside(false);

        final EditText dialog_like = dialog.findViewById(R.id.dialog_gialike);
        final EditText dialog_share = dialog.findViewById(R.id.dialog_giashre);
        final EditText dialog_follow = dialog.findViewById(R.id.dialog_follow);
        Button dialog_huy = dialog.findViewById(R.id.dialog_huy);
        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);

        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_like.getText().toString().length() > 0 && dialog_follow.getText().toString().length() > 0
                && dialog_share.getText().toString().length() > 0) {

                    mGiafollow = Integer.parseInt(dialog_follow.getText().toString().trim());
                    mGialike = Integer.parseInt(dialog_like.getText().toString().trim());
                    mGiashare = Integer.parseInt(dialog_share.getText().toString().trim());
                    day = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    dialog_xacminh_banggia();
                    dialog.cancel();

                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    private void dialog_xacminh_banggia() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_passwork);
        dialog.setCanceledOnTouchOutside(false);

        final EditText dialog_matkhau = dialog.findViewById(R.id.dialog_matkhau);
        Button dialog_ok = dialog.findViewById(R.id.dialog_btok);
        Button dialog_huy = dialog.findViewById(R.id.dialog_bthuy);

        dialog_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog_matkhau.getText().toString().length() > 0) {

                    if (dialog_matkhau.getText().toString().equals(my_account.get(0).getMatkhau())) {

                        progress_setgia.show();

                        final model_banggia model_banggia = new model_banggia();
                        model_banggia.setGiaflollow(mGiafollow);
                        model_banggia.setGialike(mGialike);
                        model_banggia.setGiashare(mGiashare);
                        model_banggia.setTime("Ngày: " + day + " Giờ: " + time);

                        FirebaseDatabase.getInstance().getReference().child("bang gia").child("gia").setValue(model_banggia)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        FirebaseDatabase.getInstance().getReference().child("bang gia").addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                list_banggia.clear();
                                                list_banggia.add(dataSnapshot.getValue(com.example.facebookmaster.model.model_banggia.class));
                                                gialike.setText("Giá like: " + list_banggia.get(0).getGialike() + " vnd / 1k");
                                                giafollow.setText("Giá follow: " + list_banggia.get(0).getGiaflollow() + " vnd / 1k");
                                                giashare.setText("Giá share: " + list_banggia.get(0).getGiashare() + " vnd / 1k");
                                                progress_setgia.cancel();
                                                dialog.cancel();
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
                                progress_setgia.cancel();
                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void get_inten() {

        if (getIntent() != null) {
            mSdt = getIntent().getStringExtra(console.SAVE_SDT);
        }
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

        list_don = findViewById(R.id.list_don);
        history = findViewById(R.id.history);
        toolbar = findViewById(R.id.toolbar);
        txt_nguoiquanly = findViewById(R.id.txt_nguoiquanly);
        danhsachdon = findViewById(R.id.danhsachdon);
        gialike = findViewById(R.id.gialike);
        giashare = findViewById(R.id.giashare);
        giafollow = findViewById(R.id.giafollow);
        bt_chinhsua = findViewById(R.id.bt_chinhsua);
        bt_admin = findViewById(R.id.bt_admin);
        bt_daily = findViewById(R.id.bt_daily);
        sdt_naptien = findViewById(R.id.edit_sdtnaptien);
        txt_naptien = findViewById(R.id.txt_naptien);
        sotien = findViewById(R.id.edit_sotien);
        naptien = findViewById(R.id.bt_naptien);
        edit_sdtcapquen = findViewById(R.id.edit_sdtcapquyen);
        sodon = findViewById(R.id.sodon);

        list_banggia = new ArrayList<>();
        list_taikhoan = new ArrayList<>();
        my_account = new ArrayList<>();

        list_donhang = new ArrayList<>();
        adaper_list_donhang = new Adaper_list_donhang(getApplicationContext(), list_donhang);
        list_don.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        list_don.setLayoutManager(linearLayoutManager);
        list_don.setAdapter(adaper_list_donhang);
    }
}
