package com.example.crud_firestore;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder  extends RecyclerView.ViewHolder {

    TextView name,mail;
    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
        name= itemView.findViewById(R.id.textView_Name);
        mail= itemView.findViewById(R.id.textView2_mail);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickListener.onItemClick(v, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mclickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });
    }


    private ViewHolder.ClickListener mclickListener;

    public interface ClickListener{

void onItemClick(View view,int position);
void onItemLongClick(View view,int position);

    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){

        mclickListener = clickListener;

    }

}
