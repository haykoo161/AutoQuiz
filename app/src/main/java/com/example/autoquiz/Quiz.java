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
            "Какой производитель автомобилей выпускает 370Z?",
            "Какой автомобильный бренд создал модель Model X, электрический кроссовер?",
            "Как называется компания, выпускающая электромобили, основанная Илоном Маском?",
            "Какой производитель автомобилей производит модели Focus и Fiesta?",
            "Как называется место, где проходят гонки автомобилей по бездорожью?",
            "Какая страна является крупнейшим производителем автомобилей?",
            "Какая страна производит автомобили под маркой Nissan?",
            "Какой тип кузова имеет автомобиль Porsche 911?",
            "Какой автомобильный бренд производит модель Range Rover?",
            "Какой тип кузова имеет автомобиль Ford Explorer?",
            "Какая компания производит автомобили с логотипом трезубца?",
            "Как называется режим, в котором автомобиль использует электродвигатель и двигатель внутреннего сгорания одновременно?",
            "Что означает аббревиатура \"RPM\"?",
            "Как называется концепция автомобильного двигателя, при которой цилиндры расположены в ряд?",
            "Кто является основателем компании Ford?",
            "Какая компания производит модели X5 и 3 Series?",
            "Какая трасса является домашней для команды Red Bull Racing?",
            "Как называется самая известная гонка Формулы-1, которая проходит ежегодно в Монако?",
            "Какая компания является основным спонсором команды Red Bull Racing?",
            "Как называется основной гонщик команды Red Bull Racing в настоящее время?"
    };
    String[][] answers = {
            {       "Honda",
                    "Subaru",
                    "Nissan",
                    "Toyota",
                    "Nissan",
            },
            {
                    "Tesla", "Nissan", "Chevrolet","Audi",
                    "Tesla",},
            {
                    "Nissan",
                    "Chevrolet",
                    "Ford",
                    "Tesla",
                    "Tesla",
            },
            {
                    "Toyota",
                    "Honda",
                    "Ford",
                    "Chevrolet",
                    "Ford",
            },
            {
                    "Драг-рейсинг (Drag Racing)",
                    "Ралли (Rally)",
                    "Кольцевая трасса (Circuit Racing)",
                    "Дрифт (Drifting)",
                    "Ралли (Rally)",
            },
            {
                    "Китай",
                    "США",
                    "Япония",
                    "Германия",
                    "Китай",
            },
            {
                    "Япония",
                    "США",
                    "Корея",
                    "Китай",
                    "Япония",
            },
            {

                    "Кабриолет",
                    "Родстер",
                    "Купе",
                    "Тарга",
                    "Купе",
            },
            {

                    "Jeep",
                    "Land Rover",
                    "Toyota",
                    "Subaru",
                    "Land Rover",
            },
            {
                    "Кроссовер",
                    "Внедорожник",
                    "Универсал",
                    "Пикап",
                    "Внедорожник",
            },
            {
                    "Maserati",
                    "Lamborghini",
                    "Ferrari",
                    "Alfa Romeo",
                    "Maserati",
            },
            {
                    "Hybrid Mode",
                    "Electric Mode",
                    "Eco Mode",
                    "Power Mode",
                    "Hybrid Mode",
            },
            {
                    "Rotations Per Mile",
                    "Revolutions Per Meter",
                    "Rounds Per Minute",
                    "Revolutions Per Minute",
                    "Revolutions Per Minute",
            },
            {
                    "V-образный",
                    "Рядный",
                    "W-образный",
                    "Роторный",
                    "Рядный",
            },
            {
                    "Henry Ford",
                    "William Ford",
                    "John Ford",
                    "Thomas Ford",
                    "Henry Ford",
            },
            {
                    "Audi",
                    "Mercedes-Benz",
                    "BMW",
                    "Volvo",
                    "BMW",
            },
            {
                    "Autodromo Nazionale Monza",
                    "Circuit de Monaco",
                    "Silverstone Circuit",
                    "Red Bull Ring",
                    "Red Bull Ring",
            },
            {
                    "Гран При Бельгии",
                    "Гран При Австралии",
                    "Гран При Монако",
                    "Гран При Сингапура",
                    "Гран При Монако",
            },
            {
                    "Mercedes-Benz",
                    "Red Bull GmbH",
                    "Ferrari",
                    "Aston Martin",
                    "Red Bull GmbH",
            },
            {
                    "Sebastian Vettel",
                    "Max Verstappen",
                    "Lewis Hamilton",
                    "Fernando Alonso",
                    "Max Verstappen",
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
    int lives = 3;
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
                btn.setBackgroundColor(Color.parseColor("#6E7379"));
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
                btn.setBackgroundColor(Color.parseColor("#6E7379"));

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
                btn1.setBackgroundColor(Color.parseColor("#FF5500"));
                btn2.setBackgroundColor(Color.parseColor("#6E7379"));
                btn3.setBackgroundColor(Color.parseColor("#6E7379"));
                btn4.setBackgroundColor(Color.parseColor("#6E7379"));
                isans1 = 1;
                isans2 = 0;
                isans3 = 0;
                isans4 = 0;
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundColor(Color.parseColor("#FF5500"));
                btn1.setBackgroundColor(Color.parseColor("#6E7379"));
                btn3.setBackgroundColor(Color.parseColor("#6E7379"));
                btn4.setBackgroundColor(Color.parseColor("#6E7379"));
                isans1 = 0;
                isans2 = 1;
                isans3 = 0;
                isans4 = 0;
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackgroundColor(Color.parseColor("#FF5500"));
                btn2.setBackgroundColor(Color.parseColor("#6E7379"));
                btn1.setBackgroundColor(Color.parseColor("#6E7379"));
                btn4.setBackgroundColor(Color.parseColor("#6E7379"));
                isans1 = 0;
                isans2 = 0;
                isans3 = 1;
                isans4 = 0;
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setBackgroundColor(Color.parseColor("#FF5500"));
                btn2.setBackgroundColor(Color.parseColor("#6E7379"));
                btn3.setBackgroundColor(Color.parseColor("#6E7379"));
                btn1.setBackgroundColor(Color.parseColor("#6E7379"));
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
                btn1.setBackgroundColor(Color.parseColor("#6E7379"));
                btn2.setBackgroundColor(Color.parseColor("#6E7379"));
                btn3.setBackgroundColor(Color.parseColor("#6E7379"));
                btn4.setBackgroundColor(Color.parseColor("#6E7379"));
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
}
