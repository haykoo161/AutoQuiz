package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Hub extends AppCompatActivity {

    Button game1, game2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        game1 = findViewById(R.id.game1);
        game2 = findViewById(R.id.game2);

        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hub.this, Quiz.class);
                startActivity(intent);
                finish();
            }
        });



    }
}