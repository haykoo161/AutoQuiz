package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    String[] questions = {
            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
    };
    String[][] answers = {
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
           },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
           },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
           },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            },
            {
                    "", "", "", "", "",
            }
    };
    int[] numbers = new int[20];
    private void addNumber(int a){
        for (int i = 0; i < 20; ++i) {
            if(numbers[i] == -1){
                numbers[i] = a;
                break;
            }
        }
    }


    private boolean checkNumber(int a){
        for (int i: numbers) {
            if(i == a){
                return false;
            }
        }return true;

    }


    TextView question;
    Button btn1, btn2, btn3, btn4, btn5;
    String ans;

    TextView point, live, time;
    CountDownTimer start;
    FirebaseDatabase database;

    FirebaseFirestore fStore;
    FirebaseFirestore dbroot;



    int points = 0;
    int lives = 2;
    int isans1, isans2, isans3, isans4;

    private void changeQuestion(int a){
        question.setText(questions[a]);
        btn1.setText(answers[a][0]);
        btn2.setText(answers[a][1]);
        btn3.setText(answers[a][2]);
        btn4.setText(answers[a][3]);
        ans = answers[a][4];
        point.setText(String.valueOf(points));
        live.setText(String.valueOf(lives));
    }

    private void nextQuestion(){
        if(points == 20 || ((points + lives) == 20 && lives == 1) ){
            gameOver();
            return;
        }
        Random random = new Random();
        int a = random.nextInt(20);
        if (checkNumber(a)){
            addNumber(a);
            changeQuestion(a);
        }else{
            nextQuestion();
        }
        return;
    }

    private void gameOver(){
        start.cancel();
        Intent intent = new Intent(Quiz.this, EndQuiz.class);
        intent.putExtra("score", points);
        startActivity(intent);
        finish();
    }
    private void setAnsColor(){
        if(btn1.getText().toString().equals(ans)){
            setRAnsColor(btn1);
        }
        else if(btn2.getText().toString().equals(ans)){
            setRAnsColor(btn2);
        }
        else if(btn3.getText().toString().equals(ans)){
            setRAnsColor(btn3);
        }
        else if(btn4.getText().toString().equals(ans)){
            setRAnsColor(btn4);
        }
    }

    private void setRAnsColor(Button btn){
        Handler handler1 = new Handler();
        Runnable x1 = new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundColor(Color.parseColor("#00FF0A"));
                btn1.setClickable(false);
                btn2.setClickable(false);
                btn3.setClickable(false);
                btn4.setClickable(false);
                btn5.setClickable(false);
            }
        };

        handler1.post(x1);
        Handler handler = new Handler();
        Runnable x = new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundColor(Color.parseColor("#008BCA"));
                btn1.setClickable(true);
                btn2.setClickable(true);
                btn3.setClickable(true);
                btn4.setClickable(true);
                btn5.setClickable(true);
                nextQuestion();
            }
        };
        handler.postDelayed(x, 1000);
    }

    private void setWAnsColor(Button btn){
        Handler handler1 = new Handler();
        Runnable x1 = new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundColor(Color.parseColor("#FF0000"));
            }
        };

        handler1.post(x1);
        Handler handler = new Handler();
        Runnable x = new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundColor(Color.parseColor("#008BCA"));

            }
        };

        handler.postDelayed(x, 1000);
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
        btn5 = findViewById(R.id.btn5);
        point = findViewById(R.id.points123);
        live = findViewById(R.id.lives123);
        time = findViewById(R.id.time123);
        database = FirebaseDatabase.getInstance();
        fStore = FirebaseFirestore.getInstance();
        dbroot = FirebaseFirestore.getInstance();
        fetchdata();
        nextQuestion();

        start = new CountDownTimer(120000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                if (l > 60000) {
                    int m = (int) (l / 60000);
                    int s = (int) (l / 1000 - (l / 60000) * 60);
                    if (s > 9) {
                        time.setText(m + ":" + s);
                    } else {
                        time.setText(m + ":0" + s);
                    }
                } else if (l < 60000) {
                    if (l > 10000) {
                        time.setText("0:" + l / 1000);
                    } else {
                        time.setText("0:0" + l / 1000);
                    }
                }
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setBackgroundColor(Color.parseColor("#0021DA"));
                btn2.setBackgroundColor(Color.parseColor("#008BCA"));
                btn3.setBackgroundColor(Color.parseColor("#008BCA"));
                btn4.setBackgroundColor(Color.parseColor("#008BCA"));
                isans1 = 1;
                isans2 = 0;
                isans3 = 0;
                isans4 = 0;
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundColor(Color.parseColor("#0021DA"));
                btn1.setBackgroundColor(Color.parseColor("#008BCA"));
                btn3.setBackgroundColor(Color.parseColor("#008BCA"));
                btn4.setBackgroundColor(Color.parseColor("#008BCA"));
                isans1 = 0;
                isans2 = 1;
                isans3 = 0;
                isans4 = 0;
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackgroundColor(Color.parseColor("#0021DA"));
                btn2.setBackgroundColor(Color.parseColor("#008BCA"));
                btn1.setBackgroundColor(Color.parseColor("#008BCA"));
                btn4.setBackgroundColor(Color.parseColor("#008BCA"));
                isans1 = 0;
                isans2 = 0;
                isans3 = 1;
                isans4 = 0;
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setBackgroundColor(Color.parseColor("#0021DA"));
                btn2.setBackgroundColor(Color.parseColor("#008BCA"));
                btn3.setBackgroundColor(Color.parseColor("#008BCA"));
                btn1.setBackgroundColor(Color.parseColor("#008BCA"));
                isans1 = 0;
                isans2 = 0;
                isans3 = 0;
                isans4 = 1;
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isans1 == 1){
                    if(btn1.getText().toString().equals(ans)){
                        points++;
                        setRAnsColor(btn1);
                    }
                    else{
                        lives--;
                        live.setText(String.valueOf(lives));
                        setWAnsColor(btn1);
                        setAnsColor();
                    }
                    if(checkLives()){
                        gameOver();
                    }
                }
                if(isans2 == 1){
                    if(btn2.getText().toString().equals(ans)){
                        points++;
                        setRAnsColor(btn2);
                    }
                    else{
                        lives--;
                        live.setText(String.valueOf(lives));
                        setWAnsColor(btn2);
                        setAnsColor();
                    }
                    if(checkLives()){
                        gameOver();
                    }
                }
                if(isans3 == 1){
                    if(btn3.getText().toString().equals(ans)){
                        points++;
                        setRAnsColor(btn3);
                    }
                    else{
                        lives--;
                        live.setText(String.valueOf(lives));
                        setWAnsColor(btn3);
                        setAnsColor();
                    }
                    if(checkLives()){
                        gameOver();
                    }
                }
                if(isans4 == 1){
                    if(btn4.getText().toString().equals(ans)){
                        points++;
                        setRAnsColor(btn4);
                    }
                    else{
                        lives--;
                        live.setText(String.valueOf(lives));
                        setWAnsColor(btn4);
                        setAnsColor();
                    }
                    if(checkLives()){
                        gameOver();
                    }
                }
                btn1.setBackgroundColor(Color.parseColor("#008BCA"));
                btn2.setBackgroundColor(Color.parseColor("#008BCA"));
                btn3.setBackgroundColor(Color.parseColor("#008BCA"));
                btn4.setBackgroundColor(Color.parseColor("#008BCA"));
                isans1 = 0;
                isans2 = 0;
                isans3 = 0;
                isans4 = 0;
            }
        });
    }
    @Override
    protected void onDestroy() {
        start.cancel();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        start.cancel();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        start.cancel();
        super.onStop();
    }
    public void fetchdata(){
        DocumentReference document = dbroot.collection("quiz1lvlquestions").document("question1");
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[0] = documentSnapshot.getString("question");
                    answers[0][0] = documentSnapshot.getString("a");
                    answers[0][1] = documentSnapshot.getString("b");
                    answers[0][2] = documentSnapshot.getString("c");
                    answers[0][3] = documentSnapshot.getString("d");
                    answers[0][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document1 = dbroot.collection("quiz1lvlquestions").document("question2");
        document1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[1] = documentSnapshot.getString("question");
                    answers[1][0] = documentSnapshot.getString("a");
                    answers[1][1] = documentSnapshot.getString("b");
                    answers[1][2] = documentSnapshot.getString("c");
                    answers[1][3] = documentSnapshot.getString("d");
                    answers[1][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document2 = dbroot.collection("quiz1lvlquestions").document("question3");
        document2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[2] = documentSnapshot.getString("question");
                    answers[2][0] = documentSnapshot.getString("a");
                    answers[2][1] = documentSnapshot.getString("b");
                    answers[2][2] = documentSnapshot.getString("c");
                    answers[2][3] = documentSnapshot.getString("d");
                    answers[2][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document3 = dbroot.collection("quiz1lvlquestions").document("question4");
        document3.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[3] = documentSnapshot.getString("question");
                    answers[3][0] = documentSnapshot.getString("a");
                    answers[3][1] = documentSnapshot.getString("b");
                    answers[3][2] = documentSnapshot.getString("c");
                    answers[3][3] = documentSnapshot.getString("d");
                    answers[3][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document4 = dbroot.collection("quiz1lvlquestions").document("question5");
        document4.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[4] = documentSnapshot.getString("question");
                    answers[4][0] = documentSnapshot.getString("a");
                    answers[4][1] = documentSnapshot.getString("b");
                    answers[4][2] = documentSnapshot.getString("c");
                    answers[4][3] = documentSnapshot.getString("d");
                    answers[4][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document5 = dbroot.collection("quiz1lvlquestions").document("question6");
        document5.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[5] = documentSnapshot.getString("question");
                    answers[5][0] = documentSnapshot.getString("a");
                    answers[5][1] = documentSnapshot.getString("b");
                    answers[5][2] = documentSnapshot.getString("c");
                    answers[5][3] = documentSnapshot.getString("d");
                    answers[5][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document6 = dbroot.collection("quiz1lvlquestions").document("question7");
        document6.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[6] = documentSnapshot.getString("question");
                    answers[6][0] = documentSnapshot.getString("a");
                    answers[6][1] = documentSnapshot.getString("b");
                    answers[6][2] = documentSnapshot.getString("c");
                    answers[6][3] = documentSnapshot.getString("d");
                    answers[6][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document7 = dbroot.collection("quiz1lvlquestions").document("question8");
        document7.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[7] = documentSnapshot.getString("question");
                    answers[7][0] = documentSnapshot.getString("a");
                    answers[7][1] = documentSnapshot.getString("b");
                    answers[7][2] = documentSnapshot.getString("c");
                    answers[7][3] = documentSnapshot.getString("d");
                    answers[7][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document8 = dbroot.collection("quiz1lvlquestions").document("question9");
        document8.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[8] = documentSnapshot.getString("question");
                    answers[8][0] = documentSnapshot.getString("a");
                    answers[8][1] = documentSnapshot.getString("b");
                    answers[8][2] = documentSnapshot.getString("c");
                    answers[8][3] = documentSnapshot.getString("d");
                    answers[8][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document9 = dbroot.collection("quiz1lvlquestions").document("question10");
        document9.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[9] = documentSnapshot.getString("question");
                    answers[9][0] = documentSnapshot.getString("a");
                    answers[9][1] = documentSnapshot.getString("b");
                    answers[9][2] = documentSnapshot.getString("c");
                    answers[9][3] = documentSnapshot.getString("d");
                    answers[9][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document10 = dbroot.collection("quiz1lvlquestions").document("question11");
        document10.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[10] = documentSnapshot.getString("question");
                    answers[10][0] = documentSnapshot.getString("a");
                    answers[10][1] = documentSnapshot.getString("b");
                    answers[10][2] = documentSnapshot.getString("c");
                    answers[10][3] = documentSnapshot.getString("d");
                    answers[10][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document11 = dbroot.collection("quiz1lvlquestions").document("question12");
        document11.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[11] = documentSnapshot.getString("question");
                    answers[11][0] = documentSnapshot.getString("a");
                    answers[11][1] = documentSnapshot.getString("b");
                    answers[11][2] = documentSnapshot.getString("c");
                    answers[11][3] = documentSnapshot.getString("d");
                    answers[11][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document12 = dbroot.collection("quiz1lvlquestions").document("question13");
        document12.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[12] = documentSnapshot.getString("question");
                    answers[12][0] = documentSnapshot.getString("a");
                    answers[12][1] = documentSnapshot.getString("b");
                    answers[12][2] = documentSnapshot.getString("c");
                    answers[12][3] = documentSnapshot.getString("d");
                    answers[12][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document13 = dbroot.collection("quiz1lvlquestions").document("question14");
        document13.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[13] = documentSnapshot.getString("question");
                    answers[13][0] = documentSnapshot.getString("a");
                    answers[13][1] = documentSnapshot.getString("b");
                    answers[13][2] = documentSnapshot.getString("c");
                    answers[13][3] = documentSnapshot.getString("d");
                    answers[13][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document14 = dbroot.collection("quiz1lvlquestions").document("question15");
        document14.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[14] = documentSnapshot.getString("question");
                    answers[14][0] = documentSnapshot.getString("a");
                    answers[14][1] = documentSnapshot.getString("b");
                    answers[14][2] = documentSnapshot.getString("c");
                    answers[14][3] = documentSnapshot.getString("d");
                    answers[14][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document15 = dbroot.collection("quiz1lvlquestions").document("question16");
        document15.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[15] = documentSnapshot.getString("question");
                    answers[15][0] = documentSnapshot.getString("a");
                    answers[15][1] = documentSnapshot.getString("b");
                    answers[15][2] = documentSnapshot.getString("c");
                    answers[15][3] = documentSnapshot.getString("d");
                    answers[15][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document16 = dbroot.collection("quiz1lvlquestions").document("question17");
        document16.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[16] = documentSnapshot.getString("question");
                    answers[16][0] = documentSnapshot.getString("a");
                    answers[16][1] = documentSnapshot.getString("b");
                    answers[16][2] = documentSnapshot.getString("c");
                    answers[16][3] = documentSnapshot.getString("d");
                    answers[16][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document17 = dbroot.collection("quiz1lvlquestions").document("question18");
        document17.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[17] = documentSnapshot.getString("question");
                    answers[17][0] = documentSnapshot.getString("a");
                    answers[17][1] = documentSnapshot.getString("b");
                    answers[17][2] = documentSnapshot.getString("c");
                    answers[17][3] = documentSnapshot.getString("d");
                    answers[17][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document18 = dbroot.collection("quiz1lvlquestions").document("question19");
        document18.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[18] = documentSnapshot.getString("question");
                    answers[18][0] = documentSnapshot.getString("a");
                    answers[18][1] = documentSnapshot.getString("b");
                    answers[18][2] = documentSnapshot.getString("c");
                    answers[18][3] = documentSnapshot.getString("d");
                    answers[18][4] = documentSnapshot.getString("answer");
                }
            }
        });
        DocumentReference document19 = dbroot.collection("quiz1lvlquestions").document("question20");
        document19.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    questions[19] = documentSnapshot.getString("question");
                    answers[19][0] = documentSnapshot.getString("a");
                    answers[19][1] = documentSnapshot.getString("b");
                    answers[19][2] = documentSnapshot.getString("c");
                    answers[19][3] = documentSnapshot.getString("d");
                    answers[19][4] = documentSnapshot.getString("answer");
                }
            }
        });
    }
}
