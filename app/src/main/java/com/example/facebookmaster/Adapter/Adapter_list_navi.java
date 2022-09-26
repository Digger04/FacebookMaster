package com.example.facebookmaster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.facebookmaster.R;
import com.example.facebookmaster.model.model_list_navi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_list_navi extends BaseAdapter {

    private Context context;
    private ArrayList<model_list_navi> arrayList;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Adapter_list_navi(Context context, ArrayList<model_list_navi> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public class Viewholder{

        private CircleImageView circlerview;
        private TextView txt_menu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder viewholder = null;

        if (convertView == null) {
            viewholder = new Viewholder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_list_menu_trangchu, null);
            viewholder.circlerview = convertView.findViewById(R.id.circlerview);
            viewholder.txt_menu = convertView.findViewById(R.id.txt_menu);
            convertView.setTag(viewholder);
        }else {
            viewholder = (Viewholder) convertView.getTag();
        }
        model_list_navi model_list_navi = arrayList.get(position);
        Picasso.with(context).load(model_list_navi.getCirclerview()).into(viewholder.circlerview);
        viewholder.txt_menu.setText(model_list_navi.getTxt_menu());

        return convertView;
    }
}
