package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Hub extends AppCompatActivity {

    Button game1, game2;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        game1 = findViewById(R.id.game1);
        game2 = findViewById(R.id.game2);

        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("questions");
                reference.setValue("question1");
                reference.child("question1").setValue("kkk");
                Intent intent = new Intent(Hub.this, Quiz.class);
                startActivity(intent);
                finish();
            }
        });



    }
}