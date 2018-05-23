package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private DBManager dbManager;
    private ExcelReader excelReader;

    @FXML Button excelReadBut;
    @FXML TextField excelFilePathTf;
    @FXML Button addQuestionBut , addAnswerBut;
    @FXML ChoiceBox fileTypeMenu , questionTargetMenu;
    @FXML TextField questionText , answerText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public void init(){

        excelFilePathTf.setDisable(false);
        excelReadBut.setDisable(false);
        fileTypeMenu.setDisable(false);
        questionText.setDisable(false);
        addQuestionBut.setDisable(true);

        questionTargetMenu.setItems(FXCollections.observableArrayList(DBManager.projectTypes));

        fileTypeMenu.setItems(FXCollections.observableArrayList(ExcelReader.fileTypes));

        dbManager = new DBManager();
    }

    public void addQuestionButClicked(){
        addQuestion();
    }


    public void addQuestion(){

        int type = -1;

        if(questionTargetMenu.getValue() != null) {
            for (int i = 0; i < DBManager.projectTypes.length; i++) {
                if (DBManager.projectTypes[i].equals(questionTargetMenu.getValue().toString())) {
                    type = i;
                    break;
                }
            }
        }

        Questions question = new Questions(questionText.getText(),answers,type);

        dbManager.addQuestionAndAnswer(question);
        answers = new ArrayList<>();
        addAnswerBut.setDisable(false);
        addQuestionBut.setDisable(true);
        questionTargetMenu.setValue(null);
        clearAll();
    }


    public void addDataFromExcelFile(){

        excelReader = new ExcelReader(excelFilePathTf.getText(),fileTypeMenu.getValue().toString());

        if(!fileTypeMenu.getValue().toString().equals(ExcelReader.fileTypes[2])){
            ArrayList<Project> projects = excelReader.getProjects();


            for (Project p : projects){


              dbManager.addProject(p);
            }
        }else {

            ArrayList<Professor> professors = excelReader.getProfessors();

            for(Professor p : professors){
                dbManager.addProfessor(p);
            }


        }


    }

    private void clearAll(){
        questionText.setText("");
        answerText.setText("");
        excelFilePathTf.setText("");
        fileTypeMenu.setValue(null);
    }

    ArrayList<String>answers = new ArrayList<>();
    public void addAnswerButClicked(ActionEvent event) {

        if(answerText.getText().equals("")) return;
        answers.add(answerText.getText());
        answerText.setText("");

        if(answers.size() >=2){
            addAnswerBut.setDisable(true);
            addQuestionBut.setDisable(false);
        }

    }
}
