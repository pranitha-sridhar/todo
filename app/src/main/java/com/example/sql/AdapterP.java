package com.example.sql;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class AdapterP extends RecyclerView.Adapter<AdapterP.viewH>{
    private ArrayList<Model> modelArrayList;
    private OnItemClickListener mlistener;
    private ItemCheckListener clistener;

    public AdapterP(ArrayList<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener, ItemCheckListener itemCheckListener){
        this.mlistener=itemClickListener;
        this.clistener=itemCheckListener;
    }

    @NonNull
    @Override
    public viewH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        AdapterP.viewH vh=new AdapterP.viewH(view,mlistener,clistener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewH holder, int position) {
        Model model=modelArrayList.get(position);
        holder.text.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface ItemCheckListener{
        void onItemCheck(int position);
    }

    public class viewH extends RecyclerView.ViewHolder {
        TextView text;
        CheckBox checkBox;
        public viewH(@NonNull View itemView, final OnItemClickListener listener, final ItemCheckListener checkListener) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            checkBox=itemView.findViewById(R.id.checkbox2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position=getAdapterPosition();
                        listener.onItemClick(position);
                    }
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(checkListener!=null){
                        if(isChecked){
                        int position=getAdapterPosition();
                        checkListener.onItemCheck(position);}
                        
                    }
                }
            });

        }
    }
}
