package com.example.BlockPy; // Use your package name

import android.os.Bundle; import android.util.Log; import android.view.*; import android.widget.*;
import androidx.annotation.*; import androidx.fragment.app.Fragment;

import com.example.BlockPy.R;

public class LectureFragment extends Fragment {
    private static final String TAG="LectureFragment";
    private static final String ARG_CONCEPT="conceptText";
    private static final String ARG_TITLE="lessonTitle";
    private String notesText, lessonTitle;
    private TextView titleView, notesView;
    private Button nextButton;
    public static LectureFragment newInstance(Lesson l){
        LectureFragment f=new LectureFragment();
        Bundle a=new Bundle();
        if (l!=null) {
            a.putString(ARG_CONCEPT,l.getConceptDescription());
            a.putString(ARG_TITLE,l.getTitle());
        }
        f.setArguments(a);
        return f;
    }
    @Override
    public void onCreate(@Nullable Bundle sIS){
        super.onCreate(sIS);
        if (getArguments()!=null) {
            notesText=getArguments().getString(ARG_CONCEPT,"Err");
            lessonTitle=getArguments().getString(ARG_TITLE,"Err");
        }
        else {
            notesText="Err";
            lessonTitle="Err";
        }
    }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup c, @Nullable Bundle sIS) {
        return i.inflate(com.example.BlockPy.R.layout.fragment_lecture,c,false);
    }
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle sIS) {
        super.onViewCreated(v,sIS);
        titleView=v.findViewById(R.id.lecture_concept_text);
        notesView=v.findViewById(R.id.lecture_notes_content);
        nextButton=v.findViewById(R.id.lecture_next_button);

        if(titleView==null||notesView==null||nextButton==null) {
            Log.e(TAG,"View missing");return;
        }

        titleView.setText(lessonTitle);
        notesView.setText(notesText);
        nextButton.setOnClickListener(clk->{
            if(getActivity()instanceof LessonActivity) {
                ((LessonActivity)getActivity()).proceedToQuiz();
            }
            else if(getActivity()!=null)
                getActivity().finish();
        });
    }
    
}