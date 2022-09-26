package com.example.facebookmaster.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.Adapter.Adapter_tinnhan;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_taikhoan;
import com.example.facebookmaster.model.model_tinnhan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_admin extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView avata_all, avata_admin;
    private RecyclerView list_tinnhan;
    private TextView edit_nhaptn;
    private ImageView send_img;
    private ImageView send_tn;
    private LinkedList<model_tinnhan> linkedList;
    private Adapter_tinnhan adapter_tinnhan;

    private ArrayList<model_taikhoan> list_taikhoan;
    private ArrayList<model_taikhoan> list_admin;
    private String mSdt;
    private String mPathPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);

        get_inten();
        anhxa();
        get_data_taikhoan();
        set_toolbar();
        get_data_tinnhan();
        set_avata();

    }

    public void chat_all(View view) {
        Intent intent = new Intent(getApplicationContext(), chat.class);
        intent.putExtra(console.SAVE_SDT, mSdt);
        startActivity(intent);
    }

    private void set_avata() {
        Picasso.with(getApplicationContext()).load("https://www.nicepng.com/png/detail/131-1318812_avatar-group-icon.png")
                .into(avata_all);
        FirebaseDatabase.getInstance().getReference().child("user").child("0333690316").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    list_admin.add(dataSnapshot.getValue(model_taikhoan.class));
                    Glide.with(getApplicationContext()).load(list_admin.get(0).getAvata()).into(avata_admin);
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

    public void send_tn(View view) {
        if (edit_nhaptn.getText().toString().length() > 0) {

            final String data = edit_nhaptn.getText().toString();

            String ngay = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault()).format(new Date());
            String gio = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            model_tinnhan model_tinnhan = new model_tinnhan();
            model_tinnhan.setAvata(list_taikhoan.get(0).getAvata());
            model_tinnhan.setID(Long.parseLong(list_taikhoan.get(0).getSdt()));
            model_tinnhan.setSdt(mSdt);
            model_tinnhan.setMessage(data);
            model_tinnhan.setType(3);
            model_tinnhan.setPicture("");
            model_tinnhan.setTime("Ngày: "+ ngay + " Giờ: " + gio);

            FirebaseDatabase.getInstance().getReference().child("chat").child("0333690316").child(System.currentTimeMillis() + "").setValue(model_tinnhan)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            edit_nhaptn.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tin nhắn trước", Toast.LENGTH_SHORT).show();
        }
    }

    private void get_data_taikhoan() {
        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.exists()) {
                    list_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));
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

    public void send_img(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {

        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            FirebaseStorage.getInstance().getReference().child("chat").child("0333690316").child(System.currentTimeMillis()+ "")
                    .putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mPathPicture = taskSnapshot.getDownloadUrl().toString();

                    String ngay = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault()).format(new Date());
                    String gio = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    model_tinnhan model_tinnhan = new model_tinnhan();
                    model_tinnhan.setAvata(list_taikhoan.get(0).getAvata());
                    model_tinnhan.setID(Long.parseLong(list_taikhoan.get(0).getSdt()));
                    model_tinnhan.setPicture(mPathPicture);
                    model_tinnhan.setSdt(mSdt);
                    model_tinnhan.setType(1);
                    model_tinnhan.setMessage("");
                    model_tinnhan.setTime("Ngày: "+ ngay + " Giờ: " + gio);

                    FirebaseDatabase.getInstance().getReference().child("chat").child("0333690316").child(System.currentTimeMillis() + "").setValue(model_tinnhan)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            });
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void get_data_tinnhan() {

        FirebaseDatabase.getInstance().getReference().child("chat").child("0333690316").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    linkedList.add(dataSnapshot.getValue(model_tinnhan.class));
                    adapter_tinnhan.notifyItemInserted(linkedList.size());
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
        toolbar = findViewById(R.id.toolbar);
        avata_all = findViewById(R.id.avata_all);
        avata_admin = findViewById(R.id.avata_admin);
        list_tinnhan = findViewById(R.id.list_tinnhan);
        edit_nhaptn = findViewById(R.id.edit_nhaptn);
        send_img = findViewById(R.id.send_img);
        send_tn = findViewById(R.id.send_tn);
        linkedList = new LinkedList<>();
        list_taikhoan = new ArrayList<>();
        list_admin = new ArrayList<>();
        adapter_tinnhan = new Adapter_tinnhan(getApplicationContext(),linkedList, Long.parseLong(mSdt));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        list_tinnhan.setHasFixedSize(true);
        list_tinnhan.setLayoutManager(linearLayoutManager);
        list_tinnhan.setAdapter(adapter_tinnhan);
    }
}

