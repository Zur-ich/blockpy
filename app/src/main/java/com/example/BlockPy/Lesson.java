package com.example.BlockPy;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Lesson implements Serializable {
    private String id; private String title; private int iconResourceId;
    private String conceptDescription; private int instructionAudioId;
    private int backgroundColor; private String successMessage;
    private int successSoundId; private List<Question> questions;

    public Lesson(String id,String t,int iRId,String cD,int iAId,int bC,String sM,int sSId,List<Question> q){
        this.id=id;
        this.title=t;
        this.iconResourceId=iRId;
        this.conceptDescription=cD;
        this.instructionAudioId=iAId;
        this.backgroundColor=bC;
        this.successMessage=sM;
        this.successSoundId=sSId;
        this.questions=(q!=null)?new ArrayList<>(q):new ArrayList<>();
    }
    // Getters...
    public String getId() {return id;}
    public String getTitle() {return title;}
    public int getIconResourceId(){return iconResourceId;}
    public String getConceptDescription(){return conceptDescription;}
    public int getInstructionAudioId(){return instructionAudioId;}
    public int getBackgroundColor(){return backgroundColor;}
    public String getSuccessMessage(){return successMessage;}
    public int getSuccessSoundId(){return successSoundId;}
    public List<Question> getQuestions(){return questions;}
}