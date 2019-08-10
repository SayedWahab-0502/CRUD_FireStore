package com.example.crud_firestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder>
{
    Main2Activity main2Activity;
    List<Model> modelList;

    public CustomAdapter(Main2Activity main2Activity, List<Model> modelList)
    {
        this.main2Activity = main2Activity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,parent,false);
        ViewHolder viewHolder= new ViewHolder(itemview);


        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String name=modelList.get(position).getName();
                String mail=modelList.get(position).getMailid();

                Toast.makeText(main2Activity, name+"\n"+mail,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {

                AlertDialog.Builder builder= new AlertDialog.Builder(main2Activity);

                String[] option ={"UPDATE","DELETE"};

                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0)
                        {
                           String id=modelList.get(position).getId();
                           String  name=modelList.get(position).getName();
                            String mail=modelList.get(position).getMailid();

                            Intent intent= new Intent(main2Activity,Update_Data.class);

                            intent.putExtra("uid",id);
                            intent.putExtra("uname",name);
                            intent.putExtra("umail",mail);
                            main2Activity.startActivity(intent);

                        }
                        if (which == 1)
                        {
                            main2Activity.deleteData(position);

                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.name.setText(modelList.get(position).getName());
        holder.mail.setText(modelList.get(position).getMailid());
    }

    @Override
    public int getItemCount()
    {
        return modelList.size();
    }
}

