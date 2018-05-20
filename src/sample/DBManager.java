package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    Connection con;
    ArrayList<Professor> professors;
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
            String profName=project.getProf();
            int id =findIdByName(profName);
            statement.setInt(19,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            ResultSet answersSet =  con.createStatement().executeQuery("select * from answers");
            while(answersSet.next()){
                int q_id= answersSet.getInt(3);
                if (q_id==id){
                    answers.add(answersSet.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return answers;
    }

}
