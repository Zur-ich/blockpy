package com.example.BlockPy;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;

/**
 * Fragment to display sub-lesson content
 */
public class SubLessonLectureFragment extends Fragment {
    private static final String TAG = "SubLessonFragment";
    private static final String ARG_SUB_LESSON = "subLesson";

    private SubLesson subLesson;
    private TextView titleView;
    private TextView contentView;
    private Button nextButton;

    public static SubLessonLectureFragment newInstance(SubLesson subLesson) {
        SubLessonLectureFragment fragment = new SubLessonLectureFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SUB_LESSON, subLesson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subLesson = (SubLesson) getArguments().getSerializable(ARG_SUB_LESSON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lecture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = view.findViewById(R.id.lecture_concept_text);
        contentView = view.findViewById(R.id.lecture_notes_content);
        nextButton = view.findViewById(R.id.lecture_next_button);

        if (titleView == null || contentView == null || nextButton == null) {
            Log.e(TAG, "View missing");
            return;
        }

        // Set up the views with sub-lesson content
        if (subLesson != null) {
            // Show main lesson ID and sub-lesson title
            String displayTitle = subLesson.getMainLessonId() + " - " + subLesson.getTitle();
            titleView.setText(displayTitle);

            // Set the content
            contentView.setText(subLesson.getContent());

            // Handle next button click
            nextButton.setOnClickListener(v -> {
                if (getActivity() instanceof LessonActivity) {
                    ((LessonActivity) getActivity()).proceedToQuiz();
                } else if (getActivity() != null) {
                    getActivity().finish();
                }
            });
        } else {
            titleView.setText("Error: Sub-lesson not found");
            contentView.setText("Unable to load sub-lesson content.");
            nextButton.setEnabled(false);
        }
    }
}