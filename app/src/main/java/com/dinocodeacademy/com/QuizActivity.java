package com.dinocodeacademy.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

    private Context mContext;
    private MediaPlayer mediaPlayer;

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
    private boolean answered;

    private final Handler handler = new Handler();

    private int correctAns = 0, wrongAns = 0;

    private TimerDialog timerDialog;
    private CorrectDialog correctDialog;
    private WrongDialog wrongDialog;
    private OnFireDialog fireDialog;

    private PlayAudioForAnswers playAudioForAnswers;

    int FLAG = 0;
    int score =0;


    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMillis;

    private long backPressedTime;

    private String categoryValue;
    private int levelValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setupUI();

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
       // playMusic(R.raw.theme_music);
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



         BackBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final Intent intent = new Intent(QuizActivity.this, CategoryActivity.class);
                 startActivity(intent);
             }
         });
         rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @SuppressLint("NonConstantResourceId")
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {



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

             }
         });

         buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {

                        quizOperation();
                    } else {

                        Toast.makeText(QuizActivity.this, "Please Select the Answer ", Toast.LENGTH_SHORT).show();
                    }
                }
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
            answered = false;
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

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    showResultDialog(score);

                }
            }, 2000);
        }
    }

    private void quizOperation() {
        answered = true;

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
                    rb1.setBackground(ContextCompat.getDrawable(
                            this, R.drawable.correct_option_background));
                    rb1.setTextColor(Color.BLACK);
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

                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb2.setTextColor(Color.BLACK);
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

                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb3.setTextColor(Color.BLACK);
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

                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.correct_option_background));
                    rb4.setTextColor(Color.BLACK);
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
            correctDialog.correctDialog(score,this, streakCounter, fireDialog);
        }
        else{
            FLAG = 2;
            wrongAns ++;
        }

        playAudioForAnswers.setAudioforAnswer(FLAG);
        if (questionCounter == questionTotalCount) {
            buttonConfirmNext.setText("Confirm and Finish");
        }
    }

    void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.wrong_answer_background));

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

      //  String timeFormatted = String.format(Locale.getDefault(),"02d:%02d",minutes,seconds);

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
        textViewCountDown.setText(timeFormatted);


     if (timeleftinMillis < 10000){
         
         
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.red));
         
         FLAG = 3;
         playAudioForAnswers.setAudioforAnswer(FLAG);
         
         
     }else {
         textViewCountDown.setTextColor(ContextCompat.getColor(this,R.color.black));
     }


     if (timeleftinMillis == 0 ){


         Toast.makeText(this, "Times Up!", Toast.LENGTH_SHORT).show();


         handler.postDelayed(new Runnable() {
             @Override
             public void run() {

                timerDialog.timerDialog();

             }
         },2000);



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
        //mediaPlayer.stop();
        if(correctAns == questionList.size()){
            Intent resultData = new Intent(QuizActivity.this, FinalDialog100.class);
            resultData.putExtra("UserScore",score);
            resultData.putExtra("TotalQuestion",questionTotalCount);
            resultData.putExtra("CorrectQues",correctAns);
            resultData.putExtra("WrongQues",wrongAns);
            resultData.putExtra("Category", categoryValue);
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

            Intent intent = new Intent(QuizActivity.this,PlayActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }
    private void playMusic(int audiofile) {

        mediaPlayer = MediaPlayer.create(mContext,audiofile);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });

    }
}