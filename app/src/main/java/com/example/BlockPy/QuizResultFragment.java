package com.example.BlockPy; // Use your package name

import android.os.Bundle; import android.util.Log; import android.view.*; import android.widget.*;
import androidx.annotation.*; import androidx.fragment.app.Fragment;

import com.example.BlockPy.R;

public class QuizResultFragment extends Fragment {
    private static final String TAG = "QuizResultFragment";
    private static final String ARG_SCORE = "finalScore";
    private static final String ARG_TOTAL = "totalQuestions";

    private int score;
    private int totalQuestions;
    private TextView scoreTextView;
    private Button continueButton;

    public static QuizResultFragment newInstance(int score, int totalQuestions) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        args.putInt(ARG_TOTAL, totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(ARG_SCORE, 0);
            totalQuestions = getArguments().getInt(ARG_TOTAL, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scoreTextView = view.findViewById(R.id.result_score_text);
        continueButton = view.findViewById(R.id.result_continue_button);
        if (scoreTextView == null || continueButton == null) {
            Log.e(TAG, "Views not found!");
            return;
        }
        scoreTextView.setText("Your Score: " + score + " / " + totalQuestions);
        continueButton.setOnClickListener(clk -> {
            if (getActivity() instanceof LessonActivity) {
                ((LessonActivity) getActivity()).lessonComplete();
            } else if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }
}