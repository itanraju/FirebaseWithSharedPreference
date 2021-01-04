package com.example.firebasetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView name,address,jobtitle,gender;
    Button getData;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref=db.collection("user").document("DGQ9MzQaCeX3Doy8tvfa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        jobtitle=findViewById(R.id.jobtitle);
        gender=findViewById(R.id.gender);
        getData=findViewById(R.id.getData);


        SharedPreferences getsharedData=getSharedPreferences("Demo",MODE_PRIVATE);
        String spName=getsharedData.getString("name","No Data Found");
        String spAddress=getsharedData.getString("address","No Data Found");
        String spJobTitle=getsharedData.getString("jobtitle","No Data Found");
        String spgender=getsharedData.getString("gender","No Data Found");

        name.setText(spName);
        address.setText(spAddress);
        jobtitle.setText(spJobTitle);
        gender.setText(spgender);


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               getUserData();

            }
        });

    }
    public void getUserData()
    {
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

                    SharedPreferences sharedPreferences=getSharedPreferences("Demo",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("name",sName);
                    editor.putString("address",sAddress);
                    editor.putString("jobtitle",sJobTitle);
                    editor.putString("gender",sGender);

                    editor.apply();

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