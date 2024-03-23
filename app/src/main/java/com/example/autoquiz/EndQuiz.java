package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndQuiz extends AppCompatActivity {

    TextView score;
    Button back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);
        score = findViewById(R.id.score);
        back = findViewById(R.id.back);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int s = extras.getInt("score");
            score.setText(String.valueOf(s));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndQuiz.this, Hub.class);
                startActivity(intent);
                finish();
            }
        });
    }
}