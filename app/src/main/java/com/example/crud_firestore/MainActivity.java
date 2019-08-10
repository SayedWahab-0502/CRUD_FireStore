package com.example.crud_firestore;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //Views
    EditText name,mail;
    Button create,retrieve;

    ProgressDialog progressDialog;
    FirebaseFirestore db;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.editText_name);
        mail=(EditText)findViewById(R.id.editText_mail);
        create=(Button)findViewById(R.id.button_save);
        retrieve=(Button)findViewById(R.id.button_retrieve);

        progressDialog= new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   //adding new data
                    String nme=name.getText().toString().trim();
                    String ml=mail.getText().toString().trim();
                    uploadData(nme,ml);
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
    }

    private void uploadData(String name,String mailid)
    {
        progressDialog.setTitle("Adding Data to FireStore");
        progressDialog.show();

        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("username",name);
        doc.put("usermail",mailid);

        db.collection("DATA").document(id).set(doc)
             .addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {

                     progressDialog.dismiss();
                     Toast.makeText(MainActivity.this,"Data Uploaded Successfully",Toast.LENGTH_LONG).show();
                 }
             })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,"Data Uploading Fail",Toast.LENGTH_LONG).show();
                    }
                });
    }
}