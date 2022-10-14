package pl.edu.pb.wi;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String QUIZ_TAG ="MainActivity";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private TextView counterTextView;
    private int Counter=0;
    private boolean answerWasShown;
    private Question[] questions=new Question[]{
            new Question(R.string.q_sys,false),
            new Question(R.string.q_version,false),
            new Question(R.string.q_history,true),
            new Question(R.string.q_open,false),
            new Question(R.string.q_four,true),
    };
    private int currentIndex = 0;
    private static final String KEY_CURRENT_INDEX="currentIndex";
    private static final int REQUEST_CODE_PROMPT=0;
    public static final String KEY_EXTRA_ANSWER="pl.edu.pb.wi.quiz.correctAnswer";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana zostałą metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null){
            currentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questionTextView=findViewById(R.id.question_text_view);
        counterTextView=findViewById(R.id.counter_text_view);

        promptButton=findViewById(R.id.answer_button);

        promptButton.setOnClickListener((v)->{
            Intent intent=new Intent( MainActivity.this, PromptActivity.class);
            boolean correctAnswer=questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
            startActivityForResult(intent,REQUEST_CODE_PROMPT);
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
                checkpoint();
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown=false;
                setNextQuestion();
            }

        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
                checkpoint();
                currentIndex = (currentIndex + 1) % questions.length;
                answerWasShown=false;
                setNextQuestion();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown=false;
                setNextQuestion();
            }
        });
        setNextQuestion();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK) {return;}
        if(requestCode ==REQUEST_CODE_PROMPT) {
            if(data==null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
    protected void onStart(){
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołana została metoda: onStart");
    }
    protected void onResume(){
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metoda: onResume");
    }
    protected void onPause(){
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metoda: onPause");
    }
    protected void onStop(){
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metoda: onStop");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołana została metoda: onDestroy");
    }
    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        }
        else{
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                Counter++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    public void checkpoint()
    {
        if(((currentIndex + 1)%questions.length)==0){
            String a="Uzyskałes "+ Counter + " pkt";
            Toast.makeText(getBaseContext(),a, Toast.LENGTH_SHORT).show();
            Counter=0;
        }
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
        counterTextView.setText(Counter+"pkt");
    }
}