package com.dinocodeacademy.com;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class QuizActivity extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private ImageButton BackBtn;


    
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;


    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    public int multiplier =1;
    private int streakCounter =0;
    private Questions currentQuestions;
    private int correctAns = 0;
    private int wrongAns = 0;
    int FLAG = 0;
    int score =0;

    private TimerDialog timerDialog;
    private CorrectDialog correctDialog;
    private WrongDialog wrongDialog;
    private OnFireDialog fireDialog;
    private  Settings settings;

    private PlayAudioForAnswers playAudioForAnswers;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMillis;
    private long backPressedTime;

    private String categoryValue;
    private int levelValue;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

        loadPreferences();

        Intent intentCategoryLevel = getIntent();

        levelValue = intentCategoryLevel.getIntExtra("Level",0);
        categoryValue = intentCategoryLevel.getStringExtra("Category");


        fetchDB();
        Log.i("BUGBUG","onCreate() in QuizActivity");


        timerDialog = new TimerDialog(this);
        correctDialog = new CorrectDialog(this);
        wrongDialog = new WrongDialog(this);
        fireDialog = new OnFireDialog(this);
        playAudioForAnswers = new PlayAudioForAnswers(this);

    }


    private void setupUI(){
        textViewQuestion = findViewById(R.id.txtQuestionContainer);
        
        textViewScore = findViewById(R.id.txtScore);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        textViewCountDown = findViewById(R.id.txtViewTimer);


        
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button);

        BackBtn = findViewById(R.id.backBtn);
        settings = new Settings(this);
    }

    public void fetchDB() {
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getLevelAndCategoryQuestions(levelValue,categoryValue);
        startQuiz();
     
    }
    

     public void startQuiz() {

        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);
       
        showQuestions();   // calling showQuestion() method



         BackBtn.setOnClickListener(view -> {
             final Intent intent = new Intent(QuizActivity.this, CategoryActivity.class);
             startActivity(intent);
         });
         rbGroup.setOnCheckedChangeListener((group, checkedId) -> {



             switch (checkedId){

                 case R.id.radio_button1:

                     rb1.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                     rb2.setTextColor(Color.BLACK);
                     rb3.setTextColor(Color.BLACK);
                     rb4.setTextColor(Color.BLACK);



                     rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                     rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                 break;
                 case R.id.radio_button2:
                     rb2.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));

                     rb1.setTextColor(Color.BLACK);
                     rb3.setTextColor(Color.BLACK);
                     rb4.setTextColor(Color.BLACK);



                     rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                     rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                     break;

                 case R.id.radio_button3:
                     rb3.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                     rb2.setTextColor(Color.BLACK);
                     rb1.setTextColor(Color.BLACK);
                     rb4.setTextColor(Color.BLACK);


                     rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                     rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                     break;

                 case R.id.radio_button4:
                     rb4.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
                     rb2.setTextColor(Color.BLACK);
                     rb3.setTextColor(Color.BLACK);
                     rb1.setTextColor(Color.BLACK);



                     rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_answer_selected));
                     rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
                     rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

                     break;
             }

         });

         buttonConfirmNext.setOnClickListener(v -> {
                 if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {

                     quizOperation();
                 } else {

                     Toast.makeText(QuizActivity.this, "Please Select the Answer ", Toast.LENGTH_SHORT).show();
                 }
         });

    }

    @SuppressLint("SetTextI18n")
    public void showQuestions() {


        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.option_default_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);


        if (questionCounter < questionTotalCount) {
            currentQuestions = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestions.getQuestion());
            rb1.setText(currentQuestions.getOption1());
            rb2.setText(currentQuestions.getOption2());
            rb3.setText(currentQuestions.getOption3());
            rb4.setText(currentQuestions.getOption4());
            questionCounter++;
            buttonConfirmNext.setText("Confirm");
            textViewQuestionCount.setText("Questions: " + questionCounter + "/" + questionTotalCount);


            timeleftinMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();

        } else {

            // If Number of Questions Finishes then we need to finish the Quiz and Shows the User Quiz Performance


            Toast.makeText(this, "Quiz Finshed", Toast.LENGTH_SHORT).show();

            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonConfirmNext.setClickable(false);

            handler.postDelayed(() -> showResultDialog(score), 2000);
        }
    }

    private void quizOperation() {

        countDownTimer.cancel();

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);

    }


    @SuppressLint("SetTextI18n")
    private void checkSolution(int answerNr, RadioButton rbselected) {
        boolean answeredRight = false;

        switch (currentQuestions.getAnswerNr()) {
            case 1:
                if (currentQuestions.getAnswerNr() == answerNr) {
                    changeToCorrectColor(rbselected);
                    answeredRight = true;
                    streakCounter ++;
                } else {
                    multiplier =1;
                    streakCounter =0;
                    changetoIncorrectColor(rbselected);
                    String correctAnswer = (String) rb1.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                }
                break;
            case 2:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    changeToCorrectColor(rbselected);
                    answeredRight = true;
                    streakCounter ++;

                } else {
                    multiplier =1;
                    streakCounter =0;
                    changetoIncorrectColor(rbselected);
                    String correctAnswer = (String) rb2.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);
                }
                break;
            case 3:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    changeToCorrectColor(rbselected);
                    answeredRight = true;
                    streakCounter ++;


                } else {
                    multiplier =1;
                    streakCounter =0;
                    changetoIncorrectColor(rbselected);
                    String correctAnswer = (String) rb3.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                }

                break;
            case 4:
                if (currentQuestions.getAnswerNr() == answerNr) {

                    changeToCorrectColor(rbselected);
                    answeredRight = true;
                    streakCounter ++;

                } else {
                    streakCounter =0;
                    multiplier =1;
                    changetoIncorrectColor(rbselected);
                    String correctAnswer = (String) rb4.getText();
                    wrongDialog.wrongDialog(correctAnswer,this);

                }
                break;
        }

        if(answeredRight){
            FLAG = 1;
            correctAns++;
            score += 10 * multiplier;
            textViewScore.setText("Score: " + score);
            correctDialog.correctDialog(score,this, streakCounter, fireDialog, questionCounter, questionTotalCount);
        }
        else{
            FLAG = 2;
            wrongAns ++;
        }

        if(settings.getSoundState().equals(settings.PREFERRENCE_SOUND_ON))
        {playAudioForAnswers.setAudioforAnswer(FLAG);}

        if (questionCounter == questionTotalCount) {
            buttonConfirmNext.setText("Confirm and Finish");
        }
    }

    void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));

        rbselected.setTextColor(Color.BLACK);
    }
    void changeToCorrectColor(RadioButton rbselected){
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
        rbselected.setTextColor(Color.BLACK);
    }



                 //  the timer code

    private void startCountDown(){

        countDownTimer = new CountDownTimer(timeleftinMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinMillis = millisUntilFinished;

                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeleftinMillis = 0;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText(){

     int minutes = (int) (timeleftinMillis/1000) /60;
     int seconds = (int) (timeleftinMillis/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);// time format
        textViewCountDown.setText(timeFormatted);

     if (timeleftinMillis < 10000){ // if less than 10 seconds left
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.red));// change color to red
         FLAG = 3;
         playAudioForAnswers.setAudioforAnswer(FLAG); // play ticking sound

     }else {
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.black));
     }
     if (timeleftinMillis == 0 ){// time is up
         Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();
         handler.postDelayed(() -> timerDialog.timerDialog(),2000);
     }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("BUGBUG","onRestart() in QuizActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG","onStop() in QuizActivity");
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("BUGBUG","onPause() in QuizActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("BUGBUG","onResume() in QuizActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("BUGBUG","onStart() in QuizActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        Log.i("BUGBUG","onDestroy() in QuizActivity");


    }


    private void showResultDialog(int score){
        if(correctAns == questionList.size()){
            Intent resultData = new Intent(QuizActivity.this, FinalDialog100.class);
            resultData.putExtra("UserScore",score);
            resultData.putExtra("TotalQuestion",questionTotalCount);
            resultData.putExtra("CorrectQues",correctAns);
            resultData.putExtra("WrongQues",wrongAns);
            resultData.putExtra("Category", categoryValue.toString());
            resultData.putExtra("Level", levelValue);
            startActivity(resultData);
        }
        else if (correctAns >= questionList.size()*0.7){
            Intent resultData = new Intent(QuizActivity.this, FinalDialog70.class);
            resultData.putExtra("UserScore",score);
            resultData.putExtra("TotalQuestion",questionTotalCount);
            resultData.putExtra("CorrectQues",correctAns);
            resultData.putExtra("WrongQues",wrongAns);
            resultData.putExtra("Category", categoryValue);
            resultData.putExtra("Level", levelValue);
            startActivity(resultData);
        }
        else if(correctAns >= questionList.size()*0.5){
            Intent resultData = new Intent(QuizActivity.this, FinalDialog50.class);
            resultData.putExtra("UserScore",score);
            resultData.putExtra("TotalQuestion",questionTotalCount);
            resultData.putExtra("CorrectQues",correctAns);
            resultData.putExtra("WrongQues",wrongAns);
            resultData.putExtra("Category", categoryValue);
            resultData.putExtra("Level", levelValue);
            startActivity(resultData);
        }
        else {
            Intent resultData = new Intent(QuizActivity.this, FinalDialog20.class);
            resultData.putExtra("UserScore", score);
            resultData.putExtra("TotalQuestion", questionTotalCount);
            resultData.putExtra("CorrectQues", correctAns);
            resultData.putExtra("WrongQues", wrongAns);
            resultData.putExtra("Category", categoryValue);
            resultData.putExtra("Level", levelValue);
            startActivity(resultData);

        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){

            Intent intent = new Intent(QuizActivity.this,CategoryActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }
    private void loadPreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences(settings.PREFERRENCE, MODE_PRIVATE);
        String sound = sharedPreferences.getString(settings.SOUND_STATE,settings.PREFERRENCE_SOUND_ON);
        settings.setSoundState(sound);
    }
}
