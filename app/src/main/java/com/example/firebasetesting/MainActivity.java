package com.example.firebasetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView name, address, jobtitle, gender;
    Button getData,addData;
    EditText edName,edAddress,edJobtitle,edGender;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref = db.collection("user").document("DGQ9MzQaCeX3Doy8tvfa");
    DocumentReference add=db.collection("user").document();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        jobtitle = findViewById(R.id.jobtitle);
        gender = findViewById(R.id.gender);
        getData = findViewById(R.id.getData);
        addData=findViewById(R.id.addData);

        edName=findViewById(R.id.edName);
        edAddress=findViewById(R.id.edaddress);
        edJobtitle=findViewById(R.id.edjobtitile);
        edGender=findViewById(R.id.edgender);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName=edName.getText().toString().trim();
                String sAddress=edAddress.getText().toString().trim();
                String sJobtitle=edJobtitle.getText().toString().trim();
                String sGender=edGender.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                user.put("Name",sName);
                user.put("Address", sAddress);
                user.put("Job Title",sJobtitle);
                user.put("Gender",sGender);

                add.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        getDatafromSharedPreference();

        refreshing();

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserData();

            }
        });

    }

    public void getDatafromSharedPreference()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            SharedPreferences getsharedData = getSharedPreferences("Demo", MODE_PRIVATE);
            String spName = getsharedData.getString("name", "No Data Found");
            String spAddress = getsharedData.getString("address", "No Data Found");
            String spJobTitle = getsharedData.getString("jobtitle", "No Data Found");
            String spgender = getsharedData.getString("gender", "No Data Found");

            name.setText(spName);
            address.setText(spAddress);
            jobtitle.setText(spJobTitle);
            gender.setText(spgender);

        } else {
            Toast.makeText(this, "Make sure Your Data is On", Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshing()
    {
        (
                new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                getDatafromSharedPreference();

                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                refreshing();

            }
        }, 5000);
    }


    public void getUserData()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


            CollectionReference collectionReference=db.collection("user");

            collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
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

                }
            });


        }
        else
        {
            Toast.makeText(MainActivity.this, "Make sure your internet is on", Toast.LENGTH_SHORT).show();
        }

    }

}