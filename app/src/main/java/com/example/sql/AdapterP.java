package com.example.sql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class AdapterP extends RecyclerView.Adapter<AdapterP.viewHolder>{
    private ArrayList<Model> modelArrayList;
    private OnItemClickListener mlistener;
    Context context;

    public AdapterP(Context context, ArrayList<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
        this.context=context;
    }

    public void setOnItemClickListener(AdapterP.OnItemClickListener itemClickListener){
        this.mlistener=itemClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        AdapterP.viewHolder vh=new AdapterP.viewHolder(view,mlistener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        final Model model=modelArrayList.get(position);
        holder.text.setText(model.getTitle());

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlistener!=null){
                    mlistener.onItemClick(model,position);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mlistener!=null){
                    mlistener.onItemDelete(model,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (modelArrayList!=null)? modelArrayList.size():0;
    }

    public interface OnItemClickListener{
        void onItemClick(Model model,int position);

        void onItemDelete(Model model,int position);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView delete;
        public viewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            delete=itemView.findViewById(R.id.delete);
        }
    }
}
