package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    Connection con;
    ArrayList<Professor> professors;
    public static String[] projectTypes = {"اتمتة","ذكاء","قواعد بيانات","ويب","خوارزميات","دراسة نظام","ألعاب","اختبار نظام","أمن معلومات","اندرويد","مقارنة نظم","وسائط متعددة","تصميم نظام","محاكاة"};
    public DBManager(){

       try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        con = makeConnection();
        professors = getProfessors();

    }


    private Connection makeConnection(){
      try {
            Connection res = DriverManager.getConnection("jdbc:mysql://localhost/semProject?user=root&password=5314");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addProject(Project project){

      try {
            String query = "insert into dataset values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);

            for(int i = 1 ; i <= 3 ;i++ ){

                if(project.getDept()[i-1])
                    statement.setInt(i,1);
                else
                    statement.setInt(i,0);


            }

            for(int i = 4 ; i <= 17 ;i++ ){

                if(project.getTypes()[i-4])
                    statement.setInt(i,1);
                else
                    statement.setInt(i,0);

            }

            statement.setString(18,project.getTitle());

            String profName=project.getProf().getName();
            int id =findIdByName(profName);
            statement.setInt(19,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private int findIdByName(String name){
        int id=0;
        for(Professor professor:professors){
            if(professor.getName().equals(name)){
                id= professor.getId();
                break;
            }
        }

        return id;
    }


    public void addProfessor(Professor professor){
        try {
            String query = "insert into professors values(?,?,?);";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1,professor.getId());
            statement.setString(2,professor.getName());
            statement.setInt(3,professor.getDept());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private ArrayList<Professor> getProfessors(){
        ArrayList<Professor> professors =new ArrayList<>();

        try {
            ResultSet resultSet= con.createStatement().executeQuery("select * from professors");
            int id;
            String name;
            int dept;
            while (resultSet.next()){
                id =resultSet.getInt(1);
                name= resultSet.getString(2);
                dept= resultSet.getInt(3);
                professors.add(new Professor(id,name,dept));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return professors;
    }

    public void readFromDB(){

        try {
            ResultSet resultSet = con.createStatement().executeQuery("select * from dataset");


            while(resultSet.next()){
                System.out.println(resultSet.getString(19));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




    public ArrayList<Questions> getQuestions(){
        ArrayList<Questions> questions= new ArrayList<>();
        try{
            ResultSet questionsSet = con.createStatement().executeQuery("select * from questions");
            int id;
            String question;
            ArrayList<String> answers;
            int type;
            while(questionsSet.next()){
                 id = questionsSet.getInt(1);
                 question = questionsSet.getString(2);
                 answers = findAnswersById(id);
                 type = questionsSet.getInt(3);
                 questions.add(new Questions(id,question,answers,type));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return questions;
    }

    private ArrayList<String> findAnswersById(int id){
        ArrayList<String> answers= new ArrayList<>();

        try {
            ResultSet answersSet =  con.createStatement().executeQuery("select answer from answers where q_id="+id);
            while(answersSet.next()){
                answers.add(answersSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return answers;
    }


    public void addQuestionAndAnswer(Questions question){

        try{
            /* ADD THE QUESTION*/
            String ques_query = "insert into questions(question,target_type) values(?,?);";
            PreparedStatement ques_statement = con.prepareStatement(ques_query);
            ques_statement.setString(1,question.getQuestion());
            ques_statement.setInt(2,question.getType());
            ques_statement.executeUpdate();


            /* GET ITS ID */
//            ResultSet resultSet = con.createStatement().executeQuery("select id from questions where question ="+question.getQuestion());
//            int id = resultSet.getInt(1);


            int id=0;
            ArrayList<Questions> questions =getQuestions();
            for(Questions q:questions){
                if (q.getQuestion().equals(question.getQuestion())){
                    id=q.getId();
                    break;
                }
            }



            /* ADD ITS ANSWERS*/
            String ans1_query = "insert into answers(answer,q_id) values(?,?);";
            PreparedStatement ans1_statement = con.prepareStatement(ans1_query);
            ans1_statement.setString(1,question.getAnswers().get(0));
            ans1_statement.setInt(2,id);

            String ans2_query = "insert into answers(answer,q_id) values(?,?);";
            PreparedStatement ans2_statement = con.prepareStatement(ans2_query);
            ans2_statement.setString(1,question.getAnswers().get(1));
            ans2_statement.setInt(2,id);


            ans1_statement.executeUpdate();
            ans2_statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Project> getProjects(){
        ArrayList<Project> projects =new ArrayList<>();

        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from dataset");
            ArrayList<Professor> professors =getProfessors();
            String title;
            boolean[] dept = new boolean[3];
            boolean[] types= new boolean[14];
            int profID;
            Professor professor = new Professor();
            while(resultSet.next()){
                for(int i=1;i<=3;i++){
                    if (resultSet.getInt(i)==0){
                        dept[i-1]=false;
                    }
                    else
                        dept[i-1]=true;
                }

                for (int i=4;i<=17;i++){
                    if (resultSet.getInt(i)==0){
                        types[i-4]=false;
                    }
                    else{
                        types[i-4]=true;
                    }
                }

                title= resultSet.getString(18);
                profID= resultSet.getInt(19);

                for(Professor prof:professors){
                    if (prof.getId()==profID){
                        professor =new Professor(prof);
                    }
                }

                projects.add(new Project(title,dept,types,professor));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }



        return projects;
    }




}
