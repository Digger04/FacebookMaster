package com.example.facebookmaster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.facebookmaster.R;
import com.example.facebookmaster.model.model_tinnhan;

import java.util.LinkedList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_tinnhan extends RecyclerView.Adapter<Adapter_tinnhan.Viewholder>  {

    private Context context;
    private LinkedList<model_tinnhan> linkedList;
    private long ID;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LinkedList<model_tinnhan> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList<model_tinnhan> linkedList) {
        this.linkedList = linkedList;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Adapter_tinnhan(Context context, LinkedList<model_tinnhan> linkedList, long ID) {
        this.context = context;
        this.linkedList = linkedList;
        this.ID = ID;
    }

    private final int send_message = 100;
    private final int send_picture = 101;
    private final int receiver_message = 102;
    private final int receiver_picture = 103;
    private final int send_message_top = 104;
    private final int send_message_bottom = 105;
    private final int send_message_right = 106;
    private final int receiver_message_left = 107;
    private final int receiver_message_top = 108;
    private final int receiver_message_bottom = 109;

    @Override
    public int getItemViewType(int position) {

        if (linkedList.get(position).getID() == ID){
            if (linkedList.get(position).getType()==1){
                return send_picture;
            }else if (linkedList.get(position).getType()==2){
                return send_picture;
            }else {
                if (linkedList.size()==1){
                    return send_message;
                }else if (linkedList.size()==2){
                    if (position==0 && linkedList.get(position+1).getID() != ID){
                        return send_message;
                    }else if (position==0 && linkedList.get(position+1).getID() == ID){
                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return send_message;
                            case 2:
                                return send_message;
                            case 3:
                                return send_message_top;
                        }
                    }else if (position==1 && linkedList.get(position-1).getID() != ID){
                        return send_message;
                    }else if (position==1 && linkedList.get(position-1).getID() == ID){
                        switch (linkedList.get(position-1).getType()){
                            case 1:
                                return send_message;
                            case 2:
                                return send_message;
                            case 3:
                                return send_message_bottom;
                        }
                    }
                }else if(linkedList.size()>=3){
                    if (position==0 && linkedList.get(position+1).getID() != ID){
                        return send_message;
                    }else if (position==0 && linkedList.get(position+1).getID() == ID){
                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return send_message;
                            case 2:
                                return send_message;
                            case 3:
                                return send_message_top;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() != ID &&
                            linkedList.get(position+1).getID() != ID){
                        return send_message;
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID()== ID &&
                            linkedList.get(position+1).getID() != ID){

                        switch (linkedList.get(position-1).getType()){
                            case 1:
                                return send_message;
                            case 2:
                                return send_message;
                            case 3:
                                return send_message_bottom;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() != ID &&
                            linkedList.get(position+1).getID() == ID){

                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return send_message;
                            case 2:
                                return send_message;
                            case 3:
                                return send_message_top;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() == ID &&
                            linkedList.get(position+1).getID() == ID){

                        if (linkedList.get(position-1).getType()==1 && linkedList.get(position+1).getType()==1 ||
                                linkedList.get(position-1).getType()==2 && linkedList.get(position+1).getType()==2 ||
                                linkedList.get(position-1).getType()==1 && linkedList.get(position+1).getType()==2 ||
                                linkedList.get(position-1).getType()==2 && linkedList.get(position+1).getType()==1){
                            return send_message;
                        }else if(linkedList.get(position-1).getType()==3 && (linkedList.get(position+1).getType()==1 || linkedList.get(position+1).getType()==2) ){
                            return send_message_bottom;
                        }else if((linkedList.get(position-1).getType()==1 || linkedList.get(position-1).getType()==2) &&  linkedList.get(position+1).getType()==3){
                            return send_message_top;
                        }else if(linkedList.get(position-1).getType()==3 && linkedList.get(position-1).getType()==3){
                            return send_message_right;
                        }
                    }else if (position==(linkedList.size()-1)){

                        if (linkedList.get(position-1).getID() != ID){
                            return send_message;
                        }else {
                            switch (linkedList.get(position-1).getType()){
                                case 1:
                                    return send_message;
                                case 2:
                                    return send_message;
                                case 3:
                                    return send_message_bottom;
                            }
                        }
                    }
                }
            }
        }else {
            if (linkedList.get(position).getType()==1){
                return receiver_picture;
            }else if (linkedList.get(position).getType()==2){
                return receiver_picture;
            }else {
                if (linkedList.size()==1){
                    return receiver_message;
                }else if (linkedList.size()==2){
                    if (position==0 && linkedList.get(position+1).getID() == ID){
                        return receiver_message;
                    }else if (position==0 && linkedList.get(position+1).getID() != ID){
                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return receiver_message;
                            case 2:
                                return receiver_message;
                            case 3:
                                return receiver_message_top;
                        }
                    }else if (position==1 && linkedList.get(position-1).getID() == ID){
                        return receiver_message;
                    }else if (position==1 && linkedList.get(position-1).getID() != ID){
                        switch (linkedList.get(position-1).getType()){
                            case 1:
                                return receiver_message;
                            case 2:
                                return receiver_message;
                            case 3:
                                return receiver_message_bottom;
                        }
                    }
                }else if(linkedList.size()>=3){
                    if (position==0 && linkedList.get(position+1).getID() == ID){
                        return receiver_message;
                    }else if (position==0 && linkedList.get(position+1).getID() != ID){
                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return receiver_message;
                            case 2:
                                return receiver_message;
                            case 3:
                                return receiver_message_top;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() == ID &&
                            linkedList.get(position+1).getID() == ID){
                        return receiver_message;
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() != ID &&
                            linkedList.get(position+1).getID() == ID){

                        switch (linkedList.get(position-1).getType()){
                            case 1:
                                return receiver_message;
                            case 2:
                                return receiver_message;
                            case 3:
                                return receiver_message_bottom;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() == ID &&
                            linkedList.get(position+1).getID() != ID){

                        switch (linkedList.get(position+1).getType()){
                            case 1:
                                return receiver_message;
                            case 2:
                                return receiver_message;
                            case 3:
                                return receiver_message_top;
                        }
                    }else if (position>0 && position < (linkedList.size()-1) &&
                            linkedList.get(position-1).getID() != ID &&
                            linkedList.get(position+1).getID() != ID){

                        if (linkedList.get(position-1).getType()==1 && linkedList.get(position+1).getType()==1 ||
                                linkedList.get(position-1).getType()==2 && linkedList.get(position+1).getType()==2 ||
                                linkedList.get(position-1).getType()==1 && linkedList.get(position+1).getType()==2 ||
                                linkedList.get(position-1).getType()==2 && linkedList.get(position+1).getType()==1){
                            return receiver_message;
                        }else if(linkedList.get(position-1).getType()==3 && (linkedList.get(position+1).getType()==1 || linkedList.get(position+1).getType()==2) ){
                            return receiver_message_bottom;
                        }else if((linkedList.get(position-1).getType()==1 || linkedList.get(position-1).getType()==2) &&  linkedList.get(position+1).getType()==3){
                            return receiver_message_top;
                        }else if(linkedList.get(position-1).getType()==3 && linkedList.get(position-1).getType()==3){
                            return receiver_message_left;
                        }
                    }else if (position==(linkedList.size()-1)){

                        if (linkedList.get(position-1).getID() == ID){
                            return receiver_message;
                        }else {
                            switch (linkedList.get(position-1).getType()){
                                case 1:
                                    return receiver_message;
                                case 2:
                                    return receiver_message;
                                case 3:
                                    return receiver_message_bottom;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case send_message:
                view = LayoutInflater.from(context).inflate(R.layout.send_message, parent,false);
                break;
            case receiver_message:
                view = LayoutInflater.from(context).inflate(R.layout.receiver_message, parent,false);
                break;
            case send_picture:
                view = LayoutInflater.from(context).inflate(R.layout.send_picture, parent, false);
                break;
            case receiver_picture:
                view = LayoutInflater.from(context).inflate(R.layout.receiver_picture,parent,false);
                break;
            case send_message_bottom:
                view = LayoutInflater.from(context).inflate(R.layout.send_message_bottom, parent, false);
                break;
            case send_message_right:
                view = LayoutInflater.from(context).inflate(R.layout.sned_message_right, parent, false);
                break;
            case send_message_top:
                view = LayoutInflater.from(context).inflate(R.layout.send_message_top, parent, false);
                break;
            case receiver_message_bottom:
                view = LayoutInflater.from(context).inflate(R.layout.receiver_message_bottom, parent, false);
                break;
            case receiver_message_top:
                view = LayoutInflater.from(context).inflate(R.layout.receiver_message_top, parent, false);
                break;
            case receiver_message_left:
                view = LayoutInflater.from(context).inflate(R.layout.receiver_left, parent, false);
                break;

        }
        return new Viewholder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        switch (holder.getItemViewType()) {
            case send_message:
                holder.time.setText(linkedList.get(position).getTime());
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                break;
            case receiver_message:
                holder.time.setText(linkedList.get(position).getTime());
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                Glide.with(context).load(linkedList.get(position).getAvata()).into(holder.avata);

                holder.avata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "avata", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case send_picture:
                holder.time.setText(linkedList.get(position).getTime());
                Glide.with(context).load(linkedList.get(position).getPicture()).into(holder.picture);

                holder.picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "hinh anh", Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case receiver_picture:
                holder.time.setText(linkedList.get(position).getTime());
                Glide.with(context).load(linkedList.get(position).getPicture()).into(holder.picture);
                Glide.with(context).load(linkedList.get(position).getAvata()).into(holder.avata);

                holder.avata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context, "avata", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.picture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "hinh anh", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case send_message_top:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                holder.time.setText(linkedList.get(position).getTime());
                break;
            case send_message_bottom:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                break;
            case send_message_right:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                break;
            case receiver_message_top:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                holder.time.setText(linkedList.get(position).getTime());
                Glide.with(context).load(linkedList.get(position).getAvata()).into(holder.avata);
                break;
            case receiver_message_bottom:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                break;
            case receiver_message_left:
                holder.tinnhan.setText(linkedList.get(position).getMessage());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView tinnhan;
        private CircleImageView avata;
        private ImageView picture;
        private TextView time;

        public Viewholder(@NonNull View itemView, int viewType) {
            super(itemView);

            switch (viewType) {
                case send_message:
                    time = itemView.findViewById(R.id.time);
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
                case receiver_message:
                    time = itemView.findViewById(R.id.time);
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    avata = itemView.findViewById(R.id.avata);
                    break;
                case send_picture:
                    time = itemView.findViewById(R.id.time);
                    picture = itemView.findViewById(R.id.picture);
                    break;
                case receiver_picture:
                    time = itemView.findViewById(R.id.time);
                    picture = itemView.findViewById(R.id.picture);
                    avata = itemView.findViewById(R.id.avata);
                    break;
                case send_message_top:
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    time = itemView.findViewById(R.id.time);
                    break;
                case send_message_right:
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
                case send_message_bottom:
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
                case receiver_message_top:
                    avata = itemView.findViewById(R.id.avata);
                    time = itemView.findViewById(R.id.time);
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
                case receiver_message_bottom:
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
                case receiver_message_left:
                    tinnhan = itemView.findViewById(R.id.tinnhan);
                    break;
            }
        }
    }
}
