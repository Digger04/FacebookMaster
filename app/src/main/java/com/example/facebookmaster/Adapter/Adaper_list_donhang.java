package com.example.facebookmaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebookmaster.Activity.Webview_check;
import com.example.facebookmaster.Activity.edit_donhang;
import com.example.facebookmaster.R;
import com.example.facebookmaster.key.console;
import com.example.facebookmaster.model.model_donhang;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Adaper_list_donhang extends RecyclerView.Adapter<Adaper_list_donhang.Viewholder> {

    private Context context;
    private List<model_donhang> list;

    public List<model_donhang> getList() {
        return list;
    }

    public void setList(List<model_donhang> list) {
        this.list = list;
    }

    public Adaper_list_donhang(Context context, List<model_donhang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.row_list_donhang, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        holder.loaigiaodich.setText("Loại giao dịch: " +list.get(position).getStype());
        holder.baohanh.setText("Bảo hành: " +list.get(position).getBaohanh());
        holder.time.setText("Thời gian: " +list.get(position).getTime());
        holder.status.setText("Trạng thái: " + list.get(position).getStatus());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.sotien.setText("Số tiền: " +decimalFormat.format(list.get(position).getSotien()) + " vnd");
        holder.ghichu.setText("Ghi chú: " +list.get(position).getGhichu());
        holder.linkbaiviet.setText("Link: " +list.get(position).getLink());
        holder.taikhoan.setText("Tài khoản: " +list.get(position).getTaikhoan());
        holder.sdt.setText("Số điện thoại: " +list.get(position).getSdt());
        holder.soluong.setText("Số lượng: " + decimalFormat.format(list.get(position).getSoluong()));
        holder.madon.setText("Mã đơn: " + Integer.parseInt(String.valueOf(position + 1)));

        if (list.get(position).getHientai() == 0) {

        }else {
            holder.hientai.setText("Hiện tại: " + list.get(position).getHientai());
        }
        if (list.get(position).getHoanthanh() == 0) {

        }else {
            holder.hoanthanh.setText("Hoàn thành: " + list.get(position).getHoanthanh());
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, edit_donhang.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.img_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Webview_check.class);
                intent.putExtra(console.LINK, list.get(position).getLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView loaigiaodich;
        private TextView sdt;
        private TextView taikhoan;
        private TextView linkbaiviet;
        private TextView ghichu;
        private TextView sotien;
        private TextView status;
        private TextView time;
        private TextView baohanh;
        private TextView soluong;
        private ImageView edit;
        private ImageView img_link;
        private TextView hientai;
        private TextView hoanthanh;
        private Button batdau;
        private TextView madon;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            loaigiaodich = itemView.findViewById(R.id.loaigiaodich);
            sdt = itemView.findViewById(R.id.sdt);
            taikhoan = itemView.findViewById(R.id.taikhoan);
            linkbaiviet = itemView.findViewById(R.id.linkbaiviet);
            ghichu = itemView.findViewById(R.id.ghichu);
            sotien = itemView.findViewById(R.id.sotien);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.time);
            baohanh = itemView.findViewById(R.id.baohanh);
            soluong = itemView.findViewById(R.id.soluong);
            edit = itemView.findViewById(R.id.img_edit);
            img_link = itemView.findViewById(R.id.img_link);
            hientai = itemView.findViewById(R.id.hientai);
            hoanthanh = itemView.findViewById(R.id.hoanthanh);
            batdau = itemView.findViewById(R.id.batdau);
            madon = itemView.findViewById(R.id.madon);
        }
    }
}
