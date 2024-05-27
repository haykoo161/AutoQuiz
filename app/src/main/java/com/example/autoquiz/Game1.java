package com.example.autoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game1 extends AppCompatActivity {

    Drawable[] images = new Drawable[20];
    int[] numbers = new int[20];

    int currentImgNumber1, currentImgNumber2;

    @SuppressLint("UseCompatLoadingForDrawables")

    void init() {
        images[0] = getDrawable(R.drawable.astonmartinvantage);
        images[1] = getDrawable(R.drawable.audirs3sedan);
        images[2] = getDrawable(R.drawable.bentleycontinentalgt);
        images[3] = getDrawable(R.drawable.bmwm3xdrive);
        images[4] = getDrawable(R.drawable.camaross);
        images[5] = getDrawable(R.drawable.ferrari488gtb);
        images[6] = getDrawable(R.drawable.nissan);
        images[7] = getDrawable(R.drawable.subaruwrxsti);
        images[8] = getDrawable(R.drawable.teslasplaid);
        images[9] = getDrawable(R.drawable.toyotagrsupra);
        images[10] = getDrawable(R.drawable.hellcat);
        images[11] = getDrawable(R.drawable.fordmustanggt);
        images[12] = getDrawable(R.drawable.jaguarftyper);
        images[13] = getDrawable(R.drawable.lamborghinihuracan);
        images[14] = getDrawable(R.drawable.mclaren720s);
        images[15] = getDrawable(R.drawable.rrwraith);
        images[16] = getDrawable(R.drawable.toyotasumpramk4);
        images[17] = getDrawable(R.drawable.volkswagengolfr);
        images[18] = getDrawable(R.drawable.volvo);
        images[19] = getDrawable(R.drawable.carrera911s);

        numbers[0] = 503;
        numbers[1] = 400;
        numbers[2] = 626;
        numbers[3] = 503;
        numbers[4] = 455;
        numbers[5] = 661;
        numbers[6] = 565;
        numbers[7] = 310;
        numbers[8] = 1020;
        numbers[9] = 382;
        numbers[10] = 717;
        numbers[11] = 450;
        numbers[12] = 575;
        numbers[13] = 610;
        numbers[14] = 710;
        numbers[15] = 624;
        numbers[16] = 280;
        numbers[17] = 315;
        numbers[18] = 415;
        numbers[19] = 443;
    }

    TextView score, life, time;
    ImageButton imb1, imb2;
    int points = 0;
    int lives = 3;
    int count = 20;

    CountDownTimer start;
    List<Integer> order = new ArrayList<>();

    @SuppressLint({"UseCompatLoadingForDrawables", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        init();
        score = findViewById(R.id.points123);
        life = findViewById(R.id.lives123);
        time = findViewById(R.id.time123);
        imb1 = findViewById(R.id.image1);
        imb2 = findViewById(R.id.image2);

        //Initialize image buttons with images
        nextRound();

        // Set click listeners
        imb1.setOnClickListener(v -> onImageButtonClick1());
        imb2.setOnClickListener(v -> onImageButtonClick2());

        start = new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                if (lives <= 0) {
                    gameOver();
                }
                life.setText(Integer.toString(lives));
                score.setText(Integer.toString(points));
                if (l > 60000) {
                    int m = (int) (l / 60000);
                    int s = (int) (l / 1000 % 60);
                    time.setText(String.format("%d:%02d", m, s));
                } else {
                    int s = (int) (l / 1000);
                    time.setText(String.format("0:%02d", s));
                }
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    private void onImageButtonClick1() {
        if (currentImgNumber1 > currentImgNumber2) {
            points++;
            Toast.makeText(this, "Правильно " + currentImgNumber1 + " > " + currentImgNumber2, Toast.LENGTH_SHORT).show();
        } else {
            lives--;
            Toast.makeText(this, "Не правильно " + currentImgNumber1 + " < " + currentImgNumber2, Toast.LENGTH_SHORT).show();
        }
        updateUI();
        nextRound();
    }

    private void onImageButtonClick2() {
        if (currentImgNumber2 > currentImgNumber1) {
            points++;
            Toast.makeText(this, "Правильно " + currentImgNumber2 + " > " + currentImgNumber1, Toast.LENGTH_SHORT).show();
        } else {
            lives--;
            Toast.makeText(this, "Не правильно " + currentImgNumber2 + " < " + currentImgNumber1, Toast.LENGTH_SHORT).show();
        }
        updateUI();
        nextRound();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        score.setText(Integer.toString(points));
        life.setText(Integer.toString(lives));
    }

    private void nextRound() {
        // Get the next set of images
        int num1, num2;
        num1 = generateUniqueRandomNumber();
        num2 = generateUniqueRandomNumber();
        if (num1 == num2) {
            num2 = generateUniqueRandomNumber();
        }
        imb1.setImageDrawable(images[num1]);
        imb2.setImageDrawable(images[num2]);
        currentImgNumber1 = numbers[num1];
        currentImgNumber2 = numbers[num2];
    }
    private void gameOver() {
        start.cancel();
        Intent intent = new Intent(Game1.this, EndQuiz.class);
        intent.putExtra("score", points);
        startActivity(intent);
        finish();
    }

    public int generateUniqueRandomNumber() {
        int random = new Random().nextInt(20);
        return random;
    }
}