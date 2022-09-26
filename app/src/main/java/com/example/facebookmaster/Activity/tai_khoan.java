package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.Adapter.Adapter_listdon_canhan;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_donhang;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class tai_khoan extends AppCompatActivity {

    private ImageView anhbia;
    private CircleImageView avata;
    private TextView time, ten, diachi, sdt, txt_nhatki, sodu, fb;
    private String mSdt;
    private ArrayList<model_taikhoan> arrayList;
    private String mPath_bia;
    private TextView edit;
    private Toolbar toolbar;
    private RecyclerView list_lichsu;
    private Adapter_listdon_canhan adapter_listdon_canhan;
    private ArrayList<model_donhang> list_donhang;

    private ProgressDialog progress_load_image;

    private String mEdit_diachi;
    private String mPath_avata;
    private String mEdit_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan);

        getintent();
        anhxa();
        get_database();
        set_progress_load_image();
        set_toobar();
        get_donhang();
        see_avata();

    }

    private void see_avata() {
        avata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Dialog dialog1 = new Dialog(tai_khoan.this);
                dialog1.setContentView(R.layout.see_image);
                ImageView see_img;
                see_img = dialog1.findViewById(R.id.see_img);
                Glide.with(getApplicationContext()).load(arrayList.get(0).getAvata()).into(see_img);
                dialog1.show();

                return false;
            }
        });
    }

    public void click_anhbia(View view ) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.see_image);
        ImageView see_img;
        see_img = dialog.findViewById(R.id.see_img);
        Glide.with(getApplicationContext()).load(arrayList.get(0).getAnhbia()).into(see_img);
        dialog.show();
    }

    private void get_donhang() {
        FirebaseDatabase.getInstance().getReference().child("don hang user").child(mSdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    list_donhang.add(dataSnapshot.getValue(model_donhang.class));
                    adapter_listdon_canhan.notifyDataSetChanged();
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

    private void set_toobar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void set_progress_load_image() {
        progress_load_image = new ProgressDialog(this);
        progress_load_image.setCancelable(false);
        progress_load_image.setTitle("Đang load...");
    }

    public void add_avata (View view) {

        Toast.makeText(getApplicationContext(), "Giữ để xem avata", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 321);
    }

    public void edit_inf(View view) {
        dialog_edit_inf();
    }

    public void dialog_edit_inf() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.row_edit_inf);
        dialog.setCanceledOnTouchOutside(false);
        final EditText edit_diachi = dialog.findViewById(R.id.edit_diachi);
        final EditText edit_link = dialog.findViewById(R.id.edit_link);
        Button edit_ok = dialog.findViewById(R.id.edit_ok);
        Button edit_huy = dialog.findViewById(R.id.edit_huy);

        edit_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        edit_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEdit_diachi = edit_diachi.getText().toString();
                mEdit_link = edit_link.getText().toString();

                model_taikhoan model_taikhoan = new model_taikhoan();

                model_taikhoan.setSdt(arrayList.get(0).getSdt());
                model_taikhoan.setAnhbia(arrayList.get(0).getAnhbia());
                model_taikhoan.setDiachi(mEdit_diachi);
                model_taikhoan.setSodu(arrayList.get(0).getSodu());
                model_taikhoan.setTaikhoan(arrayList.get(0).getTaikhoan());
                model_taikhoan.setMatkhau(arrayList.get(0).getMatkhau());
                model_taikhoan.setAvata(arrayList.get(0).getAvata());
                model_taikhoan.setDate(arrayList.get(0).getDate());
                model_taikhoan.setFacebook(mEdit_link);

                FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).child("information").setValue(model_taikhoan)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        diachi.setText("Địa chỉ: " + mEdit_diachi);
                        fb.setText("Facebook: " + mEdit_link);
                        dialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    public void add_bia_image(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {

            progress_load_image.show();

            FirebaseStorage.getInstance().getReference().child(mSdt).child("anhbia").putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mPath_bia = taskSnapshot.getDownloadUrl().toString();
                    Glide.with(getApplicationContext()).load(mPath_bia).into(anhbia);

                    final model_taikhoan model_taikhoan = new model_taikhoan();
                    model_taikhoan.setAnhbia(mPath_bia);
                    model_taikhoan.setDate(arrayList.get(0).getDate());
                    model_taikhoan.setAvata(arrayList.get(0).getAvata());
                    model_taikhoan.setMatkhau(arrayList.get(0).getMatkhau());
                    model_taikhoan.setTaikhoan(arrayList.get(0).getTaikhoan());
                    model_taikhoan.setSodu(arrayList.get(0).getSodu());
                    model_taikhoan.setDiachi(arrayList.get(0).getSodu() + "");
                    model_taikhoan.setFacebook(arrayList.get(0).getFacebook());
                    model_taikhoan.setDiachi(arrayList.get(0).getDiachi());
                    model_taikhoan.setSdt(mSdt);
                    model_taikhoan.setQuyentk(arrayList.get(0).getQuyentk());

                    FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).child("information").setValue(model_taikhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    if (dataSnapshot.exists()) {
                                        arrayList.clear();
                                        arrayList.add(dataSnapshot.getValue(com.example.facebookmaster.model.model_taikhoan.class));
                                        progress_load_image.cancel();
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
                            progress_load_image.cancel();
                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress_load_image.cancel();
                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            });

        }else if (requestCode == 321 && resultCode == RESULT_OK && data != null) {

            progress_load_image.show();

            FirebaseStorage.getInstance().getReference().child(mSdt).child("avata").putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    
                    mPath_avata = taskSnapshot.getDownloadUrl().toString();
                    Glide.with(getApplicationContext()).load(mPath_avata).into(avata);

                    model_taikhoan model_taikhoan = new model_taikhoan();
                    model_taikhoan.setAnhbia(arrayList.get(0).getAnhbia());
                    model_taikhoan.setDate(arrayList.get(0).getDate());
                    model_taikhoan.setAvata(mPath_avata);
                    model_taikhoan.setMatkhau(arrayList.get(0).getMatkhau());
                    model_taikhoan.setTaikhoan(arrayList.get(0).getTaikhoan());
                    model_taikhoan.setSodu(arrayList.get(0).getSodu());
                    model_taikhoan.setDiachi(arrayList.get(0).getSodu() + "");
                    model_taikhoan.setFacebook(arrayList.get(0).getFacebook());
                    model_taikhoan.setDiachi(arrayList.get(0).getDiachi());
                    model_taikhoan.setSdt(mSdt);
                    model_taikhoan.setQuyentk(arrayList.get(0).getQuyentk());
                    
                    FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).child("information").setValue(model_taikhoan)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progress_load_image.cancel();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_load_image.cancel();
                            Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress_load_image.cancel();
                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void get_database() {

        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {

                    arrayList.add(dataSnapshot.getValue(model_taikhoan.class));
                    set_data();
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
        }
    }

    private void set_data() {

        Glide.with(getApplicationContext()).load(arrayList.get(0).getAvata()).into(avata);
        Glide.with(getApplicationContext()).load(arrayList.get(0).getAnhbia()).into(anhbia);
        time.setText("Ngày tham gia: \n" + arrayList.get(0).getDate());
        ten.setText(arrayList.get(0).getTaikhoan());
        sdt.setText("Số điện thoại: " + arrayList.get(0).getSdt());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        sodu.setText("Số dư: " + decimalFormat.format(arrayList.get(0).getSodu()) + " vnd");
        diachi.setText("Địa chỉ: " + arrayList.get(0).getDiachi());
        fb.setText("Facebook: " + arrayList.get(0).getFacebook());
    }

    private void anhxa() {
        anhbia = findViewById(R.id.anhbia);
        avata = findViewById(R.id.avata);
        time = findViewById(R.id.time);
        ten = findViewById(R.id.ten);
        diachi = findViewById(R.id.diachi);
        sdt = findViewById(R.id.sdt);
        txt_nhatki = findViewById(R.id.txt_nhatki);
        sodu = findViewById(R.id.sodu);
        fb = findViewById(R.id.fb);
        toolbar = findViewById(R.id.toolbar);
        list_lichsu = findViewById(R.id.list_lichsu);

        edit = findViewById(R.id.edit);

        arrayList = new ArrayList<>();
        list_donhang = new ArrayList<>();
        adapter_listdon_canhan = new Adapter_listdon_canhan(getApplicationContext(), list_donhang);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        list_lichsu.setLayoutManager(linearLayoutManager);
        list_lichsu.setHasFixedSize(true);
        list_lichsu.setAdapter(adapter_listdon_canhan);
    }
}
