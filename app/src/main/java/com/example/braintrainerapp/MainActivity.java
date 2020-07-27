package com.example.braintrainerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    public Button playBtn;
    public GridLayout answersGrid;
    public TextView scoreTxt;
    public TextView questionTxt;
    public TextView timerTxt;
    public TextView resTxt;
    public TextView finalScoreTxt;
    int answer;
    int step; // for question (+ or -)
    int score;
    public void setNulls(){
        setNumInBtn(-1,1);
        setNumInBtn(-1,2);
        setNumInBtn(-1,3);
        setNumInBtn(-1,4);
    }
    public void answerOfPlayer(View view){
        Button curBtn = (Button) view;
        if (Integer.parseInt(curBtn.getText().toString()) == answer) {
            Log.i("Answer", "Right!");
            resTxt.setText("Right!");
            resTxt.setVisibility(View.VISIBLE);
            score++;
            scoreTxt.setText(score+"/"+step);
            setNulls();
            stepPlay();
        }
        else {
            Log.i("Answer", "Wrong!");
            resTxt.setText("Wrong!");
            resTxt.setVisibility(View.VISIBLE);

            scoreTxt.setText(score+"/"+step);
            setNulls();
            stepPlay();
        }
    }

    public void setNumInBtn(int num, int tag){ // function for setting the number into the button
        String idStr = "answer" + Integer.toString(tag);
        int id = getResources().getIdentifier(idStr,"id",this.getPackageName());
        Button ansBtn = (Button) findViewById(id);
        if (num == -1) { // for setting 'nulls'
            ansBtn.setText(Integer.toString(num));
            return;
        }
        if (ansBtn.getText().toString().equals("-1")) { // for setting the answer only into the 'empty' button
            ansBtn.setText(Integer.toString(num));
            return;
        }
        else {
            int newTag = getRandomInt(1,4);
            setNumInBtn(num,newTag);
        }

    }

    public void stepPlay(){

        // get question
        String question="";
        int x = getRandomInt(0,50);
        int y = getRandomInt(0,50);
        String sign="";
        if (step % 2 == 0) {
            sign = " + ";
            answer = x + y;
            question = Integer.toString(x) + sign + Integer.toString(y) + " = ?";
        }
        else {
            sign = " - ";
            if (x >= y) {
                question = Integer.toString(x) + sign + Integer.toString(y) + " = ?";
                answer = x - y;
            }
            else {
                question = Integer.toString(y) + sign + Integer.toString(x) + " = ?";
                answer = y - x;
            }
        }

        questionTxt.setText(question);

        // fill in answers buttons
        int tag = getRandomInt(1,4);
        int num = answer;
        setNumInBtn(num,tag); // fill in right answer
        int min = answer - 5; // for random answers
        if (min < 0) min = 0;
        for (int i = 0; i < 3; i++){ // fill in all left buttons
            tag = getRandomInt(1,4);
            num = getRandomInt(min, answer + 5);
            while(num == answer) {
                num = getRandomInt(min, answer + 5);
            }
            setNumInBtn(num,tag);
        }
        step++;



    }
    public int getRandomInt(int min, int max){ // func for random
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public void startGame (View view){
        playBtn.setVisibility(View.INVISIBLE);
        finalScoreTxt.setVisibility(View.INVISIBLE);
        answersGrid.setVisibility(View.VISIBLE);
        scoreTxt.setVisibility(View.VISIBLE);
        questionTxt.setVisibility(View.VISIBLE);
        timerTxt.setVisibility(View.VISIBLE);
        CountDownTimer timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timerTxt.setText(Integer.toString((int)l/1000) + "s");
            }

            @Override
            public void onFinish() {
                finalScoreTxt.setText(score+"/"+step);
                playBtn.setVisibility(View.VISIBLE);
                answersGrid.setVisibility(View.INVISIBLE);
                scoreTxt.setVisibility(View.INVISIBLE);
                questionTxt.setVisibility(View.INVISIBLE);
                timerTxt.setVisibility(View.INVISIBLE);
                finalScoreTxt.setVisibility(View.VISIBLE);
                resTxt.setVisibility(View.INVISIBLE);
                setNulls();
                step = 0;
                score = 0;
                scoreTxt.setText(score+"/"+step);
            }
        };
        timer.start();
        stepPlay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playBtn = (Button) findViewById(R.id.playButton);
        answersGrid = (GridLayout) findViewById(R.id.answersGridLayout);
        scoreTxt = (TextView) findViewById(R.id.scoreTextView);
        questionTxt = (TextView) findViewById(R.id.questionTextView);
        timerTxt = (TextView) findViewById(R.id.timerTextView);
        resTxt = (TextView) findViewById(R.id.resTextView);
        finalScoreTxt = (TextView) findViewById(R.id.finalScoreTextView);
        step = 0;
        score = 0;
    }
}