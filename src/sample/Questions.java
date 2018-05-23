package sample;

import java.util.ArrayList;

public class Questions {
    int id;
    String question;
    ArrayList<String> answers= new ArrayList<>();
    int type;

    public Questions(int id, String question,ArrayList<String> answers,int type){
        this.id=id;
        this.question=question;
        this.answers=answers;
        this.type=type;
    }

    public Questions(String question,ArrayList<String> answers,int type){
        this.question=question;
        this.answers=answers;
        this.type=type;
    }

/////////////////////////GETTERS//////////////////////
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getType() {
        return type;
    }

   ///////////////////////////SETTERS//////////////////////


    public void setId(int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setType(int type) {
        this.type = type;
    }
}
