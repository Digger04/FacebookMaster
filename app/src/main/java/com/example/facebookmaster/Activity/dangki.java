package com.example.facebookmaster.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.R;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class dangki extends AppCompatActivity {
    
    private TextView ten, matkhau, sdt;
    private Button dangki;
    private String mPathavata;
    private String mPathpicture;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog_dangki;

    private CircleImageView dialog_avata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        
        anhxa();
        dialog();
        set_toolbar();
        progressDialog_dangki();

    }

    private void progressDialog_dangki() {
        progressDialog_dangki = new ProgressDialog(this);
        progressDialog_dangki.setTitle("Đang load");
        progressDialog_dangki.setCancelable(false);
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

    private void dialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Đang load ảnh");
    }
    public void dialog_add_avata() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_signup_addavata);
        dialog.setCanceledOnTouchOutside(false);
        dialog_avata = dialog.findViewById(R.id.add_avata);
        TextView camera = dialog.findViewById(R.id.camera);
        Button bt_dangki = dialog.findViewById(R.id.bt_dangki);
        TextView thuvien = dialog.findViewById(R.id.thuvien);

        thuvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,111);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Vui lòng cấp quyền truy cập camera cho ứng dụng",
                            Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(dangki.this, new String[] {
                            Manifest.permission.CAMERA
                    },1);
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 222);
                }
            }
        });
        bt_dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPathavata != null ) {
                    finish();
                    Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Chưa có ảnh đại diện", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {

        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {
            progressDialog.show();
            FirebaseStorage.getInstance().getReference().child(sdt.getText().toString()).putFile(data.getData())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mPathavata = taskSnapshot.getDownloadUrl().toString();
                            Glide.with(getApplicationContext()).load(mPathavata).into(dialog_avata);

                            String ngay = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault()).format(new Date());
                            String gio = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                            model_taikhoan model_taikhoan = new model_taikhoan();
                            model_taikhoan.setMatkhau(matkhau.getText().toString());
                            model_taikhoan.setTaikhoan(ten.getText().toString());
                            model_taikhoan.setAvata(mPathavata);
                            model_taikhoan.setSdt(sdt.getText().toString());
                            model_taikhoan.setDate("Ngày: "+ ngay + " Giờ: " + gio);
                            model_taikhoan.setSodu(0);
                            model_taikhoan.setQuyentk("Người dùng");
                            model_taikhoan.setAnhbia("");

                            FirebaseDatabase.getInstance().getReference().child("user").child(sdt.getText().toString()).child("information").setValue(model_taikhoan)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.cancel();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.cancel();
                }
            });
        }else if (requestCode == 222 && resultCode == RESULT_OK && data != null) {
            progressDialog.show();
            Bundle dataok = data.getExtras();
            Bitmap bitmap = (Bitmap) dataok.get("data");
            ByteArrayOutputStream ok = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ok);
            byte [] uri = ok.toByteArray();
            Toast.makeText(getApplicationContext(), uri + "", Toast.LENGTH_SHORT).show();
            FirebaseStorage.getInstance().getReference().child(sdt.getText().toString()).putBytes(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mPathpicture = taskSnapshot.getDownloadUrl().toString();
                    Glide.with(getApplicationContext()).load(mPathpicture).into(dialog_avata);

                    model_taikhoan model_taikhoan = new model_taikhoan();
                    model_taikhoan.setAvata(mPathpicture);
                    model_taikhoan.setSdt(sdt.getText().toString());
                    model_taikhoan.setMatkhau(matkhau.getText().toString());
                    model_taikhoan.setTaikhoan(ten.getText().toString());
                    model_taikhoan.setQuyentk("Người dùng");

                    FirebaseDatabase.getInstance().getReference().child("user").child(sdt.getText().toString())
                            .child("information").setValue(model_taikhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressDialog.cancel();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            });

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void dangki(View view) {
        if (ten.getText().toString().length() > 0 && sdt.getText().toString().length() > 0
        && matkhau.getText().toString().length() > 0) {
            final model_taikhoan model_taikhoan = new model_taikhoan();
            model_taikhoan.setMatkhau(matkhau.getText().toString());
            model_taikhoan.setTaikhoan(ten.getText().toString());
            model_taikhoan.setAvata(mPathavata);
            model_taikhoan.setSdt(sdt.getText().toString());
            model_taikhoan.setQuyentk("Người dùng");
            progressDialog_dangki.show();

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("user").hasChild(sdt.getText().toString())) {

                        progressDialog_dangki.cancel();
                        Toast.makeText(getApplicationContext(), "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();

                    }else {

                        FirebaseDatabase.getInstance().getReference().child("user").child(sdt.getText().toString()).
                                child("information").setValue(model_taikhoan)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog_add_avata();
                                        progressDialog_dangki.cancel();
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
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa() {
        ten = findViewById(R.id.ten);
        matkhau = findViewById(R.id.matkhau);
        sdt = findViewById(R.id.sdt);
        dangki = findViewById(R.id.dangki);
        toolbar = findViewById(R.id.toolbar);
    }
}
