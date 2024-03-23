package com.example.autoquiz;
import org.json.*;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                    "Tesla",
                    "Nissan",
                    "Chevrolet",
                    "Audi",
                    "Tesla",
            },
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
    Button btn1, btn2, btn3, btn4;
    String ans;

    TextView point, live, time;
    CountDownTimer start;


    int points = 0;
    int lives = 2;
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
        Random random = new Random();
        int a = random.nextInt(20);
        if (checkNumber(a)){
            addNumber(a);
            changeQuestion(a);
        }else{
            nextQuestion();
        }
    }

    private void gameOver(){
        start.cancel();
        Intent intent = new Intent(Quiz.this, EndQuiz.class);
        intent.putExtra("score", points);
        startActivity(intent);
        finish();
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
        point = findViewById(R.id.points123);
        live = findViewById(R.id.lives123);
        time = findViewById(R.id.time123);
        nextQuestion();

        start = new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                time.setText("0:" + l / 1000);
                if ((l / 1000) < 10) time.setText("0:0" + l / 1000);
            }

            @Override
            public void onFinish() {
                Intent intent1 = new Intent(Quiz.this, EndQuiz.class);
                startActivity(intent1);
                finish();
            }
        }.start();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn1.getText().toString().equals(ans)){
                    points++;
                    nextQuestion();
                }else{
                    lives--;
                    live.setText(String.valueOf(lives));
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
                    live.setText(String.valueOf(lives));
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
                    live.setText(String.valueOf(lives));
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
                    live.setText(String.valueOf(lives));
                }
                if(checkLives()){
                    gameOver();
                }
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
