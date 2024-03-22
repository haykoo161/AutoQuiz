package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;


public class Quiz extends AppCompatActivity {

    String[] questions = new String[20];
    String[][] answers = new String[20][5];
    int[] numbers = new int[10];
    private void addNumber(int a){
        for (int i = 0; i < 10; ++i) {
            if(numbers[i] == -1){
                numbers[i] = a;
            }
        }
    }
    private boolean checkNumber(int a){
        for (int i: numbers) {
            if(i == a){
                return true;
            }
        }return false;

    }


    TextView question;
    Button btn1, btn2, btn3, btn4;
    String ans;

    TextView Score, Lives, Time;


    int points = 0;
    int lives = 2;
    private void changeQuestion(int a){
        question.setText(questions[a]);
        btn1.setText(answers[a][0]);
        btn2.setText(answers[a][1]);
        btn3.setText(answers[a][2]);
        btn4.setText(answers[a][3]);
        ans = answers[a][4];
        Score.setText(points);
        Lives.setText(lives);

    }

    private void nextQuestion(){
        Random random = new Random();
        int a = random.nextInt(20);
        if (!checkNumber(a)){
            addNumber(a);
            changeQuestion(a);
        }
    }

    private void gameOver(){

    }
    private boolean checkLives()
    {
        return lives == 0;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Arrays.fill(numbers, -1);
        question = findViewById(R.id.question);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        Score = findViewById(R.id.points);
        Lives = findViewById(R.id.lives);
        nextQuestion();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn1.getText().toString().equals(ans)){
                    points++;
                    nextQuestion();
                }else{
                    lives--;
                }
                if(checkLives()){
                    gameOver();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn2.getText().toString().equals(ans)){
                    points++;
                    nextQuestion();
                }else{
                    lives--;
                }
                if(checkLives()){
                    gameOver();
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn3.getText().toString().equals(ans)){
                    points++;
                    nextQuestion();
                }else{
                    lives--;
                }
                if(checkLives()){
                    gameOver();
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn4.getText().toString().equals(ans)){
                    points++;
                    nextQuestion();
                }else{
                    lives--;
                }
                if(checkLives()){
                    gameOver();
                }
            }
        });
    }
}
