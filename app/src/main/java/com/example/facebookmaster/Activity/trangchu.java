package com.example.facebookmaster.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.Adapter.Adapter_list_navi;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_banggia;
import com.example.facebookmaster.model.model_list_navi;
import com.example.facebookmaster.model.model_taikhoan;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class trangchu extends AppCompatActivity {

    private ViewFlipper viewfliper;
    private ImageView like, share, follow, chat;
    private Toolbar toolbar;
    private CircleImageView avata_toolbar;
    private ImageView img_background;
    private DrawerLayout layout_full;

    private TextView gia_like, gia_share, gia_follow;

    private ArrayList<String> arrayList;

    private String mPathavata;
    private String mSdt = "null";

    private ListView list_menu;
    private ArrayList<model_list_navi> arrayList_menu;
    private Adapter_list_navi adapter_list_navi;
    private ArrayList<model_taikhoan> arrayList_taikhoan;
    private ArrayList<model_banggia> list_banggia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        getinten();
        getData();
        anhxa();
        setviewfliper();
       // set_background_trangchu();
        set_rotate_image();
        get_banggia();
    }
    private void set_gia() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia_like.setText("Giá: " + decimalFormat.format(list_banggia.get(0).getGialike()) + " vnd / 1k");
        gia_share.setText("Giá: " + decimalFormat.format(list_banggia.get(0).getGiashare()) + " vnd / 1k");
        gia_follow.setText("Giá: " + decimalFormat.format(list_banggia.get(0).getGiaflollow()) + " vnd / 1k");
    }

    private void get_banggia() {
        FirebaseDatabase.getInstance().getReference().child("bang gia").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    list_banggia.add(dataSnapshot.getValue(model_banggia.class));
                    set_gia();
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

    private void getData() {

        FirebaseDatabase.getInstance().getReference().child("user").child(mSdt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                arrayList_taikhoan.add(dataSnapshot.getValue(model_taikhoan.class));
                mPathavata = arrayList_taikhoan.get(0).getAvata();
                adapter_list_navi.notifyDataSetChanged();
                set_avata_toolbar();
                set_menu();
                list_menu_clicker();
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

    public void click_like(View view) {
        Intent intent2 = new Intent(getApplicationContext(), tanglike.class);
        intent2.putExtra(console.SAVE_SDT, mSdt);
        intent2.putExtra(console.SAVE_AVATA, mPathavata);
        startActivity(intent2);
    }
    public void click_share(View view) {
        Intent intent2 = new Intent(getApplicationContext(), TangShare.class);
        intent2.putExtra(console.SAVE_SDT, mSdt);
        intent2.putExtra(console.SAVE_AVATA, mPathavata);
        startActivity(intent2);
    }
    public void click_follow(View view) {
        Intent intent2 = new Intent(getApplicationContext(), TangFollow.class);
        intent2.putExtra(console.SAVE_SDT, mSdt);
        intent2.putExtra(console.SAVE_AVATA, mPathavata);
        startActivity(intent2);
    }
    public void click_hotro(View view) {
        Intent intent3 = new Intent(getApplicationContext(), chat.class);
        intent3.putExtra(console.SAVE_SDT, mSdt);
        intent3.putExtra(console.SAVE_AVATA, mPathavata);
        startActivity(intent3);
    }

    private void setdialog_exit() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bạn muốn đăng xuất");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void list_menu_clicker() {
        list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), tai_khoan.class);
                        intent.putExtra(console.SAVE_SDT, mSdt);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(), tanglike.class);
                        intent2.putExtra(console.SAVE_SDT, mSdt);
                        intent2.putExtra(console.SAVE_AVATA, mPathavata);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getApplicationContext(), TangShare.class);
                        intent3.putExtra(console.SAVE_SDT, mSdt);
                        intent3.putExtra(console.SAVE_AVATA, mPathavata);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getApplicationContext(), TangFollow.class);
                        intent4.putExtra(console.SAVE_SDT, mSdt);
                        intent4.putExtra(console.SAVE_AVATA, mPathavata);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent7 = new Intent(getApplicationContext(), Naptien.class);
                        intent7.putExtra(console.SAVE_SDT, mSdt);
                        startActivity(intent7);
                        break;
                    case 5:
                        Intent intent5 = new Intent(getApplicationContext(), chat.class);
                        intent5.putExtra(console.SAVE_SDT, mSdt);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(getApplicationContext(), CaiDat.class);
                        intent6.putExtra(console.SAVE_SDT,mSdt);
                        startActivity(intent6);
                        break;
                    case 7:
                        setdialog_exit();
                        break;
                    case 8:
                        Intent intent8 = new Intent(getApplicationContext(), QuanLy.class);
                        intent8.putExtra(console.SAVE_SDT, mSdt);
                        startActivity(intent8);
                }

            }
        });
    }

    private void set_menu() {
        arrayList_menu.add(new model_list_navi(mPathavata, "Tài khoản"));
        arrayList_menu.add(new model_list_navi("https://image.similarpng.com/very-thumbnail/2020/06/Thumb-up-icon-like-transparent-PNG.png",
                "Tăng like"));
        arrayList_menu.add(new model_list_navi("https://w1.pngwing.com/pngs/880/588/png-transparent-facebook-social-media-icons-share-icon-social-networking-service-sharing-blue-line-circle-symbol.png",
                "Tăng share"));
        arrayList_menu.add(new model_list_navi("https://www.pinclipart.com/picdir/middle/16-164866_instagram-follow-button-transparent-clipart.png",
                "Tăng follow"));
        arrayList_menu.add(new model_list_navi("https://banthe365.vn/assets/data/avatar/service/52ce059d1a7ead8a7759c26cf32693d0.png?v=1575622600",
                "Nạp tiền"));
        arrayList_menu.add(new model_list_navi("https://w7.pngwing.com/pngs/767/808/png-transparent-online-chat-chat-room-computer-icons-buble-miscellaneous-blue-online-chat.png",
                "Hỗ trợ"));
        arrayList_menu.add(new model_list_navi("https://www.clipartmax.com/png/middle/49-494368_setting-clipart-logo-png-setting-icon-in-android.png",
                "Cài đặt"));
        arrayList_menu.add(new model_list_navi("https://cdn.iconscout.com/icon/free/png-256/exit-56-386757.png",
                "Đăng xuất"));
        arrayList_menu.add(new model_list_navi("https://toppng.com/uploads/preview/dm-icon-admin-client-round-icon-administrator-11563182866urbejlvbxt.png",
                "Quản lý"));
        
        if (mSdt.equals("0333690316") || arrayList_taikhoan.get(0).getQuyentk().equals("admin")) {

        }else {
            arrayList_menu.remove(8);
        }
    }

    private void set_rotate_image() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_image);
        like.startAnimation(animation);
        follow.startAnimation(animation);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_image_2);
        share.startAnimation(animation2);
        chat.startAnimation(animation2);
    }

    private void set_background_trangchu() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_background_trangchu);
        img_background.startAnimation(animation);
    }

    private void set_avata_toolbar() {
        if (mPathavata != null) {
            Glide.with(getApplicationContext()).load(mPathavata).into(avata_toolbar);
        }else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_account).into(avata_toolbar);
        }
        avata_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_full.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getinten() {
        if (getIntent() != null) {
            mSdt = getIntent().getStringExtra(console.SAVE_SDT);
        }
    }

    private void setviewfliper() {

        arrayList = new ArrayList<>();
        arrayList.add("https://blog.puziness.com/wp-content/uploads/2018/04/Facebook-Support-Number.png");
        arrayList.add("https://admarket.vn/wp-content/uploads/2018/02/bang-gia-dich-vutang-like-facebook-gia-re-hieu-qua.png");
        arrayList.add("https://fptshop.com.vn/uploads/originals/2017/8/5//636375331739213910_cach-xem-nguoi-khac-theo-doi-minh-tren-facebook-cover.jpg");
        arrayList.add("https://admarket.vn/wp-content/uploads/2014/11/cach-tang-luot-share-tren-facebook2.jpg");

        viewfliper.setFlipInterval(3000);
        viewfliper.setAutoStart(true);

        for (int i = 0; i < arrayList.size(); i ++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getApplicationContext()).load(arrayList.get(i)).into(imageView);
            if (i==0) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), chat.class);
                        intent.putExtra(console.SAVE_SDT, mSdt);
                        startActivity(intent);
                    }
                });
            }else if (i==1) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(getApplicationContext(), tanglike.class);
                        intent2.putExtra(console.SAVE_SDT, mSdt);
                        intent2.putExtra(console.SAVE_AVATA, mPathavata);
                        startActivity(intent2);
                    }
                });
            }else if (i==2) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TangFollow.class);
                        startActivity(intent);
                    }
                });
            }else if (i==3) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), TangShare.class);
                        startActivity(intent);
                    }
                });
            }
            viewfliper.addView(imageView);
        }

        Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_out);
        viewfliper.setInAnimation(in);
        viewfliper.setOutAnimation(out);

    }

    private void anhxa() {
        viewfliper = findViewById(R.id.viewfliper);
        like = findViewById(R.id.like);
        share = findViewById(R.id.share);
        follow = findViewById(R.id.follow);
        chat = findViewById(R.id.chat);
        toolbar = findViewById(R.id.toolbar);
        avata_toolbar = findViewById(R.id.avata_toolbar);
        img_background = findViewById(R.id.img_backgound);
        layout_full = findViewById(R.id.layout_full);
        list_menu = findViewById(R.id.list_menu);
        gia_like = findViewById(R.id.txt_gialike);
        gia_share = findViewById(R.id.txt_giashare);
        gia_follow = findViewById(R.id.txt_giafollow);

        arrayList_menu = new ArrayList<>();
        adapter_list_navi = new Adapter_list_navi(getApplicationContext(), arrayList_menu);
        list_menu.setAdapter(adapter_list_navi);
        arrayList_taikhoan = new ArrayList<>();
        list_banggia = new ArrayList<>();
    }
}
