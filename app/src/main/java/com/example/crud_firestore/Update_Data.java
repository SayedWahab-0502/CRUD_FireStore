package com.example.crud_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Update_Data extends AppCompatActivity {

    EditText updatename,updatemail;
    Button Update;

    ProgressDialog progressDialog;
    FirebaseFirestore db;

    String uid,uname,umail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__data);

        progressDialog= new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        updatename= findViewById(R.id.editText_update_name);
        updatemail= findViewById(R.id.editText_update_mail);

        Update= findViewById(R.id.button_update);

        final Bundle bundle=getIntent().getExtras();

        if (bundle != null) {

             uid = bundle.getString("uid");
             uname = bundle.getString("uname");
             umail = bundle.getString("umail");

             updatename.setText(uname);
             updatemail.setText(umail);

         }

            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle1 = getIntent().getExtras();

                    if (bundle1 != null) {

                        String id = uid;
                        String nme = updatename.getText().toString().trim();
                        String ml = updatemail.getText().toString().trim();

                        updateData(id, nme, ml);
                    }
                }

            });
}

    private void updateData(String id, String nme, String ml) {

        progressDialog.setTitle("Updating Data...");
        progressDialog.show();

        db.collection("DATA").document(id).update("username",nme,"usermail",ml)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        startActivity(new Intent(Update_Data.this,Main2Activity.class));
                        Toast.makeText(Update_Data.this,"Data Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Update_Data.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }
}