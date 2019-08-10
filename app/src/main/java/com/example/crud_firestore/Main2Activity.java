package com.example.crud_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Model>  modelList = new ArrayList<>();
    FirebaseFirestore db;
    CustomAdapter customAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressDialog= new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       showData();
    }

    private void showData()
    {

        progressDialog.setTitle("Loading Data...");
        progressDialog.show();

        db.collection("DATA")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        modelList.clear();
                        progressDialog.dismiss();

                        for (DocumentSnapshot documentSnapshot: task.getResult())
                        {
                            Model model = new Model(documentSnapshot.getString("id"),documentSnapshot.getString("username"),documentSnapshot.getString("usermail"));
                            modelList.add(model);
                        }

                        customAdapter = new CustomAdapter(Main2Activity.this,modelList);
                        recyclerView.setAdapter(customAdapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(Main2Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
    }

public void deleteData(int index)
{

    db.collection("DATA").document(modelList.get(index).getId())
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(Main2Activity.this,"Data Deleted Successfully",Toast.LENGTH_LONG).show();
                    showData();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(Main2Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
}
}
