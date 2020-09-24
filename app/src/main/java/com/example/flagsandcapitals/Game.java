package com.example.flagsandcapitals;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import QuestionHandling.AnswerChecker;
import QuestionHandling.Country;
import QuestionHandling.DataRetriever;
import QuestionHandling.MakeQuestion;
import QuestionHandling.SetQuestion;
import UIEffects.WidthHeight;

public class Game extends AppCompatActivity {

    public static List<Country> data,fullData;
    private String mode;
    private MakeQuestion makeQuestion;
    ImageView flag;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    TextView right,wrong;
    LinearLayout Choices;
    ConstraintLayout Won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        mode = intent.getStringExtra("key");
        assignVars();

        new WidthHeight(this,355,40,700,60,choice1,choice2,choice3,choice4);

        makeQuestion = new MakeQuestion(mode,"Game");
        new SetQuestion(this,flag,choice1,choice2,choice3,choice4,makeQuestion);
    }

    public static int getResId(String resName) {
        try {
            Field idField = R.drawable.class.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void Next(){new Handler().postDelayed(new Runnable() {
        @Override public void run() {
            if(finished())
                return;
            makeQuestion = new MakeQuestion(mode,"Game");
            new SetQuestion(Game.this,flag,choice1,choice2,choice3,choice4,makeQuestion);
        }},1000);}

    private void assignVars(){
        DataRetriever dataRetriever = new DataRetriever(this,mode);
        data=dataRetriever.getData();
        fullData = new ArrayList<>();
        fullData.addAll(data);
        flag = findViewById(R.id.FlagImage);
        choice1 = findViewById(R.id.c1);
        choice2 = findViewById(R.id.c2);
        choice3 = findViewById(R.id.c3);
        choice4 = findViewById(R.id.c4);
        right = findViewById(R.id.rightCount);
        wrong = findViewById(R.id.wrongCount);
        Choices = findViewById(R.id.choicesLayout);
        Won = findViewById(R.id.wonLayout);
    }

    private boolean finished(){
        if(data.size()==0)
        {
            Choices.setVisibility(View.INVISIBLE);
            Won.setVisibility(View.VISIBLE);
            flag.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    public void choiceOne(View view){ new AnswerChecker(this,choice1,choice2,choice3,choice4,makeQuestion,0,right,wrong); Next();}

    public void choiceTwo(View view){ new AnswerChecker(this,choice1,choice2,choice3,choice4,makeQuestion,1,right,wrong); Next();}

    public void choiceThree(View view){ new AnswerChecker(this,choice1,choice2,choice3,choice4,makeQuestion,2,right,wrong); Next();}

    public void choiceFour(View view){ new AnswerChecker(this,choice1,choice2,choice3,choice4,makeQuestion,3,right,wrong); Next();}
}
