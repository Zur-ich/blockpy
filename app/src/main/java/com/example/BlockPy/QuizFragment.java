package com.example.BlockPy; // Use your package name

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.io.Serializable;
import java.util.*;
import com.example.BlockPy.R;

public class QuizFragment extends Fragment {
    private static final String TAG="QuizFragment";
    private static final String ARG_QUESTIONS="questionsList";
    private List<Question> questions;
    private int currentQuestionIndex=0;
    private int score=0;
    private TextView questionTextView, scoreTextView;
    private LinearLayout optionsContainer;
    private Button submitButton;
    private ProgressBar progressBar;
    private int selectedAnswerIndex=-1;
    private List<CardView> optionCardViews=new ArrayList<>();

    // MediaPlayer for click sounds
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer submitSoundPlayer;
    private MediaPlayer correctSoundPlayer;
    private MediaPlayer incorrectSoundPlayer;

    public static QuizFragment newInstance(List<Question> q){
        QuizFragment f=new QuizFragment();
        Bundle a=new Bundle();
        a.putSerializable(ARG_QUESTIONS,(Serializable)q);
        f.setArguments(a);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle sIS){
        super.onCreate(sIS);
        if(getArguments()!=null){
            Object d=getArguments().getSerializable(ARG_QUESTIONS);
            if(d instanceof List){
                try {
                    questions=new ArrayList<>((List<Question>)d);
                }catch(Exception e){
                    questions=new ArrayList<>();
                }
            }else {
                questions=new ArrayList<>();
            }
        }
        if(questions==null)questions=new ArrayList<>();

        // Initialize sound players
        initSoundPlayers();
    }

    private void initSoundPlayers() {
        if (getContext() != null) {
            // Create sound players for different actions
            clickSoundPlayer = MediaPlayer.create(getContext(), R.raw.clicks);
            submitSoundPlayer = MediaPlayer.create(getContext(), R.raw.submit);
            correctSoundPlayer = MediaPlayer.create(getContext(), R.raw.correct);
            incorrectSoundPlayer = MediaPlayer.create(getContext(), R.raw.incorrect);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle sIS){
        return i.inflate(R.layout.fragment_quiz, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle sIS){
        super.onViewCreated(v,sIS);
        questionTextView=v.findViewById(R.id.quiz_question_text);
        optionsContainer=v.findViewById(R.id.quiz_options_container);
        submitButton=v.findViewById(R.id.quiz_submit_button);
        scoreTextView=v.findViewById(R.id.quiz_score_text);
        progressBar=v.findViewById(R.id.quiz_progress_bar);

        if(questionTextView==null||optionsContainer==null||submitButton==null||progressBar==null){
            if(getActivity()!=null)getActivity().finish();
            return;
        }

        if(questions!=null&&!questions.isEmpty())progressBar.setMax(questions.size());
        else progressBar.setMax(1);

        loadQuestion();

        submitButton.setOnClickListener(clk -> {
            playSound(submitSoundPlayer);
            handleSubmit();
        });
    }

    private void loadQuestion(){
        selectedAnswerIndex=-1;
        optionCardViews.clear();
        optionsContainer.removeAllViews();
        if(progressBar!=null){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)progressBar.setProgress(currentQuestionIndex,true);
            else progressBar.setProgress(currentQuestionIndex);
        }

        if(questions==null||currentQuestionIndex>=questions.size()){
            finishQuiz();
            return;
        }

        Question q=questions.get(currentQuestionIndex);
        if(q==null||q.getQuestionText()==null||q.getOptions()==null){
            currentQuestionIndex++;
            loadQuestion();
            return;
        }

        questionTextView.setText(q.getQuestionText());
        List<String> opts=q.getOptions();
        Context ctx=getContext();

        if(ctx!=null){
            for(int i=0;i<opts.size();i++){
                CardView cv=createOptionCardView(ctx,opts.get(i),i);
                optionsContainer.addView(cv);
                optionCardViews.add(cv);
            }
        }

        submitButton.setEnabled(false);
        submitButton.setVisibility(View.VISIBLE);

        /* Ensure submit shown */
        if(scoreTextView!=null)scoreTextView.setText("Score: "+score);
        Log.d(TAG,"Displayed Q"+(currentQuestionIndex+1));
    }

    @NonNull
    private CardView createOptionCardView(Context ctx, String txt, int idx){
        CardView cv=new CardView(ctx);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0,0,0,(int)(8*ctx.getResources().getDisplayMetrics().density));
        cv.setLayoutParams(p);
        cv.setRadius(12*ctx.getResources().getDisplayMetrics().density);
        cv.setCardBackgroundColor(ContextCompat.getColor(ctx,R.color.quiz_default_background));
        cv.setClickable(true);
        cv.setFocusable(true);

        TextView tv=new TextView(ctx);
        tv.setText(txt);
        tv.setTextColor(ContextCompat.getColor(ctx,R.color.dark_text_primary));
        tv.setPadding(32,32,32,32);
        tv.setTextAppearance(ctx,androidx.appcompat.R.style.TextAppearance_AppCompat_Medium);
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cv.addView(tv);

        cv.setOnClickListener(v -> {
            playSound(clickSoundPlayer);
            handleOptionSelection(idx,cv);
        });

        return cv;
    }

    private void handleOptionSelection(int idx, CardView card){
        Context ctx=getContext();
        if(ctx==null)return;

        if(selectedAnswerIndex!=-1 && selectedAnswerIndex!=idx && selectedAnswerIndex<optionCardViews.size()){
            optionCardViews.get(selectedAnswerIndex).setCardBackgroundColor(
                    ContextCompat.getColor(ctx,R.color.quiz_default_background));
        }

        card.setCardBackgroundColor(ContextCompat.getColor(ctx,R.color.quiz_selected_background));
        selectedAnswerIndex=idx;
        submitButton.setEnabled(true);
    }

    private void handleSubmit(){
        if(questions==null||currentQuestionIndex>=questions.size()||selectedAnswerIndex==-1)return;

        submitButton.setEnabled(false);
        for(CardView card:optionCardViews)card.setClickable(false);

        Question q=questions.get(currentQuestionIndex);
        if(q==null||q.getOptions()==null||q.getCorrectAnswerIndex()<0){
            finishQuiz();
            return;
        }

        int correctIndex = q.getCorrectAnswerIndex();
        CardView selCard=(selectedAnswerIndex<optionCardViews.size())?optionCardViews.get(selectedAnswerIndex):null;
        CardView correctCard=(correctIndex>=0&&correctIndex<optionCardViews.size())?optionCardViews.get(correctIndex):null;

        Context ctx=getContext();
        if(ctx!=null&&selCard!=null){
            if(selectedAnswerIndex==correctIndex){
                score++;
                playSound(correctSoundPlayer);
                Toast.makeText(ctx,"Correct!",Toast.LENGTH_SHORT).show();
                selCard.setCardBackgroundColor(ContextCompat.getColor(ctx,R.color.quiz_correct_green));
            } else {
                playSound(incorrectSoundPlayer);
                String correctTxt=(correctCard!=null&&correctIndex<q.getOptions().size())?
                        q.getOptions().get(correctIndex):"[Err]";
                Toast.makeText(ctx,"Incorrect. Correct: "+correctTxt,Toast.LENGTH_LONG).show();
                selCard.setCardBackgroundColor(ContextCompat.getColor(ctx,R.color.quiz_incorrect_red));
                if(correctCard!=null)
                    correctCard.setCardBackgroundColor(ContextCompat.getColor(ctx,R.color.quiz_correct_green));
            }
        }

        currentQuestionIndex++;
        new Handler(Looper.getMainLooper()).postDelayed(this::loadQuestion,1500); // Auto-proceed after delay
    }

    private void playSound(MediaPlayer player) {
        if (player != null) {
            try {
                // Reset player to start position and play
                player.seekTo(0);
                player.start();
            } catch (Exception e) {
                Log.e(TAG, "Error playing sound", e);
            }
        }
    }

    private void finishQuiz(){
        int total=(questions!=null)?questions.size():0;
        Log.d(TAG,"finishQuiz. Score:"+score+"/"+total);
        if(getActivity()instanceof LessonActivity){
            ((LessonActivity)getActivity()).showQuizResult(score,total);
        }else if(getActivity()!=null)
            getActivity().finish();
    }

    @Override
    public void onDestroy() {
        // Release media players to prevent memory leaks
        releaseMediaPlayers();
        super.onDestroy();
    }

    private void releaseMediaPlayers() {
        if (clickSoundPlayer != null) {
            clickSoundPlayer.release();
            clickSoundPlayer = null;
        }

        if (submitSoundPlayer != null) {
            submitSoundPlayer.release();
            submitSoundPlayer = null;
        }

        if (correctSoundPlayer != null) {
            correctSoundPlayer.release();
            correctSoundPlayer = null;
        }

        if (incorrectSoundPlayer != null) {
            incorrectSoundPlayer.release();
            incorrectSoundPlayer = null;
        }
    }
}