package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Hub extends AppCompatActivity {

    Button game1, game2;
    ImageButton logout, about;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        game1 = findViewById(R.id.game1);
        game2 = findViewById(R.id.game2);
        logout = findViewById(R.id.logout);
        about = findViewById(R.id.about);
        mAuth = FirebaseAuth.getInstance();

        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("questions");
                reference.setValue("question1");
                reference.child("question1").setValue("kkk");
                Intent intent = new Intent(Hub.this, Quiz.class);
                startActivity(intent);
            }
        });
        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hub.this, Game1.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Hub.this, Login.class);
                startActivity(intent);
                Toast.makeText(Hub.this, "Logout Successful", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hub.this, AboutGame.class);
                startActivity(intent);
            }
        });
    }
}