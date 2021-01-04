package com.example.firebasetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasetesting.Model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView name,address,jobtitle,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        jobtitle=findViewById(R.id.jobtitle);
        gender=findViewById(R.id.gender);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref=db.collection("user").document("DGQ9MzQaCeX3Doy8tvfa");

        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists())
                {
                    String sName=documentSnapshot.getString("Name");
                    name.setText(sName);

                    String sAddress=documentSnapshot.getString("Address");
                    address.setText(sAddress);

                    String sJobTitle=documentSnapshot.getString("Job Title");
                    jobtitle.setText(sJobTitle);

                    String sGender=documentSnapshot.getString("Gender");
                    gender.setText(sGender);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not Exists", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}