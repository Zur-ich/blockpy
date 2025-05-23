package com.example.BlockPy; // Use your package name

import android.os.Bundle; import android.util.Log; import android.view.*; import android.widget.*;
import androidx.annotation.*; import androidx.fragment.app.Fragment;

import com.example.BlockPy.R;

/**
 * QuizResultFragment displays the final results of a completed quiz.
 *
 * This fragment is responsible for:
 * - Showing the user's score from a completed quiz
 * - Displaying feedback based on quiz performance
 * - Providing a continue button to proceed after quiz completion
 * - Handling the transition to the next learning activity
 *
 * The fragment uses the same gradient background as other lesson and quiz screens
 * to maintain visual consistency throughout the app.
 */
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
    }
}